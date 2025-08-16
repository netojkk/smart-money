package com.neto.smart_money.services;

import com.neto.smart_money.domain.entities.account.Account;
import com.neto.smart_money.domain.entities.category.Category;
import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.domain.entities.lauch.Launch;
import com.neto.smart_money.domain.enums.CategoryType;
import com.neto.smart_money.dto.LaunchRequestDTO;
import com.neto.smart_money.dto.LaunchResponseDTO;
import com.neto.smart_money.dto.UpdateLaunchDTO;
import com.neto.smart_money.exceptions.custom.*;
import com.neto.smart_money.repositories.AccountRepository;
import com.neto.smart_money.repositories.CategoryRepository;
import com.neto.smart_money.repositories.ClientRepository;
import com.neto.smart_money.repositories.LaunchRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LaunchService {

    @Autowired
    private LaunchRepository launchRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Client getAuthenticated(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.clientRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    @Transactional
    public LaunchResponseDTO createLaunch(LaunchRequestDTO data){
        Client client = getAuthenticated();

        //checking that the account and category belong to the client
        Account account = this.accountRepository.findByIdAndClientId(data.account(), client.getId())
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));

        Category category = this.categoryRepository.findByIdAndClientId(data.category(), client.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

        boolean exists = this.launchRepository.existsByAccountIdAndDateAndDescriptionAndAmount(
                account.getId(),
                data.transactiondate(),
                data.description(),
                data.amount()
                );

        if (exists){
            throw new LaunchAlreadyExistsException("Launch already exists");
        }

        Launch launch = new Launch();
        launch.setDescription(data.description());
        launch.setAmount(data.amount());
        launch.setDate(data.transactiondate());
        launch.setStatus(data.status());
        launch.setAccount(account);
        launch.setCategory(category);
        launch.setClient(client);
        launch.setType(data.type());

        if (data.type().equals(CategoryType.EXPENSES)){
            account.setBalance(account.getBalance().subtract(data.amount()));
        } else if (data.type().equals(CategoryType.INCOME)){
            account.setBalance(account.getBalance().add(data.amount()));
        }

        accountRepository.save(account);
        Launch saved = launchRepository.save(launch);

        return new LaunchResponseDTO(
                saved.getId(),
                saved.getDescription(),
                saved.getAmount(),
                saved.getDate(),
                saved.getType(),
                saved.getStatus(),
                saved.getClient().getId(),
                saved.getAccount().getId(),
                saved.getCategory().getId());
    }

    public List<LaunchResponseDTO> getAllByClient(){
        Client client = getAuthenticated();

        return launchRepository.findByClientId(client.getId()).stream().map(acc -> new LaunchResponseDTO(
                        acc.getId(),
                        acc.getDescription(),
                        acc.getAmount(),
                        acc.getDate(),
                        acc.getType(),
                        acc.getStatus(),
                        client.getId(),
                        acc.getAccount().getId(),
                        acc.getCategory().getId())
                ).toList();
    }

    public LaunchResponseDTO editLauncherById(UUID launchId, UpdateLaunchDTO data){
        Client client = getAuthenticated();

        //checking that launch exists
        Launch launch = launchRepository.findById(launchId).orElseThrow(() -> new LaunchNotFoundException("Launch Not Found"));

        //checking that launch belong the client
        if (!launch.getClient().getId().equals(client.getId())){
            throw new LaunchNotFoundException("Launch Not Found");
        }

        if (data.amount().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidTransactionAmountException("The value of the transaction must be greater than zero.");
        }
        Account account = launch.getAccount();
        Category category;

        //testing the new account that was sent
        if (data.account() != null) {
            account = accountRepository.findById(data.account())
                    .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));

            launch.setAccount(account);
        }

        //testing the new account was sent
        if (data.category() != null) {
            category = categoryRepository.findById(data.category())
                    .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

            launch.setCategory(category);
        }

        //checking if the amount was CHANGED with compareTo (if 0 is equal, -1 is minus and 1 is more than the old amount
        boolean amountChanged = data.amount().compareTo(launch.getAmount()) != 0;

        //checking if the type was CHANGED
        boolean typeChanged = data.type() != null && data.type() != launch.getType();

        //calling the method to adjust the balance
        if (amountChanged || typeChanged){
            revertAccountBalance(launch);

            applyAccountBalance(account, data.amount(), data.type(), launch);
        }

        if (data.description() != null) {
            launch.setDescription(data.description());
        }

        if (data.transactiondate() != null) {
            launch.setDate(data.transactiondate());
        }

        if (data.status() != null) {
            launch.setStatus(data.status());
        }

        accountRepository.save(account);
        launchRepository.save(launch);

        return new LaunchResponseDTO(
                launch.getId(),
                launch.getDescription(),
                launch.getAmount(),
                launch.getDate(),
                launch.getType(),
                launch.getStatus(),
                launch.getClient().getId(),
                launch.getAccount().getId(),
                launch.getCategory().getId());
    }

    public void deleteById(UUID id){
        Client client = getAuthenticated();

//        boolean exists = launchRepository.existsByAccountIdAndDateAndDescriptionAndAmount();
    }

    private void revertAccountBalance(Launch launch){
        Account account = launch.getAccount();

        BigDecimal amount = launch.getAmount();
        if (launch.getType() == CategoryType.INCOME){
            account.setBalance(account.getBalance().subtract(amount));
        } else if (launch.getType() == CategoryType.EXPENSES){
            account.setBalance(account.getBalance().add(amount));
        }
    }

    private void applyAccountBalance(Account account, BigDecimal amount, CategoryType type, Launch oldLaunch){

        BigDecimal newAmount = amount != null ? amount : oldLaunch.getAmount();
        CategoryType newType = type != null ? type : oldLaunch.getType();

        if (newType == CategoryType.INCOME){
            account.setBalance(account.getBalance().add(newAmount));
        } else if (newType == CategoryType.EXPENSES) {
            account.setBalance(account.getBalance().subtract(newAmount));
        }
    }

    //I split this function to use again
//    private void adjustAccountBalance(Launch oldLaunch, UpdateLaunchDTO changedLaunch){
//        BigDecimal oldAmount = oldLaunch.getAmount();
//        CategoryType oldType = oldLaunch.getType();
//
//        BigDecimal newAmount = changedLaunch.amount() != null ? changedLaunch.amount() : oldAmount;
//        CategoryType newType = changedLaunch.type() != null ? changedLaunch.type() : oldType;
//
//        Account account = oldLaunch.getAccount();
//
//        //This block of code reverts the balance of the previous transaction, bringing it back to zero.
//        if (oldType == CategoryType.INCOME){
//            account.setBalance(account.getBalance().subtract(oldAmount));
//        } else if (oldType == CategoryType.EXPENSES){
//            account.setBalance(account.getBalance().add(oldAmount));
//        }
//
//        if (newType == CategoryType.INCOME){
//            account.setBalance(account.getBalance().add(newAmount));
//        }else if (newType == CategoryType.EXPENSES){
//            account.setBalance(account.getBalance().subtract(newAmount));
//        }
//
//    }

}

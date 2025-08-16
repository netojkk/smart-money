package com.neto.smart_money.services;

import com.neto.smart_money.domain.entities.account.Account;
import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.dto.AccountRequestDTO;
import com.neto.smart_money.dto.AccountResponseDTO;
import com.neto.smart_money.exceptions.custom.*;
import com.neto.smart_money.repositories.AccountRepository;
import com.neto.smart_money.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {

    private ClientRepository clientRepository;
    private AccountRepository accountRepository;

    private Client getAuthenticatedClient() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    public AccountResponseDTO createAccount(AccountRequestDTO data){
        Client client = getAuthenticatedClient();

        Optional<Account> search = this.accountRepository.findByNameAndClientId(data.name(), client.getId());

        if (search.isPresent()){

            throw new AccountAlreadyExistsException("Account Already exists");

        }

        Account account = new Account();
        account.setName(data.name());
        account.setBalance(data.balance());
        account.setClient(client);

        Account saved = this.accountRepository.save(account);
        return new AccountResponseDTO(saved.getId(), saved.getName(), saved.getBalance());
    }

    public List<AccountResponseDTO> getAllByClient(){
        Client client = getAuthenticatedClient();

        return accountRepository.findByClientId(client.getId()).stream()
                .map(acc -> new AccountResponseDTO(acc.getId(), acc.getName(), acc.getBalance()))
                        .toList();

    }

    @Transactional
    public AccountResponseDTO deposit(UUID accountId, BigDecimal amount){
        Client client = getAuthenticatedClient();

        Account account = this.accountRepository.findByIdAndClientId(accountId,client.getId())
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 ){
            throw new InvalidTransactionAmountException("The value of the transaction must be greater than zero.");
        }


        account.setBalance(account.getBalance().add(amount));
        Account updated = accountRepository.save(account);
        return new AccountResponseDTO(updated.getId(), updated.getName(), updated.getBalance());

    }

    @Transactional
    public AccountResponseDTO withdraw(UUID accountId, BigDecimal amount){
        Client client = getAuthenticatedClient();

        Account account = this.accountRepository.findByIdAndClientId(accountId,client.getId())
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidTransactionAmountException("The value of transaction must be greater than zero.");
        }
        if (account.getBalance().compareTo(amount) < 0){
            throw new InvalidTransactionAmountException("Insufficient balance for withdrawal.");
        }

        account.setBalance(account.getBalance().subtract(amount));
        Account updated = accountRepository.save(account);
        return new AccountResponseDTO(updated.getId(), updated.getName(), updated.getBalance());
    }

    @Transactional
    public void deleteById(UUID accountId){
        Client client = getAuthenticatedClient();

        Account account = this.accountRepository.findByIdAndClientId(accountId, client.getId()).orElseThrow(
                () -> new AccountNotFoundException("Account Not Found"));


        accountRepository.deleteById(account.getId());
    }
}

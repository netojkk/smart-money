package com.neto.smart_money.services;

import com.neto.smart_money.domain.entities.account.Account;
import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.dto.AccountRequestDTO;
import com.neto.smart_money.dto.AccountResponseDTO;
import com.neto.smart_money.exceptions.custom.AccountNotFoundException;
import com.neto.smart_money.exceptions.custom.DifferentUserException;
import com.neto.smart_money.exceptions.custom.InvalidTransactionAmountException;
import com.neto.smart_money.exceptions.custom.UserNotFoundException;
import com.neto.smart_money.repositories.AccountRepository;
import com.neto.smart_money.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {

    private ClientRepository clientRepository;
    private AccountRepository accountRepository;

    public AccountResponseDTO createAccount(AccountRequestDTO data){
        Client client = this.clientRepository.findById(data.clientId()).orElseThrow(() -> new UserNotFoundException("User Not Found!"));

        Account account = new Account();
        account.setName(data.name());
        account.setBalance(data.balance());
        account.setClient(client);

        Account saved = this.accountRepository.save(account);
        return new AccountResponseDTO(saved.getId(), saved.getName(), saved.getBalance());
    }

    public List<AccountResponseDTO> getAllByClient(UUID clientId){
        return accountRepository.findByClientId(clientId).stream()
                .map(acc -> new AccountResponseDTO(acc.getId(), acc.getName(), acc.getBalance()))
                        .toList();

    }

    public AccountResponseDTO deposit(UUID accountId, BigDecimal amount){
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 ){
            throw new InvalidTransactionAmountException("The value of the transaction must be greater than zero.");
        }
        Account account = this.accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));

        account.setBalance(account.getBalance().add(amount));
        Account updated = accountRepository.save(account);
        return new AccountResponseDTO(updated.getId(), updated.getName(), updated.getBalance());

    }

    public AccountResponseDTO withdraw(UUID accountId, BigDecimal amount){
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidTransactionAmountException("The value of transaction must be greater than zero.");
        }

        Account account = this.accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));

        if (account.getBalance().compareTo(amount) < 0){
            throw new InvalidTransactionAmountException("Insufficient balance for withdrawal.");
        }

        account.setBalance(account.getBalance().subtract(amount));
        Account updated = accountRepository.save(account);
        return new AccountResponseDTO(updated.getId(), updated.getName(), updated.getBalance());
    }
    @Transactional
    public void deleteById(UUID clientId,UUID accountId){
        Account account = this.accountRepository.findById(accountId).orElseThrow( () -> new AccountNotFoundException("Account Not Found"));

        if(!account.getClient().getId().equals(clientId)){
            throw new DifferentUserException("That account doesn't exists");
        }

        accountRepository.deleteById(account.getId());
    }
}

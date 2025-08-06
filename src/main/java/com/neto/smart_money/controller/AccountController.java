package com.neto.smart_money.controller;

import com.neto.smart_money.dto.AccountRequestDTO;
import com.neto.smart_money.dto.AccountResponseDTO;
import com.neto.smart_money.dto.TransactionDTO;
import com.neto.smart_money.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO body){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(body));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<AccountResponseDTO>> getByClient(@PathVariable UUID clientId){
        return ResponseEntity.ok(accountService.getAllByClient(clientId));
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountResponseDTO> deposit(@PathVariable UUID accountId, @RequestBody TransactionDTO body){
        return ResponseEntity.ok(accountService.deposit(accountId, body.balance()));
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<AccountResponseDTO> wihtdraw(@PathVariable UUID accountId, @RequestBody TransactionDTO body){
        return ResponseEntity.ok(accountService.withdraw(accountId, body.balance()));
    }

    @DeleteMapping("/{clientId}/{accountId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID clientId, @PathVariable UUID accountId){
        accountService.deleteById(clientId,accountId);

        return ResponseEntity.noContent().build();
    }
}

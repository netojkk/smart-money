package com.neto.smart_money.repositories;

import com.neto.smart_money.domain.entities.account.Account;
import com.neto.smart_money.domain.entities.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByClientId(UUID clientId);
    Optional<Account> findByIdAndClientId(UUID AccountId , UUID clientId);
    Optional<Account> findByNameAndClientId(String AccountName, UUID clientId);

}

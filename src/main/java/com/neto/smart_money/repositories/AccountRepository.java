package com.neto.smart_money.repositories;

import com.neto.smart_money.domain.entities.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}

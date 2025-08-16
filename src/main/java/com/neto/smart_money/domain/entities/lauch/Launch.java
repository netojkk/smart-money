package com.neto.smart_money.domain.entities.lauch;

import com.neto.smart_money.domain.entities.account.Account;
import com.neto.smart_money.domain.entities.category.Category;
import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.domain.enums.CategoryType;
import com.neto.smart_money.domain.enums.SetStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "launch")
@Getter
@Setter
@NoArgsConstructor@AllArgsConstructor
public class   Launch {

    @Id
    @GeneratedValue
    private UUID id;

    private String description;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private CategoryType type;
    @Enumerated(EnumType.STRING)
    private SetStatus status;


    //Relations
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

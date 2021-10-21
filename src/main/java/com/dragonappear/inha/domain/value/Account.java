package com.dragonappear.inha.domain.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import java.util.Objects;

import static javax.persistence.EnumType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Account {
    @Column(nullable = false)
    @Enumerated(STRING)
    private BankName bankName;

    @Column(nullable = false,unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String accountHolder;

    public Account(BankName bankName, String accountNumber, String accountHolder) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return getBankName() == account.getBankName() && Objects.equals(getAccountNumber(), account.getAccountNumber()) && Objects.equals(getAccountHolder(), account.getAccountHolder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBankName(), getAccountNumber(), getAccountHolder());
    }
}

package com.dragonappear.inha.domain.user.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import java.util.Objects;

import static javax.persistence.EnumType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Account {
    @Enumerated(STRING)
    private BankName bankName;
    private String accountNumber;

    public Account(BankName bankName, String accountNumber) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return getBankName() == account.getBankName() && Objects.equals(getAccountNumber(), account.getAccountNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBankName(), getAccountNumber());
    }
}

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
public class Card {

    @Column(nullable = false)
    @Enumerated(STRING)
    private CardCompanyName cardCompanyName;
    @Column(nullable = false)
    private String cardNumber;

    public Card(CardCompanyName cardCompanyName, String cardNumber) {
        this.cardCompanyName = cardCompanyName;
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return getCardCompanyName() == card.getCardCompanyName() && Objects.equals(getCardNumber(), card.getCardNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardCompanyName(), getCardNumber());
    }
}

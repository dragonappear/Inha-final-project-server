package com.dragonappear.inha.api.controller.user.deal.dto;

import com.dragonappear.inha.domain.value.Card;
import com.dragonappear.inha.domain.value.CardCompanyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDto {
    private Long cardId;
    private CardCompanyName cardCompanyName;
    private String cardNumber;

    public Card toEntity() {
        return new Card(this.getCardCompanyName(), this.getCardNumber());
    }
}

package com.dragonappear.inha.api.controller.selling.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class InstantSellingDto extends SellingDto{
    private Long buyingId;

    @Builder
    public InstantSellingDto(Long userId, Long itemId, BigDecimal price, Long buyingId) {
        super(userId, itemId, price);
        this.buyingId = buyingId;
    }
}

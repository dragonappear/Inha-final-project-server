package com.dragonappear.inha.api.controller.selling.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class SellingDto {
    private Long userId;
    private Long itemId;
    private Long buyingId;
    private BigDecimal price;


    public SellingDto(Long userId, Long itemId, Long buyingId, BigDecimal price) {
        this.userId = userId;
        this.itemId = itemId;
        this.buyingId = buyingId;
        this.price = price;
    }
}

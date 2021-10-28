package com.dragonappear.inha.api.controller.selling.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BidSellingDto extends SellingDto{
    private LocalDateTime endDate;

    public BidSellingDto(Long userId, Long itemId, BigDecimal price, LocalDateTime endDate) {
        super(userId, itemId, price);
        this.endDate = endDate;
    }
}

package com.dragonappear.inha.api.controller.selling.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BidSellingDto extends SellingDto{
    private LocalDateTime endDate;

    @Builder
    public BidSellingDto(Long userId, Long itemId, Long buyingId, BigDecimal price, LocalDateTime endDate) {
        super(userId, itemId, buyingId, price);
        this.endDate = endDate;
    }
}

package com.dragonappear.inha.repository.buying.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class BuyingPriceDto {
    private Long auctionitemId;
    private BigDecimal price;

    @Builder
    public BuyingPriceDto(Long auctionitemId, BigDecimal price) {
        this.auctionitemId = auctionitemId;
        this.price = price;
    }
}

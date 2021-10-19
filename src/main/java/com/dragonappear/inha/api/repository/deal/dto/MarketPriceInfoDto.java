package com.dragonappear.inha.api.repository.deal.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MarketPriceInfoDto {
    private BigDecimal amount;
    private LocalDate localDate;

    @Builder
    public MarketPriceInfoDto(BigDecimal amount, LocalDate localDate) {
        this.amount = amount;
        this.localDate = localDate;
    }
}

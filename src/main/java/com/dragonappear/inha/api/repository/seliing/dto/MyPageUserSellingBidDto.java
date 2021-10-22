package com.dragonappear.inha.api.repository.seliing.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class MyPageUserSellingBidDto {
    private String imageUrl;
    private String itemName;
    private BigDecimal amount;
    private LocalDateTime endDate;

    @Builder
    @QueryProjection
    public MyPageUserSellingBidDto(String imageUrl, String itemName, BigDecimal amount, LocalDateTime endDate) {
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.amount = amount;
        this.endDate = endDate;
    }
}

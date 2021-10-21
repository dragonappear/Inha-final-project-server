package com.dragonappear.inha.api.repository.buying.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class MyPageUserBuyingBidDto  {
    private String imageUrl;
    private String itemName;
    private BigDecimal bigDecimal;
    private LocalDateTime endDate;

    @Builder
    @QueryProjection
    public MyPageUserBuyingBidDto(String imageUrl, String itemName, BigDecimal bigDecimal, LocalDateTime endDate) {
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.bigDecimal = bigDecimal;
        this.endDate = endDate;
    }
}

package com.dragonappear.inha.api.repository.buying.dto;

import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class MyPageUserBuyingEndDto{
    private Long buyingId;
    private String imageUrl;
    private String itemName;
    private LocalDateTime purchaseTime;
    private BuyingStatus buyingStatus;

    @Builder
    @QueryProjection
    public MyPageUserBuyingEndDto(Long buyingId, String imageUrl, String itemName, LocalDateTime purchaseTime, BuyingStatus buyingStatus) {
        this.buyingId = buyingId;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.purchaseTime = purchaseTime;
        this.buyingStatus = buyingStatus;
    }
}

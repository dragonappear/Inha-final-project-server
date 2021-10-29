package com.dragonappear.inha.api.repository.buying.dto;

import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MyPageUserBuyingOngoingDto {
    private Long buyingId;
    private String imageUrl;
    private String itemName;
    private BuyingStatus dealStatus;

    @Builder
    @QueryProjection
    public MyPageUserBuyingOngoingDto(Long buyingId, String imageUrl, String itemName, BuyingStatus dealStatus) {
        this.buyingId = buyingId;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.dealStatus = dealStatus;
    }
}

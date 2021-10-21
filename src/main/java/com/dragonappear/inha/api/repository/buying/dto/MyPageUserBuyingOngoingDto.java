package com.dragonappear.inha.api.repository.buying.dto;

import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MyPageUserBuyingOngoingDto {
    private Long dealId;
    private String imageUrl;
    private String itemName;
    private DealStatus dealStatus;

    @Builder
    @QueryProjection
    public MyPageUserBuyingOngoingDto(Long dealId, String imageUrl, String itemName, DealStatus dealStatus) {
        this.dealId = dealId;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.dealStatus = dealStatus;
    }
}

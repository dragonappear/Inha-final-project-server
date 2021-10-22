package com.dragonappear.inha.api.repository.seliing.dto;

import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MyPageUserSellingOngoingDto {
    private Long dealId;
    private String imageUrl;
    private String itemName;
    private DealStatus dealStatus;

    @Builder
    @QueryProjection
    public MyPageUserSellingOngoingDto(Long dealId, String imageUrl, String itemName, DealStatus dealStatus) {
        this.dealId = dealId;
        this.imageUrl=imageUrl;
        this.itemName = itemName;
        this.dealStatus = dealStatus;
    }
}

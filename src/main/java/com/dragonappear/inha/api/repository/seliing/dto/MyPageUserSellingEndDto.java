package com.dragonappear.inha.api.repository.seliing.dto;

import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class MyPageUserSellingEndDto {
    private Long sellingId;
    private String imageUrl;
    private String itemName;
    private LocalDateTime purchaseTime;
    private SellingStatus sellingStatus;


    @Builder
    @QueryProjection
    public MyPageUserSellingEndDto(Long sellingId , String imageUrl, String itemName, LocalDateTime purchaseTime, SellingStatus sellingStatus) {
        this.sellingId = sellingId;
        this.imageUrl= imageUrl;
        this.itemName = itemName;
        this.purchaseTime = purchaseTime;
        this.sellingStatus = sellingStatus;
    }
}

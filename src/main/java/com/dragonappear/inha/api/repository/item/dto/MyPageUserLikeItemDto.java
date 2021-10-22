package com.dragonappear.inha.api.repository.item.dto;

import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class MyPageUserLikeItemDto {
    private String itemUrl;
    private ManufacturerName manufacturerName;
    private String itemName;
    private BigDecimal latestPrice;

    @Builder
    @QueryProjection
    public MyPageUserLikeItemDto(String itemUrl, ManufacturerName manufacturerName, String itemName, BigDecimal latestPrice) {
        this.itemUrl = itemUrl;
        this.manufacturerName = manufacturerName;
        this.itemName = itemName;
        this.latestPrice = latestPrice;
    }
}

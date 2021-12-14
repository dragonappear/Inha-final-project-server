package com.dragonappear.inha.api.repository.item.dto;

import com.dragonappear.inha.api.controller.auctionitem.dto.DetailItemDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class KeyboardApiDto extends DetailItemDto {
    private String length;
    private String weight;
    private String keyType;
    private String type;

    @QueryProjection
    public KeyboardApiDto(Long itemId, String manufacturer, String itemName, String modelNumber, LocalDate releaseDay, BigDecimal releasePrice, String color, Long itemLike, BigDecimal latestPrice, String length, String weight, String keyType, String type) {
        super(itemId, manufacturer, itemName, modelNumber, releaseDay, releasePrice, color, itemLike, latestPrice);
        this.length = length;
        this.weight = weight;
        this.keyType = keyType;
        this.type = type;
    }
}

package com.dragonappear.inha.api.controller.auctionitem.dto;

import com.dragonappear.inha.domain.value.Money;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@Data
public class DetailItemDto {
    private Long itemId;
    private String manufacturer;
    private String itemName;
    private String modelNumber;
    private LocalDate releaseDay;
    private BigDecimal releasePrice;
    private String color;
    private Long itemLike;
    private BigDecimal latestPrice;

    public DetailItemDto(Long itemId, String manufacturer, String itemName, String modelNumber, LocalDate releaseDay, BigDecimal releasePrice, String color, Long itemLike, BigDecimal latestPrice) {
        this.itemId = itemId;
        this.manufacturer = manufacturer;
        this.itemName = itemName;
        this.modelNumber = modelNumber;
        this.releaseDay = releaseDay;
        this.releasePrice = releasePrice;
        this.color = color;
        this.itemLike = itemLike;
        this.latestPrice = latestPrice;
    }
}

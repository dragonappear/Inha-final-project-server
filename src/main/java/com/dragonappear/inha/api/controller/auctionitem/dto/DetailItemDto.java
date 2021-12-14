package com.dragonappear.inha.api.controller.auctionitem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
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
}

package com.dragonappear.inha.api.controller.auctionitem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class DetailItemDto {
    private Long itemId;
    private List<String> fileOriginName;
    private Enum manufacturer;
    private String itemName;
    private String modelNumber;
    private LocalDate releaseDay;
    private String color;
    private Long itemLike;
    private BigDecimal latestPrice;

    public DetailItemDto(Long itemId,List<String> fileOriginName, Enum manufacturer
            , String itemName, String modelNumber, LocalDate releaseDay
            , String color, Long itemLike, BigDecimal latestPrice) {
        this.itemId = itemId;
        this.fileOriginName = fileOriginName;
        this.manufacturer = manufacturer;
        this.itemName = itemName;
        this.modelNumber = modelNumber;
        this.releaseDay = releaseDay;
        this.color = color;
        this.itemLike = itemLike;
        this.latestPrice = latestPrice;
    }
}

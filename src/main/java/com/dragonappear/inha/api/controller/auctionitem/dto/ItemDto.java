package com.dragonappear.inha.api.controller.auctionitem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemDto {
    private String fileOriginName;
    private Enum manufacturer;
    private String itemName;
    private Long itemLike;
    private BigDecimal latestPrice;

    public ItemDto(String fileOriginName, Enum manufacturer, String itemName, Long itemLike, BigDecimal latestPrice) {
        this.fileOriginName = fileOriginName;
        this.manufacturer = manufacturer;
        this.itemName = itemName;
        this.itemLike = itemLike;
        this.latestPrice = latestPrice;
    }
}

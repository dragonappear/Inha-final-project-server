package com.dragonappear.inha.api.controller.auctionitem.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemDto {
    private Long itemId;
    private String fileOriginName;
    private Enum manufacturer;
    private String itemName;
    private Long itemLike;
    private BigDecimal latestPrice;

    public ItemDto(Long itemId,String fileOriginName, Enum manufacturer, String itemName, Long itemLike, BigDecimal latestPrice) {
        this.itemId = itemId;
        this.fileOriginName = fileOriginName;
        this.manufacturer = manufacturer;
        this.itemName = itemName;
        this.itemLike = itemLike;
        this.latestPrice = latestPrice;
    }
}

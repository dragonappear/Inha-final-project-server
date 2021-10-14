package com.dragonappear.inha.api.controller.auctionitem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemDetailDto {
    private List<String> fileOriginName;
    private Enum manufacturer;
    private String itemName;
    private Long itemLike;
    private String modelNumber;
    private BigDecimal latestPrice;

}

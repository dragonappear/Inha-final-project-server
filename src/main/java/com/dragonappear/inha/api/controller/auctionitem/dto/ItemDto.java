package com.dragonappear.inha.api.controller.auctionitem.dto;

import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.value.Money;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemDto {
    private List<String> imageUrl;
    private Enum manufacturer;
    private String itemName;
    private BigDecimal latestPrice;

    public ItemDto(List<String>imageUrl, Enum manufacturer, String itemName, BigDecimal latestPrice) {
        this.imageUrl = imageUrl;
        this.manufacturer = manufacturer;
        this.itemName = itemName;
        this.latestPrice = latestPrice;
    }
}

package com.dragonappear.inha.api.repository.item.dto;

import com.dragonappear.inha.api.controller.auctionitem.dto.DetailItemDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class SmartPhoneApiDto extends DetailItemDto {
    private String inch;
    private String cpu;
    private String core;
    private String memory;
    private String storage;
    private String gpu;
    private String weight;
    private String os;
    private String apType;
    private String ppi;
    private String maxInjectionRate;

    @QueryProjection
    public SmartPhoneApiDto(Long itemId, String manufacturer, String itemName, String modelNumber, LocalDate releaseDay, BigDecimal releasePrice, String color, Long itemLike, BigDecimal latestPrice, String inch, String cpu, String core, String memory, String storage, String gpu, String weight, String os, String apType, String ppi, String maxInjectionRate) {
        super(itemId, manufacturer, itemName, modelNumber, releaseDay, releasePrice, color, itemLike, latestPrice);
        this.inch = inch;
        this.cpu = cpu;
        this.core = core;
        this.memory = memory;
        this.storage = storage;
        this.gpu = gpu;
        this.weight = weight;
        this.os = os;
        this.apType = apType;
        this.ppi = ppi;
        this.maxInjectionRate = maxInjectionRate;
    }
}

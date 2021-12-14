package com.dragonappear.inha.api.repository.item.dto;

import com.dragonappear.inha.api.controller.auctionitem.dto.DetailItemDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class TabletApiDto extends DetailItemDto {
    private String inch;
    private String cpu;
    private String core;
    private String os;
    private String memory;
    private String storage;
    private String gpu;
    private String weight;
    private String ppi;
    private String maxInjectionRate;

    @QueryProjection
    public TabletApiDto(Long itemId, String manufacturer, String itemName, String modelNumber, LocalDate releaseDay, BigDecimal releasePrice, String color, Long itemLike, BigDecimal latestPrice, String inch, String cpu, String core, String os, String memory, String storage, String gpu, String weight, String ppi, String maxInjectionRate) {
        super(itemId, manufacturer, itemName, modelNumber, releaseDay, releasePrice, color, itemLike, latestPrice);
        this.inch = inch;
        this.cpu = cpu;
        this.core = core;
        this.os = os;
        this.memory = memory;
        this.storage = storage;
        this.gpu = gpu;
        this.weight = weight;
        this.ppi = ppi;
        this.maxInjectionRate = maxInjectionRate;
    }
}

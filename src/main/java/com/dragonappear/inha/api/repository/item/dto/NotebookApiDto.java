package com.dragonappear.inha.api.repository.item.dto;


import com.dragonappear.inha.api.controller.auctionitem.dto.DetailItemDto;
import com.dragonappear.inha.domain.item.product.Notebook;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class NotebookApiDto extends DetailItemDto{
    private String inch;
    private String cpu;
    private String core;
    private String os;
    private String memory;
    private String storage;
    private String gpu;
    private String weight;

    public NotebookApiDto(Notebook item) {
        super(item.getId(),item.getManufacturer().getManufacturerName().toString(),item.getItemName(),item.getModelNumber(),item.getReleaseDay(),item.getReleasePrice().getAmount()
        ,item.getColor(),item.getLikeCount(),item.getLatestPrice().getAmount());
        this.inch = item.getInch();
        this.cpu = item.getCpu();
        this.core = item.getCore();
        this.os = item.getOs();
        this.memory = item.getMemory();
        this.storage = item.getStorage();
        this.gpu = item.getGpu();
        this.weight = item.getWeight();
    }

    @QueryProjection
    public NotebookApiDto(Long itemId, String manufacturer, String itemName, String modelNumber
            , LocalDate releaseDay, BigDecimal releasePrice, String color, Long itemLike, BigDecimal latestPrice
            , String inch, String cpu, String core, String os, String memory, String storage, String gpu, String weight) {
        super(itemId, manufacturer, itemName, modelNumber, releaseDay, releasePrice, color, itemLike, latestPrice);
        this.inch = inch;
        this.cpu = cpu;
        this.core = core;
        this.os = os;
        this.memory = memory;
        this.storage = storage;
        this.gpu = gpu;
        this.weight = weight;
    }
}

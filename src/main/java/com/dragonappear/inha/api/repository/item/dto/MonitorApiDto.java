package com.dragonappear.inha.api.repository.item.dto;

import com.dragonappear.inha.api.controller.auctionitem.dto.DetailItemDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class MonitorApiDto extends DetailItemDto {
    private String inch;
    private String displayRate;
    private String panelType;
    private String resolution;
    private Boolean dpPort;
    private Boolean hdmi;
    private String maxInjectionRate;

    @QueryProjection
    public MonitorApiDto(Long itemId, String manufacturer, String itemName, String modelNumber, LocalDate releaseDay, BigDecimal releasePrice, String color, Long itemLike, BigDecimal latestPrice, String inch, String displayRate, String panelType, String resolution, Boolean dpPort, Boolean hdmi, String maxInjectionRate) {
        super(itemId, manufacturer, itemName, modelNumber, releaseDay, releasePrice, color, itemLike, latestPrice);
        this.inch = inch;
        this.displayRate = displayRate;
        this.panelType = panelType;
        this.resolution = resolution;
        this.dpPort = dpPort;
        this.hdmi = hdmi;
        this.maxInjectionRate = maxInjectionRate;
    }
}

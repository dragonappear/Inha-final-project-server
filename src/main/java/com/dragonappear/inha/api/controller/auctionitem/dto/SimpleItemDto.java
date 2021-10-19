package com.dragonappear.inha.api.controller.auctionitem.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SimpleItemDto {
    private String modelNumber;
    private String itemName;
    private String fileOriName;

    @Builder
    public SimpleItemDto(String modelNumber, String itemName, String fileOriName) {
        this.modelNumber = modelNumber;
        this.itemName = itemName;
        this.fileOriName = fileOriName;
    }
}

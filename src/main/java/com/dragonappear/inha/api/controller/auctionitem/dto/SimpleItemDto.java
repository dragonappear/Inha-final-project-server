package com.dragonappear.inha.api.controller.auctionitem.dto;

import com.dragonappear.inha.domain.item.value.ManufacturerName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SimpleItemDto {
    private String modelNumber;
    private ManufacturerName manufacturerName;
    private String itemName;
    private String fileOriName;

    @Builder
    public SimpleItemDto(String modelNumber, ManufacturerName manufacturerName, String itemName, String fileOriName) {
        this.modelNumber = modelNumber;
        this.manufacturerName = manufacturerName;
        this.itemName = itemName;
        this.fileOriName = fileOriName;
    }
}

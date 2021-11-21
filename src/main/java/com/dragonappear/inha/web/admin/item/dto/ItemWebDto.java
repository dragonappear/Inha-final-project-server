package com.dragonappear.inha.web.admin.item.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemWebDto {
    private Long itemId;
    private String category;
    private String manufacturer;
    private String itemName;
    private String itemImageName;
}

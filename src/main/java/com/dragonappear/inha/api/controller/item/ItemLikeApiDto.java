package com.dragonappear.inha.api.controller.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemLikeApiDto {
    private Long itemId;
    private Long userId;


}

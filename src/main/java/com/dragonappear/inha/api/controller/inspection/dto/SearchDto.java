package com.dragonappear.inha.api.controller.inspection.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SearchDto {
    private Long sellingId;
    private Long buyingId;
}

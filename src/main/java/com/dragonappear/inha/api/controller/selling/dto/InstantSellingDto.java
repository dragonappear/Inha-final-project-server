package com.dragonappear.inha.api.controller.selling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstantSellingDto extends SellingDto{
    @NotNull
    private Long buyingId;

}

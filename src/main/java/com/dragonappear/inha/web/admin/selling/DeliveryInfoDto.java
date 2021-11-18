package com.dragonappear.inha.web.admin.selling;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryInfoDto {
    private String t_key;
    private String t_code;
    private String t_invoice;

}

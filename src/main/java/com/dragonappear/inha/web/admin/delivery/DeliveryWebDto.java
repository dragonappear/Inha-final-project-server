package com.dragonappear.inha.web.admin.delivery;

import com.dragonappear.inha.domain.value.CourierName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryWebDto {
    private CourierName courierName;
    private String deliveryNumber;
}

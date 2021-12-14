package com.dragonappear.inha.web.repository.dto;

import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.value.CourierName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
public class ReturnDealWebDto {
    private Long dealId;
    private Long sellingId;
    private Long addressId;
    private SellingStatus sellingStatus;
    private CourierName courierName;
    private String deliveryNumber;

    @QueryProjection
    public ReturnDealWebDto(Long dealId, Long sellingId, Long addressId, SellingStatus sellingStatus, CourierName courierName, String deliveryNumber) {
        this.dealId = dealId;
        this.sellingId = sellingId;
        this.addressId = addressId;
        this.sellingStatus = sellingStatus;
        this.courierName = courierName;
        this.deliveryNumber = deliveryNumber;
    }
}

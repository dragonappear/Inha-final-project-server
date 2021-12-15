package com.dragonappear.inha.web.repository.dto;

import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.value.CourierName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReturnDealWebDto {
    private Long dealId;
    private Long sellingId;
    private SellingStatus sellingStatus;
    private Long inspectionId;
    private CourierName courierName;
    private String deliveryNumber;

    @QueryProjection

    public ReturnDealWebDto(Long dealId, Long sellingId, SellingStatus sellingStatus, Long inspectionId, CourierName courierName, String deliveryNumber) {
        this.dealId = dealId;
        this.sellingId = sellingId;
        this.sellingStatus = sellingStatus;
        this.inspectionId = inspectionId;
        this.courierName = courierName;
        this.deliveryNumber = deliveryNumber;
    }
}

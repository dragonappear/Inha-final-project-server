package com.dragonappear.inha.web.repository.dto;

import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.value.CourierName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SendDealWebDto {
    private Long dealId;
    private Long buyingId;
    private Long addressId;
    private BuyingStatus buyingStatus;
    private Long inspectionId;
    private CourierName courierName;
    private String deliveryNumber;

    @QueryProjection
    public SendDealWebDto(Long dealId, Long buyingId, Long addressId, BuyingStatus buyingStatus, Long inspectionId, CourierName courierName, String deliveryNumber) {
        this.dealId = dealId;
        this.buyingId = buyingId;
        this.addressId = addressId;
        this.buyingStatus = buyingStatus;
        this.inspectionId = inspectionId;
        this.courierName = courierName;
        this.deliveryNumber = deliveryNumber;
    }
}

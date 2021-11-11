package com.dragonappear.inha.web.admin.payment.dto;

import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class PaymentWebDto {
    private Long paymentId;
    private Long userId;
    private LocalDateTime updateDate;
    private PaymentStatus status;
    private BigDecimal price;
    private BigDecimal point;
    private String pgName;
    private String impId;
    private String merchantId;

    @Builder
    public PaymentWebDto(Long paymentId, Long userId, LocalDateTime updateDate, PaymentStatus status, BigDecimal price, BigDecimal point, String pgName, String impId, String merchantId) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.updateDate = updateDate;
        this.status = status;
        this.price = price;
        this.point = point;
        this.pgName = pgName;
        this.impId = impId;
        this.merchantId = merchantId;
    }
}

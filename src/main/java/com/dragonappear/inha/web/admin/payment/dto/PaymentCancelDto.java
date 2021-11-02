package com.dragonappear.inha.web.admin.payment.dto;

import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class PaymentCancelDto {
    private Long paymentId;
    private LocalDateTime updateDate;
    private PaymentStatus status;
    private BigDecimal price;
    private BigDecimal point;
    private String pgName;
    private String merchantId;

    @Builder
    public PaymentCancelDto(Long paymentId, LocalDateTime updateDate, PaymentStatus status, BigDecimal price, BigDecimal point, String pgName, String merchantId) {
        this.paymentId = paymentId;
        this.updateDate = updateDate;
        this.status = status;
        this.price = price;
        this.point = point;
        this.pgName = pgName;
        this.merchantId = merchantId;
    }
}

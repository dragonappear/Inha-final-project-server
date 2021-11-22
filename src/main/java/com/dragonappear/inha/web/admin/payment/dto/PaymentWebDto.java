package com.dragonappear.inha.web.admin.payment.dto;

import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
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

}

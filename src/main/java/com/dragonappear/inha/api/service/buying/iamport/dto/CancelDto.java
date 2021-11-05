package com.dragonappear.inha.api.service.buying.iamport.dto;

import com.dragonappear.inha.api.controller.buying.dto.PaymentApiDto;
import com.dragonappear.inha.api.service.buying.iamport.IamportService;
import com.dragonappear.inha.domain.payment.Payment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CancelDto {
    private String token;
    private String reason;
    private String impId;
    private String merchantId;
    private String amount;
    private String checksum;

    @Builder
    public CancelDto(String token, String reason, String impId, String merchantId, String amount, String checksum) {
        this.token = token;
        this.reason = reason;
        this.impId = impId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.checksum = checksum;
    }

    public static CancelDto getCancelDto(Payment payment) {
        return CancelDto.builder()
                .token(IamportService.getImportToken())
                .impId(payment.getImpId())
                .merchantId(payment.getMerchantId())
                .amount(payment.getPaymentPrice().toString())
                .checksum(payment.getPaymentPrice().toString())
                .build();
    }



    public static CancelDto getCancelDto(PaymentApiDto dto) {
        return CancelDto.builder()
                .token(IamportService.getImportToken())
                .impId(dto.getImpId())
                .merchantId(dto.getMerchantId())
                .amount(dto.getPaymentPrice().toString())
                .checksum(dto.getPaymentPrice().toString())
                .build();
    }
}
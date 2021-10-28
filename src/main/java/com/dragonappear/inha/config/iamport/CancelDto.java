package com.dragonappear.inha.config.iamport;

import com.dragonappear.inha.api.controller.user.deal.dto.PaymentDto;
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

    public static CancelDto getCancelDto(PaymentDto dto) {
        return CancelDto.builder()
                .token(IamportConfig.getImportToken())
                .impId(dto.getImpId())
                .merchantId(dto.getMerchantId())
                .amount(dto.getPaymentPrice().toString())
                .checksum(dto.getPaymentPrice().toString())
                .build();
    }
}
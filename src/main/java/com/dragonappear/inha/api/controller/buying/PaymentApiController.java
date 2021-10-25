package com.dragonappear.inha.api.controller.buying;


import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.service.payment.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Api(tags = {"구매 결제 정보 저장 API"})
@RestController
@RequiredArgsConstructor
public class PaymentApiController {
    private final PaymentService paymentService;

    @ApiOperation(value = "결제 정보 저장", notes = "결제 정보 저장하기")
    @GetMapping("/payments/new/{auctionitemId}")
    public void savePayment(@PathVariable("auctionitemId") Long auctionitemId, @RequestBody PaymentDto dto) {

    }

    @NoArgsConstructor
    @Data
    static class PaymentDto {
        private String pg;
        private String pay_method;
        private String merchant_uid;
        private String itemName;
        private BigDecimal paymentAmount;
        private String buyer_email;
        private String buyer_name;
        private String buyer_tel;
        private Address buyer_address;

        @Builder
        public PaymentDto(String pg, String pay_method, String merchant_uid
                , String itemName, BigDecimal paymentAmount, String buyer_email
                , String buyer_name, String buyer_tel, Address buyer_address) {
            this.pg = pg;
            this.pay_method = pay_method;
            this.merchant_uid = merchant_uid;
            this.itemName = itemName;
            this.paymentAmount = paymentAmount;
            this.buyer_email = buyer_email;
            this.buyer_name = buyer_name;
            this.buyer_tel = buyer_tel;
            this.buyer_address = buyer_address;
        }
    }
}

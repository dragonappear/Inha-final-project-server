package com.dragonappear.inha.api.controller.payment;


import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.payment.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"결제 API"})
@RestController
@RequiredArgsConstructor
public class PaymentApiController {
    private final PaymentService paymentService;

    @ApiOperation(value = "결제 정보 저장", notes = "결제 정보 저장하기")
    @GetMapping("/payments/new")
    public void savePayment(Long auctionitemId) {

    }
}

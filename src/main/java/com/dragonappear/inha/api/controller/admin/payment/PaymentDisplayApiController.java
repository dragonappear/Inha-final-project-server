package com.dragonappear.inha.api.controller.admin.payment;

import com.dragonappear.inha.service.payment.PaymentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"관리자 결제내역 조회 API"})
@RequiredArgsConstructor
@RestController
public class PaymentDisplayApiController {
    private final PaymentService paymentService;

}

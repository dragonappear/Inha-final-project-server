package com.dragonappear.inha.api.controller.buying;

import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.controller.buying.dto.InstantPaymentDto;
import com.dragonappear.inha.api.service.deal.CreateDealService;
import com.dragonappear.inha.api.service.buying.ValidatePaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"즉시구매 API"})
@RestController
@RequiredArgsConstructor
public class BuyingInstantApiController {
    private final ValidatePaymentService validatePaymentService;
    private final CreateDealService createDealService;


    @ApiOperation(value = "즉시구매 결제저장 API", notes = "즉시구매 결제저장")
    @PostMapping("/payments/new/instant")
    public MessageDto postInstantPayment(@RequestBody InstantPaymentDto dto) {
        validatePaymentService.validateInstantPayment(dto);
        createDealService.createInstantBuying(dto);
        return MessageDto.builder()
                .message(getMessage("isPaySuccess", true, "Status", "결제가 완료되었습니다."))
                .build();
    }

}





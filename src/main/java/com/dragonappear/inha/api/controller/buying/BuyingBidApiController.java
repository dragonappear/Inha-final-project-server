package com.dragonappear.inha.api.controller.buying;

import com.dragonappear.inha.api.controller.buying.dto.BidPaymentDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.service.deal.CreateDealService;
import com.dragonappear.inha.api.service.buying.ValidatePaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"입찰구매 API"})
@RestController
@RequiredArgsConstructor
public class BuyingBidApiController {
    private final ValidatePaymentService validatePaymentService;
    private final CreateDealService createDealService;

    @ApiOperation(value = "입찰구매 결제저장 API", notes = "입찰구매 결제저장")
    @PostMapping("/payments/new/bid")
    public MessageDto postBidPayment(@RequestBody BidPaymentDto dto) {
        validatePaymentService.validateBidPayment(dto);
        createDealService.createBidBuying(dto);
        return MessageDto.builder()
                .message(getMessage("isPaySuccess", true, "Status", "결제가 완료되었습니다."))
                .build();
    }

}

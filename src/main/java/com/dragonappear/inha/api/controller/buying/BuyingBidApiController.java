package com.dragonappear.inha.api.controller.buying;

import com.dragonappear.inha.api.returndto.MessageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"구매입찰 생성 API"})
@RestController
@RequiredArgsConstructor
public class BuyingBidApiController {

    /*@ApiOperation(value = "구매 입찰 저장 API", notes = "구매 입찰 저장")
    @PostMapping("/payments/new/bid")
    public MessageDto postBidPayment() {

    }*/
}

package com.dragonappear.inha.api.controller.selling;


import com.dragonappear.inha.api.controller.selling.dto.BidSellingDto;
import com.dragonappear.inha.api.controller.selling.dto.InstantSellingDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.service.deal.CreateDealService;
import com.dragonappear.inha.api.service.selling.ValidateSellingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"아이템 판매 API"})
@RestController
@RequiredArgsConstructor
public class SellingApiController {
    private final ValidateSellingService validateSellingService;
    private final CreateDealService createDealService;

    @ApiOperation(value = "입찰판매 저장 API", notes = "입찰판매 저장")
    @PostMapping("/sellings/new/bid")
    public MessageDto postInstantSelling(@RequestBody BidSellingDto dto) {
        validateSellingService.validateBidSelling(dto);
        createDealService.createBidSelling(dto);
        return MessageDto.builder()
                .message(getMessage("isCreatedSuccess", true, "Status", "판매신청이 완료되었습니다."))
                .build();
    }

    @ApiOperation(value = "즉시판매 저장 API", notes = "즉시판매 저장")
    @PostMapping("/sellings/new/instant")
    public MessageDto createInstantSelling(@RequestBody InstantSellingDto dto) {
        validateSellingService.validateInstantSelling(dto);
        createDealService.createInstantSelling(dto);
        return MessageDto.builder()
                .message(getMessage("isCreatedSuccess", true, "Status", "판매신청이 완료되었습니다."))
                .build();
    }
}

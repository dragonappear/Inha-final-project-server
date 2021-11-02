package com.dragonappear.inha.api.controller.selling;

import com.dragonappear.inha.api.controller.selling.dto.SellingDeliveryDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.SellingDelivery;
import com.dragonappear.inha.service.selling.SellingDeliveryService;
import com.dragonappear.inha.service.selling.SellingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"아이템 판매 택배 배송 API"})
@RestController
@RequiredArgsConstructor
public class SellingDeliveryApiController {
    private SellingDeliveryService sellingDeliveryService;
    private SellingService sellingService;

    @ApiOperation(value = "택배 배송 등록 API", notes = "택배 배송 등록")
    @PostMapping("/sellings/deliveries/new")
    public MessageDto postSellingDelivery(@RequestBody SellingDeliveryDto dto) {
        Selling selling = sellingService.findBySellingId(dto.getSellingId());
        Long save = sellingDeliveryService.save(selling, dto.getDelivery());
        return MessageDto.builder()
                .message(getMessage("isUploadedSuccess", true, "Status", "택배등록이 완료되었습니다."))
                .build();
    }

    @ApiOperation(value = "택배 배송 조회 API by 판매 아이디", notes = "택배 배송 조회")
    @PostMapping("/sellings/deliveries/find/{sellingId}")
    public MessageDto findSellingDelivery(@PathVariable("sellingId") Long sellingId) {
        Selling selling = sellingService.findBySellingId(sellingId);
        SellingDelivery delivery = selling.getSellingDelivery();
        return MessageDto.builder()
                .message(getMessage("delivery info", delivery.getDelivery().toString()))
                .build();
    }


    @ApiOperation(value = "택배 배송 수정 API by 판매 아이디", notes = "택배 배송 조회")
    @PostMapping("/sellings/deliveries/update/{sellingId}")
    public MessageDto updateSellingDelivery(@PathVariable("sellingId") Long sellingId, @RequestBody SellingDeliveryDto dto) {
        Selling selling = sellingService.findBySellingId(sellingId);
        sellingDeliveryService.update(selling, dto.getDelivery());
        return MessageDto.builder()
                .message(getMessage("isUploadedSuccess", true, "Status", "택배수정이 완료되었습니다."))
                .build();
    }
}

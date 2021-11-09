package com.dragonappear.inha.api.controller.selling;

import com.dragonappear.inha.api.controller.selling.dto.SellingDeliveryDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.SellingDelivery;
import com.dragonappear.inha.exception.NotFoundCustomException;
import com.dragonappear.inha.service.selling.SellingDeliveryService;
import com.dragonappear.inha.service.selling.SellingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"아이템 판매 택배 배송 API"})
@RestController
@RequiredArgsConstructor
public class SellingDeliveryApiController {
    private final SellingDeliveryService sellingDeliveryService;
    private final SellingService sellingService;

    @ApiOperation(value = "택배 배송 등록 API", notes = "택배 배송 등록")
    @PostMapping("/sellings/deliveries/new")
    public MessageDto postSellingDelivery(@RequestBody SellingDeliveryDto dto) {
        Selling selling = sellingService.findBySellingId(dto.getSellingId());
        if (selling.getSellingDelivery() != null) {
            return MessageDto.builder()
                    .message(getMessage("isUploadedSuccess", false, "Status", "택배등록이 이미 등록되었습니다."))
                    .build();
        }
        Long save = sellingDeliveryService.save(selling, dto.getDelivery());
        return MessageDto.builder()
                .message(getMessage("isUploadedSuccess", true, "Status", "택배등록이 완료되었습니다."))
                .build();
    }

    @ApiOperation(value = "택배 배송 조회 API by 판매 아이디", notes = "택배 배송 조회")
    @GetMapping("/sellings/deliveries/{sellingId}")
    public MessageDto findSellingDelivery(@PathVariable("sellingId") Long sellingId) {
        Selling selling = sellingService.findBySellingId(sellingId);
        SellingDelivery delivery = selling.getSellingDelivery();
        return MessageDto.builder()
                .message(getMessage("info",
                        (delivery==null) ? "송장번호가 등록되지 않았습니다.": delivery.getDelivery()))
                .build();
    }

    @ApiOperation(value = "택배 배송 수정 API by 판매 아이디", notes = "택배 배송 조회")
    @PostMapping("/sellings/deliveries/update")
    public MessageDto updateSellingDelivery(@RequestBody SellingDeliveryDto dto) {
        Selling selling = sellingService.findBySellingId(dto.getSellingId());
        if (selling.getSellingDelivery() == null) {
            return MessageDto.builder()
                    .message(getMessage("isUploadedSuccess", false, "Status", "택배가 조회되지 않습니다."))
                    .build();
        }
        sellingDeliveryService.update(selling, dto.getDelivery());
        return MessageDto.builder()
                .message(getMessage("isUploadedSuccess", true, "Status", "택배수정이 완료되었습니다."))
                .build();
    }
}

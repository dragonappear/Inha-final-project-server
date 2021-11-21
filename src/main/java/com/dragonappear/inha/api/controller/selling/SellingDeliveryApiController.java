package com.dragonappear.inha.api.controller.selling;

import com.dragonappear.inha.api.controller.selling.dto.SellingDeliveryDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.service.firebase.FcmSendService;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.SellingDelivery;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.selling.SellingDeliveryService;
import com.dragonappear.inha.service.selling.SellingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Slf4j
@Api(tags = {"아이템 판매 택배 배송 API"})
@Controller
@RequiredArgsConstructor
public class SellingDeliveryApiController {
    private final SellingDeliveryService sellingDeliveryService;
    private final SellingService sellingService;
    private final FcmSendService fcmSendService;
    private final DealService dealService;
    private static final String sweetTracker = "k5kVWOLhTjQ9TNSDpBM8iw";

    @ResponseBody
    @ApiOperation(value = "택배 배송 등록 API", notes = "택배 배송 등록")
    @PostMapping("/api/v1/sellings/deliveries/new")
    public MessageDto postSellingDelivery(@Valid @RequestBody SellingDeliveryDto dto) {
        Selling selling = sellingService.findBySellingId(dto.getSellingId());
        Deal deal = selling.getDeal();
        if (selling.getSellingDelivery() != null) {
            return MessageDto.builder()
                    .message(getMessage("isUploadedSuccess", false, "Status", "택배등록이 이미 등록되었습니다."))
                    .build();
        }

        Long deliveryId = sellingDeliveryService.save(selling, dto.getDelivery());
        dealService.updateDealStatus(deal, DealStatus.판매자발송완료);

        try {
            User buyer = deal.getBuying().getPayment().getUser();
            String title = "판매자 아이템 발송완료 알림";
            String body = "판매자가 " + selling.getAuctionitem().getItem().getItemName() + "을 발송하였습니다.";
            fcmSendService.sendFCM(buyer, title, body);
        } catch (Exception e) {
            log.error("deliveryId:{} 발송완료 FCM 메시지가 전송되지 않았습니다.", deliveryId);
        }

        return MessageDto.builder()
                .message(getMessage("isUploadedSuccess", true, "Status", "택배등록이 완료되었습니다."))
                .build();
    }

    @ResponseBody
    @ApiOperation(value = "택배 송장번호 조회 API by 판매 아이디", notes = "택배 송장번호 조회")
    @GetMapping("/api/v1/sellings/deliveries/{sellingId}")
    public MessageDto findSellingDelivery(@PathVariable("sellingId") Long sellingId) {
        Selling selling = sellingService.findBySellingId(sellingId);
        SellingDelivery delivery = selling.getSellingDelivery();
        return MessageDto.builder()
                .message(getMessage("info",
                        (delivery == null) ? "송장번호가 등록되지 않았습니다." : delivery.getDelivery()))
                .build();
    }

    @ResponseBody
    @ApiOperation(value = "택배 배송 수정 API by 판매 아이디", notes = "택배 배송 수정")
    @PostMapping("/api/v1/sellings/deliveries/update")
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

    @ApiOperation(value = "택배 추적 조회 API by 판매 아이디", notes = "택배 추적 조회")
    @GetMapping("/api/v1/sellings/deliveries/trace/{sellingId}")
    public String findSellingDeliveryTest(@PathVariable("sellingId") Long sellingId, Model model) throws IOException {
        Selling selling = sellingService.findBySellingId(sellingId);
        SellingDelivery delivery = selling.getSellingDelivery();

        String t_key = sweetTracker;
        String t_code = delivery.getDelivery().getCourierName().getCode();
        String t_invoice = delivery.getDelivery().getInvoiceNumber();
        model.addAttribute("t_key",t_key);
        model.addAttribute("t_code",t_code);
        model.addAttribute("t_invoice",t_invoice);
        return "delivery/deliveryResult";
    }
}

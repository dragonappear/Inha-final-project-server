package com.dragonappear.inha.web.admin.deal;

import com.dragonappear.inha.api.service.firebase.FcmSendService;
import com.dragonappear.inha.api.service.firebase.FirebaseCloudMessageService;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.user.UserTokenService;
import com.dragonappear.inha.web.admin.deal.dto.DealWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.domain.deal.value.DealStatus.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(("/web/deals"))
public class DealWebController {
    private final DealService dealService;
    private final FcmSendService fcmSendService;

    @GetMapping
    public String getAllDeals(Model model) {
        List<DealWebDto> dtos = dealService.findAll().stream().map(deal -> {
            return DealWebDto.builder()
                    .dealId(deal.getId())
                    .createdDate(deal.getUpdatedDate())
                    .status(deal.getDealStatus())
                    .buyingId(deal.getBuying().getId())
                    .sellingId(deal.getSelling().getId())
                    .inspectionId((deal.getInspection() == null) ? null : deal.getInspection().getId())
                    .build();
        }).collect(Collectors.toList());
        model.addAttribute("deals", dtos);
        return "deal/dealList";
    }

    @PostMapping("/{dealId}/receivingRegister")
    public String receivingRegister(@PathVariable("dealId") Long dealId) {
        Deal deal = dealService.findById(dealId);
        if (deal.getDealStatus() == 거래진행) {
            dealService.updateDealStatus(deal,입고완료);
            String title = "아이템 입고 알림";
            String body = deal.getSelling().getAuctionitem().getItem().getItemName() + " 입고가 완료되었습니다.";
            User buyer = deal.getBuying().getPayment().getUser();
            User seller = deal.getSelling().getSeller();
            try {
                fcmSendService.sendFCM(buyer, title, body);
                fcmSendService.sendFCM(seller, title, body);
            } catch (IOException e) {
                log.error("dealId:{} 입고완료 FCM 메시지가 전송되지 않았습니다.",deal.getId());
            }
        }
        return "redirect:/web/deals";
    }


    @PostMapping("/{dealId}/inspectionStart")
    public String inspectionStart(@PathVariable("dealId") Long dealId) {
        Deal deal = dealService.findById(dealId);
        if (deal.getDealStatus() == 입고완료) {
            dealService.updateDealStatus(deal,검수진행);
            String title = "아이템 검수진행 알림";
            String body = deal.getSelling().getAuctionitem().getItem().getItemName() + " 의 검수를 시작을 알려드립니다.";
            User buyer = deal.getBuying().getPayment().getUser();
            User seller = deal.getSelling().getSeller();
            try {
                fcmSendService.sendFCM(buyer, title, body);
                fcmSendService.sendFCM(seller, title, body);
            } catch (IOException e) {
                log.error("dealId:{} 검수진행 FCM 메시지가 전송되지 않았습니다.",deal.getId());
            }
        }
        return "redirect:/web/deals";
    }

}

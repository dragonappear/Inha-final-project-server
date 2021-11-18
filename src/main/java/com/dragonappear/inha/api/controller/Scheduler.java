package com.dragonappear.inha.api.controller;

import com.dragonappear.inha.api.service.buying.iamport.IamportService;
import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import com.dragonappear.inha.api.service.firebase.FcmSendService;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.selling.SellingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dragonappear.inha.domain.deal.value.DealStatus.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class Scheduler {
    private final SellingService sellingService;
    private final BuyingService buyingService;
    private final IamportService iamportService;
    private final DealService dealService;
    private final FcmSendService fcmSendService;

    @Transactional
    @Scheduled(fixedDelay = 1000*60  ,zone = "Asia/Seoul")
    public void checkSellingDate() {
        sellingService.overdue();
    }

    @Transactional
    @Scheduled(fixedDelay = 1000*60  ,zone = "Asia/Seoul")
    public void checkBuyingDate() {
        buyingService.overdue();
    }

    @Transactional
    @Scheduled(fixedDelay = 1000 * 60, zone = "Asia/Seoul")
    public void cancelOverduePayment() {
    buyingService.findOverdueAndNotCanceled().stream().forEach(bidBuying -> {
        String itemName = bidBuying.getPayment().getAuctionitem().getItem().getItemName();
        String body = "입찰 구매하신" + itemName + "의 입찰기한이 만료되어서 결제 취소되었음을 알려드립니다.";
        cancelPayment(bidBuying.getPayment(),body);
        });
    }

    @Transactional
    @Scheduled(fixedDelay = 1000 * 60, zone = "Asia/Seoul")
    public void cancelUnregisteredDeliveryDeal() {
        List<Deal> deals = dealService.findUnregisteredSellingDelivery();
        deals.stream().forEach(deal -> {
            deal.updateDealStatus(미입고취소);
            Payment payment = deal.getBuying().getPayment();
            String itemName = payment.getAuctionitem().getItem().getItemName();
            String body = "판매자가 " + itemName + "을 배송하지 않아 결제가 취소되었음을 알려드립니다.";
            cancelPayment(payment,body);
            /**
             * 판매자 페널티 결제추가해야됌
             */
        });
    }

    /**
     * 입고완료가 7일동안 되지 않으면 취소
     */
    @Transactional
    @Scheduled(fixedDelay = 1000 * 60, zone = "Asia/Seoul")
    public void cancelUndeliveredDeal() {
        List<Deal> deals = dealService.findUndeliveredDeal();
        deals.stream().forEach(deal->{
            deal.updateDealStatus(미입고취소);
            Payment payment = deal.getBuying().getPayment();
            String itemName = payment.getAuctionitem().getItem().getItemName();
            String body = "판매자가 " + itemName + "을 배송하지 않아 결제가 취소되었음을 알려드립니다.";
            cancelPayment(payment,body);
            /**
             * 판매자 페널티 결제추가해야됌
             */
        });
    }


    /**
     * 검수탈락시에 구매자 결제 취소
     */

    @Transactional
    @Scheduled(fixedDelay = 1000 * 60, zone = "Asia/Seoul")
    public void cancelFailInspectionDeal() {
        List<Deal> deals = dealService.findUndeliveredDeal();
        deals.stream().forEach(deal->{
            deal.updateDealStatus(검수탈락취소);
            Payment payment = deal.getBuying().getPayment();
            String itemName = payment.getAuctionitem().getItem().getItemName();
            String body = "구매하신 " + itemName + "이 검수결과 탈락하여 결제가 취소되었음을 알려드립니다.";
            cancelPayment(payment,body);
            /**
             * 판매자 페널티 결제추가해야됌
             */
        });
    }

    private void cancelPayment(Payment payment,String body) {
        try {
            iamportService.cancelPayment(CancelDto.getCancelDto(payment));
            payment.cancel();
            log.info("paymentId:{} 결제취소가 완료되었습니다.", payment.getId());
            User buyer = payment.getUser();
            String title = "결제 취소 알림";
            try {
                fcmSendService.sendFCM(buyer,title,body);
            } catch (Exception e) {
                log.error("paymentId:{} 결제취소 FCM 메시지가 전송되지 않았습니다.",payment.getId());
            }
        }catch (Exception e){
            payment.updateStatus(PaymentStatus.결제취소실패);
            log.error("paymentId:{} 결제취소가 실패하였습니다.", payment.getId());
        }
    }
}

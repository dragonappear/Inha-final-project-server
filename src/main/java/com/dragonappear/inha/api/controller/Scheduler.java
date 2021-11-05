package com.dragonappear.inha.api.controller;

import com.dragonappear.inha.api.service.buying.iamport.IamportService;
import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.selling.SellingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;
import static com.dragonappear.inha.domain.deal.value.DealStatus.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class Scheduler {
    private final SellingService sellingService;
    private final BuyingService buyingService;
    private final IamportService iamportService;
    private final DealService dealService;

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
    public void cancelPayment() {
    buyingService.findOverdueAndNotCanceled().stream().forEach(bidBuying -> {
            cancelPayment(bidBuying.getPayment());
        });
    }

    @Transactional
    @Scheduled(fixedDelay = 1000 * 60, zone = "Asia/Seoul")
    public void cancelDeal() {
        List<Deal> deals = dealService.findUnregisteredSellingDelivery();
        deals.stream().forEach(deal -> {
            deal.updateDealStatus(거래취소);
            Payment payment = deal.getBuying().getPayment();
            cancelPayment(payment);
        });
    }

    private void cancelPayment(Payment payment) {
        try {
            iamportService.cancelPayment(CancelDto.getCancelDto(payment));
            payment.cancel();
            log.info("paymentId:{} 결제취소가 완료되었습니다.", payment.getId());
        }catch (Exception e){
            payment.updateStatus(PaymentStatus.결제취소실패);
            log.error("paymentId:{} 결제취소가 실패하였습니다.", payment.getId());
        }
    }
}

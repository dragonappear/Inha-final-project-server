package com.dragonappear.inha.api.controller;

import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.service.buying.iamport.IamportService;
import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.selling.SellingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@RequiredArgsConstructor
@RestController
@Slf4j
public class Scheduler {
    private final SellingService sellingService;
    private final BuyingService buyingService;
    private final IamportService iamportService;

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
    @Scheduled(fixedDelay = 1000 * 60 * 10, zone = "Asia/Seoul")
    public void cancelPayment() {
            buyingService.findOnGoing(BuyingStatus.구매입찰종료).stream().forEach(bidBuying -> {
                iamportService.cancelPayment(CancelDto.getCancelDto(bidBuying.getPayment()));
                MessageDto messageDto = MessageDto.builder()
                        .message(getMessage("isCanceledSuccess", false, "Status", "결제를 취소하였습니다.")).build();
                log.info(String.valueOf(messageDto));
            });
    }
}

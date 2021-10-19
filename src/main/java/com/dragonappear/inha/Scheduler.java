package com.dragonappear.inha;

import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.service.selling.SellingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@Component
public class Scheduler {
    private final SellingService sellingService;

    @Transactional
    @Scheduled(fixedDelay = 1000*60  ,zone = "Asia/Seoul")
    public void checkSellingDate() {
        sellingService.findOnSale(SellingStatus.판매중).stream().forEach(selling -> {
            if (selling.getAuctionitem().getEndDate().isBefore(LocalDateTime.now(ZoneId.of("Asia/Seoul")))) {
                sellingService.overdue(selling);
            }
        });
    }
}

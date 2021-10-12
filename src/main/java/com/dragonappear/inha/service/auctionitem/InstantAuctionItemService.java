package com.dragonappear.inha.service.auctionitem;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.auctionitem.InstantAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.selling.SellingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InstantAuctionItemService {
    private final AuctionitemRepository auctionitemRepository;
    private final SellingRepository sellingRepository;

    // 즉시경매아이템 등록
    @Transactional
    public Long save(Item item) {
        Money price = new Money(sellingRepository.findLowestPriceByItemId(item.getId()).
                orElse(item.getReleasePrice().getAmount()));
        return auctionitemRepository.save(new InstantAuctionitem(item, price)).getId();
    }

    // 즉시판매아이템 판매기한만료시 status 변경
    @Transactional
    //@EnableScheduling
    public void overdue(Auctionitem auctionitem) {
        auctionitem.updateStatus(AuctionitemStatus.경매기한만료);
    }
}

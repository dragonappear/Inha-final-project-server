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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuctionItemService {
    private final AuctionitemRepository auctionitemRepository;
    private final SellingRepository sellingRepository;


    /**
     * CREATE
     */
    // 입찰경매아이템 등록
    @Transactional
    public Long bidSave(Item item, Money price, LocalDateTime endDate) {
        return auctionitemRepository.save(new BidAuctionitem(item, price, endDate)).getId();
    }

    // 즉시경매아이템 등록
    @Transactional
    public Long instantSave(Item item) {
        Money price = new Money(sellingRepository.findLowestPriceByItemId(item.getId()).
                orElse(item.getLatestPrice().getAmount()));
        return auctionitemRepository.save(new InstantAuctionitem(item, price)).getId();
    }

    /**
     * READ
     */
    public Auctionitem findById(Long auctionitemId) {
        return auctionitemRepository.findById(auctionitemId).orElseThrow(()-> new IllegalStateException("존재하지 않는 경매품입니다."));
    }


    /**
     * UPDATE
     */

    // 경매가성사되었을때 status 변경
    @Transactional
    public void proceed(Auctionitem auctionitem) {
        auctionitem.updateStatus(AuctionitemStatus.거래중);
    }

    // 경매아이템 판매기한만료시 status 변경
    @Transactional
    public void overdue(Auctionitem auctionitem) {
        validateOverdue(auctionitem.getEndDate());
        auctionitem.updateStatus(AuctionitemStatus.경매기한만료);
    }

    // 판매가완료되었을때 status 변경
    @Transactional
    public void complete(Auctionitem auctionitem) {
        auctionitem.updateStatus(AuctionitemStatus.경매완료);
    }


    // 판매가취소되었을때 status 변경
    @Transactional
    public void cancel(Auctionitem auctionitem) {
        auctionitem.updateStatus(AuctionitemStatus.경매취소);
    }

    /**
     * 검증로직
     */
    private void validateOverdue(LocalDateTime endDate) {
        if (LocalDateTime.now().isAfter(endDate)) {
            throw new IllegalStateException("경매기간이 만료되었습니다");
        }
    }
}

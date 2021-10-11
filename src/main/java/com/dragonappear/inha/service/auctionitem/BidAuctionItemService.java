package com.dragonappear.inha.service.auctionitem;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.BidAuctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BidAuctionItemService {
    @Autowired AuctionitemRepository auctionitemRepository;

    @Transactional
    // 입찰경매아이템 등록
    public Long save(Item item, Money price, LocalDateTime endDate) {
        return auctionitemRepository.save(new BidAuctionitem(item, price, endDate)).getId();
    }

    // 경매가성사되었을때 status 변경
    @Transactional
    public void proceed(Auctionitem auctionitem) {
        auctionitem.updateStatus(AuctionitemStatus.거래중);
    }

    // 입찰경매아이템 판매기한만료시 status 변경
    @Transactional
    //@EnableScheduling
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

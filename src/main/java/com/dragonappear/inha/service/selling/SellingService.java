package com.dragonappear.inha.service.selling;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.selling.BidSelling;
import com.dragonappear.inha.domain.selling.InstantSelling;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.exception.NotFoundCustomException;
import com.dragonappear.inha.repository.selling.SellingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SellingService {
    @Autowired SellingRepository sellingRepository;

    /**
     * CREATE
     */
    // 입찰판매 등록
    @Transactional
    public Long bidSave(User user, Auctionitem auctionitem, LocalDateTime endDate) {
        return sellingRepository.save(new BidSelling(user, auctionitem,endDate)).getId();
    }

    // 즉시판매 등록
    @Transactional
    public Long instantSave(User user, Auctionitem auctionitem) {
        return sellingRepository.save(new InstantSelling(user, auctionitem)).getId();
    }

    /**
     * READ
     */

    // 판매조회 by 판매아이디
    public Selling findBySellingId(Long sellingId) {
        return  sellingRepository.findById(sellingId).orElseThrow(() -> new NotFoundCustomException("판매가 조회되지않습니다"));
    }

    // 판매조회 by 경매아이템 아이디
    public Selling findByAuctionitemId(Long auctionitemId) {
        return sellingRepository.findByAuctionitemId(auctionitemId).orElseThrow(()-> new NotFoundCustomException("판매가 조회되지않습니다"));
    }


    // 판매내역리스트 조회 by 유저아이디
    public List<Selling> findByUserId(Long userId) {
        List<Selling> list = sellingRepository.findByUserId(userId);
        if (list.size() == 0) {
            throw new IllegalArgumentException("판매가 조회되지않습니다");
        }
        return list;
    }

    // 판매내역리스트 조회 by 상품명
    public List<Selling> findByItemName(String itemName) {
        List<Selling> list = sellingRepository.findByItemName(itemName);
        if (list.size() == 0) {
            throw new IllegalArgumentException("판매가 조회되지않습니다");
        }
        return list;
    }

    // 판매중인 판매 조회
    public List<Selling> findOnGoing(SellingStatus sellingStatus) {
        return sellingRepository
                .findByStatus(sellingStatus);

    }

    /**
     * UPDATE
     */

    // 경매상품기한이 기간만료되었을때 status 변경 (경매상품아이템의 기한이 만료된경우)
    @Transactional
    public void overdue() {
        Long aLong = sellingRepository.endBidSelling();
    }

}

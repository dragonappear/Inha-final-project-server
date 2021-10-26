package com.dragonappear.inha.service.selling;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.repository.selling.SellingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SellingService {
    @Autowired SellingRepository sellingRepository;


    /**
     * CREATE
     */
    // 경매상품 판매 등록
    @Transactional
    public Long save(User user,Auctionitem auctionitem) {
        validateAuctionItem(auctionitem); // 경매상품 중복등록 검증
        return sellingRepository.save(new Selling(user, auctionitem)).getId();
    }

    /**
     * READ
     */

    // 판매조회 by 판매아이디
    public Selling findBySellingId(Long sellingId) {
            return sellingRepository.findById(sellingId).orElseThrow(()-> new IllegalStateException("판매가 조회되지않습니다"));
    }

    // 판매내역리스트 조회 by 유저아이디
    public List<Selling> findByUserId(Long userId) {
        return sellingRepository.findByUserId(userId);
    }

    // 판매내역리스트 조회 by 상품명
    public List<Selling> findByItemName(String itemName) {
        return sellingRepository.findByItemName(itemName);
    }

    // 경매상품판매가 거래중일때 status 변경( Deal이 생성된 경우)
    @Transactional
    public void proceed(Selling selling) {
        selling.updateStatus(SellingStatus.거래중);
    }

    // 경매상품판매가 완료되었을때 status 변경 (검수합격+정산까지 생성된 경우)
    @Transactional
    public void complete(Selling selling) {
        selling.updateStatus(SellingStatus.판매완료);
    }

    // 경매상품판매가 취소되었을때 status 변경 (검수탈락했을경우)
    @Transactional
    public void cancel(Selling selling) {
        selling.updateStatus(SellingStatus.판매취소);
    }

    // 판매중인 판매 조회
    public List<Selling> findOnSale(SellingStatus sellingStatus) {
        return sellingRepository.findByStatus(sellingStatus);
    }

    // 경매상품기한이 기간만료되었을때 status 변경 (경매상품아이템의 기한이 만료된경우)
    @Transactional
    public void overdue(Selling selling) {
        selling.updateStatus(SellingStatus.판매입찰종료);
    }

    /**
     * 검증로직
     */
    private void validateAuctionItem(Auctionitem auctionitem) {
        if(auctionitem.getAuctionitemStatus()!= 경매중){
            throw new IllegalStateException("해당 경매상품은 경매로 올릴 수 없습니다.");
        }
    }
}

package com.dragonappear.inha.service.buying;

import com.dragonappear.inha.domain.buying.BidBuying;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.exception.NotFoundCustomException;
import com.dragonappear.inha.repository.buying.BuyingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BuyingService {
    private final BuyingRepository buyingRepository;

    /**
     * CREATE
     */

    // 구매 생성
    @Transactional
    public Long save(Buying buying) {
        return buyingRepository.save(buying).getId();
    }

    /**
     * READ
     */

    // 구매내역리스트 조회 by 유저아이디
    public List<Buying> findByUserId(Long userId) {
        List<Buying> list = buyingRepository.findByUserId(userId);
        if (list.size() == 0) {
            throw new IllegalArgumentException("아이템이 존재하지 않습니다");
        }
        return list;
    }

    // 구매내역 조회 by 구매아이디
    public Buying findById(Long id) {
        return buyingRepository.findById(id)
                .orElseThrow(() -> new NotFoundCustomException("구매내역이 존재하지 않습니다."));
    }

    public List<BidBuying> findOnGoing(BuyingStatus buyingStatus) {
            return buyingRepository.findByStatus(buyingStatus);
    }

    // 구매입찰이 지났지만 결제취소처리가 안된 구매 조회
    public List<Buying> findOverdueAndNotCanceled() {
        return buyingRepository.findOverdueAndNotCanceled();
    }


    /**
     * UPDATE
     */

    // 입찰기간 만료시 status 변경
    @Transactional
    public void overdue() {
        buyingRepository.endBidBuying();
    }
}

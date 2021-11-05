package com.dragonappear.inha.service.deal;

import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.exception.NotFoundCustomException;
import com.dragonappear.inha.repository.deal.DealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dragonappear.inha.domain.deal.value.DealStatus.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DealService {
    private final DealRepository dealRepository;


    /**
     *  CREATE
     */
    // 거래 생성
    @Transactional
    public Long save(Deal deal) {

        return dealRepository.save(deal).getId();
    }

    /**
     * READ
     */

    // 거래 조회 by 거래 아이디
    public Deal findById(Long dealId) {
        return dealRepository.findById(dealId)
                .orElseThrow(() -> new NotFoundCustomException("해당 거래가 존재하지 않습니다."));
    }

    // 판매자 배송등록 안된 거래 조회
    public List<Deal> findUnregisteredSellingDelivery() {
        return dealRepository.findUnregisteredSellingDelivery();
    }

    /**
     * UPDATE
     */

    // 거래상태변경 when 검수탈락시
    @Transactional
    public void cancel(Deal deal) {
        deal.updateDealStatus(거래취소);
    }

    // 거래상태변경 when 검수합격시
    @Transactional
    public void complete(Deal deal) {
        deal.updateDealStatus(거래진행);
    }


}

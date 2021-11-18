package com.dragonappear.inha.service.deal;

import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.deal.value.DealStatus;
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

    // 미입고된 거래 조회
    public List<Deal> findUndeliveredDeal() {
        return dealRepository.findUndeliveredDeal();
    }

    // 검수 탈락된 거래 조회
    public List<Deal> findFailInspectionDeal() {
        return dealRepository.findFailInspectionDeal();
    }


    // 거래 전체 조회
    public List<Deal> findAll() {
        return dealRepository.findAll();
    }



    /**
     * UPDATE
     */
    @Transactional
    public void updateDealStatus(Deal deal, DealStatus status) {
        deal.updateDealStatus(status);
    }

}

package com.dragonappear.inha.service.selling;

import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.SellingDelivery;
import com.dragonappear.inha.domain.value.Delivery;
import com.dragonappear.inha.repository.selling.SellingDeliveryRepository;
import com.dragonappear.inha.repository.selling.SellingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SellingDeliveryService {
    @Autowired SellingDeliveryRepository sellingDeliveryRepository;

    // 판매자배송 등록
    @Transactional
    public Long save(Selling selling, Delivery delivery) {
        validateSellingDelivery(selling);
        return sellingDeliveryRepository.save(new SellingDelivery(selling, delivery)).getId();
    }

    // 판매자배송 수정
    @Transactional
    public void update(Selling selling, Delivery delivery) {
        SellingDelivery sellingDelivery = sellingDeliveryRepository.findBySellingId(selling.getId())
                .orElseThrow(() -> new IllegalArgumentException("판매자 배송이 조회되지 않습니다"));
        updateSellingDelivery(selling, delivery, sellingDelivery);
    }

    /**
     * 비즈니스 로직
     */
    @Transactional
    private void updateSellingDelivery(Selling selling, Delivery delivery, SellingDelivery sellingDelivery) {
        sellingDelivery.updateDelivery(delivery);
        selling.updateSellingDelivery(sellingDelivery);
    }

    /**
     * 검증로직
     */
    private void validateSellingDelivery(Selling selling) {
        if (selling.getSellingDelivery()!=null) {
            throw new IllegalArgumentException("택배 등록이 이미 완료되었습니다.");
        }
    }

}

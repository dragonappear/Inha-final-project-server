package com.dragonappear.inha.service.inspection.pass;

import com.dragonappear.inha.domain.inspection.pass.PassDelivery;
import com.dragonappear.inha.repository.inspection.pass.PassDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PassDeliveryService {
    private final PassDeliveryRepository passDeliveryRepository;

    // 합격검수배송 등록
    @Transactional
    public Long save(PassDelivery passDelivery) {
        return passDeliveryRepository.save(passDelivery).getId();
    }

    // 합격검수배송 조회 by 배송아이디
    public PassDelivery findById(Long passDeliveryId) {
        return passDeliveryRepository.findById(passDeliveryId)
                .orElseThrow(() -> new IllegalStateException("해당 배송정보가 조회되지 않습니다"));
    }

    // 배송완료체크 서비스
    // 배송완료시 -> 구매,판매,거래 상태 변경
}

package com.dragonappear.inha.service.inspection.fail;

import com.dragonappear.inha.domain.inspection.fail.FailDelivery;
import com.dragonappear.inha.repository.inspection.fail.FailDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FailDeliveryService {
    @Autowired FailDeliveryRepository failDeliveryRepository;

    // 탈락검수배송 등록
    @Transactional
    public Long save(FailDelivery failDelivery) {
        return failDeliveryRepository.save(failDelivery).getId();
    }

    public FailDelivery findById(Long failDeliveryId) {
        return failDeliveryRepository.findById(failDeliveryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 배송정보가 조회되지 않습니다"));
    }

    // 배송완료체크 서비스
    // 배송완료시 -> 판매 상태 변경
}

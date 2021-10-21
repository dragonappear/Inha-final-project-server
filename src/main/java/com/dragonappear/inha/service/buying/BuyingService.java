package com.dragonappear.inha.service.buying;

import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
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

    // 구매내역 생성
    @Transactional
    public Long save(Buying buying) {
        return buyingRepository.save(buying).getId();
    }

    /**
     * READ
     */

    // 구매내역리스트 조회 by 유저아이디
    public List<Buying> findByUserId(Long userId) {
        return buyingRepository.findByUserId(userId);
    }

    // 구매내역 조회 by 구매아이디
    public Buying findById(Long id) {
        return buyingRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("구매내역이 존재하지 않습니다."));
    }


    /**
     * UPDATE
     */

    // 검수합격시 status 변경
    @Transactional
    public void complete(Buying buying) {
        buying.updateStatus(BuyingStatus.구매완료);
    }

    // 검수합격시 sttatus 변경
    @Transactional
    public void cancel(Buying buying) {
        buying.updateStatus(BuyingStatus.구매취소);
    }


}

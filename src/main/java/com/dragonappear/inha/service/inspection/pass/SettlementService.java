package com.dragonappear.inha.service.inspection.pass;

import com.dragonappear.inha.domain.inspection.pass.Settlement;
import com.dragonappear.inha.repository.inspection.pass.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SettlementService {
    private final SettlementRepository settlementRepository;

    /**
     * CREATE
     */

    public Long save(Settlement settlement) {
        return settlementRepository.save(settlement).getId();
    }

    /**
     * READ
     */

    public Settlement findById(Long settlementId) {
        return settlementRepository.findById(settlementId).orElse(null);
    }

    /**
     * UPDATE
     */

    /**
     * DELETE
     */
}

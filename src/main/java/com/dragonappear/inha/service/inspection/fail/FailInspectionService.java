package com.dragonappear.inha.service.inspection.fail;

import com.dragonappear.inha.domain.inspection.fail.FailInspection;
import com.dragonappear.inha.repository.inspection.fail.FailInspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FailInspectionService {
    private final FailInspectionRepository failInspectionRepository;

    // 탈락검수 등록
    @Transactional
    public Long save(FailInspection failInspection) {
        return failInspectionRepository.save(failInspection).getId();
    }

    // 탈락검수 조회 by 탈락검수 아이디
    public FailInspection findById(Long failInspectionId) {
        return failInspectionRepository.findById(failInspectionId)
                .orElseThrow(() -> new IllegalArgumentException("탈락검수 내역이 존재하지 않습니다."));
    }
}

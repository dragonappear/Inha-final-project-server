package com.dragonappear.inha.service.inspection.pass;

import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import com.dragonappear.inha.repository.inspection.pass.PassInspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PassInspectionService {
    private final PassInspectionRepository passInspectionRepository;

    // 합격검수 등록
    @Transactional
    public Long save(PassInspection passInspection) {
        return passInspectionRepository.save(passInspection).getId();
    }

    // 합격검수 조회 by 검수아이디
    public PassInspection findById(Long passInspectionId) {
        return passInspectionRepository.findById(passInspectionId)
                .orElseThrow(() -> new IllegalStateException("검수 합격내역이 존재하지 않습니다"));
    }
}

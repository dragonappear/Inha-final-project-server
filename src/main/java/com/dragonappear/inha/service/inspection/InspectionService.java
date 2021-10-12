package com.dragonappear.inha.service.inspection;

import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.repository.inspection.InspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InspectionService {
    private final InspectionRepository inspectionRepository;

    // 검수 생성
    @Transactional
    public Long save(Inspection inspection) {
        return inspectionRepository.save(inspection).getId();
    }

    // 검수 조회
    public Inspection findById(Long inspectionId) {
        return inspectionRepository.findById(inspectionId)
                .orElseThrow(() -> new IllegalStateException("해당 검수가 존재하지 않습니다"));
    }
}

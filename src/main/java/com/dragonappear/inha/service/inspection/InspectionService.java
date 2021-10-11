package com.dragonappear.inha.service.inspection;

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
}

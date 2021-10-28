package com.dragonappear.inha.service.inspection;

import com.dragonappear.inha.domain.inspection.InspectionImage;
import com.dragonappear.inha.repository.inspection.InspectionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InspectionImageService {
    @Autowired InspectionImageRepository inspectionImageRepository;

    // 검수이미지 등록
    @Transactional
    public void save(List<InspectionImage> image) {
        image.stream().forEach(image1 -> {inspectionImageRepository.save(image1);});
    }

    // 검수이미지리스트 조회 by 검수이미지아이디
    public List<InspectionImage> findByInspectionId(Long inspectionId) {
        List<InspectionImage> list = inspectionImageRepository.findByInspectionId(inspectionId);
        if (list.size() == 0) {
            throw new IllegalArgumentException("아이템이 존재하지 않습니다");
        }
        return list;
    }
}

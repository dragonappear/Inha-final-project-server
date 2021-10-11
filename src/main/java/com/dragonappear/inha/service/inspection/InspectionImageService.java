package com.dragonappear.inha.service.inspection;

import com.dragonappear.inha.repository.inspection.InspectionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InspectionImageService {
    @Autowired InspectionImageRepository inspectionImageRepository;


}

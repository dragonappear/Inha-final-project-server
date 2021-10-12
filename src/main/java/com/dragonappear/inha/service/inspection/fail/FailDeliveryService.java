package com.dragonappear.inha.service.inspection.fail;

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



}

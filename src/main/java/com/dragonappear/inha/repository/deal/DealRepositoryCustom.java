package com.dragonappear.inha.repository.deal;

import com.dragonappear.inha.domain.deal.Deal;

import java.util.List;

public interface DealRepositoryCustom {
    List<Deal> findUnregisteredSellingDelivery();

    List<Deal> findUndeliveredDeal();

    List<Deal> findFailInspectionDeal();
}

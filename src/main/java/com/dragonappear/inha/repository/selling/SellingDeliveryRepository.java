package com.dragonappear.inha.repository.selling;

import com.dragonappear.inha.domain.selling.SellingDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellingDeliveryRepository extends JpaRepository<SellingDelivery,Long> {

    @Query("select sd from SellingDelivery sd where sd.selling.id=:sellingId")
    Optional<SellingDelivery> findBySellingId(@Param("sellingId") Long sellingId);
}

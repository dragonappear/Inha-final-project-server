package com.dragonappear.inha.repository.inspection;

import com.dragonappear.inha.domain.inspection.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface
InspectionRepository extends JpaRepository<Inspection,Long> {

    @Query("select i from Inspection i where i.deal.id=:dealId")
    Optional<Inspection> findByDealId(Long dealId);
}

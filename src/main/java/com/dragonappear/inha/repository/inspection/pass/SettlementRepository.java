package com.dragonappear.inha.repository.inspection.pass;

import com.dragonappear.inha.domain.inspection.pass.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SettlementRepository extends JpaRepository<Settlement,Long> {

    @Query("select s from Settlement s where s.passInspection.id=:inspectionId")
    Optional<Settlement> findByInspectionId(@Param("inspectionId")Long inspectionId);
}

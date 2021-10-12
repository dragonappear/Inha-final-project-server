package com.dragonappear.inha.repository.inspection;

import com.dragonappear.inha.domain.inspection.InspectionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InspectionImageRepository extends JpaRepository<InspectionImage,Long> {

    @Query("select i from InspectionImage i where i.inspection.id=:inspectionId ")
    List<InspectionImage> findByInspectionId(@Param("inspectionId") Long inspectionId);
}

package com.dragonappear.inha.repository.inspection;

import com.dragonappear.inha.domain.inspection.failinspection.FailInspection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailInspectionRepository extends JpaRepository<FailInspection,Long> {
}

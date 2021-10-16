package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.Manufacturer;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {
    @Query("select m from Manufacturer m where m.manufacturerName=:manufacturerName")
    Optional<Manufacturer> findByManufacturerName(@Param("manufacturerName") ManufacturerName manufacturerName);

}

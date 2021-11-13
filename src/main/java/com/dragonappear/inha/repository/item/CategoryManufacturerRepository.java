package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.value.CategoryManufacturer;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryManufacturerRepository extends JpaRepository<CategoryManufacturer,Long> {
    @Query("select cm.manufacturer.manufacturerName from CategoryManufacturer cm where cm.category.categoryName=:categoryName")
    List<ManufacturerName> findByCategoryName(@Param("categoryName") CategoryName categoryName);
}

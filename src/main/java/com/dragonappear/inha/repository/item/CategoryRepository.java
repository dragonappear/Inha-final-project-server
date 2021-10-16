package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.Category;
import com.dragonappear.inha.domain.item.value.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select c from Category c where c.categoryName=:categoryName")
    Optional<Category> findByCategoryName(@Param("categoryName") CategoryName categoryName);

}

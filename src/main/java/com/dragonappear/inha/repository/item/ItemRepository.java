package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {
    @Query("select i from Item i where i.itemName =:itemName")
    List<Item> findByItemName(@Param("itemName") String itemName);

    @Query("select i from Item i where i.modelNumber =:modelNumber")
    Optional<Item> findByModelNumber(@Param("modelNumber")String modelNumber);

    @Query("select i from Item i where i.manufacturer.manufacturerName =:manufacturerName")
    List<Item> findByManufacturerName(@Param("manufacturerName") ManufacturerName manufacturerName);

    @Query("select i from Item i where i.category.categoryName =:categoryName")
    List<Item> findByCategoryName(@Param("categoryName") CategoryName categoryName);
}

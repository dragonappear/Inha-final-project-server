package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.ItemImage;
import com.dragonappear.inha.domain.value.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage,Long> {

    @Query("select i from ItemImage i where i.item.id=:itemId")
    List<ItemImage> findByItemId(@Param("itemId") Long itemId);

    @Modifying
    @Query("delete from ItemImage i where i.id=:imageId")
    void delete(@Param("imageId") Long imageId);

}

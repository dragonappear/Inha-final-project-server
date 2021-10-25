package com.dragonappear.inha.repository.selling;

import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SellingRepository extends JpaRepository<Selling,Long> , SellingRepositoryCustom {
    @Query("select min(a.price) from Selling s join s.auctionitem a on a.id=:itemId")
    Optional<BigDecimal> findLowestPriceByItemId(@Param("itemId") Long itemId);

    @Query("select s from Selling s where s.seller.id=:userId")
    List<Selling> findByUserId(@Param("userId") Long userId);

    @Query("select s from Selling s where s.auctionitem.item.itemName=:itemName")
    List<Selling> findByItemName(@Param("itemName") String itemName);

    @Query("select s from Selling s where s.sellingStatus =:sellingStatus")
    List<Selling> findByStatus(@Param("sellingStatus") SellingStatus sellingStatus);
}

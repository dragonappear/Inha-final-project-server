package com.dragonappear.inha.repository.selling;

import com.dragonappear.inha.domain.selling.Selling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface SellingRepository extends JpaRepository<Selling,Long>{
    @Query("select min(a.price) from Selling s join s.auctionitem a on a.id=:itemId")
    Optional<BigDecimal> findLowestPriceByItemId(@Param("itemId") Long itemId);
}

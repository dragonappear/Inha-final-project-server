package com.dragonappear.inha.repository.buying;

import com.dragonappear.inha.domain.buying.Buying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuyingRepository extends JpaRepository<Buying,Long> {

    @Query("select b from Buying b where b.payment.user.id=:userId")
    List<Buying> findByUserId(@Param("userId") Long userId);

}

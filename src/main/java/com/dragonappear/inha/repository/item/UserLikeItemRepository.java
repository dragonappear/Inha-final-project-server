package com.dragonappear.inha.repository.item;

import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.UserLikeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserLikeItemRepository extends JpaRepository<UserLikeItem,Long> {

    @Query("select i from UserLikeItem i where i.user.id=:userId")
    List<UserLikeItem> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("delete from UserLikeItem i where i.id=:userLikeItemId")
    void delete(@Param("userLikeItemId") Long userLikeItemId);
}

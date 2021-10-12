package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserImageRepository extends JpaRepository<UserImage,Long> {

    @Query("select ui from UserImage ui where ui.user.id=:userId")
    Optional<UserImage> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("delete from UserImage ui where ui.user.id=:userId")
    void deleteByUserId(@Param("userId") Long userId);
}

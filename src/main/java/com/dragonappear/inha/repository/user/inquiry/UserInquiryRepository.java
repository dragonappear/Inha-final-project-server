package com.dragonappear.inha.repository.user.inquiry;

import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserInquiryRepository extends JpaRepository<UserInquiry,Long> {

    @Query("select i from UserInquiry i where i.user.id=:userId")
    List<UserInquiry> findAllByUserId(@Param("userId") Long userId);

    @Query("select i from UserInquiry i where i.user.id=:userId and i.id=:inquiryId")
    Optional<UserInquiry> findByUserIdAndInquiryId(@Param("userId") Long userId, @Param("inquiryId") Long inquiryId);
}

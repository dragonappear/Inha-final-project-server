package com.dragonappear.inha.repository.user.inquiry;

import com.dragonappear.inha.domain.user.inquiry.UserInquiryAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInquiryAnswerRepository extends JpaRepository<UserInquiryAnswer,Long> {
    @Query("select a from UserInquiryAnswer a where a.userInquiry.user.id=:userId")
    List<UserInquiryAnswer> findByUserId(@Param("userId") Long userId);

}

package com.dragonappear.inha.repository.user.inquiry;

import com.dragonappear.inha.domain.user.inquiry.UserInquiryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserInquiryImageRepository extends JpaRepository<UserInquiryImage,Long> {
    @Query("select i from UserInquiryImage i where i.userInquiry.id=:inquiryId")
    List<UserInquiryImage> findByInquiryId(@Param("inquiryId") Long inquiryId);

    @Modifying
    @Query("delete from UserInquiryImage i where i.id=:imageId ")
    void delete(@Param("imageId") Long imageId);
}

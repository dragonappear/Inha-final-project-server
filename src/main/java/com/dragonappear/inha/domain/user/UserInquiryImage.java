package com.dragonappear.inha.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserInquiryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_inquiry_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_inquiry_user_inquiry_id")
    private UserInquiry userInquiry;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileOriName;

    @Column(nullable = false)
    private String fileUrl;

    /**
     * 생성자 메서드
     */

    public UserInquiryImage(UserInquiry userInquiry, String fileName, String fileOriName, String fileUrl) {
        if (userInquiry != null) {
            updateUserInquiryImage(userInquiry);
        }
        this.fileName = fileName;
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
    }

    /**
     * 연관관계 편의 메서드
     */
    private void updateUserInquiryImage(UserInquiry userInquiry) {
        this.userInquiry = userInquiry;
        userInquiry.updateUserInquiryImage(this);
    }


}
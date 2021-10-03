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

    @NotNull
    private String url;

    /**
     * 생성자 메서드
     */
    public UserInquiryImage(UserInquiry userInquiry, String url) {
        this.url = url;
        if (userInquiry != null) {
            updateUserInquiryImage(userInquiry);
        }
    }

    /**
     * 연관관계 편의 메서드
     */
    private void updateUserInquiryImage(UserInquiry userInquiry) {
        this.userInquiry = userInquiry;
        userInquiry.updateUserInquiryImage(this);
    }


}
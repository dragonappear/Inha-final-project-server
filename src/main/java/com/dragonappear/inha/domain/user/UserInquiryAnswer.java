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
public class UserInquiryAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_inquiry_answer_id")
    private Long id;

    @NotNull
    private String content;

    @OneToOne(fetch = LAZY,mappedBy = "userInquiryAnswer")
    private UserInquiry userInquiry;

    /**
     * 생성자 메서드
     */
    public UserInquiryAnswer(String content, UserInquiry userInquiry) {
        this.content = content;
        if (userInquiry != null) {
            updateUserInquiryAnswer(userInquiry);
        }
    }

    /**
     * 연관관계 편의 메서드
     */
    private void updateUserInquiryAnswer(UserInquiry userInquiry) {
        this.userInquiry = userInquiry;
        userInquiry.updateUserInquiryAnswer(this);
    }



}
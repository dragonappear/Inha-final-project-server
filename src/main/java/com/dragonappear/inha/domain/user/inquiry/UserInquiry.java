package com.dragonappear.inha.domain.user.inquiry;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.value.InquiryStatus;
import com.dragonappear.inha.domain.user.value.InquiryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserInquiry extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_inquiry_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(STRING)
    private InquiryType inquiryType;

    @Column(nullable = false)
    private String title;
    @NotNull
    private String content;

    @Column(nullable = false)
    @Enumerated(STRING)
    private InquiryStatus inquiryStatus;

    /**
     * 연관관계
     */

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_inquiry_answer_id")
    private UserInquiryAnswer userInquiryAnswer;

    @OneToMany(mappedBy = "userInquiry")
    private List<UserInquiryImage> userInquiryImages = new ArrayList<>();

    /**
     * 생성자 메서드
     */
    public UserInquiry(User user, InquiryType inquiryType, String title, String content, InquiryStatus inquiryStatus) {
        this.inquiryType = inquiryType;
        this.title = title;
        this.content = content;
        this.inquiryStatus = inquiryStatus;
        if (user != null) {
            updateUserInquiry(user);
        }
    }

    /**
     * 연관관계 편의 메서드
     */

    private void updateUserInquiry(User user) {
        this.user = user;
        user.getUserInquiries().add(this);
    }

    public void updateUserInquiryAnswer(UserInquiryAnswer userInquiryAnswer) {
        this.userInquiryAnswer = userInquiryAnswer;
        this.inquiryStatus = InquiryStatus.답변완료;
    }

    public void updateUserInquiryImage(UserInquiryImage image) {
        this.getUserInquiryImages().add(image);
    }

}

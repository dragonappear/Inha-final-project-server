package com.dragonappear.inha.domain.user.inquiry;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.value.InquiryStatus;
import com.dragonappear.inha.domain.user.value.InquiryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static com.dragonappear.inha.domain.user.value.InquiryStatus.*;
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

    @Column(nullable = false)
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

    @OneToOne(fetch = LAZY,mappedBy = "userInquiry")
    private UserInquiryAnswer userInquiryAnswer;

    @OneToMany(mappedBy = "userInquiry")
    private List<UserInquiryImage> userInquiryImages = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */

    private void updateUserInquiry(User user) {
        this.user = user;
        user.getUserInquiries().add(this);
    }

    public void updateUserInquiryAnswer(UserInquiryAnswer userInquiryAnswer) {
        this.userInquiryAnswer = userInquiryAnswer;
        if(userInquiryAnswer!=null){
            this.inquiryStatus = 답변완료;
        }
        else{
            this.inquiryStatus = 답변미완료;
        }

    }

    public void updateUserInquiryImage(UserInquiryImage image) {
        this.getUserInquiryImages().add(image);
    }

    /**
     * 생성자 메서드
     */
    public
    UserInquiry(User user, InquiryType inquiryType, String title, String content) {
        this.inquiryType = inquiryType;
        this.title = title;
        this.content = content;
        this.inquiryStatus = 답변미완료;
        if (user != null) {
            updateUserInquiry(user);
        }
    }


    /**
     * 비즈니스 로직
     */

    public void changeInquiry(InquiryType inquiryType, String title, String content) {
        this.inquiryType = inquiryType;
        this.title = title;
        this.content = content;
    }
}

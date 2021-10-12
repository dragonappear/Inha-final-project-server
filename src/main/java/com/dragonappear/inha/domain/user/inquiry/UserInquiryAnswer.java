package com.dragonappear.inha.domain.user.inquiry;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserInquiryAnswer extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_inquiry_answer_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_inquiry_id",unique = true)
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

    /**
     * 비즈니스 로직
     */
    public void updateContent(String content) {
        this.content = content;
    }
}
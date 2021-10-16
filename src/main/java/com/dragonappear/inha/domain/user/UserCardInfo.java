package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.value.Card;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserCardInfo extends JpaBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_card_info_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Card userCard;


    /**
     * 연관관계
     */

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 연관관계 편의 메서드
     */
    private void updateUserCardInfo(User user) {
        this.user = user;
        user.getUserCardInfos().add(this);
    }


    /**
     * 생성자 메서드
     */

    public UserCardInfo(Card userCard, User user) {
        this.userCard = userCard;
        if (user != null) {
            updateUserCardInfo(user);
        }
    }

}

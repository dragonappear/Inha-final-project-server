package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.value.Image;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserImage extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_image_id")
    private Long id;

    @Embedded
    private Image image;

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    /**
     * 연관관계 편의 메서드
     */

    private void updateUserImage(User user) {
        this.user = user;
        user.updateUserImage(this);

    }

    /**
     * 생성자 메서드
     */
    public UserImage(User user, Image image) {
        if (user != null) {
            updateUserImage(user);
        }
        this.image = image;
    }

    public void changeImage(Image image) {
        this.image = image;
    }
}

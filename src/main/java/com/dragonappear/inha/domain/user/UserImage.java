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
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_image_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    private User user;

    @NotNull
    private String url;

    /**
     * 생성자 메서드
     */
    public UserImage(User user, String url) {
        this.user = user;
        this.url = url;
    }

    /**
     * 연관관계 편의 메서드
     */

    public void updateUser(User user) {
        this.user = user;
    }


}

package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.JpaBaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserImage extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_image_id")
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileOriName;

    @Column(nullable = false)
    private String fileUrl;

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY)
    private User user;


    /**
     * 생성자 메서드
     */
    public UserImage(User user, String fileName, String fileOriName, String fileUrl) {
        this.user = user;
        this.fileName = fileName;
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
    }

    /**
     * 연관관계 편의 메서드
     */

    public void updateUser(User user) {
        this.user = user;
    }


}

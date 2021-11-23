package com.dragonappear.inha.domain.user;


import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Entity
public class UserNotification extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_notification_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserNotification(String title, String body, User user) {
        this.title = title;
        this.body = body;
        if (user != null) {
            updateUserNotification(user);
        }
    }

    private void updateUserNotification(User user) {
        this.user = user;
        user.getUserNotifications().add(this);
    }
}

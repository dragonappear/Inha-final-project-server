package com.dragonappear.inha.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@NoArgsConstructor
@Getter
@Entity
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_token_id")
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String token;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserToken(String type, String token, User user) {
        this.type = type;
        this.token = token;
        if (user != null) {
            updateUserToken(user);
        }
    }

    private void updateUserToken(User user) {
        this.user = user;
        user.getUserTokens().add(this);
    }
}
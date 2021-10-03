package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.domain.user.value.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_account_id")
    private Long id;


    @OneToOne(fetch = LAZY)
    private User user;


    @Embedded
    @NotNull
    private Account userAccount;


    /**
     * 생성자 메서드
     */
    public UserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * 연관관계 편의 메서드
     */

    public void updateUser(User user) {
        this.user = user;
    }

    /**
     * 비즈니스 로직
     */
    public void changeUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }
}

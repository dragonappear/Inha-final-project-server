package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.value.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class UserAccount extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_account_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Account userAccount;

    /**
     * 연관관계
     */
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 생성자 메서드
     */
    /*public UserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }*/

    public UserAccount( User user,Account userAccount) {
        this.userAccount = userAccount;
        if (user != null) {
            this.user = user;
            user.updateUserAccount(this);
        }
    }

    /**
     * 연관관계 편의 메서드
     */



    /**
     * 비즈니스 로직
     */
    public void
    changeUserAccount(Account userAccount) {
        this.userAccount = userAccount;
    }
}

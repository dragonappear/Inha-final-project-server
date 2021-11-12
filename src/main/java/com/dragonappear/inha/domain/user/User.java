package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.item.UserLikeItem;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.value.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.dragonappear.inha.domain.user.value.UserRole.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.LAZY;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email","userTel"})})
@NoArgsConstructor
@Getter
@Entity
public class User extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String userTel;

    @Enumerated(STRING)
    @Column(nullable = false)
    private UserRole userRole;

    /**
     * 연관관계
     */
    @JsonIgnore
    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "user")
    private List<UserPoint> userPoints = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = ALL)
    private List<UserCardInfo> userCardInfos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAddress> userAddresses = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = LAZY,cascade = ALL,mappedBy = "user")
    private UserAccount userAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = ALL)
    private List<UserInquiry> userInquiries = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = LAZY,mappedBy = "user",cascade = ALL)
    private UserImage userImage;

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = ALL)
    private List<UserLikeItem> userLikeItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = ALL)
    private List<Payment> payments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "seller",cascade = ALL)
    private List<Selling> sellings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<UserToken> userTokens = new ArrayList<>();


    /**
     * 연관관계 메서드
     */

    public void updateUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void updateUserImage(UserImage image) {
        this.userImage = image;
    }

    /**
     * 생성자 메서드
     */
    @Builder
    public User(String username, String nickname, String email, String userTel,UserRole userRole,String password) {
        this(username, email, userRole, password,userTel);
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.userTel = userTel;
        if (userRole == null) {
            this.userRole = USER;
        }
    }

    @Builder
    public User(String username, String email, UserRole userRole, String password, String userTel) {
        this.username = username;
        this.email = email;
        this.userRole = userRole;
        this.password = password;
        this.userTel = userTel;
    }

    /**
     * 테스트용
     */
    public User(String username, String nickname, String email, String userTel) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.userTel = userTel;
        this.userRole = USER;
    }

    /**
     * 비즈니스 로직
     */
    public User update(String username,String picture) {
        this.username = username;
        this.password = picture;
        return this;
    }

    public String getRoleKey() {
        return this.userRole.getKey();
    }

    public void updateUserTel(String userTel) {
        this.userTel = userTel;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateUsername(String username) {
        this.username = username;
    }
}


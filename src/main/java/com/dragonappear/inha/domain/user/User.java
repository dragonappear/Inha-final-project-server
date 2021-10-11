package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.item.UserLikeItem;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.value.UserRole;
import lombok.AccessLevel;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String userTel;

    @Column(nullable = false)
    @Enumerated(STRING)
    private UserRole userRole;

    /**
     * 연관관계
     */
    @OneToOne(fetch = LAZY, cascade = ALL,mappedBy = "user")
    private UserPoint userPoint;

    @OneToMany(mappedBy = "user",cascade = ALL)
    private List<UserCardInfo>
            userCardInfos = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = ALL)
    private List<UserAddress> userAddresses = new ArrayList<>();

    @OneToOne(fetch = LAZY,cascade = ALL,mappedBy = "user")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "user",cascade = ALL)
    private List<UserInquiry> userInquiries = new ArrayList<>();

    @OneToOne(fetch = LAZY,mappedBy = "user",cascade = ALL)
    private UserImage userImage;

    @OneToMany(mappedBy = "user",cascade = ALL)
    private List<UserLikeItem> userLikeItems = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = ALL)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "seller",cascade = ALL)
    private List<Selling> sellings = new ArrayList<>();

    /**
     * 연관관계 메서드
     */
    public void updateUserPoint(UserPoint userPoint) {
        this.userPoint = userPoint;
    }

    public void updateUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void updateUserImage(UserImage image) {
        this.userImage = image;
    }

    /**
     * 생성자 메서드
     */
    public User(String username, String nickname, String email, String userTel) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.userTel = userTel;
        this.userRole = 일반사용자;
    }
}


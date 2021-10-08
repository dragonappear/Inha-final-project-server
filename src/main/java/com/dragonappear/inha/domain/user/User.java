package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.item.UserLikeItem;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
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

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY,cascade = ALL)
    @JoinColumn(name = "user_point_id")
    private UserPoint userPoint;

    @OneToMany(mappedBy = "user")
    private List<UserCardInfo> userCardInfos = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserAddress> userAddresses = new ArrayList<>();

    @OneToOne(fetch = LAZY,cascade = ALL)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;


    @OneToMany(mappedBy = "user")
    private List<UserInquiry> userInquiries = new ArrayList<>();

    @OneToOne(fetch = LAZY,cascade = ALL)
    @JoinColumn(name = "user_image_id")
    private UserImage userImage;

    @OneToMany(mappedBy = "user")
    private List<UserLikeItem> userLikeItems = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<Selling> sellings = new ArrayList<>();

    /**
     * 연관관계 메서드
     */
    public void updateUserPoint(UserPoint userPoint) {
        this.userPoint = userPoint;
        userPoint.updateUser(this);
    }

    public void updateUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        userAccount.updateUser(this);
    }

    public void updateUserImage(UserImage image) {
        this.userImage = image;
        image.updateUser(this);
    }

    /**
     * 생성자 메서드
     */

    public User(String username, String nickname, String email, String userTel) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.userTel = userTel;
    }

    public User(String username, String nickname, String email, String userTel, UserPoint userPoint) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.userTel = userTel;
        if (userPoint != null) {
            updateUserPoint(userPoint);
        }
    }





}


package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.domain.user.value.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class UserAddress {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_address_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    @Column(nullable = false)
    private Address userAddress;

    public UserAddress(User user, Address userAddress) {
        this.userAddress = userAddress;
        if (user != null) {
            updateUserAddress(user);
        }
    }

    /**
     * 비즈니스 로직
     * /



    /**
     * 연관관계 편의 메서드
     */
    private void updateUserAddress(User user) {
        this.user = user;
    }



}

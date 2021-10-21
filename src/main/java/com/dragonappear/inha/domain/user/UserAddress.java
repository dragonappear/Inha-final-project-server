package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.value.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class UserAddress extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_address_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private Address userAddress;

    /**
     * 연관관계
     */
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 연관관계 편의 메서드
     */
    public void updateUserAddress(User user) {
        this.user = user;
        user.getUserAddresses().add(this);
    }

    private void updateAddress(Address userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * 생성자 함수
     */

    public UserAddress(User user, Address userAddress) {
        if (user != null) {
            updateUserAddress(user);
        }
        if (userAddress != null) {
            updateAddress(userAddress);
        }
    }

    /**
     * 비즈니스 로직
     */


}

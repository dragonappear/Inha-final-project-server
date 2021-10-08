package com.dragonappear.inha.domain.inspection.passinspection;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Delivery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class PassDelivery extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "pass_delivery_id")
    private Long id;

    @Column(nullable = false)
    @Embedded
    private Delivery delivery;


    @Column(nullable = false)
    @Embedded
    private Address buyerAddress;


    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "pass_inspection_id")
    private PassInspection passInspection;

    /**
     * 연관관계편의메서드
     */

    /**
     * 생성자메서드
     */



    /**
     * 비즈니스로직
     */


}
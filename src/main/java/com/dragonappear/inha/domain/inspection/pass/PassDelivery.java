package com.dragonappear.inha.domain.inspection.pass;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Delivery;
import com.dragonappear.inha.domain.value.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import static javax.persistence.EnumType.*;
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

    @Column(nullable = false)
    @Enumerated(STRING)
    private DeliveryStatus deliveryStatus;

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "pass_inspection_id")
    private PassInspection passInspection;

    /**
     * 연관관계편의메서드
     */

    private void updateInspectionDelivery(PassInspection passInspection) {
        this.passInspection = passInspection;
        passInspection.updateDelivery(this);
    }

    /**
     * 생성자메서드
     */
    public PassDelivery(Delivery delivery, Address buyerAddress,PassInspection passInspection) {
        this.delivery = delivery;
        this.buyerAddress = buyerAddress;
        this.deliveryStatus = DeliveryStatus.배송시작;
        if (passInspection != null) {
            updateInspectionDelivery(passInspection);
        }
    }

    /**
     * 비즈니스로직
     */
    public void updateDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }


}
package com.dragonappear.inha.domain.inspection.failinspection;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Delivery;
import com.dragonappear.inha.domain.value.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class FailDelivery extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "fail_delivery_id")
    private Long id;

    @Column(nullable = false)
    @Embedded
    private Delivery delivery;

    @Column(nullable = false)
    @Embedded
    private Address sellerAddress;

    @Column(nullable = false)
    @Enumerated(STRING)
    private DeliveryStatus deliveryStatus;

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "fail_delivery_id")
    private FailInspection failInspection;

    /**
     * 연관관계편의메서드
     */

    private void updateFailDelivery(FailInspection failInspection) {
        this.failInspection = failInspection;
        failInspection.updateDelivery(this);
    }

    /**
     * 생성자메서드
     */
    public FailDelivery(Delivery delivery, Address sellerAddress, DeliveryStatus deliveryStatus, FailInspection failInspection) {
        this.delivery = delivery;
        this.sellerAddress = sellerAddress;
        this.deliveryStatus = deliveryStatus;
        if (failInspection != null) {
            updateFailDelivery(failInspection);
        }

    }

    /**
     * 비즈니스로직
     */


}

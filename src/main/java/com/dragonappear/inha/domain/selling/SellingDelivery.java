package com.dragonappear.inha.domain.selling;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.value.Delivery;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class SellingDelivery extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selling_delivery_id")
    private Long id;

    @Column(nullable = false)
    @Embedded
    private Delivery delivery;

    /**
     * 연관관계
     */
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "selling_id")
    private Selling selling;

    /**
     * 연관관계편의메서드
     */

    public void updateSellingDelivery(Selling selling) {
        if(selling!=null){
            this.selling = selling;
            selling.updateSellingDelivery(this);
        }
    }
    /**
     * 생성자 메서드
     */

    public SellingDelivery(Selling selling,Delivery delivery) {
        this.delivery = delivery;
        updateSellingDelivery(selling);
    }

    /**
     * 비즈니스 로직
     */

    public void updateDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}

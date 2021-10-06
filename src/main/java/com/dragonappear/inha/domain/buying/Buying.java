package com.dragonappear.inha.domain.buying;

import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.payment.Payment;
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
public class Buying {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "buying_id")
    private Long id;

    @Enumerated(STRING)
    private BuyingStatus buyingStatus;

    /**
     * 연관관계
     */
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    /**
     * 연관관계편의메서드
     */

    private void updateBuyingPayment(Payment payment) {
        this.payment = payment;
        payment.updatePayment(this);
    }
    /**
     * 생성자메서드
     */

    public Buying(BuyingStatus buyingStatus, Payment payment) {
        this.buyingStatus = buyingStatus;
        if (payment != null) {
            this.payment = payment;
            updateBuyingPayment(payment);
        }
    }


}

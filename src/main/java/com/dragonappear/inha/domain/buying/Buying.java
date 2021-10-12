package com.dragonappear.inha.domain.buying;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.buying.value.BuyingStatus;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.dragonappear.inha.domain.buying.value.BuyingStatus.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Buying extends JpaBaseTimeEntity {
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

    @OneToOne(fetch = LAZY,mappedBy = "buying")
    private Deal deal;

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

    public Buying(Payment payment) {
        this.buyingStatus = 구매중;
        if (payment != null) {
            this.payment = payment;
            updateBuyingPayment(payment);
        }
    }

    /**
     * 비즈니스로직
     */
    public void updateStatus(BuyingStatus buyingStatus) {
        this.buyingStatus = buyingStatus;
        if (buyingStatus == 구매취소) {
            this.payment.cancel();
        }
    }


}

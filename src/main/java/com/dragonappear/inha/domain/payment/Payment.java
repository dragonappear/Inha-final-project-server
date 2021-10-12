package com.dragonappear.inha.domain.payment;

import com.dragonappear.inha.domain.JpaBaseEntity;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.dragonappear.inha.domain.payment.value.PaymentStatus.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Payment extends JpaBaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    @Embedded
    private Money paymentPrice;

    @Column(nullable = false)
    private String buyerName;

    @Column(nullable = false)
    private String buyerEmail;

    @Column(nullable = false)
    private String buyerTel;

    @Column(nullable = false)
    @Embedded
    private Address buyerAddress;

    @Column(nullable = false)
    @Enumerated(STRING)
    private PaymentStatus paymentStatus;

    /**
     * 연관관계
     */

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "auctionitem_id")
    private Auctionitem auctionitem;

    @OneToOne(fetch = LAZY, mappedBy = "payment")
    private Buying buying;

    /**
     * 연관관계편의메서드
     */
    private void updateAuctionitem(Auctionitem auctionitem) {
        if(auctionitem.getAuctionitemStatus()!= AuctionitemStatus.경매중){
            throw new IllegalStateException("이 제품은 경매가 종료되었습니다");
        }
        this.auctionitem = auctionitem;
        auctionitem.updateAuctionitemPayment(this);
    }

    private void updatePaymentUser(User user) {
        this.user = user;
        user.getPayments().add(this);
    }

    public void updatePayment(Buying buying) {
        this.buying = buying;
    }

    /**
     * 생성자메서드
     */

    public Payment(String itemName, Money paymentPrice, String buyerName, String buyerEmail, String buyerTel, Address buyerAddress, User user, Auctionitem auctionitem) {
        this.itemName = itemName;
        this.paymentPrice = paymentPrice;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.buyerTel = buyerTel;
        this.buyerAddress = buyerAddress;
        this.paymentStatus = 결제완료;
        if(user!=null){
            updatePaymentUser(user);
        }
        if (auctionitem != null) {
            updateAuctionitem(auctionitem);
        }


    }

    /**
     * 비즈니스로직
     */

    public void cancel() {
        this.paymentStatus = 결제취소;
    }
}

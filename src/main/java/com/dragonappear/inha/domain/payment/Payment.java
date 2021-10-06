package com.dragonappear.inha.domain.payment;

import com.dragonappear.inha.JpaBaseEntity;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.user.value.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Long paymentPrice;

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

    public Payment(String itemName, Long paymentPrice, String buyerName, String buyerEmail, String buyerTel, Address buyerAddress, PaymentStatus paymentStatus, User user, Auctionitem auctionitem) {
        this.itemName = itemName;
        this.paymentPrice = paymentPrice;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.buyerTel = buyerTel;
        this.buyerAddress = buyerAddress;
        this.paymentStatus = paymentStatus;
        if(user!=null){
            updatePaymentUser(user);
        }
        if (auctionitem != null) {
            updateAuctionitem(auctionitem);
        }


    }


}

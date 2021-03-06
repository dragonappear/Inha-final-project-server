package com.dragonappear.inha.domain.payment;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;

import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
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
public class Payment extends JpaBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(nullable = false)
    private String pgName;

    @Column(nullable = false,unique = true)
    private String impId;

    @Column(nullable = false,unique = true)
    private String merchantId;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "payment_price"))})
    @Column(nullable = false)
    @Embedded
    private Money paymentPrice;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "cancel_price"))})
    @Column(nullable = false)
    @Embedded
    private Money cancelPrice;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "point"))})
    @Column(nullable = false)
    @Embedded
    private Money point;

    @Column(nullable = false)
    @Enumerated(STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private Long addressId;

    /**
     * 연관관계
     */

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "auctionitem_id")
    private Auctionitem auctionitem;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @OneToOne(fetch = LAZY, mappedBy = "payment")
    private Buying buying;

    /**
     * 연관관계편의메서드
     */
    public void updateAuctionitem(Auctionitem auctionitem) {
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

    private void updateItem(Item item) {
        this.item = item;
        item.getPayments().add(this);
    }

    /**
     * 생성자메서드
     */
    @Builder
    public Payment(String pgName, String impId, String merchantId, Money paymentPrice,Money point, User user, Long addressId, Item item) {
        this.pgName = pgName;
        this.impId = impId;
        this.merchantId = merchantId;
        this.paymentPrice = paymentPrice;
        this.cancelPrice = Money.wons(0);
        this.point = point;
        this.paymentStatus = 결제완료;
        this.addressId = addressId;
        if(user!=null){
            updatePaymentUser(user);
        }
        if (item != null) {
            updateItem(item);
        }
    }

    @Builder
    public Payment(String pgName, String impId, String merchantId, Money paymentPrice,Money point, User user, Auctionitem auctionitem,Long addressId) {
        this.pgName = pgName;
        this.impId = impId;
        this.merchantId = merchantId;
        this.paymentPrice = paymentPrice;
        this.cancelPrice = Money.wons(0);
        this.point = point;
        this.paymentStatus = 결제완료;
        this.addressId = addressId;
        if(user!=null){
            updatePaymentUser(user);
        }
        if (auctionitem != null) {
            updateAuctionitem(auctionitem);
            updateItem(auctionitem.getItem());
        }
    }



    /**
     * 비즈니스로직
     */

    public void cancel() {
        this.paymentStatus = 결제취소완료;
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}

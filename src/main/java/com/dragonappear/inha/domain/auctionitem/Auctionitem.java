package com.dragonappear.inha.domain.auctionitem;


import com.dragonappear.inha.JpaBaseEntity;
import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.payment.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.*;

@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Auctionitem extends JpaBaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "auctionitem_id")
    private Long id;

    @Column(nullable = false)
    private Long price;

    @Enumerated(STRING)
    @Column(nullable = false)
    private AuctionitemStatus auctionitemStatus;


    /**
     * 연관관계
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = LAZY,mappedBy = "auctionitem")
    private Payment payment;

    /**
     * 연관관계편의메서드
     */
    private void updateAuctionItem(Item item) {
        this.item = item;
        item.getAuctionitems().add(this);
    }

    public void updateAuctionitemPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * 생성자메서드
     */
    public Auctionitem(Item item, Long price, AuctionitemStatus auctionitemStatus) {
        this.price = price;
        this.auctionitemStatus = auctionitemStatus;
        if (item != null) {
            updateAuctionItem(item);
        }
    }
}

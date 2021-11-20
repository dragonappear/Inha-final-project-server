package com.dragonappear.inha.domain.inspection.pass.settlement;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import com.dragonappear.inha.domain.value.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class Settlement extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "settlement_id")
    private Long id;


    @Column(name = "deal_id",nullable = false)
    private Long dealId;

    @Column(name = "seller_id",nullable = false)
    private Long sellerId;

    @Column(name = "auctionitem_id",nullable = false)
    private Long auctionitemId;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "auctionitem_price"))})
    @Column(nullable = false)
    @Embedded
    private Money auctionitemPrice;

    @AttributeOverrides({ @AttributeOverride(name = "amount", column = @Column(name = "settlement_price"))})
    @Column(nullable = false)
    @Embedded
    private Money settlementPrice;

    /**
     * 연관관계
     */
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "pass_inspection_id")
    private PassInspection passInspection;

    /**
     * 생성자
     */

    @Builder
    public Settlement(Long dealId, Long sellerId, Long auctionitemId, Money auctionitemPrice, Money settlementPrice, PassInspection passInspection) {
        this.dealId = dealId;
        this.sellerId = sellerId;
        this.auctionitemId = auctionitemId;
        this.auctionitemPrice = auctionitemPrice;
        this.settlementPrice = settlementPrice;
        if (passInspection != null) {
            updateInspectionSettlement(passInspection);
        }
    }


    /**
     * 연관관계 편의메서드
     */

    private void updateInspectionSettlement(PassInspection passInspection) {
        this.passInspection = passInspection;
        passInspection.updateSettlement(this);
    }
}

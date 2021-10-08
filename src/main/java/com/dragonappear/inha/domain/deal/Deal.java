package com.dragonappear.inha.domain.deal;

import com.dragonappear.inha.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.selling.Selling;
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
public class Deal extends JpaBaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "deal_id")
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false)
    private DealStatus dealStatus;

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "buying_id")
    private Buying buying;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "selling_id")
    private Selling selling;

    @OneToOne(fetch = LAZY)
    private Inspection inspection;

    /**
     * 연관관계메서드
     */


    /**
     * 생성자메서드
     */
    public Deal(DealStatus dealStatus, Buying buying, Selling selling) {

        this.dealStatus = dealStatus;
        this.buying = buying;
        this.selling = selling;
    }
}
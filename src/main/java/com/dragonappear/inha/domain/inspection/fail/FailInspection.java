package com.dragonappear.inha.domain.inspection.fail;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.inspection.Inspection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@DiscriminatorValue("fail")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class FailInspection extends Inspection {

    public FailInspection(Deal deal) {
        super(deal);
    }

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY,cascade = ALL, mappedBy = "failInspection")
    private FailDelivery failDelivery;


    /**
     * 연관관계편의메서드
     */

    public void updateDelivery(FailDelivery failDelivery) {
        this.failDelivery = failDelivery;
    }


}
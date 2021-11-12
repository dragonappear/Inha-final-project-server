package com.dragonappear.inha.domain.inspection.pass;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.inspection.Inspection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@DiscriminatorValue("pass")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class PassInspection extends Inspection {

    public PassInspection(Deal deal) {
        super(deal);
    }

    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY,cascade = ALL,mappedBy = "passInspection")
    private PassDelivery passDelivery;

    /**
     * 연관관계편의메서드
     */

    public void updateDelivery(PassDelivery passDelivery) {
        this.passDelivery = passDelivery;
    }

}
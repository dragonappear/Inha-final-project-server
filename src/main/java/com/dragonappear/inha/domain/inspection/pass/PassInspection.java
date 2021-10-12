package com.dragonappear.inha.domain.inspection.pass;

import com.dragonappear.inha.domain.JpaBaseTimeEntity;
import com.dragonappear.inha.domain.inspection.Inspection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class PassInspection extends JpaBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "pass_inspection_id")
    private Long id;


    /**
     * 연관관계
     */

    @OneToOne(fetch = LAZY,cascade = ALL,mappedBy = "passInspection")
    private PassDelivery passDelivery;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "inspection_id")
    private Inspection inspection;

    /**
     * 연관관계편의메서드
     */


    private void updatePassInspection(Inspection inspection) {
        this.inspection = inspection;
        inspection.updatePassInspection(this);
    }

    public void updateDelivery(PassDelivery passDelivery) {
        this.passDelivery = passDelivery;
    }

    /**
     * 생성자메서드
     */
    public PassInspection(Inspection inspection) {
        if (inspection != null) {
            updatePassInspection(inspection);
        }
    }

    /**
     * 비즈니스로직
     */


}
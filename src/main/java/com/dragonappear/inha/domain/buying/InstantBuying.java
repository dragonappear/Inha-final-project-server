package com.dragonappear.inha.domain.buying;

import com.dragonappear.inha.domain.payment.Payment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("instant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class InstantBuying extends Buying {
    @Builder
    public InstantBuying(Payment payment) {
        super(payment);
    }
}

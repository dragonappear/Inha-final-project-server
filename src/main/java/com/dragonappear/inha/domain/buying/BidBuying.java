package com.dragonappear.inha.domain.buying;

import com.dragonappear.inha.domain.payment.Payment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

@DiscriminatorValue("bid")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BidBuying extends Buying {
    @Column(name="end_date")
    private LocalDateTime endDate;

    @Builder
    public BidBuying(Payment payment, LocalDateTime endDate) {
        super(payment);
        this.endDate = endDate;
    }
}

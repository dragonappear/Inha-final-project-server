package com.dragonappear.inha.repository.payment;

import com.dragonappear.inha.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}

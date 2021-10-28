package com.dragonappear.inha.service.payment;

import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.payment.value.PaymentStatus;
import com.dragonappear.inha.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    // 결제내역 생성
    @Transactional
    public Long save(Payment payment) {
        return paymentRepository.save(payment).getId();
    }

    // 결제 조회 by 결제아이디
    public Payment findById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 결제내역이 존재하지 않습니다"));
    }

    // 결제 조회 by 유저아이디
    public List<Payment> findByUserId(Long userId) {
        List<Payment> list = paymentRepository.findByUserId(userId);
        if (list.size() == 0) {
            throw new IllegalArgumentException("결제내역이 존재하지 않습니다");
        }
        return list;
    }

    // 결제 조회 by 경매상품아이디
    public Payment findByAuctionItemId(Long auctionItemId) {
        return paymentRepository.findByAuctionItemId(auctionItemId)
                .orElseThrow(()-> new IllegalArgumentException("해당 결제내역이 존재하지 않습니다"));
    }

    // 결제 조회 by 상품이름
    public List<Payment> findByItemName(String itemName) {
        List<Payment> list = paymentRepository.findByItemName(itemName);
        if (list.size() == 0) {
            throw new IllegalArgumentException("결제내역이 존재하지 않습니다");
        }
        return list;
    }

    // 결제완료된 결제 조회 by 경매상품이름
    public List<Payment> findByCompletedItemName(String itemName,PaymentStatus paymentStatus) {
        List<Payment> list = paymentRepository.findByCompletedItemName(itemName, paymentStatus);
        if (list.size() == 0) {
            throw new IllegalArgumentException("해당 결제내역이 존재하지 않습니다");
        }
        return list;
    }

    // 모든 결제내역 조회
    public List<Payment> findAll() {
        List<Payment> list = paymentRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedDate"));
        if (list.size() == 0) {
            throw new IllegalArgumentException("결제내역이 존재하지 않습니다");
        }
        return list;
    }


    /**
     * UPDATE
     */


    // 거래취소 시 결제내역 상태 변경
    @Transactional
    public void cancel(Payment payment) {
        payment.cancel();
    }

}

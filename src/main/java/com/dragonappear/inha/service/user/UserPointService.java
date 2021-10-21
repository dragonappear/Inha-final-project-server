package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.user.UserPointRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserPointService {
    private final UserPointRepository userPointRepository;
    private final UserRepository userRepository;

    /**
     * CREATE
     */


    // 포인트 생성
    @Transactional
    public Long create(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다"));
        UserPoint newPoint = new UserPoint(findUser);
        return userPointRepository.save(newPoint).getId();
    }

    /**
     * READ
     */

    //  포인트 조회 by 포인트 아이디로
    public UserPoint findByPointId(Long userPointId) {
        return userPointRepository.findById(userPointId).orElseThrow(() -> new IllegalStateException("존재하지 않는 포인트입니다"));
    }

    // 포인트 조회 by 유저 아이디로
    public UserPoint findByUserId(Long userId) {
        return userPointRepository.findByUserId(userId).orElseThrow(() -> new IllegalStateException("존재하지 않는 포인트입니다"));
    }

    // 유저 포인트 총합 조회
    public Money getTotal(Long userId) {
        return userPointRepository
                .findByUserId(userId).orElseThrow(() -> new IllegalStateException("존재하지 않는 포인트입니다")).getTotal();
    }

    /**
     * UPDATE
     */

    // 포인트 적립
    @Transactional
    public UserPoint accumulate(UserPoint userPoint, Money amount) throws Exception {
        userPoint.plus(amount.getAmount());
        return userPoint;
    }

    //  포인트 차감
    @Transactional
    public UserPoint subtract(UserPoint userPoint, Money amount) throws Exception {
        userPoint.minus(amount.getAmount());
        return userPoint;
    }


}

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
import java.util.List;

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
    public List<UserPoint> findByUserId(Long userId) {
        return userPointRepository.findByUserId(userId);
    }

    // 유저 포인트 총합 조회
    public Money getTotal(Long userId) {
        List<UserPoint> points = userPointRepository.findByUserId(userId);
        if(points.size()==0){
            throw new IllegalStateException("유저포인트 내역이 존재하지 않습니다");
        }
        return points.get(points.size() - 1).getTotal();
    }

    // 최신 유저 포인트 조회
    public UserPoint findLatestPoint(Long userId) {
        List<UserPoint> points = userPointRepository.findByUserId(userId);
        if(points.size()==0){
            throw new IllegalStateException("유저포인트 내역이 존재하지 않습니다");
        }
        return points.get(points.size() - 1);
    }

    /**
     * UPDATE
     */

    // 포인트 적립
    @Transactional
    public UserPoint accumulate(Long userId, Money amount) throws Exception {
        User findUser = userRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다"));

        List<UserPoint> lists = userPointRepository.findByUserId(findUser.getId());
        return userPointRepository.save(new UserPoint(findUser
                , lists.get(lists.size() - 1).plus(amount.getAmount())));
    }

    //  포인트 차감
    @Transactional
    public UserPoint subtract(Long userId, Money amount) throws Exception {
        System.out.println("amount.getAmount() = " + amount.getAmount());
        User findUser = userRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다"));
        
        List<UserPoint> lists = userPointRepository.findByUserId(findUser.getId());
        return userPointRepository.save(new UserPoint(findUser
                , lists.get(lists.size() - 1).minus(amount.getAmount())));
    }

}

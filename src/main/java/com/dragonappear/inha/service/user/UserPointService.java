package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.domain.user.value.PointStatus;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.exception.user.NotFoundUserIdException;
import com.dragonappear.inha.exception.user.NotFoundUserPointListException;
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
    private final UserService userService;

    /**
     * CREATE
     */

    // 포인트 생성
    @Transactional
    public Long create(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        UserPoint newPoint = new UserPoint(findUser);
        return userPointRepository.save(newPoint).getId();
    }

    /**
     * READ
     */
    //  포인트 조회 by 포인트 아이디로
    public UserPoint findByPointId(Long userPointId) {
        return userPointRepository.findById(userPointId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포인트입니다"));
    }

    // 포인트 조회 by 유저 아이디로
    public List<UserPoint> findByUserId(Long userId) {
        return userPointRepository.findByUserId(userId);
    }

    // 유저 포인트 총합 조회
    public Money getTotal(Long userId)  {
        List<UserPoint> points = userPointRepository.findByUserId(userId);
        if(points.size()==0){
            throw new NotFoundUserPointListException("유저포인트 내역이 존재하지 않습니다");
        }
        return points.get(points.size() - 1).getTotal();
    }

    // 최신 유저 포인트 조회
    public UserPoint findLatestPoint(Long userId) throws IllegalArgumentException {
        List<UserPoint> points = userPointRepository.findByUserId(userId);
        if(points.size()==0){
            throw new IllegalArgumentException("유저포인트 내역이 존재하지 않습니다");
        }
        return points.get(points.size() - 1);
    }

    /**
     * UPDATE
     */

    // 포인트 적립
    @Transactional
    public UserPoint accumulate(Long userId, Money amount) throws NotFoundUserIdException{
        User findUser =userRepository.findById(userId).orElseThrow(() -> new NotFoundUserIdException("유저가 조회되지 않습니다"));
        List<UserPoint> lists = userPointRepository.findByUserId(findUser.getId());
        if (lists.size() == 0) {
            throw new IllegalStateException("유저 포인트 내역이 조회되지 않습니다");
        }
        UserPoint point = lists.get(lists.size() - 1).plus(amount.getAmount());
        return userPointRepository.save(new UserPoint(findUser,point, PointStatus.적립));
    }

    //  포인트 차감
    @Transactional
    public UserPoint subtract(Long userId, Money amount) throws NotFoundUserIdException {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundUserIdException("유저가 조회되지 않습니다"));
        List<UserPoint> lists = userPointRepository.findByUserId(findUser.getId());
        if (lists.size() == 0) {
            throw new IllegalStateException("유저 포인트 내역이 조회되지 않습니다");
        }
        UserPoint point = lists.get(lists.size() - 1).minus(amount.getAmount());
        if (point.getTotal().isLessThan(Money.wons(0L))) {
            throw new IllegalStateException("포인트 사용이 올바르지 않습니다.");
        }
        return userPointRepository.save(new UserPoint(findUser, point, PointStatus.사용));
    }
}

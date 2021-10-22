package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.user.UserPointRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback
class UserPointRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired UserPointRepository userPointRepository;

    @BeforeEach
    public void setUp() {
        User user = new User("username1", "nickname1", "email1", "userTel1");
        userRepository.save(user);
    }

    @Test
    public void 유저포인트_생성조회_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        UserPoint point = new UserPoint(user);
        userPointRepository.save(point);
        //when
        UserPoint findPoint = userPointRepository.findById(point.getId()).get();
        //then
        assertThat(findPoint).isEqualTo(point);
        assertThat(findPoint.getId()).isEqualTo(point.getId());
        assertThat(findPoint.getUser()).isEqualTo(point.getUser());
        assertThat(findPoint.getTotal()).isEqualTo(point.getTotal());
        assertThat(findPoint.getUsed()).isEqualTo(point.getUsed());
        assertThat(findPoint.getEarned()).isEqualTo(point.getEarned());
    }

    @Test
    public void 유저포인트_적립_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        UserPoint point = new UserPoint(user);
        userPointRepository.save(point);
        //when
        UserPoint findPoint = userPointRepository.findById(point.getId()).get();
        findPoint.plus(BigDecimal.valueOf(100L));
        //then
        assertThat(findPoint.getEarned().getAmount()).isEqualTo(Money.wons(1100L).getAmount());
        assertThat(findPoint.getTotal().getAmount()).isEqualTo(Money.wons(1100L).getAmount());
    }

    @Test
    public void 유저포인트_차감_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        UserPoint point = new UserPoint(user);
        userPointRepository.save(point);
        //when
        point.minus(BigDecimal.valueOf(100L));
        //then
        assertThat(point.getUsed().getAmount()).isEqualTo(Money.wons(100L).getAmount());
        assertThat(point.getTotal().getAmount()).isEqualTo(Money.wons(900L).getAmount());
    }


    // 총 포인트보다 차감할 오류 포인트가 많을때
   @Test
    public void 유저포인트_차감오류_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        UserPoint point = new UserPoint(user);
        userPointRepository.save(point);
        //when
        UserPoint findPoint = userPointRepository.findById(point.getId()).get();
        //then
       Assertions.assertThrows(IllegalArgumentException.class, () -> {
           findPoint.minus(BigDecimal.valueOf(1300L));
       });
    }

    @Test
    public void 유저포인트_중복생성_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        UserPoint point = new UserPoint(user);
        userPointRepository.save(point);
        //when
        UserPoint point1 = new UserPoint(user);
        //then
        assertThat(point1).isNotEqualTo(point);
    }

}
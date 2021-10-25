package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.user.UserPointRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserPointServiceTest {
    @Autowired UserRepository userRepository;
    @Autowired UserPointRepository userPointRepository;
    @Autowired UserPointService userPointService;
    @Autowired EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        User newUser1 = new User("name1", "nickname1", "email1@", "userTel11");
        userRepository.save(newUser1);
        User newUser2 = new User("name2", "nickname2", "email2@", "userTel22");
        userRepository.save(newUser2);
    }

    @Test
    public void 유저포인트_생성_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Long id = userPointService.create(findUser.getId());
        //when
        UserPoint findPoint = userPointRepository.findById(id).get();
        //then
        assertThat(findUser.getUserPoints().get(0)).isEqualTo(findPoint);
        assertThat(findPoint.getId()).isEqualTo(id);
        assertThat(findPoint.getTotal()).isEqualTo(Money.wons(1000L));
        assertThat(findPoint.getEarned()).isEqualTo(Money.wons(1000L));
        assertThat(findPoint.getUsed()).isEqualTo(Money.wons(0L));
    }

    @Test
    public void 유저포인트_최신조회_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        userPointService.create(findUser.getId());
        //when
        UserPoint point = userPointService.findLatestPoint(findUser.getId());
        //then
        assertThat(point.getTotal()).isEqualTo(Money.wons(1000L));
        assertThat(point.getEarned()).isEqualTo(Money.wons(1000L));
        assertThat(point.getUsed()).isEqualTo(Money.wons(0L));
    }

    @Test
    public void 유저포인트_포인트아이디로_조회() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        UserPoint userPoint = new UserPoint(findUser);
        userPointRepository.save(userPoint);
        //when
        UserPoint findPoint = userPointService.findByPointId(userPoint.getId());
        //then
        assertThat(findPoint).isEqualTo(userPoint);
        assertThat(findPoint.getId()).isEqualTo(userPoint.getId());
        assertThat(findPoint.getUser()).isEqualTo(userPoint.getUser());
        assertThat(findPoint.getTotal()).isEqualTo(Money.wons(1000L));
        assertThat(findPoint.getEarned()).isEqualTo(Money.wons(1000L));
        assertThat(findPoint.getUsed()).isEqualTo(Money.wons(0L));
    }

    @Test
    public void 유저포인트_적립_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        UserPoint userPoint = new UserPoint(findUser);
        userPointRepository.save(userPoint);
        //when
        UserPoint point = userPointService.accumulate(findUser.getId(), Money.wons(100L));
        //then
        assertThat(point.getTotal().getAmount()).isEqualTo(BigDecimal.valueOf(1100L));
        assertThat(point.getUsed().getAmount()).isEqualTo(BigDecimal.valueOf(0L));
        assertThat(point.getEarned().getAmount()).isEqualTo(BigDecimal.valueOf(1100L));
    }

    @Test
    public void 유저포인트_차감_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        UserPoint userPoint = new UserPoint(findUser);
        userPointRepository.save(userPoint);
        //when
        UserPoint point = userPointService.subtract(findUser.getId(), Money.wons(200L));
        //then
        assertThat(point.getTotal().getAmount()).isEqualTo((Money.wons(800L).getAmount()));
        assertThat(point.getUsed().getAmount()).isEqualTo(Money.wons(200L).getAmount());
        assertThat(point.getEarned().getAmount()).isEqualTo(Money.wons(1000L).getAmount());
    }

}
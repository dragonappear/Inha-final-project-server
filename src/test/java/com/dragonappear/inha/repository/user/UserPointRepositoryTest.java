package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.repository.user.UserPointRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class UserPointRepositoryTest {
    @Autowired UserRepository userRepository;

    @Test
    public void 유저포인트_테스트() throws Exception{
        //given
        UserPoint newUserPoint = new UserPoint(0L, 0L, 0L);
        User user = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678",newUserPoint);
        userRepository.save(user);
        User findUser = userRepository.findById(user.getId()).get();
        //when
        findUser.getUserPoint().plus(50L);

        //then
        assertThat(findUser.getUserPoint().getTotal()).isEqualTo(50L);
        assertThat(findUser.getUserPoint().getUsed()).isEqualTo(0L);
        assertThat(findUser.getUserPoint().getEarned()).isEqualTo(50L);
    }

}
package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.User;
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
@Rollback
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    public void 유저생성_테스트() throws Exception{
        //given
        User newUser = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678");
        //when
        userRepository.save(newUser);
        User findUser = userRepository.findById(newUser.getId()).get();

        //then
        assertThat(findUser).isEqualTo(newUser);
        assertThat(findUser.getId()).isEqualTo(newUser.getId());
        assertThat(findUser.getUsername()).isEqualTo(newUser.getUsername());
        assertThat(findUser.getEmail()).isEqualTo(newUser.getEmail());
        assertThat(findUser.getUserTel()).isEqualTo(newUser.getUserTel());
    }


}
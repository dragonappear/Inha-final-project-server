package com.dragonappear.inha.domain.user;

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
@Rollback(value = false)
class UserTest {

    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    public void 유저생성_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678");
        //when
        User save = userRepository.save(user);
        User findUser = userRepository.findById(user.getId()).get();

        //then
        assertThat(findUser).isEqualTo(user);
        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());

    }


}
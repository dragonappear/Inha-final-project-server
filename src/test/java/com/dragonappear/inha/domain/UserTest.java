package com.dragonappear.inha.domain;

import com.dragonappear.inha.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class UserTest {

    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    public void 유저생성_테스트() throws Exception{
        //given
        User user = new User("사용자1", "사용자1@naver.com");
        //when
        User save = userRepository.save(user);

        em.clear();
        em.flush();

        User findUser = userRepository.findById(user.getId()).get();

        //then
        assertThat(findUser).isEqualTo(user);
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());

    }


}
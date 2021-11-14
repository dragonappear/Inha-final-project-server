package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserCardInfo;
import com.dragonappear.inha.domain.value.Card;
import com.dragonappear.inha.domain.value.CardCompanyName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest
@Transactional
@Rollback
class UserCardInfoRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired UserCardInfoRepository userCardInfoRepository;

    @Test
    public void 유저카드_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(user);
        UserCardInfo userCardInfo = new UserCardInfo(new Card(CardCompanyName.신한카드, "1234-5678"), user);
        //when
        userCardInfoRepository.save(userCardInfo);
        UserCardInfo findUserCardInfo = userCardInfoRepository.findById(userCardInfo.getId()).get();
        //then
        Assertions.assertThat(findUserCardInfo).isEqualTo(userCardInfo);
        Assertions.assertThat(findUserCardInfo.getUserCard().getCardCompanyName()).isEqualTo(CardCompanyName.신한카드);
        Assertions.assertThat(findUserCardInfo.getUserCard().getCardNumber()).isEqualTo("1234-5678");
    }

}
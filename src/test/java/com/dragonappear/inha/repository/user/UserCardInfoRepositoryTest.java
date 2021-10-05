package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserCardInfo;
import com.dragonappear.inha.domain.user.value.Card;
import com.dragonappear.inha.domain.user.value.CardCompanyName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Commit
class UserCardInfoRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired UserCardInfoRepository userCardInfoRepository;
    @Autowired EntityManager em;

    @Test
    public void 유저카드_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678");
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
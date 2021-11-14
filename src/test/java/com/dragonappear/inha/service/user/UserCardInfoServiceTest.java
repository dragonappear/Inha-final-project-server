package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserCardInfo;
import com.dragonappear.inha.domain.value.Card;
import com.dragonappear.inha.domain.value.CardCompanyName;
import com.dragonappear.inha.repository.user.UserCardInfoRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserCardInfoServiceTest {
    @Autowired UserRepository userRepository;
    @Autowired UserCardInfoRepository userCardInfoRepository;
    @Autowired UserCardInfoService userCardInfoService;

    @BeforeEach
    public void setUp() {
        User newUser = new User("name1", "nickname1", "email1@", "userTel11","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(newUser);
        UserCardInfo info1 = new UserCardInfo( new Card(CardCompanyName.신한카드, "1234-1234"), newUser);
        UserCardInfo info2 = new UserCardInfo(new Card(CardCompanyName.신한카드, "1234-2345"), newUser);
        UserCardInfo info3 = new UserCardInfo(new Card(CardCompanyName.신한카드, "1234-3456"), newUser);
        userCardInfoRepository.save(info1);
        userCardInfoRepository.save(info2);
        userCardInfoRepository.save(info3);
    }

    @Test
    public void 유저카드등록_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Card card1 = new Card(CardCompanyName.신한카드, "1234-9876");
        Card card2 = new Card(CardCompanyName.신한카드, "1234-9870");
        UserCardInfo info1 = new UserCardInfo(card1, findUser);
        UserCardInfo info2 = new UserCardInfo(card2, findUser);
        //when
        userCardInfoService.save(info1);
        userCardInfoService.save(info2);
        List<UserCardInfo> all = userCardInfoRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(5);
        assertThat(all).contains(info1,info2);
        assertThat(all).extracting("userCard").contains(card1,card2);
    }

    @Test
    public void 유저카드_리스트조회_유저아이디로_테스트() throws Exception{
        //given
        Long id = userRepository.findAll().get(0).getId();
        //when
        List<UserCardInfo> all = userCardInfoService.findAll(id);
        //then
        assertThat(all.size()).isEqualTo(3);
        assertThat(all).extracting("user").extracting("email").containsOnly("email1@");
        assertThat(all).extracting("userCard")
                .extracting("cardNumber")
                .containsOnly("1234-1234", "1234-2345", "1234-3456");
    }

    @Test
    public void 유저카드_단건조회_카드아이디로_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        UserCardInfo newInfo = new UserCardInfo(new Card(CardCompanyName.신한카드, "1234-9876"), user);
        userCardInfoRepository.save(newInfo);
        //when
        UserCardInfo findInfo = userCardInfoService.findOne(newInfo.getId());
        //then
        assertThat(findInfo).isEqualTo(newInfo);
        assertThat(findInfo.getId()).isEqualTo(newInfo.getId());
        assertThat(findInfo.getUser()).isEqualTo(newInfo.getUser());
        assertThat(findInfo.getUserCard()).isEqualTo(newInfo.getUserCard());
    }

    @Test
    public void 유저카드_단건조회_카드와유저아이디로_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        UserCardInfo newInfo = new UserCardInfo(new Card(CardCompanyName.신한카드, "1234-9876"), user);
        userCardInfoRepository.save(newInfo);
        //when
        UserCardInfo findInfo = userCardInfoService.findOne(user.getId(), newInfo.getId());
        //then
        assertThat(findInfo).isEqualTo(newInfo);
        assertThat(findInfo.getId()).isEqualTo(newInfo.getId());
        assertThat(findInfo.getUser()).isEqualTo(newInfo.getUser());
        assertThat(findInfo.getUserCard()).isEqualTo(newInfo.getUserCard());
    }
}
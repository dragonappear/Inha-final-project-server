package com.dragonappear.inha.repository.user.inquiry;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.value.InquiryStatus;
import com.dragonappear.inha.domain.user.value.InquiryType;
import com.dragonappear.inha.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserInquiryRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserInquiryRepository userInquiryRepository;

    @Test
    public void 유저질문생성_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(user);
        UserInquiry userInquiry = new UserInquiry(user, InquiryType.배송, "언제 배송출발하나요?", "빨리 배송좀해주세요");
        userInquiryRepository.save(userInquiry);
        //when
        UserInquiry findInquiry = userInquiryRepository.findById(userInquiry.getId()).get();
        //then
        assertThat(findInquiry).isEqualTo(userInquiry);
        assertThat(findInquiry.getId()).isEqualTo(userInquiry.getId());
        assertThat(findInquiry.getUser()).isEqualTo(userInquiry.getUser());
        assertThat(findInquiry.getInquiryType()).isEqualTo(userInquiry.getInquiryType());
        assertThat(findInquiry.getTitle()).isEqualTo(userInquiry.getTitle());
        assertThat(findInquiry.getContent()).isEqualTo(userInquiry.getContent());
    }

}
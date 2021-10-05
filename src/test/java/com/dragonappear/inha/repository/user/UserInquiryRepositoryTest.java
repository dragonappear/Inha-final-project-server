package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserInquiry;
import com.dragonappear.inha.domain.user.value.InquiryStatus;
import com.dragonappear.inha.domain.user.value.InquiryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserInquiryRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired UserInquiryRepository userInquiryRepository;

    @Test
    public void 유저질문생성_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678");
        userRepository.save(user);
        UserInquiry userInquiry = new UserInquiry(user, InquiryType.배송, "언제 배송출발하나요?", "빨리 배송좀해주세요", InquiryStatus.답변미완료);
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
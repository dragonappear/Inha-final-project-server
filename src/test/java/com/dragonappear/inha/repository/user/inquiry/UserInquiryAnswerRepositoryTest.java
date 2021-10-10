package com.dragonappear.inha.repository.user.inquiry;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.inquiry.UserInquiryAnswer;
import com.dragonappear.inha.domain.user.value.InquiryStatus;
import com.dragonappear.inha.domain.user.value.InquiryType;
import com.dragonappear.inha.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserInquiryAnswerRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserInquiryRepository userInquiryRepository;
    @Autowired
    UserInquiryAnswerRepository userInquiryAnswerRepository;

    @Test
    public void 유저질문답변생성_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678");
        userRepository.save(user);
        UserInquiry inquiry = new UserInquiry(user, InquiryType.배송, "언제 배송출발하나요?", "빨리 배송좀해주세요");
        userInquiryRepository.save(inquiry);
        UserInquiryAnswer answer = new UserInquiryAnswer("기다려라 좀", inquiry);
        userInquiryAnswerRepository.save(answer);
        //when
        UserInquiryAnswer findAnswer = userInquiryAnswerRepository.findById(answer.getId()).get();
        //then
        assertThat(findAnswer).isEqualTo(answer);
        assertThat(findAnswer.getUserInquiry()).isEqualTo(answer.getUserInquiry());
        assertThat(findAnswer.getUserInquiry().getInquiryStatus()).isEqualTo(InquiryStatus.답변완료);
        assertThat(findAnswer.getId()).isEqualTo(answer.getId());
        assertThat(findAnswer.getContent()).isEqualTo(answer.getContent());

    }

}
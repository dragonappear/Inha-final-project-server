package com.dragonappear.inha.service.user.inquiry;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.inquiry.UserInquiryAnswer;
import com.dragonappear.inha.domain.user.value.InquiryStatus;
import com.dragonappear.inha.repository.user.UserRepository;
import com.dragonappear.inha.repository.user.inquiry.UserInquiryAnswerRepository;
import com.dragonappear.inha.repository.user.inquiry.UserInquiryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dragonappear.inha.domain.user.value.InquiryStatus.*;
import static com.dragonappear.inha.domain.user.value.InquiryType.배송;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserInquiryAnswerServiceTest {
    @Autowired UserInquiryAnswerRepository userInquiryAnswerRepository;
    @Autowired UserInquiryAnswerService userInquiryAnswerService;
    @Autowired UserRepository userRepository;
    @Autowired UserInquiryRepository userInquiryRepository;
    @BeforeEach

    public void setUp() {
        User newUser = new User("name1", "nickname1", "email1@", "userTel11");
        userRepository.save(newUser);
        User newUser1 = new User("name1", "nickname1", "email2@", "userTel22");
        userRepository.save(newUser1);

        UserInquiry userInquiry = new UserInquiry(newUser, 배송, "title1", "content1");
        userInquiryRepository.save(userInquiry);

        UserInquiry userInquiry1 = new UserInquiry(newUser1, 배송, "title1", "content1");
        userInquiryRepository.save(userInquiry1);
    }

    @Test
    public void 답변_등록_테스트() throws Exception{
        //given
        UserInquiry userInquiry = userInquiryRepository.findAll().get(0);
        UserInquiryAnswer answer = new UserInquiryAnswer("알아서해라", userInquiry);
        //when
        userInquiryAnswerService.save(answer);
        UserInquiryAnswer find = userInquiryAnswerRepository.findById(answer.getId()).get();
        //then
        assertThat(find).isEqualTo(answer);
        assertThat(find.getId()).isEqualTo(answer.getId());
        assertThat(find.getContent()).isEqualTo(answer.getContent());
        assertThat(find.getUserInquiry()).isEqualTo(answer.getUserInquiry());
        assertThat(find.getUserInquiry().getInquiryStatus()).isEqualTo(답변완료);
    }

    @Test
    public void 답변_수정_테스트() throws Exception{
        //given
        UserInquiry userInquiry = userInquiryRepository.findAll().get(0);
        UserInquiryAnswer answer = new UserInquiryAnswer("알아서해라", userInquiry);
        userInquiryAnswerRepository.save(answer);
        //when
        userInquiryAnswerService.update(answer, "알아서좀해라");
        UserInquiryAnswer find = userInquiryAnswerRepository.findById(answer.getId()).get();
        //then
        assertThat(find).isEqualTo(answer);
        assertThat(find.getId()).isEqualTo(answer.getId());
        assertThat(find.getContent()).isEqualTo("알아서좀해라");
        assertThat(find.getUserInquiry()).isEqualTo(answer.getUserInquiry());
        assertThat(find.getUserInquiry().getInquiryStatus()).isEqualTo(답변완료);
    }

    @Test
    public void 답변_삭제_테스트() throws Exception{
        //given
        UserInquiry userInquiry = userInquiryRepository.findAll().get(0);
        UserInquiryAnswer answer = new UserInquiryAnswer("알아서해라", userInquiry);
        //when
        userInquiryAnswerService.delete(answer);
        //then
        assertThat(userInquiry.getUserInquiryAnswer()).isNull();
    }
    @Test
    public void 모든답변_조회_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        UserInquiry userInquiry = userInquiryRepository.findAll().get(0);
        UserInquiryAnswer answer = new UserInquiryAnswer("알아서해라", userInquiry);
        userInquiryAnswerRepository.save(answer);

        User user1 = userRepository.findAll().get(1);
        UserInquiry userInquiry1 = userInquiryRepository.findAll().get(1);
        UserInquiryAnswer answer1 = new UserInquiryAnswer("알아서해라1", userInquiry1);
        userInquiryAnswerRepository.save(answer1);
        //when
        List<UserInquiryAnswer> all = userInquiryAnswerService.findAll();
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).extracting("content").containsOnly("알아서해라", "알아서해라1");
        assertThat(all).extracting("userInquiry").containsOnly(userInquiry,userInquiry1);
        assertThat(all).extracting("userInquiry").extracting("user").containsOnly(user,user1);
    }

    @Test
    public void 답변조회_유저아이디로_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        UserInquiry userInquiry = userInquiryRepository.findAll().get(0);
        UserInquiryAnswer answer = new UserInquiryAnswer("알아서해라", userInquiry);
        userInquiryAnswerRepository.save(answer);

        UserInquiry userInquiry1 = userInquiryRepository.findAll().get(1);
        UserInquiryAnswer answer1 = new UserInquiryAnswer("알아서해라1", userInquiry1);
        userInquiryAnswerRepository.save(answer1);
        //when
        List<UserInquiryAnswer> all = userInquiryAnswerService.findByUserId(user);
        //then
        assertThat(all.size()).isEqualTo(1);
        assertThat(all).extracting("content").containsOnly("알아서해라");
    }

    @Test
    public void 답변조회_답변아이디로_테스트() throws Exception{
        //given
        User user = userRepository.findAll().get(0);
        UserInquiry userInquiry = userInquiryRepository.findAll().get(0);
        UserInquiryAnswer answer = new UserInquiryAnswer("알아서해라", userInquiry);
        userInquiryAnswerRepository.save(answer);
        //when
        UserInquiryAnswer find = userInquiryAnswerService.findByAnswerId(answer);
        //then
        assertThat(find).isEqualTo(answer);
        assertThat(find.getId()).isEqualTo(answer.getId());
        assertThat(find.getUserInquiry()).isEqualTo(answer.getUserInquiry());
        assertThat(find.getContent()).isEqualTo(answer.getContent());
    }
}
package com.dragonappear.inha.service.user.inquiry;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.value.InquiryStatus;
import com.dragonappear.inha.domain.user.value.InquiryType;
import com.dragonappear.inha.repository.user.UserRepository;
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
import static com.dragonappear.inha.domain.user.value.InquiryType.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserInquiryServiceTest {
    @Autowired UserInquiryRepository userInquiryRepository;
    @Autowired UserRepository userRepository;
    @Autowired UserInquiryService userInquiryService;

    @BeforeEach
    public void setUp() {
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678");
        userRepository.save(user);
    }
    // 유저질문등록
    @Test
    public void 유저질문_등록_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        UserInquiry userInquiry = new UserInquiry(findUser, 배송, "title1", "content1");
        UserInquiry userInquiry1 = new UserInquiry(findUser, 배송, "title2", "content2");
        //when
        userInquiryService.save(userInquiry);
        userInquiryService.save(userInquiry1);
        //then
        assertThat(findUser.getUserInquiries().size()).isEqualTo(2);
        assertThat(findUser.getUserInquiries()).extracting("title").containsOnly("title1", "title2");
        assertThat(findUser.getUserInquiries()).extracting("content").containsOnly("content1", "content2");
        assertThat(findUser.getUserInquiries()).extracting("inquiryType").containsOnly(배송);
        assertThat(findUser.getUserInquiries()).extracting("inquiryStatus").containsOnly(답변미완료);
    }
    // 유저질문수정
    @Test
    public void 유저질문_수정_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        UserInquiry userInquiry = new UserInquiry(findUser, 배송, "title1", "content1");
        userInquiryRepository.save(userInquiry);
        //when
        userInquiryService.change(userInquiry, 주문, "title2", "content2");
        //then
        assertThat(userInquiry.getInquiryType()).isEqualTo(주문);
        assertThat(userInquiry.getTitle()).isEqualTo("title2");
        assertThat(userInquiry.getContent()).isEqualTo("content2");
    }

    // 유저질문삭제
    @Test
    public void 유저질문_삭제_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        UserInquiry userInquiry1 = new UserInquiry(findUser, 배송, "title1", "content1");
        UserInquiry userInquiry2 = new UserInquiry(findUser, 주문, "title2", "content2");
        userInquiryRepository.save(userInquiry1);
        userInquiryRepository.save(userInquiry2);
        //when
        userInquiryService.delete(userInquiry1);
        List<UserInquiry> all = userInquiryRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(1);
        assertThat(all).containsOnly(userInquiry2);
        assertThat(all).extracting("title").containsOnly("title2");
        assertThat(all).extracting("content").containsOnly("content2");
        assertThat(all).extracting("inquiryType").containsOnly(주문);
    }

    // 유저질문리스트조회
    @Test
    public void 유저모든질문_조회_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        UserInquiry userInquiry1 = new UserInquiry(findUser, 배송, "title1", "content1");
        UserInquiry userInquiry2 = new UserInquiry(findUser, 주문, "title2", "content2");
        userInquiryRepository.save(userInquiry1);
        userInquiryRepository.save(userInquiry2);
        //when
        List<UserInquiry> all = userInquiryService.findAll();
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).containsOnly(userInquiry1,userInquiry2);
        assertThat(all).extracting("title").containsOnly("title1","title2");
        assertThat(all).extracting("content").containsOnly("content1","content2");
        assertThat(all).extracting("inquiryType").containsOnly(배송,주문);
    }

    // 유저질문조회 by 유저아이디
    @Test
    public void 유저질문_유저아이디로_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        UserInquiry userInquiry1 = new UserInquiry(findUser, 배송, "title1", "content1");
        UserInquiry userInquiry2 = new UserInquiry(findUser, 주문, "title2", "content2");
        userInquiryRepository.save(userInquiry1);
        userInquiryRepository.save(userInquiry2);
        //when
        List<UserInquiry> all = userInquiryService.findAll(findUser.getId());
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).containsOnly(userInquiry1,userInquiry2);
        assertThat(all).extracting("title").containsOnly("title1","title2");
        assertThat(all).extracting("content").containsOnly("content1","content2");
        assertThat(all).extracting("inquiryType").containsOnly(배송,주문);
    }
    // 유저질문조회 by 질문아이디

    @Test
    public void 유저질문조회_질문아이디로_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        UserInquiry userInquiry1 = new UserInquiry(findUser, 배송, "title1", "content1");
        userInquiryRepository.save(userInquiry1);
        //when
        UserInquiry findInquiry = userInquiryService.findOne(userInquiry1.getId());
        //then
        assertThat(findInquiry).isEqualTo(userInquiry1);
        assertThat(findInquiry.getId()).isEqualTo(userInquiry1.getId());
        assertThat(findInquiry.getInquiryType()).isEqualTo(userInquiry1.getInquiryType());
        assertThat(findInquiry.getTitle()).isEqualTo(userInquiry1.getTitle());
        assertThat(findInquiry.getContent()).isEqualTo(userInquiry1.getContent());
        assertThat(findInquiry.getUser()).isEqualTo(userInquiry1.getUser());
    }

    // 유저질문조회 by 유저아이디 그리고 질문아이디
    @Test
    public void 유저질문조회_유저그리고질문아이디로_테스트() throws Exception{
        User findUser = userRepository.findAll().get(0);
        UserInquiry userInquiry1 = new UserInquiry(findUser, 배송, "title1", "content1");
        userInquiryRepository.save(userInquiry1);
        //when
        UserInquiry findInquiry = userInquiryService.findOne(findUser.getId(),userInquiry1.getId());
        //then
        assertThat(findInquiry).isEqualTo(userInquiry1);
        assertThat(findInquiry.getId()).isEqualTo(userInquiry1.getId());
        assertThat(findInquiry.getInquiryType()).isEqualTo(userInquiry1.getInquiryType());
        assertThat(findInquiry.getTitle()).isEqualTo(userInquiry1.getTitle());
        assertThat(findInquiry.getContent()).isEqualTo(userInquiry1.getContent());
        assertThat(findInquiry.getUser()).isEqualTo(userInquiry1.getUser());
    }
}
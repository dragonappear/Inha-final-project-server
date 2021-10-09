package com.dragonappear.inha.repository.user.inquiry;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.inquiry.UserInquiryImage;
import com.dragonappear.inha.domain.user.value.InquiryStatus;
import com.dragonappear.inha.domain.user.value.InquiryType;
import com.dragonappear.inha.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class UserInquiryImageRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserInquiryRepository userInquiryRepository;
    @Autowired
    UserInquiryImageRepository userInquiryImageRepository;
    @Test
    public void 유저질문이미지생성_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678");
        userRepository.save(user);
        UserInquiry userInquiry = new UserInquiry(user, InquiryType.배송, "언제 배송출발하나요?", "빨리 배송좀해주세요", InquiryStatus.답변미완료);
        userInquiryRepository.save(userInquiry);
        UserInquiryImage newImage = new UserInquiryImage(userInquiry, "name1", "oriName1", "url1");
        userInquiryImageRepository.save(newImage);
        //when
        UserInquiryImage findImage = userInquiryImageRepository.findById(newImage.getId()).get();
        //then
        assertThat(findImage).isEqualTo(newImage);
        assertThat(findImage.getId()).isEqualTo(newImage.getId());
        assertThat(findImage.getUserInquiry()).isEqualTo(newImage.getUserInquiry());
        assertThat(findImage.getFileName()).isEqualTo(newImage.getFileName());
        assertThat(findImage.getFileUrl()).isEqualTo(newImage.getFileUrl());
        assertThat(findImage.getFileOriName()).isEqualTo(newImage.getFileOriName());
    }

}
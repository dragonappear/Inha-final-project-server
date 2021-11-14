package com.dragonappear.inha.service.user.inquiry;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.inquiry.UserInquiryImage;
import com.dragonappear.inha.domain.user.value.InquiryType;
import com.dragonappear.inha.repository.user.UserRepository;
import com.dragonappear.inha.repository.user.inquiry.UserInquiryImageRepository;
import com.dragonappear.inha.repository.user.inquiry.UserInquiryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.dragonappear.inha.domain.user.value.InquiryStatus.답변미완료;
import static com.dragonappear.inha.domain.user.value.InquiryType.*;
import static com.dragonappear.inha.domain.user.value.InquiryType.배송;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserInquiryImageServiceTest {
    @Autowired UserInquiryImageService userInquiryImageService;
    @Autowired UserInquiryImageRepository userInquiryImageRepository;
    @Autowired UserInquiryRepository userInquiryRepository;
    @Autowired UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(user);
        UserInquiry userInquiry = new UserInquiry(user, 배송, "title1", "content1");
        userInquiryRepository.save(userInquiry);
        UserInquiry userInquiry1 = new UserInquiry(user, 주문, "title2", "content2");
        userInquiryRepository.save(userInquiry1);
        UserInquiryImage image1 = new UserInquiryImage(userInquiry1, "filename1", "fileoriname1", "fileurl1");
        UserInquiryImage image2 = new UserInquiryImage(userInquiry1, "filename2", "fileoriname2", "fileurl2");
        UserInquiryImage image3 = new UserInquiryImage(userInquiry1, "filename3", "fileoriname3", "fileurl3");
        UserInquiryImage image4 = new UserInquiryImage(userInquiry1, "filename4", "fileoriname4", "fileurl4");
        List<UserInquiryImage> images = Arrays.asList(image1, image2, image3, image4);
        for (UserInquiryImage image : images) {
            userInquiryImageRepository.save(image);
        }
    }
    
    @Test
    public void 질문이미지_리스트추가_테스트() throws Exception{
        //given
        UserInquiry userInquiry = userInquiryRepository.findAll().get(0);
        UserInquiryImage image1 = new UserInquiryImage(userInquiry, "filename1", "fileoriname1", "fileurl1");
        UserInquiryImage image2 = new UserInquiryImage(userInquiry, "filename2", "fileoriname2", "fileurl2");
        UserInquiryImage image3 = new UserInquiryImage(userInquiry, "filename3", "fileoriname3", "fileurl3");
        UserInquiryImage image4 = new UserInquiryImage(userInquiry, "filename4", "fileoriname4", "fileurl4");
        UserInquiryImage image5 = new UserInquiryImage(userInquiry, "filename5", "fileoriname5", "fileurl5");
        List<UserInquiryImage> images = Arrays.asList(image1, image2, image3, image4,image5);
        //when
        userInquiryImageService.save(images);
        List<UserInquiryImage> all = userInquiryImageRepository.findAll();
        //then
        assertThat(all).contains(image1, image2, image3, image4,image5);
        assertThat(all.size()).isEqualTo(9);
    }
    
    @Test
    public void 질문이미지_단건생성_테스트() throws Exception{
        //given
        UserInquiry userInquiry = userInquiryRepository.findAll().get(1);
        UserInquiryImage image1 = new UserInquiryImage(userInquiry, "filename5", "fileoriname5", "fileurl5");
        //when
        userInquiryImageService.save(image1);
        List<UserInquiryImage> all = userInquiryImageRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(5);
        assertThat(all).extracting("fileName")
                .containsOnly("filename1", "filename2", "filename3", "filename4", "filename5");
        assertThat(all).extracting("fileOriName")
                .containsOnly("fileoriname1", "fileoriname2", "fileoriname3", "fileoriname4", "fileoriname5");
        assertThat(all).extracting("fileUrl")
                .containsOnly("fileurl1", "fileurl2", "fileurl3", "fileurl4", "fileurl5");
    }
    
    @Test
    public void 질문이미지_삭제_테스트() throws Exception{
        //given
        UserInquiry userInquiry = userInquiryRepository.findAll().get(1);
        List<UserInquiryImage> all = userInquiryImageRepository.findAll();
        //when
        userInquiryImageService.delete(all.get(0));
        List<UserInquiryImage> all1 = userInquiryImageRepository.findAll();
        //then
        assertThat(all1.size()).isEqualTo(3);
        assertThat(all1).extracting("userInquiry").containsOnly(userInquiry);
    }

    @Test
    public void 질문이미지조회_질문아이디로_테스트() throws Exception{
        //given
        UserInquiry userInquiry = userInquiryRepository.findAll().get(1);
        //when
        List<UserInquiryImage> all = userInquiryImageService.findByInquiryId(userInquiry.getId());
        //then
        assertThat(all.size()).isEqualTo(4);
        assertThat(all).extracting("fileName")
                .containsOnly("filename1", "filename2", "filename3", "filename4");
        assertThat(all).extracting("fileOriName")
                .containsOnly("fileoriname1", "fileoriname2", "fileoriname3", "fileoriname4");
        assertThat(all).extracting("fileUrl")
                .containsOnly("fileurl1", "fileurl2", "fileurl3", "fileurl4");
    }

    @Test
    public void 질문이미지조회_이미지아이디로_테스트() throws Exception{
        //given
        UserInquiry userInquiry = userInquiryRepository.findAll().get(1);
        UserInquiryImage newImage = new UserInquiryImage(userInquiry, "filename5", "fileoriname5", "fileurl5");
        userInquiryImageRepository.save(newImage);
        //when
        UserInquiryImage findImage = userInquiryImageService.findByImageId(newImage.getId());
        //then
        assertThat(findImage).isEqualTo(newImage);
        assertThat(findImage.getId()).isEqualTo(newImage.getId());
        assertThat(findImage.getUserInquiry()).isEqualTo(newImage.getUserInquiry());
    }

}
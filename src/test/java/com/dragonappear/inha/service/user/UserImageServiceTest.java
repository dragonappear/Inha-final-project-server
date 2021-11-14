package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserImage;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.repository.user.UserImageRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserImageServiceTest {
    @Autowired UserImageService userImageService;
    @Autowired UserImageRepository userImageRepository;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @BeforeEach
    public void setUp() {
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(user);
    }

    @Test
    public void 유저이미지_생성_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Image newImage = new Image("filename1", "fileoriname1", "fileurl1");
        //when
        userImageService.update(findUser, newImage);
        //then
        assertThat(findUser.getUserImage()).isNotNull();
        assertThat(findUser.getUserImage().getImage()).isEqualTo(newImage);
        assertThat(findUser.getUserImage().getUser()).isEqualTo(findUser);
    }

    @Test
    public void 유저이미지_수정_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Image newImage = new Image("filename1", "fileoriname1", "fileurl1");
        userImageRepository.save(new UserImage(findUser, newImage));
        Image newImage1 = new Image("filename2", "fileoriname2", "fileurl2");
        //when
        userImageService.update(findUser, newImage1);
        //then
        assertThat(findUser.getUserImage()).isNotNull();
        assertThat(findUser.getUserImage().getImage()).isEqualTo(newImage1);
        assertThat(findUser.getUserImage().getUser()).isEqualTo(findUser);
    }

    @Test
    public void 유저이미지조회_유저아이디로_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Image newImage = new Image("filename1", "fileoriname1", "fileurl1");
        UserImage saveImage = userImageRepository.save(new UserImage(findUser, newImage));
        //when
        UserImage findImage = userImageService.findByUserId(findUser.getId());
        //then
        assertThat(findImage).isEqualTo(saveImage);
        assertThat(findImage.getId()).isEqualTo(saveImage.getId());
        assertThat(findImage.getUser()).isEqualTo(saveImage.getUser());
        assertThat(findImage.getImage()).isEqualTo(saveImage.getImage());
    }

    @Test
    public void 유저이미지조회_이미지아이디로_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Image newImage = new Image("filename1", "fileoriname1", "fileurl1");
        UserImage saveImage = userImageRepository.save(new UserImage(findUser, newImage));
        //when
        UserImage findImage = userImageService.findByImageId(saveImage.getId());
        //then
        assertThat(findImage).isEqualTo(saveImage);
        assertThat(findImage.getId()).isEqualTo(saveImage.getId());
        assertThat(findImage.getUser()).isEqualTo(saveImage.getUser());
        assertThat(findImage.getImage()).isEqualTo(saveImage.getImage());
    }


    @Test
    public void 유저이미지_삭제_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Image newImage = new Image("filename1", "fileoriname1", "fileurl1");
        UserImage saveImage = userImageRepository.save(new UserImage(findUser, newImage));
        //when
        userImageService.delete(findUser);
        //then
        assertThat(findUser.getUserImage()).isNull();
    }

}
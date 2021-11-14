package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserImage;
import com.dragonappear.inha.domain.value.Image;
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
class UserImageRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired UserImageRepository userImageRepository;

    @Test
    public void 유저이미지생성_테스트() throws Exception{
        //given
        User newUser = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(newUser);
        Image image = new Image("name1", "oriName1", "url1");
        UserImage newUserImage = new UserImage(newUser, image);
        userImageRepository.save(newUserImage);
        //when
        UserImage findUserImage = userImageRepository.findById(newUserImage.getId()).get();
        //then
        assertThat(findUserImage).isEqualTo(newUserImage);
        assertThat(findUserImage.getId()).isEqualTo(newUserImage.getId());
        assertThat(findUserImage.getUser()).isEqualTo(newUserImage.getUser());
        assertThat(findUserImage.getImage().getFileName()).isEqualTo(newUserImage.getImage().getFileName());
        assertThat(findUserImage.getImage().getFileUrl()).isEqualTo(newUserImage.getImage().getFileUrl());
        assertThat(findUserImage.getImage().getFileOriName()).isEqualTo(newUserImage.getImage().getFileOriName());
    }
}
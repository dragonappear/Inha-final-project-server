package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserImage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class UserImageRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired UserImageRepository userImageRepository;

    @Test
    public void 유저이미지생성_테스트() throws Exception{
        //given
        User newUser = new User("사용자1", "yyh", "사용자1@naver.com","010-1234-5678");
        userRepository.save(newUser);
        UserImage newUserImage = new UserImage(newUser, "name1", "oriName1", "url1");
        userImageRepository.save(newUserImage);
        //when
        UserImage findUserImage = userImageRepository.findById(newUserImage.getId()).get();
        //then
        assertThat(findUserImage).isEqualTo(newUserImage);
        assertThat(findUserImage.getId()).isEqualTo(newUserImage.getId());
        assertThat(findUserImage.getUser()).isEqualTo(newUserImage.getUser());
        assertThat(findUserImage.getFileName()).isEqualTo(newUserImage.getFileName());
        assertThat(findUserImage.getFileUrl()).isEqualTo(newUserImage.getFileUrl());
        assertThat(findUserImage.getFileOriName()).isEqualTo(newUserImage.getFileOriName());
    }
}
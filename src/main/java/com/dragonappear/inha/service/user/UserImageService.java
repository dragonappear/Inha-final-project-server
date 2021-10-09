package com.dragonappear.inha.service.user;


import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserImage;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.repository.user.UserImageRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserImageService {
    private final UserImageRepository userImageRepository;
    private final UserRepository userRepository;

    // 유저이미지 생성 or 업데이트
    public void update(User user, Image image) {
        if (user.getUserImage()!=null) {
            user.getUserImage().changeImage(image);
        }
        else{
            userImageRepository.save(new UserImage(user, image));
        }
    }

   @Transactional
    // 유저이미지 삭제
    public void delete(User user) {
        user.updateUserImage(null);
        userImageRepository.deleteByUserId(user.getId());
    }

    // 유저이미지 조회 by 이미지아이디
    public UserImage findByImageId(Long imageId) {
        return userImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalStateException("이미지가 없습니다."));
    }

    // 유저이미지 조회 by 유저 아이디
    public UserImage findByUserId(Long userId) {
        return userImageRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("이미지가 없습니다."));
    }
}

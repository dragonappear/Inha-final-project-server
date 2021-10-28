package com.dragonappear.inha.service.user;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserImage;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.repository.user.UserImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserImageService {
    private final UserImageRepository userImageRepository;


    /**
     * CREATE
     */
    // 유저이미지 생성 or 업데이트
    @Transactional
    public String update(User user, Image image) {
        if (user.getUserImage()!=null) {
            user.getUserImage().changeImage(image);
        }
        else{
            userImageRepository.save(new UserImage(user, image));
        }
        return image.getFileOriName();
    }

    /**
     * 유저이미지 조회
     */

    // 유저이미지 조회 by 이미지아이디
    public UserImage findByImageId(Long imageId) {
        return userImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이미지입니다."));
    }

    // 유저이미지 조회 by 유저 아이디
    public UserImage findByUserId(Long userId) {
        return userImageRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이미지입니다."));
    }

    // 유저 이미지 Url 조회
    public String getFileUrl(Long userId) {
        return userImageRepository.findByUserId(userId)
                .orElse(new UserImage(null,new Image(null,"NequEEEQWEeqweZXCZXCZASDsitas.png",null)))
                .getImage().getFileOriName();
    }

    /**
     * DELETE
     */

    // 유저이미지 삭제
    @Transactional
    public void delete(User user) {
        user.updateUserImage(null);
        userImageRepository.deleteByUserId(user.getId());
    }
}

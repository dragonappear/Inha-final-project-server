package com.dragonappear.inha.service.user.inquiry;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserImage;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.inquiry.UserInquiryImage;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.repository.user.UserImageRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import com.dragonappear.inha.repository.user.inquiry.UserInquiryImageRepository;
import com.dragonappear.inha.repository.user.inquiry.UserInquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserInquiryImageService {
    private final UserInquiryRepository userInquiryRepository;
    private final UserInquiryImageRepository userInquiryImageRepository;

    // 유저질문이미지 단건 추가
    public void save(UserInquiryImage image) {
        UserInquiry inquiry = image.getUserInquiry();
        if (inquiry.getUserInquiryImages().size()>=6) {
            throw new IllegalStateException("이미지는 5개까지 저장가능합니다");
        }
        userInquiryImageRepository.save(image);
    }

    // 유저질문이미지 리스트로 추가
    public void save(List<UserInquiryImage> images) {
        UserInquiry inquiry = images.get(0).getUserInquiry();
        if (images.size()>=6) {
            throw new IllegalStateException("이미지는 5개까지 저장가능합니다");
        } else if (inquiry.getUserInquiryImages().size()>= 6) {
            throw new IllegalStateException("이미지는 5개까지 저장가능합니다");
        }
        for (UserInquiryImage image : images) {
            userInquiryImageRepository.save(image);
        }
    }

    @Transactional
    // 유저질문이미지 삭제
    public void delete(UserInquiryImage image) {
        image.getUserInquiry().getUserInquiryImages().remove(image);
        userInquiryImageRepository.delete(image.getId());
    }

    // 유저질문이미지 조회 by 질문아이디
    public List<UserInquiryImage> findByInquiryId(Long inquiryId) {
        return userInquiryImageRepository.findByInquiryId(inquiryId);
    }

    // 유저질문이미지 조회 by 이미지아이디
    public UserInquiryImage findByImageId(Long imageId) {
        return userInquiryImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalStateException("해당 이미지가 존재하지 않습니다"));
    }

}

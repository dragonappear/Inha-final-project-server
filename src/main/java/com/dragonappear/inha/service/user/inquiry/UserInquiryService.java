package com.dragonappear.inha.service.user.inquiry;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.value.InquiryType;
import com.dragonappear.inha.repository.user.inquiry.UserInquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserInquiryService {
    @Autowired UserInquiryRepository userInquiryRepository;

    // 유저질문등록
    public Long save(UserInquiry userInquiry) {
       return userInquiryRepository.save(userInquiry).getId();
    }

    // 유저질문수정
    public Long change(UserInquiry userInquiry,InquiryType inquiryType,String title,String content) {
        userInquiry.changeInquiry(inquiryType,title,content);
        return userInquiry.getId();
    }

    // 유저질문삭제
    public void delete(UserInquiry userInquiry) {
        userInquiry.getUser().getUserInquiries().remove(userInquiry);
        userInquiryRepository.delete(userInquiry);
    }

    // 유저질문리스트조회
    public List<UserInquiry> findAll() {
        return userInquiryRepository.findAll();
    }

    // 유저질문조회 by 유저아이디
    public List<UserInquiry> findAll(Long userId) {
        return userInquiryRepository.findAllByUserId(userId);
    }

    // 유저질문조회 by 질문아이디
    public UserInquiry findOne(Long inquiryId) {
        return userInquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalStateException("질문이 존재하지 않습니다."));
    }

    // 유저질문조회 by 유저아이디 그리고 질문아이디
    public UserInquiry findOne(Long userId, Long inquiryId) {
        return userInquiryRepository.findByUserIdAndInquiryId(userId,inquiryId)
                .orElseThrow(() -> new IllegalStateException("질문이 존재하지 않습니다."));
    }
}

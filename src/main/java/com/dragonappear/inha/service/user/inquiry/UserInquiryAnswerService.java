package com.dragonappear.inha.service.user.inquiry;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.inquiry.UserInquiry;
import com.dragonappear.inha.domain.user.inquiry.UserInquiryAnswer;
import com.dragonappear.inha.domain.user.value.InquiryStatus;
import com.dragonappear.inha.repository.user.inquiry.UserInquiryAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserInquiryAnswerService {
    private final UserInquiryAnswerRepository userInquiryAnswerRepository;

    // 답변등록
    @Transactional
    public void save(UserInquiryAnswer answer) {
        userInquiryAnswerRepository.save(answer);
    }

    // 답변수정
    @Transactional
    public void update(UserInquiryAnswer answer, String newContent) {
        answer.updateContent(newContent);
    }


    // 답변삭제
    @Transactional
    public void delete(UserInquiryAnswer answer) {
       answer.getUserInquiry().updateUserInquiryAnswer(null);
        userInquiryAnswerRepository.delete(answer);
    }

    // 모든답변조회
    public List<UserInquiryAnswer> findAll() {
        return userInquiryAnswerRepository.findAll();
    }

    // 답변조회 리스트 조회 by 유저
    public List<UserInquiryAnswer> findByUserId(User user) {
        return userInquiryAnswerRepository.findByUserId(user.getId());
    }

    // 답변조회 by 답변아이디
    public UserInquiryAnswer findByAnswerId(UserInquiryAnswer answer) {
        return userInquiryAnswerRepository.findById(answer.getId())
                .orElseThrow(() -> new IllegalStateException("답변이 존재하지 않습니다"));
    }
}

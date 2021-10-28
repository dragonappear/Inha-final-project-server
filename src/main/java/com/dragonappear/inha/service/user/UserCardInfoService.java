package com.dragonappear.inha.service.user;


import com.dragonappear.inha.domain.user.UserCardInfo;
import com.dragonappear.inha.repository.user.UserCardInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserCardInfoService {
    private final UserCardInfoRepository userCardInfoRepository;

    // 유저카드 등록
    @Transactional
    public Long save(UserCardInfo userCardInfo) {
        return userCardInfoRepository.save(userCardInfo).getId();
    }

    // 유저카드 리스트 조회 by 유저아이디
    public List<UserCardInfo> findAll(Long userId) {
        List<UserCardInfo> list = userCardInfoRepository.findAllByUserId(userId);
        if(list.size()==0){
            throw new IllegalArgumentException("카드가 존재하지 않습니다");
        }
        return list;
    }

    // 유저카드 단건조회 by 카드아이디
    public UserCardInfo findOne(Long userCardInfoId) {
       return userCardInfoRepository.findById(userCardInfoId)
                .orElseThrow(()->new IllegalArgumentException("해당 카드가 존재하지 않습니다"));
    }

    // 유저카드 단건조회 by 카드아이디 그리고 유저아이디
    public UserCardInfo findOne(Long userId, Long userCardInfoId) {
        return userCardInfoRepository.findByUserIdAndCardInfoId(userId, userCardInfoId)
                .orElseThrow(()->new IllegalArgumentException("카드가 존재하지 않습니다"));
    }

}

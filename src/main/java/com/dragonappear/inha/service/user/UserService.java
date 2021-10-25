package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.user.UserRepository;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;


    /**
     *  CREATE
     */

    // 회원등록
    @Transactional
    public Long join(User user) {
        validateUser(user.getEmail(),user.getUserTel());
        return userRepository.save(user).getId();
    }


    /**
     *  READ
     */

    //  회원 단건조회 by 유저아이디
    public User findOneById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));
    }

    // 회원 단건조회 by 유저이메일
    public User findOneByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("존재하지 않는 회원입니다."));
    }

    //  모든 회원 조회
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // 유저 닉네임 조회
    public String getNickname(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."))
                .getNickname();
    }

    // 유저 이름 조회
    public String getUsername(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."))
                .getUsername();
    }

    // 유저 Role 조회
    public String getUserRole(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."))
                .getUserRole().getTitle();
    }

    /**
     *  UPDATE
     */


    // 유저 닉네임 업데이트
    @Transactional
    public void updateNickname(Long userId, String nickname) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다.")).updateNickname(nickname);
    }

    // 유저 이름 업데이트
    @Transactional
    public void updateUsername(Long userId, String username) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다.")).updateUsername(username);
    }

    // 유저 연락처 업데이트
    @Transactional
    public void updateUserTel(Long userId, String userTel) {
        validateUserTel(userTel);
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다.")).updateUserTel(userTel);
    }

    /**
     * 검증로직
     */
    private void validateUser(String email,String userTel) {
        List<User> users = userRepository.findByEmailOrUserTel(email, userTel);
        if (!users.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    private void validateUserTel(String userTel) {
        List<User> users = userRepository.findByUserTel(userTel);
        if (!users.isEmpty()) {
            throw new IllegalStateException("이미 등록된 휴대폰 번호입니다.");
        }
    }


}

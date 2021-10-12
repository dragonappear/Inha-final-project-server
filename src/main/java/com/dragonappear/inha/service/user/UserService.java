package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;

    // 회원등록
    @Transactional
    public Long join(User user) {
        validateUser(user.getEmail(),user.getUserTel());
        return userRepository.save(user).getId();
    }

    //  회원 단건조회
    public User findOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    //  모든 회원 조회
    public List<User> findAll() {
        return userRepository.findAll();
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
}

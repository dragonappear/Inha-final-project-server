package com.dragonappear.inha.service.user;


import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAccount;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.exception.user.NotFoundUserAccountException;
import com.dragonappear.inha.repository.user.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;

    // 유저 계좌 등록/수정
    @Transactional
    public Account update(User user, Account userAccount) {
        if (user.getUserAccount() != null) {
            user.getUserAccount().changeUserAccount(userAccount);
        } else {
            userAccountRepository.save(new UserAccount(user, userAccount));
        }
        return userAccount;
    }

    //  유저 계좌 조회 by 유저아이디
    public UserAccount findByUserId(Long userId) throws NotFoundUserAccountException{
        return userAccountRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundUserAccountException("해당 계좌가 존재하지 않습니다."));
    }

    // 유저 계좌 조회 by 계좌아이디
    public UserAccount findByAccountId(Long addressId) throws NotFoundUserAccountException{
        return userAccountRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundUserAccountException("해당 계좌가 존재하지 않습니다."));
    }
}

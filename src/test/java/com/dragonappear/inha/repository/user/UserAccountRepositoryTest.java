package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAccount;
import com.dragonappear.inha.domain.user.value.Account;
import com.dragonappear.inha.domain.user.value.BankName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class UserAccountRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired UserAccountRepository userAccountRepository;

    @Test
    public void 유저계좌생성_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678");
        UserAccount userAccount = new UserAccount(new Account(BankName.신한은행, "110-1234-1234"));
        user.updateUserAccount(userAccount);
        userRepository.save(user);
        //when
        UserAccount findUserAccount = userAccountRepository.findById(userAccount.getId()).get();
        //then
        assertThat(findUserAccount).isEqualTo(userAccount);
        assertThat(findUserAccount.getUser()).isEqualTo(userAccount.getUser());
        assertThat(findUserAccount.getId()).isEqualTo(userAccount.getId());
        assertThat(findUserAccount.getUserAccount().getBankName()).isEqualTo(userAccount.getUserAccount().getBankName());
        assertThat(findUserAccount.getUserAccount().getAccountNumber()).isEqualTo(userAccount.getUserAccount().getAccountNumber());
    }

}
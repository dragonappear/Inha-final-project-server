package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAccount;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.BankName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserAccountRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired UserAccountRepository userAccountRepository;

    @Test
    public void 유저계좌생성_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(user);
        UserAccount userAccount = new UserAccount(user,new Account(BankName.신한은행, "110-1234-1234","yyh"));
        userAccountRepository.save(userAccount);
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
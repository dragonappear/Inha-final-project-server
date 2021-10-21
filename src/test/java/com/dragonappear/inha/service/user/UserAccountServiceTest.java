package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAccount;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.BankName;
import com.dragonappear.inha.repository.user.UserAccountRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserAccountServiceTest {
    @Autowired UserRepository userRepository;
    @Autowired UserAccountRepository userAccountRepository;
    @Autowired UserAccountService userAccountService;

    @BeforeEach
    public void setUp() {
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678");
        userRepository.save(user);
    }

    @Test
    public void 유저계좌_생성_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Account newAccount = new Account(BankName.신한은행, "1234","yyh");
        //when
        userAccountService.update(findUser, newAccount);
        //then
        assertThat(findUser.getUserAccount()).isNotNull();
        assertThat(findUser.getUserAccount().getUserAccount()).isEqualTo(newAccount);
        assertThat(findUser.getUserAccount().getUser()).isEqualTo(findUser);
    }

    @Test
    public void 유저계좌_업데이트_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Account newAccount = new Account(BankName.신한은행, "1234","yyh");
        //when
        userAccountService.update(findUser, newAccount);
        Account newAccount1 = new Account(BankName.신한은행, "2345","yyh");
        userAccountService.update(findUser, newAccount1);
        //then
        assertThat(findUser.getUserAccount()).isNotNull();
        assertThat(findUser.getUserAccount().getUserAccount()).isEqualTo(newAccount1);
        assertThat(findUser.getUserAccount().getUser()).isEqualTo(findUser);
    }

    @Test
    public void 유저계좌조회_유저아이디로_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Account newAccount = new Account(BankName.신한은행, "1234","yyh");
        UserAccount saveAccount = userAccountRepository.save(new UserAccount(findUser, newAccount));
        //when
        UserAccount findAccount = userAccountService.findByUserId(findUser.getId());
        //then
        assertThat(findAccount).isEqualTo(saveAccount);
        assertThat(findAccount.getId()).isEqualTo(saveAccount.getId());
        assertThat(findAccount.getUser()).isEqualTo(saveAccount.getUser());
        assertThat(findAccount.getUserAccount()).isEqualTo(saveAccount.getUserAccount());
    }
    
    @Test
    public void 유저계좌조회_계좌아이디로_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Account newAccount = new Account(BankName.신한은행, "1234","yyh");
        UserAccount saveAccount = userAccountRepository.save(new UserAccount(findUser, newAccount));
        //when
        UserAccount findAccount = userAccountService.findByAccountId(saveAccount.getId());
        //then
        assertThat(findAccount).isEqualTo(saveAccount);
        assertThat(findAccount.getId()).isEqualTo(saveAccount.getId());
        assertThat(findAccount.getUser()).isEqualTo(saveAccount.getUser());
        assertThat(findAccount.getUserAccount()).isEqualTo(saveAccount.getUserAccount());
    }


}
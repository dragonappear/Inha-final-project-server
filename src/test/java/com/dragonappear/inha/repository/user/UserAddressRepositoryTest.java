package com.dragonappear.inha.repository.user;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserAddressRepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired UserAddressRepository userAddressRepository;
    @Test
    public void 유저주소_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(user);
        UserAddress userAddress = new UserAddress(user, new Address("yyh","010-1111-1111","incheon", "inharo", "127", "1234"));
        userAddressRepository.save(userAddress);
        //when
        UserAddress findAddress = userAddressRepository.findById(userAddress.getId()).get();
        //then
        assertThat(userAddress).isEqualTo(findAddress);
        assertThat(userAddress.getUser().getId()).isEqualTo(findAddress.getUser().getId());
        assertThat(userAddress.getUserAddress().getCity()).isEqualTo(findAddress.getUserAddress().getCity());
        assertThat(userAddress.getUserAddress().getStreet()).isEqualTo(findAddress.getUserAddress().getStreet());
        assertThat(userAddress.getUserAddress().getDetail()).isEqualTo(findAddress.getUserAddress().getDetail());
        assertThat(userAddress.getUserAddress().getZipcode()).isEqualTo(findAddress.getUserAddress().getZipcode());
    }
}
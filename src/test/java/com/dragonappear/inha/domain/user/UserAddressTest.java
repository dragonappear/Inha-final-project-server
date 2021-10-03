package com.dragonappear.inha.domain.user;

import com.dragonappear.inha.domain.user.value.Address;
import com.dragonappear.inha.repository.user.UserAddressRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class UserAddressTest {
    @Autowired UserRepository userRepository;
    @Autowired UserAddressRepository userAddressRepository;

    @Test
    public void 유저주소_테스트() throws Exception{
        //given
        User user = new User("사용자1", "yyh", "사용자1@naver.com", "010-1234-5678");
        userRepository.save(user);
        UserAddress userAddress = new UserAddress(user, new Address("incheon", "inharo", "127", "1234"));
        UserAddress saveAddress = userAddressRepository.save(userAddress);
        //when
        UserAddress findAddress = userAddressRepository.findById(saveAddress.getId()).get();
        //then
        assertThat(userAddress).isEqualTo(findAddress);
        assertThat(userAddress.getUser().getId()).isEqualTo(findAddress.getUser().getId());
        assertThat(userAddress.getUserAddress().getCity()).isEqualTo(findAddress.getUserAddress().getCity());
        assertThat(userAddress.getUserAddress().getStreet()).isEqualTo(findAddress.getUserAddress().getStreet());
        assertThat(userAddress.getUserAddress().getDetail()).isEqualTo(findAddress.getUserAddress().getDetail());
        assertThat(userAddress.getUserAddress().getZipcode()).isEqualTo(findAddress.getUserAddress().getZipcode());
    }
}
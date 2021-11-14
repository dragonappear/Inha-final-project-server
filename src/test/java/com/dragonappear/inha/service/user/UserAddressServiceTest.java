package com.dragonappear.inha.service.user;

import com.dragonappear.inha.domain.user.Role;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.repository.user.UserAddressRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.boot.TempTableDdlTransactionHandling;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserAddressServiceTest {
    @Autowired UserRepository userRepository;
    @Autowired UserAddressService userAddressService;
    @Autowired UserAddressRepository userAddressRepository;

    @BeforeEach
    public void setUp() {
        User newUser = new User("name1", "nickname1", "email1@", "userTel11","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(newUser);
        User newUser1 = new User("name2", "nickname2", "email2@", "userTel22","1234",new HashSet<>(Arrays.asList(Role.builder()
                .roleName("ROLE_USER")
                .roleDesc("사용자")
                .build())));
        userRepository.save(newUser1);
    }

    @Test
    public void 유저주소_등록_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Address address = new Address("yyh","010-1111-1111","city1", "street1", "detail1", "zipcode1");
        Address address1 = new Address("yyh","010-1111-1111","city2", "street2", "detail2", "zipcode2");
        Address address2 = new Address("yyh","010-1111-1111","city3", "street3", "detail3", "zipcode3");
        Address address3 = new Address("yyh","010-1111-1111","city4", "street4", "detail4", "zipcode4");
        //when
        userAddressService.save(findUser.getId(), address);
        userAddressService.save(findUser.getId(), address1);
        userAddressService.save(findUser.getId(), address2);
        userAddressService.save(findUser.getId(), address3);
        List<UserAddress> all = userAddressRepository.findAll();
        //then
        assertThat(all).extracting("user").containsOnly(findUser);
        assertThat(all).extracting("userAddress").containsOnly(address,address1,address2,address3);
        assertThat(all.size()).isEqualTo(4);
    }

    @Test
    public void 유저주소_중복등록_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Address address = new Address("yyh","010-1111-1111","city1", "street1", "detail1", "zipcode1");
        Address address1 = new Address("yyh","010-1111-1111","city1", "street1", "detail1", "zipcode1");
        //when
        userAddressService.save(findUser.getId(), address);
        //then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userAddressService.save(findUser.getId(), address1);
        });
    }

    @Test
    public void 유저주소조회_유저아이디로_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Address address = new Address("yyh","010-1111-1111","city1", "street1", "detail1", "zipcode1");
        Address address1 = new Address("yyh","010-1111-1111","city2", "street2", "detail2", "zipcode2");
        userAddressRepository.save(new UserAddress(findUser, address));
        userAddressRepository.save(new UserAddress(findUser, address1));
        //when
        List<UserAddress> all = userAddressService.findByUserId(findUser.getId());
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).extracting("user").containsOnly(findUser);
        assertThat(all).extracting("userAddress").containsOnly(address,address1);
        assertThat(all).extracting("userAddress")
                .extracting("zipcode")
                .containsOnly("zipcode1", "zipcode2");
    }

    @Test
    public void 유저주소조회_유저주소아이디로_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Address address = new Address("yyh","010-1111-1111","city1", "street1", "detail1", "zipcode1");
        UserAddress newUserAddress = userAddressRepository.save(new UserAddress(findUser, address));
        //when
        UserAddress findUserAddress = userAddressService.findByUserAddressId(newUserAddress.getId());
        //then
        assertThat(findUserAddress).isEqualTo(newUserAddress);
        assertThat(findUserAddress.getId()).isEqualTo(newUserAddress.getId());
        assertThat(findUserAddress.getUser()).isEqualTo(newUserAddress.getUser());
        assertThat(findUserAddress.getUserAddress()).isEqualTo(newUserAddress.getUserAddress());
    }

    @Test
    public void 유저주소조회_유저그리고주소아이디로_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        Address address = new Address("yyh","010-1111-1111","city1", "street1", "detail1", "zipcode1");
        UserAddress newUserAddress = userAddressRepository.save(new UserAddress(findUser, address));
        userAddressRepository.save(newUserAddress);
        //when
        UserAddress findUserAddress = userAddressService.findByUserAndAddressId(findUser.getId(), newUserAddress.getId());
        //then
        assertThat(findUserAddress).isEqualTo(newUserAddress);
        assertThat(findUserAddress.getId()).isEqualTo(newUserAddress.getId());
        assertThat(findUserAddress.getUser()).isEqualTo(newUserAddress.getUser());
        assertThat(findUserAddress.getUserAddress()).isEqualTo(newUserAddress.getUserAddress());
    }

    @Test
    public void 모든유저주소_조회_테스트() throws Exception{
        //given
        User findUser = userRepository.findAll().get(0);
        User findUser1 = userRepository.findAll().get(1);
        Address address = new Address("yyh","010-1111-1111","city1", "street1", "detail1", "zipcode1");
        UserAddress newUserAddress = userAddressRepository.save(new UserAddress(findUser, address));
        Address address1 = new Address("yyh","010-1111-1111","city1", "street1", "detail1", "zipcode1");
        UserAddress newUserAddress1 = userAddressRepository.save(new UserAddress(findUser1, address1));
        userAddressRepository.save(newUserAddress);
        userAddressRepository.save(newUserAddress1);
        //when
        List<UserAddress> all = userAddressService.findAll();
        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).containsOnly(newUserAddress, newUserAddress1);
        assertThat(all).extracting("user").containsOnly(findUser, findUser1);
    }
}
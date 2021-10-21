package com.dragonappear.inha.service.user;


import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.repository.user.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final EntityManager entityManager;

    /**
     * CREATE
     */

    // 유저주소등록
    @Transactional
    public Long save(User user, Address address) {
        validateUserAddress(user, address);
        return userAddressRepository.save(new UserAddress(user, address)).getId();
    }


    /**
     * READ
     */

    // 유저주소조회 by 유저아이디
    public List<UserAddress> findByUserId(Long userId) {
        return userAddressRepository.findByUserId(userId);
    }

    // 유저주소조회 by 유저주소아이디
    public UserAddress findByUserAddressId(Long userAddressId) {
        return userAddressRepository.findById(userAddressId)
                .orElseThrow(()->new IllegalStateException("해당 주소가 존재하지 않습니다."));
    }

    // 유저주소조회 by 유저아이디 그리고 유저주소아이디
    public UserAddress findByUserAndAddressId(Long userId,Long userAddressId) {
        return userAddressRepository.findByUserIdAndUserAddressId(userId,userAddressId)
                .orElseThrow(()->new IllegalStateException("해당 주소가 존재하지 않습니다."));
    }

    // 모든유저주소조회
    public List<UserAddress> findAll() {
        return userAddressRepository.findAll();
    }


    /**
     * DELETE
     * DELETE
     */
    @Transactional
    public void deleteAddress(Long userId, Address address) {
        userAddressRepository.findByUserId(userId).stream().forEach(userAddress -> {
                     if (userAddress.getUserAddress().equals(address)) {
                         userAddressRepository.delete(userAddress);
                     }
                 }
         );
    }


    /**
     * 검증로직
     */

    private void validateUserAddress(User user, Address address) {
        List<UserAddress> all = userAddressRepository.findByUserId(user.getId());
        for (UserAddress userAddress : all) {
            if (userAddress.getUserAddress().equals(address)) {
                throw new IllegalStateException("이미 등록된 주소입니다.");
            }
        }

        for (UserAddress userAddress : all) {
            System.out.println("userAddress = " + userAddress);
        }
    }


}

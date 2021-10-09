package com.dragonappear.inha.service.user;


import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.repository.user.UserAddressRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;

    // 유저주소등록
    public Long save(User user, Address address) {
        validateUserAddress(user, address);
        return userAddressRepository.save(new UserAddress(user, address)).getId();
    }

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
     * 검증로직
     */

    private void validateUserAddress(User user, Address address) {
        List<UserAddress> all = userAddressRepository.findByUserId(user.getId());
        for (UserAddress userAddress : all) {
            if (userAddress.getUserAddress().getZipcode().equals(address.getZipcode())) {
                throw new IllegalStateException("주소가 중복입니다.");
            }
        }
    }
}

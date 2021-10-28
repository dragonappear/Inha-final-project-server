package com.dragonappear.inha.service.user;


import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.exception.user.NotFoundUserAddressException;
import com.dragonappear.inha.exception.user.NotFoundUserAddressListException;
import com.dragonappear.inha.repository.user.UserAddressRepository;
import com.dragonappear.inha.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;

    /**
     * CREATE
     */

    // 유저주소등록
    @Transactional
    public Long save(Long userId, Address address) {
        validateUserAddress(userId, address);
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return userAddressRepository.save(new UserAddress(user, address)).getId();
    }

    /**
     * READ
     */

    // 유저주소조회 by 유저아이디
    public List<UserAddress> findByUserId(Long userId) {
        List<UserAddress> list = userAddressRepository.findByUserId(userId);
        if (list.size() == 0) {
            throw new NotFoundUserAddressListException("해당 주소가 존재하지 않습니다.");
        }
        return list;
    }

    // 유저주소조회 by 유저주소아이디
    public UserAddress findByUserAddressId(Long userAddressId) {
        return userAddressRepository.findById(userAddressId)
                .orElseThrow(()->new NotFoundUserAddressException("해당 주소가 존재하지 않습니다."));
    }

    // 유저주소조회 by 유저아이디 그리고 유저주소아이디
    public UserAddress findByUserAndAddressId(Long userId,Long userAddressId) {
        return userAddressRepository.findByUserIdAndUserAddressId(userId,userAddressId)
                .orElseThrow(()->new IllegalArgumentException("해당 주소가 존재하지 않습니다."));
    }

    // 모든유저주소조회
    public List<UserAddress> findAll() {
        List<UserAddress> list = userAddressRepository.findAll();
        if (list.size() == 0) {
            throw new IllegalArgumentException("주소가 존재하지 않습니다.");
        }
        return list;
    }

    /**
     * UPDATE
     */
    @Transactional
    public Address updateUserAddress(Long userId, Long addressId, Address address) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        validateUserAddress(user.getId(), address);
        userAddressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주소입니다."))
                .getUserAddress()
                .update(address);
        return address;
    }


    /**
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

    public void validateUserAddress(Long userId, Address address) {
        List<UserAddress> all = userAddressRepository.findByUserId(userId);
        for (UserAddress userAddress : all) {
            if (userAddress.getUserAddress().equals(address)) {
                throw new IllegalArgumentException("이미 등록된 주소입니다.");
            }
        }
    }
}

package com.dragonappear.inha.service.security;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       /* return userRepository.findOneWithAuthoritiesByEmail(email)
                .map(user -> createUser(email, user))
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));*/
        return null;
    }

    private org.springframework.security.core.userdetails.User createUser(String email, User user) {
        /*List<SimpleGrantedAuthority> authorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail()
        ,user.getPassword(),authorities);*/
        return null;
    }
}

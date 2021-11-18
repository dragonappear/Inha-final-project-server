package com.dragonappear.inha.api.controller.user.login;

import com.dragonappear.inha.api.controller.user.login.dto.LoginDto;
import com.dragonappear.inha.api.controller.user.login.dto.TokenDto;
import com.dragonappear.inha.api.controller.user.login.dto.RegisterDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.config.jwt.JwtFilter;
import com.dragonappear.inha.config.jwt.TokenProvider;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserToken;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.repository.user.RoleRepository;
import com.dragonappear.inha.service.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"유저 로그인 API"})
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;
    private final UserAddressService userAddressService;
    private final UserImageService userImageService;
    private final UserPointService userPointService;
    private final UserAccountService userAccountService;
    private final UserTokenService userTokenService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @ApiOperation(value = "유저 토큰 API By 유저이메일, 패스워드", notes = "유저 토큰 API")
    @PostMapping("/api/v1/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    
    @ApiOperation(value = "유저 등록 조회 API By 유저이메일", notes = "유저 정보 조회")
    @GetMapping(value = "/api/v1/users/{email}")
    public MessageDto checkRegistered(@PathVariable("email") String email) {
        User user = userService.findOneByEmail(email);
        return MessageDto.builder()
                .message(getMessage("userId", user.getId()))
                .build();
    }

    @ApiOperation(value = "유저 정보 저장 API", notes = "유저 정보 저장")
    @PostMapping(value = "/api/v1/users/new")
    public MessageDto save(@Valid @RequestBody RegisterDto dto) {
        createUser(dto);
        return MessageDto.builder()
                    .message(getMessage("isRegistered", true))
                    .build();
    }

    /**
     *  유저 DB 저장 로직
     */
    private void createUser(RegisterDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword()); // 유저 패스워드 암호화
        User user = dto.toEntity(roleRepository.findByRoleName("ROLE_USER"),encodedPassword);
        userService.join(user);
        userAddressService.save(user.getId(), dto.getAddress()); // 유저 주소 저장
        userImageService.update(user // 유저 이미지 저장
                , new Image("NequEEEQWEeqweZXCZXCZASDsitas.png","NequEEEQWEeqweZXCZXCZASDsitas.png", "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/user"));
        userPointService.create(user.getId()); // 유저 포인트 초기 생성
        userAccountService.update(user, // 유저 주소 저장
                new Account(dto.getAccount().getBankName(), dto.getAccount().getAccountNumber(), dto.getAccount().getAccountHolder()));
        userTokenService.save(new UserToken("fcm", dto.getMessageToken(), user));
    }
}

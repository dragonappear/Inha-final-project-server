package com.dragonappear.inha.config.security;

import com.dragonappear.inha.config.jwt.JwtAccessDeniedHandler;
import com.dragonappear.inha.config.jwt.JwtAuthenticationEntryPoint;
import com.dragonappear.inha.config.jwt.JwtSecurityConfig;
import com.dragonappear.inha.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/favicon.ico"
                        ,"/error"
                        ,"/swagger-ui.html/**"
                        ,"/js/**"
                        ,"/css/**"
                        ,"/images/**"
                        ,"/webjars/**"
                        ,"/swagger-resources/**"
                        ,"/v2/api-docs"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
            .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
                .authorizeRequests()
                .antMatchers("/api/v1/authenticate").permitAll()
                .antMatchers("/api/v1/users/{email}").permitAll()
                .antMatchers("/api/v1/users/new").permitAll()
                .antMatchers("/web/admin/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

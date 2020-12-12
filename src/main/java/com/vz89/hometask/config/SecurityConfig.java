package com.vz89.hometask.config;

import com.vz89.hometask.security.jwt.JwtConfigurer;
import com.vz89.hometask.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    private final String[] USER_ROLE_ENDPOINT = {"/accounts/**", "/skills/**", "/developers/**"};
    private static final String ADMIN_ROLE_ENDPOINT = "/users/**";
    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_MODERATOR = "MODERATOR";
    private static final String REGISTER_USER_ENDPOINT = "/users/**";
    private static final String LOGIN_ENDPOINT = "/auth/login/**";



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, REGISTER_USER_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, USER_ROLE_ENDPOINT).authenticated()
                .antMatchers(USER_ROLE_ENDPOINT).hasAnyRole(ROLE_ADMIN, ROLE_MODERATOR)
                .antMatchers(HttpMethod.GET, "/users/**").hasRole(ROLE_MODERATOR)
                .antMatchers(ADMIN_ROLE_ENDPOINT).hasRole(ROLE_ADMIN)
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}

package com.vz89.hometask.security;

import com.vz89.hometask.model.User;
import com.vz89.hometask.security.jwt.JwtUserFactory;
import com.vz89.hometask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException(username + " not found");

        return JwtUserFactory.create(user);
    }
}

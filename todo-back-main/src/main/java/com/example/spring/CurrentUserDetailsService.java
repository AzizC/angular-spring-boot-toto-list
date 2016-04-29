package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CurrentUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CurrentUserDetailsService(UserService userService) {
        this.userService = userService;
    }
    @Override
    public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        return new CurrentUser(user);
    }
}

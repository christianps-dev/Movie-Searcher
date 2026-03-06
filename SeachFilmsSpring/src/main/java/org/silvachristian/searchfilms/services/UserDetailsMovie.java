package org.silvachristian.searchfilms.services;

import org.jspecify.annotations.NonNull;
import org.silvachristian.searchfilms.entity.UserEntity;
import org.silvachristian.searchfilms.repository.LoginRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsMovie implements UserDetailsService {

    private final LoginRepository loginRepository;

    UserDetailsMovie(LoginRepository loginrepository){
        this.loginRepository = loginrepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        UserEntity user = loginRepository.findByUsername(username);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}

package org.silvachristian.searchfilms.services;

import org.silvachristian.searchfilms.entity.UserEntity;
import org.silvachristian.searchfilms.repository.LoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServices {

    private final PasswordEncoder passwordEncoder;
    private final LoginRepository loginRepository;

    LoginServices(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(UserEntity user){
        if(!loginRepository.existsByUsername (user.getUsername())){
            user.setPassword(Objects.requireNonNull(passwordEncoder.encode(user.getPassword())));
            loginRepository.saveAndFlush(user);
            return true;
        }
        return false;
    }
}

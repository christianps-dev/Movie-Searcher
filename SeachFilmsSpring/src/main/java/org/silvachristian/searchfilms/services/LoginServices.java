package org.silvachristian.searchfilms.services;

import org.silvachristian.searchfilms.dto.RegisterUserDTO;
import org.silvachristian.searchfilms.repository.LoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {

    private final PasswordEncoder passwordEncoder;
    private final LoginRepository loginRepository;

    LoginServices(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(RegisterUserDTO user) {
        return loginRepository.existsUserEntitiesByEmail(user.email())
                && loginRepository.existsUserEntitiesByUsername (user.username());
    }
}

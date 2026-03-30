package org.silvachristian.searchfilms.services;

import org.silvachristian.searchfilms.dto.RegisterUserDTO;
import org.silvachristian.searchfilms.dto.UserResponseDTO;
import org.silvachristian.searchfilms.entity.RegisterEntity;
import org.silvachristian.searchfilms.repository.LoginRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {

    private final LoginRepository loginRepository;

    LoginServices(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean userExists(RegisterUserDTO user) {
        return loginRepository.existsByEmail(user.email())
                || loginRepository.existsByUsername(user.username());
    }

    public UserResponseDTO userExistByUsernameAndEmail(RegisterUserDTO user) {
        RegisterEntity responseDetail = new RegisterEntity();
        String alreadyUsed = "aUsed";
        if (loginRepository.existsByUsername(user.username())) {
            responseDetail.setUsername(alreadyUsed);
            System.out.println("Username already used");
        }
        if (loginRepository.existsByEmail(user.email())) {
            responseDetail.setEmail(alreadyUsed);
            System.out.println("Email already used");
        }

        return new UserResponseDTO(responseDetail.getUsername(), "null",responseDetail.getEmail());
    }
}

package org.silvachristian.searchfilms.controllers;

import jakarta.validation.Valid;
import org.silvachristian.searchfilms.dto.LoginUserDTO;
import org.silvachristian.searchfilms.dto.RegisterUserDTO;
import org.silvachristian.searchfilms.dto.UserResponseDTO;
import org.silvachristian.searchfilms.entity.UserEntity;
import org.silvachristian.searchfilms.repository.LoginRepository;
import org.silvachristian.searchfilms.security.TokenService;
import org.silvachristian.searchfilms.services.LoginServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController()
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    LoginServices loginServices;
    private final PasswordEncoder passwordEncoder;
    private final LoginRepository loginRepository;
    private final TokenService tokenService;

    LoginController(LoginServices loginServices, PasswordEncoder passwordEncoder,
                    LoginRepository loginRepository,  TokenService tokenService) {
        this.loginServices = loginServices;
        this.passwordEncoder = passwordEncoder;
        this.loginRepository = loginRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity <UserResponseDTO> signupUser(@Valid @RequestBody RegisterUserDTO user) {
        if (user != null && !loginServices.userExists(user)) {
            // Criando novo usuario.
            UserEntity newUser = new UserEntity();
            newUser.setUsername(user.username());
            newUser.setPassword(Objects.requireNonNull(passwordEncoder.encode(user.password())));
            newUser.setEmail(user.email());
            loginRepository.save(newUser);
            System.out.println("Registering user: " + user.username());

            // Criando token e retornando a response.
            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new UserResponseDTO(newUser.getUsername(), token, newUser.getEmail()));
        } else{
            assert Objects.requireNonNull(user).username() != null;
            System.out.println("Username already exists : " +  user.username());
            return ResponseEntity.ok(loginServices.userExistByUsernameAndEmail(user));

        }

    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@Valid @RequestBody LoginUserDTO user) throws UsernameNotFoundException {
        // Buscando usuario
        UserEntity loginUser = loginRepository.findByUsername(user.username()).orElse(null);
        assert loginUser != null;

        // Conferindo se a senha bate, criando token e retornando response.
        if (passwordEncoder.matches(user.password(), loginUser.getPassword())) {

            String token = tokenService.generateToken(loginUser);
            return ResponseEntity.ok(new UserResponseDTO(loginUser.getUsername(), token,  loginUser.getEmail()));
        } else{
            return ResponseEntity.badRequest().build();

        }

    }

}

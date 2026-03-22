package org.silvachristian.searchfilms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.silvachristian.searchfilms.entity.UserEntity;
import org.silvachristian.searchfilms.repository.LoginRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final LoginRepository  loginRepository;

    SecurityFilter(TokenService tokenService, LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filter) throws ServletException, IOException {
        var token = recoverToken(request);
        var login = tokenService.validateToken(token);

        if (login != null) {
            UserEntity user = loginRepository.findByUsername(login).orElse(null);
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            assert user != null;
            var authentication = new UsernamePasswordAuthenticationToken(user, login, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filter.doFilter(request, response);


    }

    private String recoverToken(HttpServletRequest request){
        var token = request.getHeader("Authorization");
        if(token == null) return null;
        return token.replace("Bearer ", "");
    }

}

package br.dev.gvitorguimaraes.nutrition.authservice.service;

import br.dev.gvitorguimaraes.nutrition.authservice.dto.LoginRequestDTO;
import br.dev.gvitorguimaraes.nutrition.authservice.model.User;
import br.dev.gvitorguimaraes.nutrition.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> authenticate(LoginRequestDTO loginRequest) {
        return userService.findByEmail(loginRequest.email())
                .filter(u -> passwordEncoder.matches(loginRequest.password(), u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));
    }

    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        }
        catch (JwtException e) {
            return false;
        }
    }
}

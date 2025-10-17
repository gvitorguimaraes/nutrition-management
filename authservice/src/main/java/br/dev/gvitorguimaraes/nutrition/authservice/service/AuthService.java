package br.dev.gvitorguimaraes.nutrition.authservice.service;

import br.dev.gvitorguimaraes.nutrition.authservice.dto.LoginRequestDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    public Optional<String> authenticate(LoginRequestDTO loginRequest) {

        // TODO

        return Optional.empty();
    }
}

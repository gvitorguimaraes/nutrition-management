package br.dev.gvitorguimaraes.nutrition.authservice.controller;

import br.dev.gvitorguimaraes.nutrition.authservice.dto.LoginRequestDTO;
import br.dev.gvitorguimaraes.nutrition.authservice.dto.LoginResponseDTO;
import br.dev.gvitorguimaraes.nutrition.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Optional<String> tokenOpt = authService.authenticate(loginRequest);
        if (tokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new LoginResponseDTO(tokenOpt.get()));
    }
}

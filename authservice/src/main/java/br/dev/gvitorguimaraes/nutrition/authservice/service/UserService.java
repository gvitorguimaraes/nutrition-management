package br.dev.gvitorguimaraes.nutrition.authservice.service;

import br.dev.gvitorguimaraes.nutrition.authservice.model.User;
import br.dev.gvitorguimaraes.nutrition.authservice.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}

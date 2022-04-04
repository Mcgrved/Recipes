package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.entities.User;
import recipes.repositories.UserRepository;

import javax.validation.Valid;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class RegisterController {

    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody User requestUser) {
        if (userRepository.findByEmail(requestUser.getEmail()) == null) {
            requestUser.setRole("ROLE_USER");
            requestUser.setPassword(encoder.encode(requestUser.getPassword()));
            userRepository.save(requestUser);
            return new ResponseEntity<>(Map.of("id", requestUser.getUserId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

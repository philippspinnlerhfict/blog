package ch.hfict.springboot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.hfict.springboot.repository.UserRepository;
import ch.hfict.springboot.util.Crypto;
import ch.hfict.springboot.model.User;
import ch.hfict.springboot.model.LoginDto;

@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        User user = userRepository.findByUsername(username);

        if (user == null || !Crypto.hash(password).equals(user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        String jwtToken = Crypto.createJwtToken(user.getId());
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}

package ch.hfict.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ch.hfict.springboot.model.Post;
import ch.hfict.springboot.model.User;
import ch.hfict.springboot.model.UserDto;
import ch.hfict.springboot.repository.PostRepository;
import ch.hfict.springboot.repository.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        
        User user = new User(username, password);
        try {
            userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(required = false) Integer newestIdFirst) {
        if (newestIdFirst != null && newestIdFirst == 1) {
            return userRepository.findAllByOrderByIdDesc();
        } else {
            return (List<User>) userRepository.findAll();
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUsers(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> getUserPosts(@PathVariable("id") Long id) {
        return postRepository.findByUserId(id);
    }
}

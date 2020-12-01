package ch.hfict.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ch.hfict.springboot.model.Post;
import ch.hfict.springboot.repository.PostRepository;


@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public List<Post> getUsers() {
        return (List<Post>) postRepository.findAll();
    }
}

package ch.hfict.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ch.hfict.springboot.model.Comment;
import ch.hfict.springboot.model.Post;
import ch.hfict.springboot.repository.CommentRepository;
import ch.hfict.springboot.repository.PostRepository;


@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/posts")
    public List<Post> getUsers() {
        return (List<Post>) postRepository.findAll();
    }

    @GetMapping("/posts/{id}/comments")
    public List<Comment> getPostComments(@PathVariable("id") Long id) {
        return commentRepository.findByPostId(id);
    }
}

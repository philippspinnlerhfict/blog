package ch.hfict.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ch.hfict.springboot.model.Comment;
import ch.hfict.springboot.repository.CommentRepository;


@RestController
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/comments")
    public List<Comment> getUsers(@RequestParam(required = false) Long limit) {
        if (limit != null) {
            return commentRepository.findAllWithLimit(limit);
        }
        return (List<Comment>) commentRepository.findAll();
    }
}

package ch.hfict.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ch.hfict.springboot.model.Comment;
import ch.hfict.springboot.model.CommentDto;
import ch.hfict.springboot.model.Post;
import ch.hfict.springboot.model.PostDto;
import ch.hfict.springboot.model.User;
import ch.hfict.springboot.repository.CommentRepository;
import ch.hfict.springboot.repository.PostRepository;
import ch.hfict.springboot.repository.UserRepository;
import ch.hfict.springboot.util.Crypto;


@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/posts")
    public List<Post> getUsers(@RequestParam(required = false) Long findByUserId) {
        if (findByUserId != null) {
            return postRepository.findByUserId(findByUserId);
        }
        return (List<Post>) postRepository.findAll();
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(HttpServletRequest request, @RequestBody PostDto postDto) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String[] authHeaderArr = authHeader.split(" ");
        if (authHeaderArr.length < 2) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeaderArr[1];

        Long userId;
        try {
            userId = Crypto.verifyJwtToken(token);
        } catch (AuthException exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<User> user = this.userRepository.findById(userId);    

        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Post post = new Post(postDto.getTitle(), postDto.getContent(), user.get());
        this.postRepository.save(post);
        
        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts/{id}/comments")
    public List<Comment> getPostComments(@PathVariable("id") Long id) {
        return this.commentRepository.findByPostId(id);
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<Comment> createPostComment(@PathVariable("id") Long id, @RequestBody CommentDto commentDto) {
        Optional<Post> post = this.postRepository.findById(id);
        if (!post.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User marge = this.userRepository.findByUsername("marge");

        Comment comment = new Comment(commentDto.getText(), marge, post.get());
        this.commentRepository.save(comment);

        return ResponseEntity.ok(comment);
    }
}

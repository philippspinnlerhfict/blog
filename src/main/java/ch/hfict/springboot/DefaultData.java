package ch.hfict.springboot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.hfict.springboot.model.Comment;
import ch.hfict.springboot.model.Post;
import ch.hfict.springboot.model.User;
import ch.hfict.springboot.repository.CommentRepository;
import ch.hfict.springboot.repository.PostRepository;
import ch.hfict.springboot.repository.UserRepository;

@Component
public class DefaultData {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostConstruct
    private void init() {
        User userHomer = new User("homer", "1234");
        userRepository.save(userHomer);

        User userMarge = new User("marge", "5678");
        userRepository.save(userMarge);

        Post postByHomer = new Post("First post by homer",
        "This is the content written by homer", userHomer);
        postRepository.save(postByHomer);

        Post postByMarge = new Post("First post by marge", "This is the content written by marge", userMarge);
        postRepository.save(postByMarge);

        Comment commentByHomerOnPostByMarge = new Comment("This is a comment by marge", userMarge, postByHomer);
        commentRepository.save(commentByHomerOnPostByMarge);

        Comment commentByMargeOnPostByHomer = new Comment("This is a comment by homer", userHomer, postByMarge);
        commentRepository.save(commentByMargeOnPostByHomer);
    }
}

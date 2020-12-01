package ch.hfict.springboot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ch.hfict.springboot.model.Post;


public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUserId(Long id);
}
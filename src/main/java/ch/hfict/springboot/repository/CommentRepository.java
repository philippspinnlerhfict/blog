package ch.hfict.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.hfict.springboot.model.Comment;


public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByPostId(Long id);
    
    @Query(value = "SELECT * FROM Comment Limit ?1", nativeQuery = true)
    List<Comment> findAllWithLimit(Long limit);
}
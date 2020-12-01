package ch.hfict.springboot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.hfict.springboot.model.Comment;


public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByPostId(Long id);
}
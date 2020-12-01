package ch.hfict.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import ch.hfict.springboot.model.User;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
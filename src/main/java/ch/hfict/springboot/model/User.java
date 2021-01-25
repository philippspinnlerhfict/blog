package ch.hfict.springboot.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.dao.DataIntegrityViolationException;

import ch.hfict.springboot.util.Crypto;

@Entity
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    String username;

    @JsonIgnore
    // This does not work with the PrePersist since the password will never be empty
    @Column(nullable = false)
    String password;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Post> posts;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Comment> comments;

    protected User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @PrePersist
    void hashPassword() {
        // We Need to check here for the empty password
        if (password == null || password.isEmpty()) {
            throw new DataIntegrityViolationException("Empty password");
        }
        password = Crypto.hash(password);
    }
}

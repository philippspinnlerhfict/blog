package ch.hfict.springboot.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Post extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String title;
    String content;

    @JsonIgnoreProperties("posts")
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    @JsonIgnoreProperties("post")
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    List<Comment> comments;

    protected Post() {}

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public List<Comment> getComments() {
        return comments;
    }
}

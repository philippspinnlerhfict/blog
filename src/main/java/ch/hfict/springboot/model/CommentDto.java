package ch.hfict.springboot.model;

public class CommentDto {
    String text;

    public CommentDto() {
    }
   
    public CommentDto(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}

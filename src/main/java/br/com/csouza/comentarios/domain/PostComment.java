package br.com.csouza.comentarios.domain;

import java.util.ArrayList;
import java.util.Collection;

public class PostComment {
    private Post post;
    private Collection<Comment> comments;

    public PostComment() {
        this.comments = new ArrayList<>();
    }

    public PostComment(Post post, Collection<Comment> comments) {
        this.post = post;
        this.comments = comments;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }
}

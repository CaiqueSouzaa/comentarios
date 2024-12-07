package br.com.csouza.comentarios.domain;

import java.util.Set;
import java.util.HashSet;

public class Publication {
	private User user;
	private Post post;
	private Set<Comment> comments;
	
	public Publication() {
		this.comments = new HashSet<>();
	}
	
	public Publication(User user) {
		this();
		this.user = user;
	}
	
	public Publication(Post post) {
		this();
		this.post = post;
	}
	
	public Publication(User user, Post post) {
		this();
		this.user = user;
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
}

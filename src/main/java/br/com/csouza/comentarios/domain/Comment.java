package br.com.csouza.comentarios.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_comments")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
	@SequenceGenerator(name = "comments_seq", sequenceName = "sq_comments", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "comment", nullable = true, columnDefinition = "TEXT")
	private String comment;
	
	@ManyToOne()
	@JoinColumn(
			name = "post_id",
			foreignKey = @ForeignKey(name = "fk_id_posts"),
			nullable = false
	)
	private Post post;
	
	@ManyToOne()
	@JoinColumn(
			name = "user_id",
			foreignKey = @ForeignKey(name = "fk_id_users"),
			nullable = false
	)
	private User user;
	
	@Column(name = "actived", nullable = false)
	private Boolean actived;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
	
	@PrePersist
	private void prePersist() {
		this.actived = true;
		this.createdAt = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getActived() {
		return actived;
	}

	public void setActived(Boolean actived) {
		this.actived = actived;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}

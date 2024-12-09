package br.com.csouza.comentarios.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;

@Entity
@Table(name = "tb_posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_seq")
	@SequenceGenerator(name = "posts_seq", sequenceName = "sq_posts", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "title", nullable = false, length = 200)
	private String title;
	
	@Column(name = "content", nullable = true, columnDefinition = "TEXT")
	private String content;
	
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

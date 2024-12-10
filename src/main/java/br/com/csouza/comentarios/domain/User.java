package br.com.csouza.comentarios.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_users")
public class User extends DatabaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
	@SequenceGenerator(name = "users_seq", sequenceName = "sq_users", initialValue = 1, allocationSize = 1)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	
	@Column(name = "surname", nullable = false, length = 100)
	private String surname;
	
	@Column(name = "login", nullable = false, unique = true, length = 50)
	private String login;
	
	@Column(name = "email", nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(name = "birthday")
	private Instant birthday;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	/**
	 * Método para definir o endereço de e-mail do usuário.
	 * @param email - Endereço de e-mail a ser atribuido ao usuário.
	 * 
	 * O endereço de e-mail deve seguir o regex "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z0-9.]+$".
	 * Exemplos: 
	 * - E-mail válido: caique.souza@email.com
	 * - E-mail inválido: caique.souzaa
	 * - E-mail inválido: caique.souzaa@email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public Instant getBirthday() {
		return birthday;
	}

	public void setBirthday(Instant birthday) {
		this.birthday = birthday;
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

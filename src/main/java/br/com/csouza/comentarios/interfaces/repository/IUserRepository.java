package br.com.csouza.comentarios.interfaces.repository;

import java.util.Collection;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;

public interface IUserRepository {
	public User register(User user);
	
	public Collection<User> findAll();
	
	public User getById(Long id) throws IDNotFoundException;
	
	public User getByLogin(String login);
	
	public User getByEmail(String email);
	
	public User update(User user) throws IDNotFoundException;
	
	public Boolean destroy(Long id) throws IDNotFoundException;
}

package br.com.csouza.comentarios.dao;

import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.interfaces.dao.IPostDAO;

public class PostDAO extends GenericDAO<Post, Long> implements IPostDAO{
	public PostDAO() {
		super(Post.class);
	}
}

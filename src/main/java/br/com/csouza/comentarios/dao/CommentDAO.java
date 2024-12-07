package br.com.csouza.comentarios.dao;

import br.com.csouza.comentarios.domain.Comment;
import br.com.csouza.comentarios.interfaces.dao.ICommentDAO;

public class CommentDAO extends GenericDAO<Comment, Long> implements ICommentDAO {
	public CommentDAO() {
		super(Comment.class);
	}
}

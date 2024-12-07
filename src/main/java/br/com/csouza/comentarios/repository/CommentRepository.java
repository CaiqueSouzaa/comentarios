package br.com.csouza.comentarios.repository;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Comment;
import br.com.csouza.comentarios.exceptions.PostNotFoundException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.ICommentDAO;
import br.com.csouza.comentarios.interfaces.repository.ICommentRepository;

public class CommentRepository extends Repository<Comment, Long> implements ICommentRepository {
	private final ICommentDAO commentDao;
	
	public CommentRepository(ICommentDAO dao) {
		super(dao);
		this.commentDao = dao;
	}

	@Override
	public Collection<Comment> getAllByPostId(Long id) throws PostNotFoundException {
		return this.commentDao.findAllByPostId(id);
	}

	@Override
	public Collection<Comment> getCreatedById(Long postId, Long userId) throws UserNotFoundException, PostNotFoundException {
		return this.commentDao.findCreatedById(postId, userId);
	}

	@Override
	public Collection<Comment> getCreatedByLogin(Long postId, String login) throws UserNotFoundException, PostNotFoundException {
		return this.commentDao.findCreatedByLogin(postId, login);
	}

	@Override
	public Collection<Comment> getCreatedByEmail(Long postId, String email)	throws UserNotFoundException, PostNotFoundException {
		return this.commentDao.findCreatedByEmail(postId, email);
	}
}

package br.com.csouza.comentarios.repository;

import java.util.Collection;
import java.util.HashSet;

import br.com.csouza.comentarios.domain.Comment;
import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.domain.Publication;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.interfaces.repository.ICommentRepository;
import br.com.csouza.comentarios.interfaces.repository.IPostRepository;
import br.com.csouza.comentarios.interfaces.repository.IRepository;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;

public class PublicationRepository {
	private final IUserRepository userRepository;
	private final IPostRepository postRepository;
	private final ICommentRepository commentRepository;
	
	public PublicationRepository(IUserRepository userRepository, IPostRepository postRepository, ICommentRepository commentRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	public void checkHasId(Long id, String objectName) {
		if (id == null) {
			throw new RuntimeException("ID " + id + " de " + objectName + " não localizado ou não registrado.");
		}
	}

	public void checkRegisteredId(IRepository<?, Long> repository, Long id, String objectName) {
		this.checkHasId(id, objectName);
		try {
			repository.getById(id);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void hasUser(final User user) {
		this.checkRegisteredId(this.userRepository, user.getId(), "usuário");
	}
	
	public void hasPost(final Post post) {
		this.checkRegisteredId(this.postRepository, post.getId(), "publicação");
	}

	/**
	 * Método para realizar o registro de uma nova publicação.
	 * @param post Publicação a ser registrada.
	 * @param user Usuário que está criando a publicação.
	 * @return Publicação registrada.
	 */
	public Publication register(Post post, final User user) {
		// Verificando se o usuário está registrado.
		this.checkRegisteredId(userRepository, user.getId(), "usuário");

		// Atribuindo o usuário ao post.
		post.setUser(user);

		// Registrando o post.
		post = this.postRepository.register(post);

		final Publication publication = new Publication();
		publication.setUser(user);
		publication.setPost(post);
		publication.setComments(new HashSet<>());

		return publication;
	}

	public Comment addComment(final Post post, final User user, final String comment) {
		// Checando se o usuário existe.
		this.hasUser(user);
		// Checando se o post existe.
		this.hasPost(post);

		Comment c = new Comment();
		c.setUser(user);
		c.setPost(post);
		c.setComment(comment);

		return this.commentRepository.register(c);
	}

	public Collection<Comment> getComments(Post post) {
		// Checando se o post existe.
		this.hasPost(post);

		try {
			return this.commentRepository.getAllByPostId(post.getId());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}

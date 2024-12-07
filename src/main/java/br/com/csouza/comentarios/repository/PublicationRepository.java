package br.com.csouza.comentarios.repository;

import java.util.HashSet;

import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.domain.Publication;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.interfaces.repository.IPostRepository;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;

public class PublicationRepository {
	private IUserRepository userRepository;
	private IPostRepository postRepository;
	
	public PublicationRepository(IUserRepository userRepository, IPostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	/**
	 * Método para realizar o registro de uma nova publicação.
	 * @param publication Publicação a ser registrada.
	 * @return Publicação registrada.
	 */
	public Publication newPublication(Publication publication) {
		
		final User user = this.userRepository.register(publication.getUser());
		publication.getPost().setUser(user);
		final Post post = this.postRepository.register(publication.getPost());
		
		final Publication p = new Publication();
		p.setUser(user);
		p.setPost(post);
		p.setComments(new HashSet<>());
		
		return p;
	}
}

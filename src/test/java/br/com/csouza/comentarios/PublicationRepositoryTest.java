package br.com.csouza.comentarios;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;

import br.com.csouza.comentarios.dao.PostDAO;
import br.com.csouza.comentarios.dao.UserDAO;
import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.domain.Publication;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.interfaces.repository.IPostRepository;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;
import br.com.csouza.comentarios.repository.PostRepository;
import br.com.csouza.comentarios.repository.PublicationRepository;
import br.com.csouza.comentarios.repository.UserRepository;
import br.com.csouza.comentarios.utils.Fake;

public class PublicationRepositoryTest {
	private final PublicationRepository publicationRepository;
	
	public PublicationRepositoryTest() {
		final IUserRepository userRepository = new UserRepository(new UserDAO());
		final IPostRepository postRepository = new PostRepository(new PostDAO());
		
		this.publicationRepository = new PublicationRepository(userRepository, postRepository);
	}
	
	@Test
	public void newPublication() {
		User u1 = new User();
		
		u1.setName("Caique");
		u1.setSurname("Souza");
		u1.setLogin("caique.souza");
		u1.setEmail("caique@email.com");
		u1.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));
		
		Post p1 = new Post();
		
		p1.setTitle("Primeiro post!");
		p1.setContent("Este é o conteúdo do post");
		p1.setUser(u1);
		
		Publication publication = new Publication();
		publication.setUser(u1);
		publication.setPost(p1);
		
		publication = this.publicationRepository.newPublication(publication);

		Assert.assertNotNull(publication.getUser().getId());
		Assert.assertNotNull(publication.getPost().getId());
	}
}

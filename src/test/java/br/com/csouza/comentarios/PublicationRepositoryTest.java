package br.com.csouza.comentarios;

import java.time.Instant;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.com.csouza.comentarios.dao.CommentDAO;
import br.com.csouza.comentarios.dao.PostDAO;
import br.com.csouza.comentarios.dao.UserDAO;
import br.com.csouza.comentarios.domain.Comment;
import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.domain.Publication;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.repository.CommentRepository;
import br.com.csouza.comentarios.repository.PostRepository;
import br.com.csouza.comentarios.repository.PublicationRepository;
import br.com.csouza.comentarios.repository.UserRepository;

public class PublicationRepositoryTest {
	private final PublicationRepository publicationRepository;
	private UserRepository userRepository;
	private PostRepository postRepository;
	private CommentRepository commentRepository;

	public PublicationRepositoryTest() {
		this.userRepository = new UserRepository(new UserDAO());
		this.commentRepository = new CommentRepository(new CommentDAO());
		this.postRepository = new PostRepository(new PostDAO(), this.userRepository, this.commentRepository);

		System.out.println(userRepository);
		
		this.publicationRepository = new PublicationRepository(this.userRepository, postRepository, commentRepository);
	}

	@After
	public void deleteRegisters() throws IDNotFoundException {
		final Collection<User> users = this.userRepository.getAll();
		final Collection<Post> posts = this.postRepository.getAll();
		final Collection<Comment> comments = this.commentRepository.getAll();

		for (Comment c : comments) {
			this.commentRepository.destroy(c.getId());
		}

		for (Post p : posts) {
			this.postRepository.destroy(p.getId());
		}

		for (User u : users) {
			this.userRepository.destroy(u.getId());
		}
	}
	
	@Test
	public void create() {
		User u1 = new User();
		
		u1.setName("Caique");
		u1.setSurname("Souza");
		u1.setLogin("caique.souza");
		u1.setEmail("caique@email.com");
		u1.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

		u1 = this.userRepository.register(u1);
		
		Post p1 = new Post();
		
		p1.setTitle("-        MEU NOME É CAIQUE            -");
		p1.setContent("Este é o conteúdo do post");
		
		final Publication publication = this.publicationRepository.register(p1, u1);

		Assert.assertNotNull(publication.getUser().getId());
		Assert.assertNotNull(publication.getPost().getId());
	}

	@Test
	public void addComment() {
		User u1 = new User();
		u1.setName("Caique");
		u1.setSurname("Souza");
		u1.setLogin("caique.souza");
		u1.setEmail("caique@email.com");
		u1.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

		User u2 = new User();
		u2.setName("Claudio");
		u2.setSurname("Antagonas");
		u2.setLogin("claudinei");
		u2.setEmail("claudinei@email.com");
		u2.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

		u1 = this.userRepository.register(u1);
		u2 = this.userRepository.register(u2);
		
		Post p1 = new Post();
		
		p1.setTitle("-        MEU NOME É CAIQUE            -");
		p1.setContent("Este é o conteúdo do post");
		
		this.publicationRepository.register(p1, u1);

		final Comment c1 = this.publicationRepository.addComment(p1, u2, "Estou vendo que você é o Caique");
		final Comment c2 = this.publicationRepository.addComment(p1, u1, "Sim, eu sou o Caique!");

		Assert.assertNotNull(c1.getId());
		Assert.assertNotNull(c2.getId());
	}

	@Test
	public void getComments() {
		User u1 = new User();
		u1.setName("Caique");
		u1.setSurname("Souza");
		u1.setLogin("caique.souza");
		u1.setEmail("caique@email.com");
		u1.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

		User u2 = new User();
		u2.setName("Claudio");
		u2.setSurname("Antagonas");
		u2.setLogin("claudinei");
		u2.setEmail("claudinei@email.com");
		u2.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

		u1 = this.userRepository.register(u1);
		u2 = this.userRepository.register(u2);
		
		Post p1 = new Post();
		
		p1.setTitle("-        MEU NOME É CAIQUE            -");
		p1.setContent("Este é o conteúdo do post");
		
		this.publicationRepository.register(p1, u1);

		this.publicationRepository.addComment(p1, u2, "Estou vendo que você é o Caique");
		this.publicationRepository.addComment(p1, u1, "Sim, eu sou o Caique!");

		final Collection<Comment> comments = this.publicationRepository.getComments(p1);

		Assert.assertTrue(comments.size() == 2);
	}
}

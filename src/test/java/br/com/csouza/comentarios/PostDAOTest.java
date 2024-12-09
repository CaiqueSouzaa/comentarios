package br.com.csouza.comentarios;

import java.time.Instant;
import java.util.Collection;

import org.junit.Test;
import org.junit.After;
import org.junit.Assert;

import br.com.csouza.comentarios.dao.PostDAO;
import br.com.csouza.comentarios.dao.UserDAO;
import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.FakeSizeException;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IPostDAO;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;
import br.com.csouza.comentarios.repository.UserRepository;
import br.com.csouza.comentarios.utils.Fake;

public class PostDAOTest {
	private final IPostDAO postDAO;
	private final IUserRepository userRepository;
	
	public PostDAOTest() {
		this.postDAO = new PostDAO();
		this.userRepository = new UserRepository(new UserDAO());
	}
	
	@After
	public void deleteRegisters() throws IDNotFoundException {
		final Collection<User> users = this.userRepository.getAll();
		final Collection<Post> posts = this.postDAO.read();
		
		for (Post post : posts) {
			System.out.println(post.getTitle());
			System.out.println(post.getId());
			this.postDAO.delete(post.getId());
		}
		
		for (User user : users) {
			this.userRepository.destroy(user.getId());
		}
	}
	
	/**
	 * Método para gerar um post aleatório.
	 * @param user Usuário a ser atribuido ao post.
	 * @return Post aleatório gerado.
	 * @throws FakeSizeException
	 */
	private Post createPost(User user) throws FakeSizeException {
		final Post post = new Post();
		
		post.setTitle(Fake.fake(10));
		post.setContent(Fake.fake(20));
		post.setUser(user);
		
		return post;
	}

	/**
	 * Método responsável por registrar um post aleatório no banco de dados.
	 * @param user Usuário a ser atribuido ao post.
	 * @return Post aleatório com seu ID de registro.
	 * @throws FakeSizeException
	 */
	private Post register(User user) throws FakeSizeException {
		final Post post = this.createPost(user);
		return this.postDAO.create(post);
	}
	
	@Test
	public void create() throws FakeSizeException {
		User u1 = new User();
		
		u1.setName("Caique");
		u1.setSurname("Souza");
		u1.setLogin(Fake.login(6, 5));
		u1.setEmail(Fake.email(6, 5, "email.com"));
		u1.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));
		
		u1 = this.userRepository.register(u1);

		Post p1 = new Post();
		Post p2 = new Post();

		p1.setTitle("Primeiro post");
		p1.setContent("Este é o teste do primeiro post.");
		p1.setUser(u1);
				
		p2.setTitle("Segundo post");
		p2.setContent("Este é o teste do segundo post.");
		p2.setUser(u1);
		
		p1 = this.postDAO.create(p1);
		p2 = this.postDAO.create(p2);

		Assert.assertNotNull(p1.getId());
		Assert.assertNotNull(p2.getId());
	}
	
	@Test
	public void read() throws FakeSizeException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);

		this.register(u1);
		this.register(u1);
		this.register(u2);
		
		final Collection<Post> posts = this.postDAO.read();
		
		Assert.assertTrue(posts.size() == 3);
	}
	
	@Test
	public void update() throws FakeSizeException, IDNotFoundException {
		final User u1 = Fake.user(userRepository);
		Post p1 = this.register(u1);
		
		p1.setTitle("Atualizando o título do post");
		p1.setContent("Agora estou atualizando o conteúdo da publicação.");
		
		p1 = this.postDAO.update(p1);
		
		final Post post = this.postDAO.findById(p1.getId());

		Assert.assertEquals(p1.getId(), post.getId());
		Assert.assertEquals(p1.getTitle(), post.getTitle());
		Assert.assertEquals(p1.getContent(), post.getContent());
	}
	
	@Test
	public void delete() throws FakeSizeException, IDNotFoundException {
		final User u1 = Fake.user(userRepository);
		final Post p1 = this.register(u1);
		
		final boolean r1 = this.postDAO.delete(p1.getId());
		
		Assert.assertTrue(r1);
	}
	
	@Test
	public void findById() throws FakeSizeException, IDNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		this.register(u1);
		final Post p2 = this.register(u2);
		this.register(u2);
		
		final Post post = this.postDAO.findById(p2.getId());

		Assert.assertEquals(p2.getId(), post.getId());
		Assert.assertEquals(p2.getTitle(), post.getTitle());
		Assert.assertEquals(p2.getContent(), post.getContent());
	}

	@Test
	public void findCreatedById() throws FakeSizeException, UserNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		this.register(u1);
		this.register(u2);
		this.register(u2);
		this.register(u2);
		this.register(u1);
		
		final Collection<Post> posts = this.postDAO.findCreatedById(u2.getId());
		
		Assert.assertTrue(posts.size() == 3);
	}
	
	@Test
	public void findCreatedByLogin() throws FakeSizeException, UserNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		this.register(u1);
		this.register(u2);
		this.register(u2);
		this.register(u2);
		this.register(u1);
		
		final Collection<Post> posts = this.postDAO.findCreatedByLogin(u1.getLogin());
		
		Assert.assertTrue(posts.size() == 2);
	}

	@Test
	public void findCreatedByEmail() throws FakeSizeException, UserNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		this.register(u1);
		this.register(u2);
		this.register(u2);
		this.register(u2);
		this.register(u1);
		
		final Collection<Post> posts = this.postDAO.findCreatedByEmail(u2.getEmail());
		
		Assert.assertTrue(posts.size() == 3);
	}
}

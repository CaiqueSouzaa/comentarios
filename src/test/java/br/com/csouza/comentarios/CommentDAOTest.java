package br.com.csouza.comentarios;

import br.com.csouza.comentarios.repository.CommentRepository;
import org.junit.Test;

import java.time.Instant;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;

import br.com.csouza.comentarios.dao.CommentDAO;
import br.com.csouza.comentarios.dao.PostDAO;
import br.com.csouza.comentarios.dao.UserDAO;
import br.com.csouza.comentarios.domain.Comment;
import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.FakeSizeException;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.PostNotFoundException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.ICommentDAO;
import br.com.csouza.comentarios.interfaces.repository.IPostRepository;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;
import br.com.csouza.comentarios.repository.PostRepository;
import br.com.csouza.comentarios.repository.UserRepository;
import br.com.csouza.comentarios.utils.Fake;


public class CommentDAOTest {
	private final ICommentDAO commentDAO;
	private final IUserRepository userRepository;
	private final IPostRepository postRepository;
	
	public CommentDAOTest() {
		this.commentDAO = new CommentDAO();
		this.userRepository = new UserRepository(new UserDAO());
		this.postRepository = new PostRepository(new PostDAO(), this.userRepository, new CommentRepository(new CommentDAO()));
	}
	
	@After
	public void deleteRegisters() throws IDNotFoundException {
		final Collection<Comment> comments = this.commentDAO.read();
		final Collection<Post> posts = this.postRepository.getAll();
		final Collection<User> users = this.userRepository.getAll();
		
		for (Comment c : comments) {
			this.commentDAO.delete(c.getId());
		}
		
		for (Post p : posts) {
			this.postRepository.destroy(p.getId());
		}
		
		for (User u : users) {
			this.userRepository.destroy(u.getId());
		}
	}
	
	/**
	 * Método para gerar comentário aleatório.
	 * @param post Post ao qual deverá receber o comentátio.
	 * @param user Usuário que deverá comentar.
	 * @return Comentário gerado.
	 * @throws FakeSizeException
	 */
	private Comment createComment(final Post post, final User user) throws FakeSizeException {
		final Comment comment = new Comment();
		
		comment.setComment(Fake.fake(15));
		comment.setPost(post);
		comment.setUser(user);
		
		return comment;
	}
	
	/**
	 * Método para gerar e registrar comentários no banco de dados.
	 * @param post Post ao qual deverá receber o comentátio.
	 * @param user Usuário que deverá comentar.
	 * @return Comentário registrado contendo seu ID.
	 * @throws FakeSizeException
	 */
	private Comment register(final Post post, final User user) throws FakeSizeException {
		return this.commentDAO.create(this.createComment(post, user));
	}
	
	@Test
	public void create() throws FakeSizeException {
		// Usuário que criou o post.
		User u1 = new User();
		u1.setName("Caique");
		u1.setSurname("Souza");
		u1.setLogin(Fake.login(6, 5));
		u1.setEmail(Fake.email(6, 5, "email.com"));
		u1.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));
		
		// Usuário que vai comentar no post.
		User u2 = new User();
		u2.setName("João");
		u2.setSurname("Barbosa");
		u2.setLogin(Fake.login(6, 5));
		u2.setEmail(Fake.email(6, 5, "email.com"));
		u2.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

		u1 = this.userRepository.register(u1);
		u2 = this.userRepository.register(u2);
		
		Post p1 = new Post();
		p1.setTitle("Primeiro post");
		p1.setContent("Este é o teste do primeiro post.");
		p1.setUser(u1);
		
		p1 = this.postRepository.register(p1);
		
		Comment c1 = new Comment();
		
		c1.setComment("Parece que isso está funcionando!");
		c1.setPost(p1);
		c1.setUser(u2);
		
		c1 = this.commentDAO.create(c1);
		
		Assert.assertNotNull(c1.getId());
	}
	
	@Test
	public void read() throws FakeSizeException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		final Post p1 = Fake.post(postRepository, u1);
		final Post p2 = Fake.post(postRepository, u2);
		
		this.register(p1, u2);
		this.register(p2, u1);
		this.register(p1, u1);
		this.register(p2, u2);
		
		final Collection<Comment> comments = this.commentDAO.read();
		
		Assert.assertTrue(comments.size() == 4);
	}
	
	@Test
	public void update() throws FakeSizeException, IDNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		final Post p1 = Fake.post(postRepository, u1);
		final Post p2 = Fake.post(postRepository, u2);
		
		this.register(p1, u2);
		this.register(p2, u1);
		Comment c3 = this.register(p1, u1);
		this.register(p2, u2);
		
		c3.setComment("Estou corrigindo meu comentário, ele estava errado :)");
		
		c3 = this.commentDAO.update(c3);
		
		final Comment comment = this.commentDAO.findById(c3.getId());
		
		Assert.assertEquals(c3.getComment(), comment.getComment());
	}
	
	@Test
	public void delete() throws FakeSizeException, IDNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		final Post p1 = Fake.post(postRepository, u1);
		final Post p2 = Fake.post(postRepository, u2);
		
		this.register(p1, u2);
		final Comment c2 = this.register(p2, u1);
		this.register(p1, u1);
		this.register(p2, u2);
		
		final boolean r1 = this.commentDAO.delete(c2.getId());
		
		Assert.assertTrue(r1);
	}
	
	@Test
	public void findById() throws FakeSizeException, IDNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		final Post p1 = Fake.post(postRepository, u1);
		final Post p2 = Fake.post(postRepository, u2);
		
		this.register(p1, u2);
		this.register(p2, u1);
		final Comment c3 = this.register(p1, u1);
		this.register(p2, u2);
		
		final Comment comment = this.commentDAO.findById(c3.getId());
		
		Assert.assertEquals(c3.getId(), comment.getId());
		Assert.assertEquals(c3.getComment(), comment.getComment());
	}

	@Test
	public void getAllByPostId() throws FakeSizeException, PostNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		final Post p1 = Fake.post(postRepository, u1);
		final Post p2 = Fake.post(postRepository, u2);
		
		this.register(p1, u2);
		this.register(p2, u1);
		this.register(p1, u1);
		this.register(p2, u2);
		this.register(p2, u2);
		
		final Collection<Comment> comments1 = this.commentDAO.findAllByPostId(p1.getId());
		final Collection<Comment> comments2 = this.commentDAO.findAllByPostId(p2.getId());

		Assert.assertTrue(comments1.size() == 2);
		Assert.assertTrue(comments2.size() == 3);
	}
	
	@Test
	public void getCreatedById() throws FakeSizeException, PostNotFoundException, UserNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		final Post p1 = Fake.post(postRepository, u1);
		final Post p2 = Fake.post(postRepository, u2);
		
		this.register(p1, u2);
		this.register(p2, u1);
		this.register(p1, u1);
		this.register(p2, u2);
		this.register(p2, u2);

		final Collection<Comment> comments1 = this.commentDAO.findCreatedById(p1.getId(), u2.getId()); // Comentou uma vez
		final Collection<Comment> comments2 = this.commentDAO.findCreatedById(p2.getId(), u1.getId()); // Comentou uma vez
		final Collection<Comment> comments3 = this.commentDAO.findCreatedById(p1.getId(), u1.getId()); // Comentou uma vez
		final Collection<Comment> comments4 = this.commentDAO.findCreatedById(p2.getId(), u2.getId()); // Comentou duas vezes

		Assert.assertTrue(comments1.size() == 1);
		Assert.assertTrue(comments2.size() == 1);
		Assert.assertTrue(comments3.size() == 1);
		Assert.assertTrue(comments4.size() == 2);
		
	}
	
	@Test
	public void getCreatedByLogin() throws FakeSizeException, UserNotFoundException, PostNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		final Post p1 = Fake.post(postRepository, u1);
		final Post p2 = Fake.post(postRepository, u2);
		
		this.register(p1, u2);
		this.register(p2, u1);
		this.register(p1, u1);
		this.register(p2, u2);
		this.register(p2, u2);

		final Collection<Comment> comments1 = this.commentDAO.findCreatedByLogin(p1.getId(), u2.getLogin()); // Comentou uma vez
		final Collection<Comment> comments2 = this.commentDAO.findCreatedByLogin(p2.getId(), u1.getLogin()); // Comentou uma vez
		final Collection<Comment> comments3 = this.commentDAO.findCreatedByLogin(p1.getId(), u1.getLogin()); // Comentou uma vez
		final Collection<Comment> comments4 = this.commentDAO.findCreatedByLogin(p2.getId(), u2.getLogin()); // Comentou duas vezes

		Assert.assertTrue(comments1.size() == 1);
		Assert.assertTrue(comments2.size() == 1);
		Assert.assertTrue(comments3.size() == 1);
		Assert.assertTrue(comments4.size() == 2);
	}
	
	@Test
	public void getCreatedByEmail() throws FakeSizeException, UserNotFoundException, PostNotFoundException {
		final User u1 = Fake.user(userRepository);
		final User u2 = Fake.user(userRepository);
		
		final Post p1 = Fake.post(postRepository, u1);
		final Post p2 = Fake.post(postRepository, u2);
		
		this.register(p1, u2);
		this.register(p2, u1);
		this.register(p1, u1);
		this.register(p2, u2);
		this.register(p2, u2);

		final Collection<Comment> comments1 = this.commentDAO.findCreatedByEmail(p1.getId(), u2.getEmail()); // Comentou uma vez
		final Collection<Comment> comments2 = this.commentDAO.findCreatedByEmail(p2.getId(), u1.getEmail()); // Comentou uma vez
		final Collection<Comment> comments3 = this.commentDAO.findCreatedByEmail(p1.getId(), u1.getEmail()); // Comentou uma vez
		final Collection<Comment> comments4 = this.commentDAO.findCreatedByEmail(p2.getId(), u2.getEmail()); // Comentou duas vezes

		Assert.assertTrue(comments1.size() == 1);
		Assert.assertTrue(comments2.size() == 1);
		Assert.assertTrue(comments3.size() == 1);
		Assert.assertTrue(comments4.size() == 2);
	}
}

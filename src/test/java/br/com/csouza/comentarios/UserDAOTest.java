package br.com.csouza.comentarios;

import org.junit.Test;
import org.junit.Assert;
import org.junit.After;

import br.com.csouza.comentarios.dao.UserDAO;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.FakeSizeException;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;
import br.com.csouza.comentarios.utils.Fake;

import java.time.Instant;
import java.util.Collection;


public class UserDAOTest {
	private final IUserDAO userDAO;
	
	public UserDAOTest() {
		this.userDAO = new UserDAO();
	}
	
	@After
	public void deleteRegisters() {
		final Collection<User> users = this.userDAO.read();
		
		users.forEach(user -> {
			try {
				this.userDAO.delete(user.getId());
			} catch (IDNotFoundException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Método para criar um objeto Usuário.
	 * @return Objeto usuário com informações fakes;
	 * @throws FakeSizeException Exceção lançada caso apresente algum erro.
	 */
	private static User createUser() throws FakeSizeException {
		final User user = new User();
		
		user.setName(Fake.fake(6));
		user.setSurname(Fake.fake(6));
		user.setLogin(Fake.login(6, 6));
		user.setEmail(Fake.email(5, 5, "email.com"));
		user.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));
		
		return user;
	}
	
	/**
	 * Método para criar e registrar um usuário no banco de dados.
	 * @return Usuário registrado no banco de dados.
	 * @throws FakeSizeException Exceção lançada caso apresente algum erro.
	 */
	private User register() throws FakeSizeException {
		return this.userDAO.create(createUser());
	}
	
	@Test
	public void create() throws FakeSizeException {
		User u1 = new User();
		
		u1.setName("Caique");
		u1.setSurname("Souza");
		u1.setLogin(Fake.login(6, 5));
		u1.setEmail(Fake.email(6, 5, "email.com"));
		u1.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));
		
		u1 = this.userDAO.create(u1);
				
		Assert.assertNotNull(u1.getId());
	}
	
	@Test
	public void read() throws FakeSizeException {	
		final User u1 = this.register();
		final User u2 = this.register();
		final User u3 = this.register();
		
		final Collection<User> users = this.userDAO.read();
		
		Assert.assertTrue(users.size() == 3);
		
	}
	
	@Test
	public void update() throws FakeSizeException, IDNotFoundException {
		final User u1 = this.register();
		final User u2 = this.register();
		final User u3 = this.register();
		
		u2.setName("Caique");
		u2.setSurname("Souza");
		u2.setLogin("caique.souza");
		u2.setEmail("caique@email.com");
		
		final User user = this.userDAO.update(u2);

		Assert.assertEquals(u2.getName(), user.getName());
		Assert.assertEquals(u2.getSurname(), user.getSurname());
		Assert.assertEquals(u2.getLogin(), user.getLogin());
		Assert.assertEquals(u2.getEmail(), user.getEmail());
	}
	
	@Test
	public void delete() throws FakeSizeException, IDNotFoundException {
		final User u1 = this.register();
		final User u2 = this.register();
		final User u3 = this.register();

		final boolean r1 = this.userDAO.delete(u1.getId());
		final boolean r2 = this.userDAO.delete(u2.getId());
		final boolean r3 = this.userDAO.delete(u3.getId());

		Assert.assertTrue(r1);
		Assert.assertTrue(r2);
		Assert.assertTrue(r3);
	}
	
	@Test
	public void findById() throws FakeSizeException, IDNotFoundException {
		final User u1 = this.register();
		final User u2 = this.register();
		final User u3 = this.register();
		final User u4 = this.register();
		
		final User user = this.userDAO.findById(u3.getId());

		Assert.assertEquals(u3.getId(), user.getId());
		Assert.assertEquals(u3.getName(), user.getName());
		Assert.assertEquals(u3.getSurname(), user.getSurname());
	}

	@Test
	public void findByLogin() throws FakeSizeException, UserNotFoundException {
		final User u1 = this.register();
		final User u2 = this.register();
		final User u3 = this.register();
		final User u4 = this.register();
		
		final User user = this.userDAO.findByLogin(u2.getLogin());

		Assert.assertEquals(u2.getId(), user.getId());
		Assert.assertEquals(u2.getName(), user.getName());
		Assert.assertEquals(u2.getSurname(), user.getSurname());
		Assert.assertEquals(u2.getLogin(), user.getLogin());
	}

	@Test
	public void findByEmail() throws FakeSizeException, UserNotFoundException {
		final User u1 = this.register();
		final User u2 = this.register();
		final User u3 = this.register();
		final User u4 = this.register();
		
		final User user = this.userDAO.findByEmail(u2.getEmail());

		Assert.assertEquals(u2.getId(), user.getId());
		Assert.assertEquals(u2.getName(), user.getName());
		Assert.assertEquals(u2.getSurname(), user.getSurname());
		Assert.assertEquals(u2.getEmail(), user.getEmail());
	}
	
}

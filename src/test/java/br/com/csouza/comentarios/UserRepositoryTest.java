package br.com.csouza.comentarios;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.com.csouza.comentarios.dao.UserDAO;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.FakeSizeException;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.repository.UserRepository;
import br.com.csouza.comentarios.utils.Fake;

import java.time.Instant;
import java.util.Collection;

public class UserRepositoryTest {
    private final UserRepository userRepository;

    public UserRepositoryTest() {
        this.userRepository = new UserRepository(new UserDAO());
    }

    @After
    public void deleteRegisters() {
        final Collection<User> users = this.userRepository.getAll();

        users.forEach(user -> {
            try {
                this.userRepository.destroy(user.getId());
            } catch (IDNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private static User createUser() throws FakeSizeException {
        final User user = new User();

        user.setName(Fake.fake(6));
        user.setSurname(Fake.fake(6));
        user.setLogin(Fake.login(6, 6));
        user.setEmail(Fake.email(5, 5, "email.com"));
        user.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

        return user;
    }

    private User register() throws FakeSizeException {
        return this.userRepository.register(createUser());
    }

    @Test
    public void registerTest() throws FakeSizeException {
        User user = new User();
        user.setName("Caique");
        user.setSurname("Souza");
        user.setLogin(Fake.login(6, 5));
        user.setEmail(Fake.email(6, 5, "email.com"));
        user.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

        user = this.userRepository.register(user);

        Assert.assertNotNull(user.getId());
    }

    @Test
    public void getAllTest() throws FakeSizeException {
        this.register();
        this.register();
        this.register();

        final Collection<User> users = this.userRepository.getAll();

        Assert.assertEquals(3, users.size());
    }

    @Test
    public void updateTest() throws FakeSizeException, IDNotFoundException {
        User user = this.register();

        user.setName("Caique");
        user.setSurname("Souza");
        user.setLogin("caique.souza");
        user.setEmail("caique@email.com");

        final User updatedUser = this.userRepository.update(user);

        Assert.assertEquals("Caique", updatedUser.getName());
        Assert.assertEquals("Souza", updatedUser.getSurname());
        Assert.assertEquals("caique.souza", updatedUser.getLogin());
        Assert.assertEquals("caique@email.com", updatedUser.getEmail());
    }

    @Test
    public void destroyTest() throws FakeSizeException, IDNotFoundException {
        User user = this.register();

        final boolean result = this.userRepository.destroy(user.getId());

        Assert.assertTrue(result);
    }

    @Test
    public void getByIdTest() throws FakeSizeException, IDNotFoundException {
        User user = this.register();

        final User foundUser = this.userRepository.getById(user.getId());

        Assert.assertEquals(user.getId(), foundUser.getId());
        Assert.assertEquals(user.getName(), foundUser.getName());
        Assert.assertEquals(user.getSurname(), foundUser.getSurname());
    }

    @Test
    public void getByLoginTest() throws FakeSizeException, UserNotFoundException {
        User user = this.register();

        final User foundUser = this.userRepository.getByLogin(user.getLogin());

        Assert.assertEquals(user.getId(), foundUser.getId());
        Assert.assertEquals(user.getName(), foundUser.getName());
        Assert.assertEquals(user.getSurname(), foundUser.getSurname());
        Assert.assertEquals(user.getLogin(), foundUser.getLogin());
    }

    @Test
    public void getByEmailTest() throws FakeSizeException, UserNotFoundException {
        User user = this.register();

        final User foundUser = this.userRepository.getByEmail(user.getEmail());

        Assert.assertEquals(user.getId(), foundUser.getId());
        Assert.assertEquals(user.getName(), foundUser.getName());
        Assert.assertEquals(user.getSurname(), foundUser.getSurname());
        Assert.assertEquals(user.getEmail(), foundUser.getEmail());
    }
}

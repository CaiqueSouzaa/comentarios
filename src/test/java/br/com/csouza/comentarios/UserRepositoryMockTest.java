package br.com.csouza.comentarios;

import org.junit.Test;

import br.com.csouza.comentarios.dao.mock.UserMock;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.FakeSizeException;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.UserEmailInvalidException;
import br.com.csouza.comentarios.exceptions.UserLoginLength;
import br.com.csouza.comentarios.exceptions.UserNameLength;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.exceptions.UserSurnameLength;
import br.com.csouza.comentarios.repository.UserRepository;
import br.com.csouza.comentarios.utils.Fake;

import java.time.Instant;

public class UserRepositoryMockTest {
    private final UserRepository userRepository;

    public UserRepositoryMockTest() {
        this.userRepository = new UserRepository(new UserMock());
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
    }

    @Test
    public void getAllTest() throws FakeSizeException {
        this.register();
        this.register();
        this.register();

        this.userRepository.getAll();

    }

    @Test
    public void updateTest() throws FakeSizeException, IDNotFoundException {
        User user = this.register();

        user.setName("Caique");
        user.setSurname("Souza");
        user.setLogin("caique.souza");
        user.setEmail("caique@email");

        this.userRepository.update(user);
    }

    @Test
    public void destroyTest() throws FakeSizeException, IDNotFoundException {
        User user = this.register();

        this.userRepository.destroy(user.getId());
    }

    @Test
    public void getByIdTest() throws FakeSizeException, IDNotFoundException {
        User user = this.register();

        this.userRepository.getById(user.getId());
    }

    @Test
    public void getByLoginTest() throws FakeSizeException, UserNotFoundException {
        User user = this.register();
        this.userRepository.getByLogin(user.getLogin());
    }

    @Test
    public void getByEmailTest() throws FakeSizeException, UserNotFoundException {
        User user = this.register();
        this.userRepository.getByEmail(user.getEmail());
    }

    // Exceptions ----------------------------------------------------------------------
    @Test(expected = UserNameLength.class)
    public void userNameLength() throws FakeSizeException {
        User user = new User();
        user.setName("");
        user.setSurname("Souza");
        user.setLogin(Fake.login(6, 5));
        user.setEmail(Fake.email(6, 5, "email.com"));
        user.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

        user = this.userRepository.register(user);
    }

    @Test(expected = UserSurnameLength.class)
    public void userSurnameLength() throws FakeSizeException {
        User user = new User();
        user.setName("Caique");
        user.setSurname("");
        user.setLogin(Fake.login(6, 5));
        user.setEmail(Fake.email(6, 5, "email"));
        user.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

        user = this.userRepository.register(user);
    }

    @Test(expected = UserLoginLength.class)
    public void userLoginLength() throws FakeSizeException {
        User user = new User();
        user.setName("Caique");
        user.setSurname("Souza");
        user.setLogin("");
        user.setEmail(Fake.email(6, 5, "email.com"));
        user.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

        user = this.userRepository.register(user);
    }

    @Test(expected = UserEmailInvalidException.class)
    public void userEmailInvalidException() throws FakeSizeException {
        User user = new User();
        user.setName("Caique");
        user.setSurname("Souza");
        user.setLogin(Fake.login(6, 5));
        user.setEmail(Fake.email(6, 5, "email"));
        user.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

        user = this.userRepository.register(user);
    }
}

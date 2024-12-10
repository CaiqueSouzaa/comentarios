package br.com.csouza.comentarios;

import java.time.Instant;

import org.junit.Test;

import br.com.csouza.comentarios.dao.mock.PostMock;
import br.com.csouza.comentarios.dao.mock.UserMock;
import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.FakeSizeException;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.PostTitleLengthException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.repository.IPostRepository;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;
import br.com.csouza.comentarios.repository.PostRepository;
import br.com.csouza.comentarios.repository.UserRepository;
import br.com.csouza.comentarios.utils.Fake;

public class PostRepositoryMockTest {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    public PostRepositoryMockTest() {
        this.userRepository = new UserRepository(new UserMock());
        this.postRepository = new PostRepository(new PostMock(), this.userRepository);
    }

    private Post createPost(User user) throws FakeSizeException {
        final Post post = new Post();
        post.setTitle(Fake.fake(10));
        post.setContent(Fake.fake(20));
        post.setUser(user);
        return post;
    }

    private Post register(User user) throws FakeSizeException {
        final Post post = this.createPost(user);
        return this.postRepository.register(post);
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
        p1.setTitle("Primeiro post");
        p1.setContent("Este é o teste do primeiro post.");
        p1.setUser(u1);

        Post p2 = new Post();
        p2.setTitle("Segundo post");
        p2.setContent("Este é o teste do segundo post.");
        p2.setUser(u1);

        p1 = this.postRepository.register(p1);
        p2 = this.postRepository.register(p2);
    }

    @Test
    public void read() throws FakeSizeException {
        final User u1 = Fake.user(userRepository);
        final User u2 = Fake.user(userRepository);

        this.register(u1);
        this.register(u1);
        this.register(u2);

        this.postRepository.getAll();
    }

    @Test
    public void update() throws FakeSizeException, IDNotFoundException {
        final User u1 = Fake.user(userRepository);
        Post p1 = this.register(u1);

        p1.setTitle("Título atualizado");
        p1.setContent("Conteúdo atualizado.");

        p1 = this.postRepository.update(p1);

        this.postRepository.getById(p1.getId());
    }

    @Test
    public void delete() throws FakeSizeException, IDNotFoundException {
        final User u1 = Fake.user(userRepository);
        final Post p1 = this.register(u1);

        this.postRepository.destroy(p1.getId());
    }

    @Test
    public void getCreatedById() throws FakeSizeException, UserNotFoundException {
        final User u1 = Fake.user(userRepository);
        final User u2 = Fake.user(userRepository);

        this.register(u1);
        this.register(u2);
        this.register(u2);

        this.postRepository.getCreatedById(u2.getId());
    }

    @Test
    public void getCreatedByLogin() throws FakeSizeException, UserNotFoundException {
        final User u1 = Fake.user(userRepository);
        final User u2 = Fake.user(userRepository);

        this.register(u1);
        this.register(u2);
        this.register(u2);

        this.postRepository.getCreatedByLogin(u1.getLogin());
    }

    @Test
    public void getCreatedByEmail() throws FakeSizeException, UserNotFoundException {
        final User u1 = Fake.user(userRepository);
        final User u2 = Fake.user(userRepository);

        this.register(u1);
        this.register(u2);
        this.register(u2);

        this.postRepository.getCreatedByEmail(u2.getEmail());
    }

    @Test(expected = PostTitleLengthException.class)
    public void sdfsdf() throws FakeSizeException {
        User u1 = new User();
        u1.setName("Caique");
        u1.setSurname("Souza");
        u1.setLogin(Fake.login(6, 5));
        u1.setEmail(Fake.email(6, 5, "email.com"));
        u1.setBirthday(Instant.parse("2000-07-29T00:00:00Z"));

        u1 = this.userRepository.register(u1);

        Post p1 = new Post();
        p1.setTitle("");
        p1.setContent("Este é o teste do primeiro post.");
        p1.setUser(u1);

        Post p2 = new Post();
        p2.setTitle("Segundo post");
        p2.setContent("Este é o teste do segundo post.");
        p2.setUser(u1);

        p1 = this.postRepository.register(p1);
        p2 = this.postRepository.register(p2);
    }
}

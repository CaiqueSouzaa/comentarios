package br.com.csouza.comentarios;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

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
import br.com.csouza.comentarios.interfaces.repository.ICommentRepository;
import br.com.csouza.comentarios.interfaces.repository.IPostRepository;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;
import br.com.csouza.comentarios.repository.CommentRepository;
import br.com.csouza.comentarios.repository.PostRepository;
import br.com.csouza.comentarios.repository.UserRepository;
import br.com.csouza.comentarios.utils.Fake;

public class CommentRepositoryTest {
    private final ICommentRepository commentRepository;
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;

    public CommentRepositoryTest() {
        this.commentRepository = new CommentRepository(new CommentDAO());
        this.userRepository = new UserRepository(new UserDAO());
        this.postRepository = new PostRepository(new PostDAO());
    }

    @After
    public void deleteRegisters() throws IDNotFoundException {
        final Collection<Comment> comments = this.commentRepository.getAll();
        final Collection<Post> posts = this.postRepository.getAll();
        final Collection<User> users = this.userRepository.getAll();

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

    private Comment register(final Post post, final User user) throws FakeSizeException {
        final Comment comment = new Comment();
        comment.setComment(Fake.fake(15));
        comment.setPost(post);
        comment.setUser(user);
        return this.commentRepository.register(comment);
    }

    @Test
    public void create() throws FakeSizeException {
        User user = Fake.user(userRepository);
        Post post = Fake.post(postRepository, user);

        Comment comment = this.register(post, user);
        Assert.assertNotNull(comment.getId());
    }

    @Test
    public void read() throws FakeSizeException {
        User user1 = Fake.user(userRepository);
        User user2 = Fake.user(userRepository);

        Post post1 = Fake.post(postRepository, user1);
        Post post2 = Fake.post(postRepository, user2);

        this.register(post1, user1);
        this.register(post2, user2);
        this.register(post1, user2);

        Collection<Comment> comments = this.commentRepository.getAll();
        Assert.assertFalse(comments.isEmpty());
    }

    @Test
    public void update() throws FakeSizeException, IDNotFoundException {
        User user = Fake.user(userRepository);
        Post post = Fake.post(postRepository, user);

        Comment comment = this.register(post, user);
        comment.setComment("Updated comment");
        Comment updatedComment = this.commentRepository.update(comment);

        Assert.assertEquals("Updated comment", updatedComment.getComment());
    }

    @Test
    public void delete() throws FakeSizeException, IDNotFoundException {
        User user = Fake.user(userRepository);
        Post post = Fake.post(postRepository, user);

        Comment comment = this.register(post, user);
        boolean deleted = this.commentRepository.destroy(comment.getId());

        Assert.assertTrue(deleted);
    }

    @Test
    public void getAllByPostId() throws FakeSizeException, PostNotFoundException {
        User user = Fake.user(userRepository);
        Post post = Fake.post(postRepository, user);

        this.register(post, user);
        Collection<Comment> comments = this.commentRepository.getAllByPostId(post.getId());

        Assert.assertFalse(comments.isEmpty());
    }

    @Test
    public void getCreatedById() throws FakeSizeException, UserNotFoundException, PostNotFoundException {
        User user = Fake.user(userRepository);
        Post post = Fake.post(postRepository, user);

        this.register(post, user);
        Collection<Comment> comments = this.commentRepository.getCreatedById(post.getId(), user.getId());

        Assert.assertEquals(1, comments.size());
    }

    @Test
    public void getCreatedByLogin() throws FakeSizeException, UserNotFoundException, PostNotFoundException {
        User user = Fake.user(userRepository);
        Post post = Fake.post(postRepository, user);

        this.register(post, user);
        Collection<Comment> comments = this.commentRepository.getCreatedByLogin(post.getId(), user.getLogin());

        Assert.assertFalse(comments.isEmpty());
    }

    @Test
    public void getCreatedByEmail() throws FakeSizeException, UserNotFoundException, PostNotFoundException {
        User user = Fake.user(userRepository);
        Post post = Fake.post(postRepository, user);

        this.register(post, user);
        Collection<Comment> comments = this.commentRepository.getCreatedByEmail(post.getId(), user.getEmail());

        Assert.assertFalse(comments.isEmpty());
    }
}

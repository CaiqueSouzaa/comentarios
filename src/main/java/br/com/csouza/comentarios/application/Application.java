package br.com.csouza.comentarios.application;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import br.com.csouza.comentarios.dao.CommentDAO;
import br.com.csouza.comentarios.dao.PostDAO;
import br.com.csouza.comentarios.dao.UserDAO;
import br.com.csouza.comentarios.domain.*;
import br.com.csouza.comentarios.exceptions.UserEmailInvalidException;
import br.com.csouza.comentarios.exceptions.UserLoginLength;
import br.com.csouza.comentarios.exceptions.UserLoginNotAvaliableException;
import br.com.csouza.comentarios.repository.CommentRepository;
import br.com.csouza.comentarios.repository.PostRepository;
import br.com.csouza.comentarios.repository.PublicationRepository;
import br.com.csouza.comentarios.repository.UserRepository;
import br.com.csouza.comentarios.utils.Data;
import br.com.csouza.comentarios.utils.Terminal;

public class Application {
    private static final UserRepository userRepository = new UserRepository(new UserDAO());
    private static final PostRepository postRepository = new PostRepository(new PostDAO(), userRepository, new CommentRepository(new CommentDAO()));
    private static final PublicationRepository publicationRepository = new PublicationRepository(userRepository, postRepository, new CommentRepository(new CommentDAO()));

    public static void init() throws InterruptedException {
        System.out.print("\033c");

        final Scanner scanner = new Scanner(System.in);

        System.out.println("My Posts!");

        initOptions(scanner);

        scanner.close();
    }

    private static void initOptions(Scanner scan) throws InterruptedException {
        System.out.println("1: Entrar\n2: Me registrar");

        final int option = scan.nextInt();

        switch (option) {
            case 1:
                login(scan);
                break;
            case 2:
                register(scan);
                break;
            default:
            System.out.println("Opção inválida! Tente novamente.");
            initOptions(scan);
        }
    }

    private static void register(final Scanner scan) throws InterruptedException {
        Terminal.clear();
        System.out.println("Precisamos das seguintes informações para seguir com seu registro:" +
        "\n1° Nome\n2° Sobrenome\n3° Endereço de e-mail\n4° Nome de login");

        scan.nextLine();
        final String name = checkName(scan);
        final String surname = checkSurname(scan);
        final String email = checkEmail(scan);
        final String login = checkLogin(scan, true);

        final User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setLogin(login);

        createUser(user);

        if (!Data.isNull(user.getId())) {
            init();
        } else {
            System.out.println("Ocorreu um problema ao tentar registrar o usuário.\nPor favor, verifique as conexões com o banco de dados e tente  novamente.");
            System.exit(0);
        }
    }

    private static void login(final Scanner scan) throws InterruptedException {
        scan.nextLine();
        final String userLogin = checkLogin(scan, false);

        if (!hasLogin(userLogin)) {
            System.out.println("Nome de login não encontrado.");
            Thread.sleep(3000);
            init();
        }

        final User user = userRepository.getByLogin(userLogin);
        home(scan, user);
    }

    private static void home(final Scanner scan, final User user) {
        Terminal.clear();
        System.out.println("Login realizado com sucesso!");

        homeOptions(scan, user);
    }

    private static void homeOptions(final Scanner scan, final User user) {
        boolean isValid = false;
        int option = 0;
        final int allPostsOption = 1;
        final int newPostOption = 2;
        final int exitOption = 3;

        while (!isValid) {
            Terminal.clear();
            System.out.println("\n1: Todos os posts\n2: Criar um novo post\n3: Sair");
            option = nextInt(scan);

            if (option == allPostsOption || option == newPostOption || option == exitOption) {
                isValid = true;
            }

            switch (option) {
                case allPostsOption:
                    allPosts(scan, user);
                    break;
                case newPostOption:
                    newPost(scan, user);
                    break;
                case exitOption:
                    exit();
                    break;
                default:
                    System.out.println("Opção inválida.\nTente novamente.");
            }
        }
    }

    /**
     * Método para obter um valor garantido de inteiro.
     * @param scan - Scanner a ser usado.
     * @return Número inteiro.
     */
    private static int nextInt(final Scanner scan) {
        boolean isValid = false;
        int value = 0;

        while (!isValid) {
            try {
                value = scan.nextInt();

                isValid = true;

            } catch (Exception e) {
                System.out.println("Insira somente valores númericos.\nTente novamente.");
                scan.nextLine();
            }
        }

        return value;
    }

    private static void allPosts(final Scanner scan, final User user) {
        boolean isValid = false;
        final int commentsOption = 1;
        final int newCommentOption = 2;
        final int backOption = 3;
        final Collection<Post> posts = postRepository.getAll();

        posts.forEach(Application::showPost);

        while (!isValid) {
            System.out.println("1: Visualizar comentários de um post\n2: Novo comentário em um post\n3: Voltar");
            int option = nextInt(scan);

            if (option == commentsOption || option == newCommentOption || option == backOption) {
                isValid = true;
            }

            switch (option) {
                case commentsOption:
                    comments(scan, user);
                    break;
                case newCommentOption:
                    newComment(scan, user);
                    break;
                case backOption:
                    homeOptions(scan, user);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void comments(final Scanner scan, final User user) {
        boolean postIdIsValid = false;
        int postId = 0;

        while (!postIdIsValid) {
            System.out.print("ID de post: ");
            postId = nextInt(scan);

            if (hasPostId(postId)) {
                postIdIsValid = true;
            } else {
                System.out.println("ID de post não localizado. Tente novamente");
            }
        }

        PostComment postComment = postRepository.getComments(postId);

        System.out.println("[ " + postComment.getPost().getTitle() + " ] - " + postComment.getPost().getUser().getName() + " " + postComment.getPost().getUser().getSurname());
        postComment.getComments().forEach(c -> showComment(postComment, c));

        homeOptions(scan, user);
    }

    private static void newComment(final Scanner scan, final User user) {
        boolean postIdIsValid = false;
        boolean commentIsValid = false;
        int postId = 0;
        String comment = "";

        while (!postIdIsValid) {
            System.out.print("ID de post: ");
            postId = nextInt(scan);

            if (hasPostId(postId)) {
                postIdIsValid = true;
            } else {
                System.out.println("ID de post não localizado. Tente novamente");
            }
        }

        while (!commentIsValid) {
            System.out.print("Comentário: ");
            scan.nextLine();
            final String c = scan.nextLine();

            if (!Data.isEmpty(c)) {
                commentIsValid = true;
            } else {
                System.out.println("Comentário é obrigatório.");
            }
            comment = c;
            System.out.println(comment);
        }

        postRepository.addComment(postId, user, comment);

        allPosts(scan, user);
    }

    private static void newPost(final Scanner scan, final User user) {
        boolean titleIsValid = false;
        String title = "";
        scan.nextLine();

        while (!titleIsValid) {
            Terminal.clear();
            System.out.print("\n----- Novo post -----\nTítulo do post: ");
            title = scan.nextLine();

            if (!Data.isEmpty(title)) {
                titleIsValid = true;
            } else {
                System.out.println("Título é um campo obrigatório.");
            }
        }

        System.out.print("Conteúdo: ");
        final String content = scan.nextLine();

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        final Publication publication = publicationRepository.register(post, user);

        if (Data.isNull(publication.getPost().getId())) {
            System.out.println("Não foi possível registrar o post.");
            home(scan, user);
        }

        home(scan, user);
    }

    private static void exit() {
        System.exit(0);
    }

    private static String input(final Scanner scan, String inputName) {
        System.out.print(inputName + ": ");

        return scan.nextLine();
    }

    private static void createUser(User user) {
        userRepository.register(user);
    }

    /**
     * Método para obter um nome válido do usuário.
     * @param scan - Scanner usado.
     * @return Nome do usuário.
     */
    private static String checkName(final Scanner scan) {
        boolean isValid = false;
        String name = input(scan, "Nome");

        while (!isValid) {
            if (!Data.isValidSize(name, 3)) {
                System.out.println("O nome deve possuir 3 ou mais caracteres.");
                name = input(scan, "Nome");
            } else {
                isValid = true;
            }
        }

        return name;
    }

    /**
     * Método para obter um sobrenome válido do usuário.
     * @param scan - Scanner usado.
     * @return Sobrenome do usuário.
     */
    private static String checkSurname(final Scanner scan) {
        boolean isValid = false;
        String surname = input(scan, "Sobrenome");

        while (!isValid) {
            if (!Data.isValidSize(surname, 3)) {
                System.out.println("O sobrenome deve possuir 3 ou mais caracteres.");
                surname = input(scan, "Sobrenome");
            } else {
                isValid = true;
            }
        }

        return surname;
    }

    /**
     * Método para obter um endereço de e-mail válido do usuário.
     * @param scan Scanner para receber o e-mail.
     * @return E-mail do usuário.
     */
    private static String checkEmail(final Scanner scan) {
        final String emailRegex = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z0-9.]+$";
        boolean isValid = false;
        String email = input(scan, "Endereço de e-mail");
        while (!isValid) {
            if (!Data.isValidRegex(emailRegex, email)) {
                System.out.println("Insira um endereço de e-mail válido.");
                email = input(scan, "Endereço de e-mail");
            }

            if (Data.isValidRegex(emailRegex, email) && hasEmail(email)) {
                System.out.println("Endereço de e-mail em uso. Use outro.");
                email = input(scan, "Endereço de e-mail");
            }

            if (Data.isValidRegex(emailRegex, email) && !hasEmail(email)) {
                isValid = true;
            }
        }

        return email;
    }

    /**
     * Método para obter um nome de login válido do usuário.
     * @param scan - Scanner para receber o login.
     * @param register - Especificar se o método será usado para login ou para registro.
     * @return Login do usuário.
     */
    private static String checkLogin(final Scanner scan, boolean register) {
        boolean isValid = false;
        String login = input(scan, "Nome de login");

        while (!isValid) {
            if (!Data.isValidSize(login, 5)) {
                System.out.println("O nome de login deve possuir 5 ou mais caracteres.");
                login = input(scan, "Nome de login");
            }

            if (register) {
                if (Data.isValidSize(login, 5) && hasLogin(login)) {
                    System.out.println("Nome de login em uso. Use outro.");
                    login = input(scan, "Nome de login");
                }

                if (Data.isValidSize(login, 5) && !hasLogin(login)) {
                    isValid = true;
                }
            } else if (Data.isValidSize(login, 5)) {
                isValid = true;
            }
        }

        return login;
    }

    /**
     * Método para verificar se o endereço de e-mail já está em uso.
     * @param email - Endereço de e-mail a ser verificado.
     * @return Booleano.
     */
    private static boolean hasEmail(String email) {
        return userRepository.getByEmail(email) != null;
    }

    /**
     * Método para verificar se nome de login já está em uso.
     * @param login - Nome de login a ser verificado.
     * @return Booleano.
     */
    private static boolean hasLogin(String login) {
        return userRepository.getByLogin(login) != null;
    }

    private static void showPost(Post post) {
        System.out.println(post.getId() + ": [ " + post.getTitle() + " ] - " + post.getUser().getName() + " " + post.getUser().getSurname() + "\n  * " + post.getContent() + "\n");
    }

    private static boolean hasPostId(long id) {
        try {
            final Post p = postRepository.getById(id);

            return !Data.isNull(p);
        } catch (Exception e) {
            return false;
        }
    }

    private static void showComment(final PostComment post, final Comment comment) {
        System.out.println(
                " -> " + comment.getUser().getName() + " " + comment.getUser().getSurname() +
                    "\n  * " + comment.getComment() + "\n"
        );
    }
}

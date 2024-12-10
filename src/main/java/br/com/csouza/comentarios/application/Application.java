package br.com.csouza.comentarios.application;

import java.util.Scanner;

import br.com.csouza.comentarios.dao.UserDAO;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.UserEmailInvalidException;
import br.com.csouza.comentarios.exceptions.UserLoginLength;
import br.com.csouza.comentarios.exceptions.UserLoginNotAvaliableException;
import br.com.csouza.comentarios.repository.UserRepository;
import br.com.csouza.comentarios.utils.Data;
import br.com.csouza.comentarios.utils.Terminal;

public class Application {
    private static UserRepository userRepository = new UserRepository(new UserDAO());

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
        }
        System.out.println("Ocorreu um problema ao tentar registrar o usuário.\nPor favor, verifique as conexões com o banco de dados e tente  novamente.");
        System.exit(0);
    }

    private static void login(final Scanner scan) throws InterruptedException {
        scan.nextLine();
        final String userLogin = checkLogin(scan, false);

        if (!hasLogin(userLogin)) {
            System.out.println("Nome de login não encontrado.");
            Thread.sleep(3000);
            init();
        }

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
}

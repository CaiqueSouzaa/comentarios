package br.com.csouza.comentarios.application;

import java.util.Scanner;

import br.com.csouza.comentarios.dao.UserDAO;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.UserEmailInvalidException;
import br.com.csouza.comentarios.exceptions.UserLoginLength;
import br.com.csouza.comentarios.exceptions.UserLoginNotAvaliableException;
import br.com.csouza.comentarios.repository.UserRepository;
import br.com.csouza.comentarios.utils.Terminal;

public class Application {
    private static UserRepository userRepository = new UserRepository(new UserDAO());

    public static void init() {
        System.out.print("\033c");

        final Scanner scanner = new Scanner(System.in);

        System.out.println("My Posts!");

        initOptions(scanner);

        scanner.close();

    }

    private static void initOptions(Scanner scan) {
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

    private static void register(final Scanner scan) {
        System.out.print("\033c");
        System.out.println("Precisamos das seguintes informações para seguir com seu registro:" +
        "\n1° Nome\n2° Sobrenome\n3° Endereço de e-mail\n4° Nome de login");

        scan.nextLine();
        final String name = input(scan, "Nome");
        final String surname = input(scan, "Sobrenome");
        final String email = input(scan, "Endereço de e-mail");
        final String login = input(scan, "Nome de login");

        final User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setLogin(login);

        createUser(scan, user);

        // init();
    }

    private static void login(final Scanner scan) {
        System.out.print("Informe seu nome de login: ");

        final String userLogin = scan.nextLine();
        User user = null;

        try {
            user = userRepository.getByLogin(userLogin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            login(scan);
        }

        System.out.println(user.getName());
    }

    private static String input(final Scanner scan, String inputName) {
        System.out.print(inputName + ": ");
        final String value = scan.nextLine();

        return value;
    }

    private static void createUser(final Scanner scan, User user) {
        // while(hasUserLogin(scan, user)) {
        //     Terminal.clear();
        //     System.out.println("Nome de login em uso. Tente usar outro.");
        //     final String login = input(scan, "Nome de login");
        //     user.setLogin(login);
        // }

        hasUserLogin(scan, user);

        while (hasUserEmail(scan, user)) {
            Terminal.clear();
            System.out.println("Endereço de e-mail já registrado.\nEntre com um novo endereço de e-mail.");
            final String email = input(scan, "Endereço de e-mail");
            user.setEmail(email);
        }

        try {
            user = userRepository.register(user);
        } catch (Exception e) {
            if (user.getLogin().length() < 5) {
                Terminal.clear();
                System.out.println("O nome de login deve possuir 5 ou mais caracteres. Tente novamente.");
                final String login = input(scan, "Nome de login");
                user.setLogin(login);

                createUser(scan, user);
            }

            e.printStackTrace();
        }


    }

    private static boolean hasUserLogin(final Scanner scan, User user) {
        boolean status = false;

        try{
            return userRepository.getByLogin(user.getLogin()) == null ? false : true;
        } catch (Exception e) {
            while (!status) {
                Terminal.clear();

                e.printStackTrace();

                if (e.getClass() == UserLoginLength.class) {
                    System.out.println("REPETINDO - O nome de login deve possuir 5 ou mais caracteres. Tente novamente.");
                    final String login = input(scan, "Nome de login");
                    if (login.trim().length() < 5) {
                        status = false;
                        continue;
                    }

                    user.setLogin(login);
                }

                if (e.getClass() == UserLoginNotAvaliableException.class) {
                    System.out.println("REPETINDO - Nome de login em uso. Tente usar outro.");
                    final String login = input(scan, "Nome de login");

                    user.setLogin(login);
                }
    
                status = true;
            }
        }

        return status;
    }

    private static boolean hasUserEmail(final Scanner scan, User user) {
        boolean status = false;

        try {
            return userRepository.getByEmail(user.getEmail()) == null ? false : true;
        } catch (Exception e) {
            while (!status) {
                Terminal.clear();

                e.printStackTrace();

                if (e.getClass() == UserEmailInvalidException.class) {
                    status = false;
                    System.out.println(e.getMessage());
                    final String email = input(scan, "Endereço de e-mail");
                    user.setEmail(email);
                }

                status = hasUserEmail(scan, user);
            }
        }

        return status;
    }
}

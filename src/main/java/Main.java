import java.sql.*;
import java.util.*;

public class Main {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static boolean repeatGame = true;

    public static void main(String[] args) {

        while (repeatGame) {

            System.out.println("Давай сыграем! Будешь играть один или вас двое?\nЕсли один нажми - 1\nЕсли вдвоем нажми - 2\nЕсли вдвоем со ставками - 3");
            Scanner scannerChooseGame = new Scanner(System.in);
            int choose = 0;
            while (choose != 1 && choose != 2 && choose != 3) {
                if (scannerChooseGame.hasNextInt()) {
                    choose = scannerChooseGame.nextInt();
                    if (choose != 1 && choose != 2 && choose != 3) {
                        System.out.println("Я же сказал - 1 (один), 2 (два) или 3 (три)!");
                    }
                } else {
                    System.out.println("Попробуй ввести цифру...");
                    scannerChooseGame.next();
                }
            }

            switch (choose) {
                case 1 -> startGame();
                case 2 -> TwoPlayerGame.startGameTwoPlayer();
                case 3 -> AdultTwoPlayer.startGameTwoPlayer();
            }

            System.out.println("Хочешь сыграть еще?\nЕсли да нажми - 1\nЕсли нет нажми - любую букву");
            Scanner scannerStartGame = new Scanner(System.in);
            int chooseRepeat = 0;
            while (chooseRepeat != 1) {
                if (scannerStartGame.hasNextInt()) {
                    chooseRepeat = scannerStartGame.nextInt();
                    if (chooseRepeat != 1) {
                        System.out.println("Балуешься? Вводи 1!");
                    }
                } else {
                    System.out.println("Это буковки, но я понял, что ты хочешь закончить\nСпасибо за игру!");
                    scannerStartGame.next();
                }
            }
            repeatGame = true;
        }
    }


    public static void startGame() {

        System.out.println("*".repeat(50));
        System.out.println("Это игра - \"Покер на костях\". Как тебя зовут?");
        Scanner scannerName = new Scanner(System.in);
        String namePlayer = scannerName.next();

        System.out.println("Привет, " + namePlayer + "! " + "Чтобы бросить кубики - нажми 1!");

        Scanner scannerStartGame = new Scanner(System.in);
        int choose = 0;

        while (choose != 1) {
            if (scannerStartGame.hasNextInt()) {
                choose = scannerStartGame.nextInt();
                if (choose != 1) {
                    System.out.println("Я же сказал - 1 (один)!");
                }
            } else {
                System.out.println("Попробуй ввести цифру...");
                scannerStartGame.next();
            }
        }


        PlayerBonesPoker player = PlayerBonesPoker.makePlayerBonesPoker(namePlayer);

        createIntrigue();
        System.out.println();

        System.out.println(player.getName() + ", у тебя выпало " + player.getBonesHand());
        System.out.println();
        player.getNameCombinationsPlayer(player);
        System.out.println("*".repeat(50));

        try {
            connect();
            addGameToDatabase(player);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }

    }

    public static void createIntrigue() {
        String[] intrigueVoice = {"Трясем кубики", "Дуем в кулачек", "Загадываем лучшую комбинацию",
                "Ахалай Махалай!", "Ну пожаааалуйста...", "Танцуем с бубном", "Луна дай мне силу!"};

        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(intrigueVoice[(int) (Math.random() * 6)]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.jdbc3.JDBC3Connection");
            connection = DriverManager.getConnection("jdbc:sqlite:bones_games_base.db");
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("INSERT INTO bones_games_base (name_player, combination, combination_name, combination_priority) VALUES (?, ?, ?, ?);");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Unable to connect");
        }
    }

    private static void addGameToDatabase(PlayerBonesPoker playerBonesPoker) throws SQLException {
        connection.setAutoCommit(false);
        preparedStatement.setString(1, playerBonesPoker.getName());
        preparedStatement.setString(2, String.valueOf(playerBonesPoker.getBonesHand()));
        preparedStatement.setString(3, playerBonesPoker.bonesCombinations.getBonesCombinationName());
        preparedStatement.setInt(4, playerBonesPoker.getPriorityCombinationsPlayer(playerBonesPoker));
        preparedStatement.executeUpdate(); //ToDo после завершения программы добавить варианты вызова методов для анализа базы: 1) Хотите еще раз 2) Узнать какая комбинация выпадает чаще всего 3) Сколько сыграно партий 4) Самая крутая комбинация с момента создания игры 5) Кто чаще играет 6) Закончить играть
        connection.commit();
    }

    public static void disconnect() {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




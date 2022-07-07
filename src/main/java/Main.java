import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.*;

public class Main {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    public static void main(String[] args) {

        bonesCombinationsCheck(startGame());

    }

    public static @NotNull
    PlayerBonesPoker startGame() {

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
                scannerStartGame.next(); //ToDo Добавить эффект кидания кубиков (дзынь дзынь)
            }
        }

        PlayerBonesPoker player = new PlayerBonesPoker(namePlayer);//ToDo Добавить возможность второго игрока
        //ToDo Добавить функцию "подбросить монетку", если два игрока
        System.out.println(player.getName() + ", у тебя выпало " + player.getBonesHand());//ToDo Добавить возможность менять руку
        System.out.println();//ToDo Добавить ЭНАМ по возможным комбинациям Название и приоритет

        try {
            connect();
            addGameToDatabase(player);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            disconnect();
        }

        return player;
    }

    public static void bonesCombinationsCheck(PlayerBonesPoker playerBonesPoker) {

        int repeatedOne = 0;
        int repeatedTwo = 0;
        int repeatedThree = 0;
        int repeatedFour = 0;
        int repeatedFive = 0;
        int repeatedSix = 0;

        for (int i = 0; i < playerBonesPoker.getBonesHand().size(); i++) {
            if (playerBonesPoker.getBonesHand().get(i) == 1) {
                repeatedOne++;
            } else if (playerBonesPoker.getBonesHand().get(i) == 2) {
                repeatedTwo++;
            } else if (playerBonesPoker.getBonesHand().get(i) == 3) {
                repeatedThree++;
            } else if (playerBonesPoker.getBonesHand().get(i) == 4) {
                repeatedFour++;
            } else if (playerBonesPoker.getBonesHand().get(i) == 5) {
                repeatedFive++;
            } else if (playerBonesPoker.getBonesHand().get(i) == 6) {
                repeatedSix++;
            }
        }

        ArrayList<Integer> bonesCombinationsPrint = new ArrayList<>();
        bonesCombinationsPrint.add(repeatedOne);
        bonesCombinationsPrint.add(repeatedTwo);
        bonesCombinationsPrint.add(repeatedThree);
        bonesCombinationsPrint.add(repeatedFour);
        bonesCombinationsPrint.add(repeatedFive);
        bonesCombinationsPrint.add(repeatedSix);
        Collections.sort(bonesCombinationsPrint);
        Collections.reverse(bonesCombinationsPrint);

        try {
            for (int i = 0; i < bonesCombinationsPrint.size(); i++) {
                if (bonesCombinationsPrint.get(i) == 5) {
                    System.out.println(playerBonesPoker.getName() + " у тебя Покер!");
                    return;
                } else if (bonesCombinationsPrint.get(i) == 4) {
                    System.out.println(playerBonesPoker.getName() + " у тебя Каре!");
                    return;
                } else if (bonesCombinationsPrint.get(i) == 3) {
                    if (bonesCombinationsPrint.get(i + 1) == 2) {
                        System.out.println(playerBonesPoker.getName() + " у тебя Фул-хаус!");
                    } else {
                        System.out.println(playerBonesPoker.getName() + " у тебя Тройка!");
                    }
                    return;
                } else if (bonesCombinationsPrint.get(i) == 2) {
                    if (bonesCombinationsPrint.get(i + 1) == 2) {
                        System.out.println(playerBonesPoker.getName() + " у тебя Две пары!");
                    } else {
                        System.out.println(playerBonesPoker.getName() + " у тебя Пара!");
                    }
                    return;
                } else {
                    int seniorBone = playerBonesPoker.getBonesHand().get(0);
                    for (Integer integer : playerBonesPoker.getBonesHand()) {
                        if (integer > seniorBone) {
                            seniorBone = integer;
                        }
                    }
                    System.out.println(playerBonesPoker.getName() + " у тебя старшая кость - " + seniorBone + "!");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Что могло пойти не так?!");;
        } finally {
            System.out.println("*".repeat(50));
        }
    }

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.jdbc3.JDBC3Connection");
            connection = DriverManager.getConnection("jdbc:sqlite:bones_games_base.db");
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("INSERT INTO bones_games_base (name_player, combination, combination_priority) VALUES (?, ?, ?);");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Unable to connect");
        }
    }

    private static void addGameToDatabase(PlayerBonesPoker playerBonesPoker) throws SQLException {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, playerBonesPoker.getName());
            preparedStatement.setString(2, String.valueOf(playerBonesPoker.getBonesHand())); //ToDo сделать передачу в базу название комбинации и саму комбинацию
            preparedStatement.setInt(3, 0); //ToDo сделать передачу в базу приоритет комбинации
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




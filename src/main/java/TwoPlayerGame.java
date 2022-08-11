import java.util.List;
import java.util.Scanner;

public class TwoPlayerGame {

    public static void startGameTwoPlayer() {

        System.out.println("*".repeat(50));
        System.out.println("Это игра - \"Покер на костях\". Версия на двоих\nКак зовут первого игрока?");

        Scanner scannerName = new Scanner(System.in);
        String namePlayerOne = scannerName.next();
        System.out.println("Привет, " + namePlayerOne + "!\nТеперь познакомимся со вторым участником! Как тебя зовут?"); // + "Чтобы бросить кубики - нажми 1!");
        String namePlayerTwo = scannerName.next();
        System.out.println("Привет, " + namePlayerTwo + "! " + "Чтобы бросить кубики - нажми 1!");


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

        createIntrigue();
        System.out.println();

        PlayerBonesPoker playerOne = PlayerBonesPoker.makePlayerBonesPoker(namePlayerOne);
        PlayerBonesPoker playerTwo = PlayerBonesPoker.makePlayerBonesPoker(namePlayerTwo);

        playerOne.getNameCombinationsPlayer(playerOne);
        playerTwo.getNameCombinationsPlayer(playerTwo);

        System.out.println(playerOne.getName() + ", у тебя выпало " + playerOne.getBonesHand());
        System.out.println(playerTwo.getName() + ", у тебя выпало " + playerTwo.getBonesHand());
        System.out.println();

        playerOne.setBonesHand(changeBonesHand(playerOne.getBonesHand(), playerOne));
        playerTwo.setBonesHand(changeBonesHand(playerTwo.getBonesHand(), playerTwo));
        playerOne.frequencyCount(playerOne);
        playerTwo.frequencyCount(playerTwo);
        playerOne.getPriorityCombinationsPlayer(playerOne);
        playerTwo.getPriorityCombinationsPlayer(playerTwo);
        playerOne.getNameCombinationsPlayer(playerOne);
        playerTwo.getNameCombinationsPlayer(playerTwo);

        if (playerOne.getPriorityCombinationsPlayer(playerOne) > playerTwo.getPriorityCombinationsPlayer(playerTwo)) {
            System.out.println("Победил " + playerOne.getName() + "!\nТвоя комбинация " + playerOne.bonesCombinations.getBonesCombinationName() + " больше комбинации "
                    + playerTwo.getName() + " - " + playerTwo.bonesCombinations.getBonesCombinationName());
        } else if (playerOne.getPriorityCombinationsPlayer(playerOne) == playerTwo.getPriorityCombinationsPlayer(playerTwo)) {
            System.out.println("Ничья по комбинациям! У " + playerOne.getName() + " - " + playerOne.bonesCombinations.getBonesCombinationName() + ", у "
                    + playerTwo.getName() + " - " + playerTwo.bonesCombinations.getBonesCombinationName());
            checkDraw(playerOne, playerTwo);
        } else {
            System.out.println("Победил " + playerTwo.getName() + "!\nТвоя комбинация " + playerTwo.bonesCombinations.getBonesCombinationName() + " Это больше комбинации "
                    + playerOne.getName() + " - " + playerOne.bonesCombinations.getBonesCombinationName());
        }
        System.out.println("*".repeat(50));
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

    public static void checkDraw(PlayerBonesPoker playerBonesPokerOne, PlayerBonesPoker playerBonesPokerTwo) {
        List<Integer> bonesHandOne = playerBonesPokerOne.getBonesHand();
        List<Integer> bonesHandTwo = playerBonesPokerTwo.getBonesHand();

        int sumBonesPlayerOne = bonesHandOne.stream().mapToInt(Integer::intValue).sum();
        int sumBonesPlayerTwo = bonesHandTwo.stream().mapToInt(Integer::intValue).sum();

        if (sumBonesPlayerOne > sumBonesPlayerTwo) {
            System.out.println("Победил " + playerBonesPokerOne.getName() + "! За счет старшей кости");
        } else {
            System.out.println("Победил " + playerBonesPokerTwo.getName() + "! За счет старшей кости");
        }

    }

    public static List<Integer> changeBonesHand(List<Integer> list, PlayerBonesPoker playerBonesPoker) {
        int wishChange = -1;
        int numberBones = -1;
        int revers = 0;

        System.out.println(playerBonesPoker.getName() + " сколько кубиков ты хочешь поменять?" + " У тебя сейчас так: " + playerBonesPoker.getBonesHand());
        Scanner scanner = new Scanner(System.in);

        while (wishChange < 0 || wishChange > 5) {
            if (scanner.hasNextInt()) {
                wishChange = scanner.nextInt();
                revers = wishChange;
                if (wishChange < 0 || wishChange > 5) {
                    System.out.println("Не понял?! Сколько-сколько кубиков хочешь поменять? Их вообще то 5 (пять)...");
                }
            } else {
                System.out.println("Хмм... и сколько я должен менять кубиков? Попробуй ввести цифру :-)");
                scanner.next();
            }
        }

        for (int i = 0; i < wishChange; i++) {
            System.out.println("Кубики идут по порядку, какой хочешь поменять? Выбирай по-одному!");
            numberBones = scanner.nextInt();
            while (numberBones < 0 || numberBones > 5) {
                if (scanner.hasNextInt()) {
                    numberBones = scanner.nextInt();
                    if (numberBones < 0 || numberBones > 5) {
                        System.out.println("Не понял?! Какой-какой кубик хочешь поменять? Их вообще то 5 (пять)...");
                    }
                } else {
                    System.out.println("Хмм... и какой кубик я должен менять? Попробуй ввести цифру :-)");
                    scanner.next();
                }
            }
            System.out.println("Убрали кубик под номером - " + numberBones + ". Тебе нужно поменять еще - " + --revers);
            list.remove(numberBones - 1);
            System.out.println(list);
        }

        System.out.println("Тааак! Внимание! Бросаем новые кубики в количестве - " + wishChange + " шт.!");
        createIntrigue();
        System.out.println();

        for (int i = 0; i < wishChange; i++) {
            list.add((int) (Math.random() * 6 + 1));
        }
        System.out.println("Итого у тебя выпало:" + list);
        playerBonesPoker.frequencyCount(playerBonesPoker);

        return list;
    }
}

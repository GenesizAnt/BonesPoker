import java.util.List;
import java.util.Scanner;

public class AdultTwoPlayer {

    private static final Scanner scanner = new Scanner(System.in);
    private static int betBank;
    private static boolean betTrue = true;
    private static int lastCallAllIn = 0;

    public static void startGameTwoPlayer() {

        System.out.println("*".repeat(50));
        System.out.println("Это игра - \"Покер на костях\". Версия на двоих и ставками\nКак зовут первого игрока?");

        String namePlayerOne = scanner.next();
        System.out.println("Привет, " + namePlayerOne + "!\nТеперь познакомимся со вторым участником! Как тебя зовут?"); // + "Чтобы бросить кубики - нажми 1!");
        String namePlayerTwo = scanner.next();
        System.out.println("Привет, " + namePlayerTwo + "! ");

        PlayerBonesPoker playerOne = PlayerBonesPoker.makePlayerBonesPoker(namePlayerOne);
        PlayerBonesPoker playerTwo = PlayerBonesPoker.makePlayerBonesPoker(namePlayerTwo);


        while (playerOne.getPurseCoin() > 0 && playerTwo.getPurseCoin() > 0) {
            System.out.println(playerOne.getName() + " у тебя в кошельке - " + playerOne.getPurseCoin() + " монеток");
            System.out.println(playerTwo.getName() + " у тебя в кошельке - " + playerTwo.getPurseCoin() + " монеток");
            System.out.println("Ну что, сделаем ставки?");

            toPlaceBet(playerOne);
            toPlaceBet(playerTwo);
            while (playerOne.getBetPlayer() != playerTwo.getBetPlayer()) {
                checkingBet(playerOne, playerTwo);
                playerOne.setBetPlayer(0);
                playerTwo.setBetPlayer(0);
            }


            System.out.println("Банк составляет - " + betBank + " Чтобы бросить кубики - нажмите 1!");

            int choose = 0;

            while (choose != 1) {
                if (scanner.hasNextInt()) {
                    choose = scanner.nextInt();
                    if (choose != 1) {
                        System.out.println("Я же сказал - 1 (один)!");
                    }
                } else {
                    System.out.println("Попробуй ввести цифру...");
                    scanner.next();
                }
            }

//            createIntrigue();
            System.out.println();

            playerOne.getNameCombinationsPlayer(playerOne);
            playerTwo.getNameCombinationsPlayer(playerTwo);

            System.out.println(playerOne.getName() + ", у тебя выпало " + playerOne.getBonesHand());
            System.out.println(playerTwo.getName() + ", у тебя выпало " + playerTwo.getBonesHand());

            doWantRaise(playerOne);
            doWantRaise(playerTwo);
            while (playerOne.getBetPlayer() != playerTwo.getBetPlayer()) {
                checkingBet(playerOne, playerTwo);
                playerOne.setBetPlayer(0);
                playerTwo.setBetPlayer(0);
            }
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
                if (playerOne.getPurseCoin() == 0 && lastCallAllIn > 0) {
                    playerOne.setPurseCoin(lastCallAllIn * 2);
                    playerTwo.setPurseCoin(betBank - lastCallAllIn * 2);
                }
                playerOne.setPurseCoin(playerOne.getPurseCoin() + betBank);
            } else if (playerOne.getPriorityCombinationsPlayer(playerOne) == playerTwo.getPriorityCombinationsPlayer(playerTwo)) {
                System.out.println("Ничья по комбинациям! У " + playerOne.getName() + " - " + playerOne.bonesCombinations.getBonesCombinationName() + ", у "
                        + playerTwo.getName() + " - " + playerTwo.bonesCombinations.getBonesCombinationName());
                checkDraw(playerOne, playerTwo);
            } else {
                System.out.println("Победил " + playerTwo.getName() + "!\nТвоя комбинация " + playerTwo.bonesCombinations.getBonesCombinationName() + " Это больше комбинации "
                        + playerOne.getName() + " - " + playerOne.bonesCombinations.getBonesCombinationName());
                if (playerTwo.getPurseCoin() == 0 && lastCallAllIn > 0) {
                    playerTwo.setPurseCoin(lastCallAllIn * 2);
                    playerOne.setPurseCoin(betBank - lastCallAllIn * 2);
                }
                playerTwo.setPurseCoin(playerTwo.getPurseCoin() + betBank);
            }
        }

        if (playerOne.getPurseCoin() == 0) {
            System.out.println("В игре \"Покер на костях\" победил " + playerTwo.getName() + "!!!");
        } else if (playerTwo.getPurseCoin() == 0) {
            System.out.println("В игре \"Покер на костях\" победил " + playerOne.getName() + "!!!");
        }

        System.out.println("*".repeat(50));
    }

    private static void doWantRaise(PlayerBonesPoker playerBonesPoker) {
        System.out.println(playerBonesPoker.getName() + ", будешь повышать?\nЕсли да нажми - 1\nЕсли нет нажми - 0");
        int chooseRise = -1;
        while (chooseRise != 0) {
            if (scanner.hasNextInt()) {
                chooseRise = scanner.nextInt();
                if (chooseRise == 1) {
                    if (playerBonesPoker.getPurseCoin() > 0) {
                        toPlaceBet(playerBonesPoker);
                        chooseRise = 0;
                    } else {
                        System.out.println("Куда тебе еще повышать! У тебя сейчас - " + playerBonesPoker.getPurseCoin());
                    }
                }
            } else {
                System.out.println("Попробуй ввести цифру...");
                scanner.next();
            }
        }
    }

    private static void checkingBet(PlayerBonesPoker playerOne, PlayerBonesPoker playerTwo) {
        if (playerOne.getBetPlayer() > playerTwo.getBetPlayer()) {
            System.out.println(playerTwo.getName() + " ты поставил меньше, тебе нужно добавить еще - " + (playerOne.getBetPlayer() - playerTwo.getBetPlayer()));
            if ((playerOne.getBetPlayer() - playerTwo.getBetPlayer()) < playerTwo.getPurseCoin()) {
                int raiseBet = scanner.nextInt();
                while (playerTwo.getBetPlayer() + raiseBet != playerOne.getBetPlayer()) {
                    System.out.println(playerTwo.getName() + " твоя ставка все еще меньше! Поставь еще - " + (playerOne.getBetPlayer() - (playerTwo.getBetPlayer() + raiseBet)));
                    raiseBet += scanner.nextInt();
                }
                playerTwo.setPurseCoin(playerTwo.getPurseCoin() - raiseBet);
                playerTwo.setBetPlayer(playerTwo.getBetPlayer() + raiseBet);
                betBank += raiseBet;
                System.out.println(playerTwo.getName() + " уровнял. У тебя осталось - " + playerTwo.getPurseCoin());
            } else {
                playerTwo.setBetPlayer(playerTwo.getBetPlayer() + playerTwo.getPurseCoin());
                lastCallAllIn = playerTwo.getBetPlayer();
                playerTwo.setPurseCoin(0);
                betBank += playerTwo.getPurseCoin();
                System.out.println(playerTwo.getName() + " поставил все");
            }
        } else {
            System.out.println(playerOne.getName() + " ты поставил меньше, тебе нужно добавить еще - " + (playerTwo.getBetPlayer() - playerOne.getBetPlayer()));
            if ((playerTwo.getBetPlayer() - playerOne.getBetPlayer()) < playerOne.getPurseCoin()) {
                int raiseBet = scanner.nextInt();
                while (playerOne.getBetPlayer() + raiseBet != playerTwo.getBetPlayer()) {
                    System.out.println(playerOne.getName() + " твоя ставка все еще меньше! Поставь еще - " + (playerTwo.getBetPlayer() - (playerOne.getBetPlayer() + raiseBet)));
                    raiseBet += scanner.nextInt();
                }
                playerOne.setPurseCoin(playerOne.getPurseCoin() - raiseBet);
                playerOne.setBetPlayer(playerOne.getBetPlayer() + raiseBet);
                betBank += raiseBet;
                System.out.println(playerOne.getName() + " уровнял. У тебя осталось - " + playerOne.getPurseCoin());
            } else {
                playerOne.setBetPlayer(playerOne.getBetPlayer() + playerOne.getPurseCoin());
                lastCallAllIn = playerTwo.getBetPlayer();
                playerOne.setPurseCoin(0);
                betBank += playerOne.getPurseCoin();
                System.out.println(playerOne.getName() + " поставил все");
            }
        }
    }

    public static void toPlaceBet(PlayerBonesPoker playerBonesPoker) {
        System.out.println(playerBonesPoker.getName() + " сколько хочешь поставить? У тебя сейчас - " + playerBonesPoker.getPurseCoin());
        playerBonesPoker.setBetPlayer(playerBonesPoker.getBetPlayer() + scanner.nextInt());
        while (betTrue) {
            if (playerBonesPoker.getBetPlayer() <= playerBonesPoker.getPurseCoin()) {
                playerBonesPoker.setPurseCoin(playerBonesPoker.getPurseCoin() - playerBonesPoker.getBetPlayer());
                System.out.println(playerBonesPoker.getName() + " ставка принята, у тебя осталось - " + playerBonesPoker.getPurseCoin());
                betTrue = false;
            } else {
                System.out.println("У тебя столько нет, попробуй еще раз");
                playerBonesPoker.setBetPlayer(scanner.nextInt());
            }
        }
        betTrue = true;
        betBank += playerBonesPoker.getBetPlayer();
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
            if (playerBonesPokerOne.getPurseCoin() == 0 && lastCallAllIn > 0) {
                playerBonesPokerOne.setPurseCoin(lastCallAllIn * 2);
                playerBonesPokerTwo.setPurseCoin(betBank - lastCallAllIn * 2);
            }
            playerBonesPokerOne.setPurseCoin(playerBonesPokerOne.getPurseCoin() + betBank);
        } else {
            System.out.println("Победил " + playerBonesPokerTwo.getName() + "! За счет старшей кости");
            if (playerBonesPokerTwo.getPurseCoin() == 0 && lastCallAllIn > 0) {
                playerBonesPokerTwo.setPurseCoin(lastCallAllIn * 2);
                playerBonesPokerOne.setPurseCoin(betBank - lastCallAllIn * 2);
            }
            playerBonesPokerTwo.setPurseCoin(playerBonesPokerTwo.getPurseCoin() + betBank);
        }

    }

    public static List<Integer> changeBonesHand(List<Integer> list, PlayerBonesPoker playerBonesPoker) {
        int wishChange = -1;
        int numberBones;
        int revers = 0;

        System.out.println(playerBonesPoker.getName() + " сколько кубиков ты хочешь поменять?" + " У тебя сейчас так: " + playerBonesPoker.getBonesHand());

        while (wishChange < 0 || wishChange > list.size()) {
            if (scanner.hasNextInt()) {
                wishChange = scanner.nextInt();
                revers = wishChange;
                if (wishChange < 0 || wishChange > list.size()) {
                    System.out.println("Не понял?! Сколько-сколько кубиков хочешь поменять? Их вообще то " + list.size());
                }
            } else {
                System.out.println("Хмм... и сколько я должен менять кубиков? Попробуй ввести цифру :-)");
                scanner.next();
            }
        }

        System.out.println("Кубики идут по порядку, какой хочешь поменять? Выбирай по-одному!");
        for (int i = 0; i < wishChange; i++) {
            numberBones = scanner.nextInt();
            while (numberBones < 0 || numberBones > list.size()) {
                if (scanner.hasNextInt()) {
                    if (numberBones < 0 || numberBones > list.size()) {
                        System.out.println("Не понял?! Какой-какой кубик хочешь поменять? Их вообще то " + list.size());
                        numberBones = scanner.nextInt();
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
//        createIntrigue();
        System.out.println();

        for (int i = 0; i < wishChange; i++) {
            list.add((int) (Math.random() * 6 + 1));
        }
        System.out.println("Итого у тебя выпало:" + list);
        playerBonesPoker.frequencyCount(playerBonesPoker);

        return list;
    }
}

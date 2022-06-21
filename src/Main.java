import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        bonesCombinationsCheck(getHandBones());
    }

    public static ArrayList<Integer> getHandBones() {
        System.out.println("Бросить кубики - нажми 1!");

        Scanner scanner = new Scanner(System.in);
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

        ArrayList<Integer> bonesHand = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bonesHand.add((int) (Math.random() * 6 + 1));
        }
        System.out.println(bonesHand);
        return bonesHand;
    }

    public static void bonesCombinationsCheck(ArrayList<Integer> list) {

        int repeatedOne = 0;
        int repeatedTwo = 0;
        int repeatedThree = 0;
        int repeatedFour = 0;
        int repeatedFive = 0;
        int repeatedSix = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == 1) {
                repeatedOne++;
            } else if (list.get(i) == 2) {
                repeatedTwo++;
            } else if (list.get(i) == 3) {
                repeatedThree++;
            } else if (list.get(i) == 4) {
                repeatedFour++;
            } else if (list.get(i) == 5) {
                repeatedFive++;
            } else if (list.get(i) == 6) {
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

        for (int i = 0; i < bonesCombinationsPrint.size(); i++) {
            if (bonesCombinationsPrint.get(i) == 5) {
                System.out.println("У вас Покер!");
                break;
            } else if (bonesCombinationsPrint.get(i) == 4) {
                System.out.println("У вас Каре!");
                break;
            } else if (bonesCombinationsPrint.get(i) == 3) {
                if (bonesCombinationsPrint.get(i + 1) == 2) {
                    System.out.println("У вас Фул-хаус!");
                } else {
                    System.out.println("У вас Тройка!");
                }
                break;
            } else if (bonesCombinationsPrint.get(i) == 2) {
                if (bonesCombinationsPrint.get(i + 1) == 2) {
                    System.out.println("У вас Две пары!");
                } else {
                    System.out.println("У вас Пара!");
                }
                break;
            } else {
                int seniorBone = list.get(0);
                for (Integer integer : list) {
                    if (integer > seniorBone) {
                        seniorBone = integer;
                    }
                }
                System.out.println("У вас старшая кость - " + seniorBone + "!");
                break;
            }
        }
    }
}




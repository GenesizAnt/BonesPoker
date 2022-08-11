import java.security.SecureRandom;
import java.util.*;

public class Test {
    static BonesCombinations bonesCombinations;
    static List<Integer> bonesHand = new ArrayList<>();
    static List<Integer> frequencyArr = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));

    public static void main(String[] args) {

//        List<Integer> getBonesHand = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            getBonesHand.add((int) (Math.random() * 6 + 1));
//        }
//
//        System.out.println(getBonesHand);


        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(3);

        System.out.println(list.size());

//        System.out.println(list);
//        changeBonesHand(getBonesHand);

//        System.out.println(getBonesHand);


//        getNameCombinationsPlayer(list, bonesCombinations);

//        rollBones();
//        System.out.println(bonesHand);
//        System.out.println(frequencyArr);
    }

    public static void rollBones() {
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            int randomBones = random.nextInt(6) + 1;
            bonesHand.add(i, randomBones);
            int oldVal = frequencyArr.get(randomBones);
            int newVal = oldVal + 1;
            frequencyArr.set(randomBones, newVal);
        }
        frequencyArr.remove(0);
    }

    public static List changeBonesHand(List list) {
        int wishChange = -1;
        int numberBones = -1;
        int revers = 0;

        System.out.println("Сколько кубиков ты хочешь поменять?");
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
            System.out.println("Кубики идут по порядку, какой хочешь поменять?");
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
        Main.createIntrigue();
        System.out.println();
        System.out.println("Итого у тебя выпало:");

        for (int i = 0; i < wishChange; i++) {
            list.add((int) (Math.random() * 6 + 1));
        }
        return list;
    }

    public static void getNameCombinationsPlayer(List<Integer> list, BonesCombinations bonesCombinations) {
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

        for (int i = 0; i < bonesCombinationsPrint.size(); i++)
            if (bonesCombinationsPrint.get(i) == 5) {
                bonesCombinations = BonesCombinations.POKER;
                System.out.println(", твоя комбинация " + BonesCombinations.POKER.getBonesCombinationName());
                return;
            } else if (bonesCombinationsPrint.get(i) == 4) {
                bonesCombinations = BonesCombinations.FOUR_OF_KIND;
                System.out.println(", твоя комбинация " + BonesCombinations.FOUR_OF_KIND.getBonesCombinationName());
                return;
            } else if (bonesCombinationsPrint.get(i) == 3) {
                if (bonesCombinationsPrint.get(i + 1) == 2) {
                    bonesCombinations = BonesCombinations.FULL_HOUSE;
                    System.out.println(", твоя комбинация " + BonesCombinations.FULL_HOUSE.getBonesCombinationName());
                } else {
                    bonesCombinations = BonesCombinations.THREE_OF_KIND;
                    System.out.println(", твоя комбинация " + BonesCombinations.THREE_OF_KIND.getBonesCombinationName());
                }
                return;
            } else if (bonesCombinationsPrint.get(i) == 2) {
                if (bonesCombinationsPrint.get(i + 1) == 2) {
                    bonesCombinations = BonesCombinations.TWO_PAIRS;
                    System.out.println(", твоя комбинация " + BonesCombinations.TWO_PAIRS.getBonesCombinationName());
                } else {
                    bonesCombinations = BonesCombinations.PAIR;
                    System.out.println(", твоя комбинация " + BonesCombinations.PAIR.getBonesCombinationName());
                }
                return;
            } else {
                Collections.sort(list);
                int sumBones = 0;
                for (int j = 0; j < list.size(); j++) {
                    sumBones += list.get(j);
                }
                    if (list.get(0) == 1 && sumBones == 15) {
                        bonesCombinations = BonesCombinations.SMALL_STREET;
                        System.out.println(", твоя комбинация " + BonesCombinations.SMALL_STREET.getBonesCombinationName());
                        return;
                    }
                    if (list.get(0) == 2 && sumBones == 20) {
                        bonesCombinations = BonesCombinations.BIG_STREET;
                        System.out.println(", твоя комбинация " + BonesCombinations.BIG_STREET.getBonesCombinationName());
                        return;
                    }
                int seniorBone = list.get(0);
                for (Integer integer : list) {
                    if (integer > seniorBone) {
                        seniorBone = integer;
                    }
                }
                bonesCombinations = BonesCombinations.HIGH_BONES;
                System.out.println(", твоя комбинация " + BonesCombinations.HIGH_BONES.getBonesCombinationName() + " " + seniorBone + "!");
                return;
            }
    }
}



//        System.out.println("Кубики идут по порядку, какой хочешь поменять? Выбирай по-одному!");
//                for (int i = 0; i < wishChange; i++) {
//        numberBones = scanner.nextInt();
//        while (numberBones < 0 || numberBones > list.size()) {
//        if (scanner.hasNextInt()) {
//        if (numberBones < 0 || numberBones > list.size()) {
//        System.out.println("Не понял?! Какой-какой кубик хочешь поменять? Их вообще то " + list.size());
//        numberBones = scanner.nextInt();
//        }
//        } else {
//        System.out.println("Хмм... и какой кубик я должен менять? Попробуй ввести цифру :-)");
//        scanner.next();
//        }
//        }
//        System.out.println("Убрали кубик под номером - " + numberBones + ". Тебе нужно поменять еще - " + --revers);
//        list.remove(numberBones - 1);
//        System.out.println(list);
//        }

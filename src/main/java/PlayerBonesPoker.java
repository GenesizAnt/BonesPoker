import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerBonesPoker {

    private final String name;
    private final List<Integer> bonesHand = new ArrayList<>();
    BonesCombinations bonesCombinations;

    public PlayerBonesPoker(String name) {
        this.name = name;
        rollBones();
    }

    public String getName() {
        return name;
    }

    public List<Integer> getBonesHand() {
        return bonesHand;
    }

    public void rollBones() {
        List<Integer> getBonesHand = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bonesHand.add((int) (Math.random() * 6 + 1));
        }
    }

    public int bonesCombinationsPriority(PlayerBonesPoker playerBonesPoker) {
        int repeatedOne = 0;
        int repeatedTwo = 0;
        int repeatedThree = 0;
        int repeatedFour = 0;
        int repeatedFive = 0;
        int repeatedSix = 0;

        for (int i = 0; i < bonesHand.size(); i++) {
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

        for (int i = 0; i < bonesCombinationsPrint.size(); i++)
            if (bonesCombinationsPrint.get(i) == 5) {
                return BonesCombinations.POKER.getBonesCombinationPriority();
            } else if (bonesCombinationsPrint.get(i) == 4) {
                return BonesCombinations.FOUR_OF_KIND.getBonesCombinationPriority();
            } else if (bonesCombinationsPrint.get(i) == 3) {
                if (bonesCombinationsPrint.get(i + 1) == 2) {
                    return BonesCombinations.FOUR_OF_KIND.getBonesCombinationPriority();
                } else {
                    return BonesCombinations.THREE_OF_KIND.getBonesCombinationPriority();
                }
            } else if (bonesCombinationsPrint.get(i) == 2) {
                if (bonesCombinationsPrint.get(i + 1) == 2) {
                    return BonesCombinations.TWO_PAIRS.getBonesCombinationPriority();
                } else {
                    return BonesCombinations.PAIR.getBonesCombinationPriority();
                }
            } else {
                int seniorBone = playerBonesPoker.getBonesHand().get(0);
                for (Integer integer : playerBonesPoker.getBonesHand()) {
                    if (integer > seniorBone) {
                        seniorBone = integer;
                    }
                }
                return BonesCombinations.HIGH_BONES.getBonesCombinationPriority();
            }
        return 0;
    }

    public void bonesCombinationsName(PlayerBonesPoker playerBonesPoker) {
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

        for (int i = 0; i < bonesCombinationsPrint.size(); i++)
            if (bonesCombinationsPrint.get(i) == 5) {
                bonesCombinations = BonesCombinations.POKER;
                System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.POKER.getBonesCombinationName());
                return;
            } else if (bonesCombinationsPrint.get(i) == 4) {
                bonesCombinations = BonesCombinations.FOUR_OF_KIND;
                System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.FOUR_OF_KIND.getBonesCombinationName());
                return;
            } else if (bonesCombinationsPrint.get(i) == 3) {
                if (bonesCombinationsPrint.get(i + 1) == 2) {
                    bonesCombinations = BonesCombinations.FOUR_OF_KIND;
                    System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.FOUR_OF_KIND.getBonesCombinationName());
                } else {
                    bonesCombinations = BonesCombinations.THREE_OF_KIND;
                    System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.THREE_OF_KIND.getBonesCombinationName());
                }
                return;
            } else if (bonesCombinationsPrint.get(i) == 2) {
                if (bonesCombinationsPrint.get(i + 1) == 2) {
                    bonesCombinations = BonesCombinations.TWO_PAIRS;
                    System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.TWO_PAIRS.getBonesCombinationName());
                } else {
                    bonesCombinations = BonesCombinations.PAIR;
                    System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.PAIR.getBonesCombinationName());
                }
                return;
            } else {
                int seniorBone = playerBonesPoker.getBonesHand().get(0);
                for (Integer integer : playerBonesPoker.getBonesHand()) {
                    if (integer > seniorBone) {
                        seniorBone = integer;
                    }
                }
                bonesCombinations = BonesCombinations.HIGH_BONES;
                System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.HIGH_BONES.getBonesCombinationName() + seniorBone + "!");
                return;
            }
    }
}


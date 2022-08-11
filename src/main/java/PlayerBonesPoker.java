import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerBonesPoker {

    private final String name;
    private List<Integer> bonesHand = new ArrayList<>();
    private List<Integer> frequencyBones = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));
    BonesCombinations bonesCombinations;
    private int purseCoin;
    private int betPlayer;

    private PlayerBonesPoker(String name) {
        this.name = name;
        rollBones();
        BonesCombinations bonesCombinations = this.bonesCombinations;
        this.purseCoin = 100;
        this.betPlayer = 0;
    }

    public static PlayerBonesPoker makePlayerBonesPoker(String name) {
        return new PlayerBonesPoker(name);
    }

    public String getName() {
        return name;
    }

    public int getPurseCoin() {
        return purseCoin;
    }

    public int getBetPlayer() {
        return betPlayer;
    }

    public void setBetPlayer(int betPlayer) {
        this.betPlayer = betPlayer;
    }

    public void setPurseCoin(int purseCoin) {
        this.purseCoin = purseCoin;
    }

    public List<Integer> getBonesHand() {
        return bonesHand;
    }

    public List<Integer> getFrequencyBones() {
        return frequencyBones;
    }

    public void setBonesHand(List<Integer> bonesHand) {
        this.bonesHand = bonesHand;
    }

    public void setFrequencyBones(List<Integer> frequencyBones) {
        this.frequencyBones = frequencyBones;
    }

    public void rollBones() {
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            int randomBones = random.nextInt(6) + 1;
            bonesHand.add(i, randomBones);
            int oldVal = frequencyBones.get(randomBones);
            int newVal = oldVal + 1;
            frequencyBones.set(randomBones, newVal);
        }
        frequencyBones.remove(0);
    }

    public void frequencyCount(PlayerBonesPoker playerBonesPoker) {
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
        playerBonesPoker.setFrequencyBones(bonesCombinationsPrint);
    }

    public int getPriorityCombinationsPlayer(PlayerBonesPoker playerBonesPoker) {
        Collections.sort(playerBonesPoker.getFrequencyBones());
        Collections.reverse(playerBonesPoker.getFrequencyBones());

        for (int i = 0; i < playerBonesPoker.getFrequencyBones().size(); i++)
            if (playerBonesPoker.getFrequencyBones().get(i) == 5) {
                return BonesCombinations.POKER.getBonesCombinationPriority();
            } else if (playerBonesPoker.getFrequencyBones().get(i) == 4) {
                return BonesCombinations.FOUR_OF_KIND.getBonesCombinationPriority();
            } else if (playerBonesPoker.getFrequencyBones().get(i) == 3) {
                if (playerBonesPoker.getFrequencyBones().get(i + 1) == 2) {
                    return BonesCombinations.FULL_HOUSE.getBonesCombinationPriority();
                } else {
                    return BonesCombinations.THREE_OF_KIND.getBonesCombinationPriority();
                }
            } else if (playerBonesPoker.getFrequencyBones().get(i) == 2) {
                if (playerBonesPoker.getFrequencyBones().get(i + 1) == 2) {
                    return BonesCombinations.TWO_PAIRS.getBonesCombinationPriority();
                } else {
                    return BonesCombinations.PAIR.getBonesCombinationPriority();
                }
            } else {
                Collections.sort(playerBonesPoker.getBonesHand());
                int sumBones = 0;
                for (int j = 0; j < playerBonesPoker.getBonesHand().size(); j++) {
                    sumBones += playerBonesPoker.getBonesHand().get(j);
                }
                if (playerBonesPoker.getBonesHand().get(0) == 1 && sumBones == 15) {
                    return BonesCombinations.SMALL_STREET.getBonesCombinationPriority();
                }
                if (playerBonesPoker.getBonesHand().get(0) == 2 && sumBones == 20) {
                    return BonesCombinations.BIG_STREET.getBonesCombinationPriority();
                }
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

    public void getNameCombinationsPlayer(PlayerBonesPoker playerBonesPoker) {
        Collections.sort(playerBonesPoker.getFrequencyBones());
        Collections.reverse(playerBonesPoker.getFrequencyBones());

        for (int i = 0; i < playerBonesPoker.getFrequencyBones().size(); i++)
            if (playerBonesPoker.getFrequencyBones().get(i) == 5) {
                bonesCombinations = BonesCombinations.POKER;
                System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.POKER.getBonesCombinationName());
                return;
            } else if (playerBonesPoker.getFrequencyBones().get(i) == 4) {
                bonesCombinations = BonesCombinations.FOUR_OF_KIND;
                System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.FOUR_OF_KIND.getBonesCombinationName());
                return;
            } else if (playerBonesPoker.getFrequencyBones().get(i) == 3) {
                if (playerBonesPoker.getFrequencyBones().get(i + 1) == 2) {
                    bonesCombinations = BonesCombinations.FULL_HOUSE;
                    System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.FULL_HOUSE.getBonesCombinationName());
                } else {
                    bonesCombinations = BonesCombinations.THREE_OF_KIND;
                    System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.THREE_OF_KIND.getBonesCombinationName());
                }
                return;
            } else if (playerBonesPoker.getFrequencyBones().get(i) == 2) {
                if (playerBonesPoker.getFrequencyBones().get(i + 1) == 2) {
                    bonesCombinations = BonesCombinations.TWO_PAIRS;
                    System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.TWO_PAIRS.getBonesCombinationName());
                } else {
                    bonesCombinations = BonesCombinations.PAIR;
                    System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.PAIR.getBonesCombinationName());
                }
                return;
            } else {
                Collections.sort(playerBonesPoker.getBonesHand());
                int sumBones = 0;
                for (int j = 0; j < playerBonesPoker.getBonesHand().size(); j++) {
                    sumBones += playerBonesPoker.getBonesHand().get(j);
                }
                if (playerBonesPoker.getBonesHand().get(0) == 1 && sumBones == 15) {
                    bonesCombinations = BonesCombinations.SMALL_STREET;
                    System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.SMALL_STREET.getBonesCombinationName());
                    return;
                }
                if (playerBonesPoker.getBonesHand().get(0) == 2 && sumBones == 20) {
                    bonesCombinations = BonesCombinations.BIG_STREET;
                    System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.BIG_STREET.getBonesCombinationName());
                    return;
                }
                int seniorBone = playerBonesPoker.getBonesHand().get(0);
                for (Integer integer : playerBonesPoker.getBonesHand()) {
                    if (integer > seniorBone) {
                        seniorBone = integer;
                    }
                }
                System.out.println(playerBonesPoker.getName() + ", твоя комбинация " + BonesCombinations.HIGH_BONES.getBonesCombinationName() + " " + seniorBone + "!");
                return;
            }
    }
}

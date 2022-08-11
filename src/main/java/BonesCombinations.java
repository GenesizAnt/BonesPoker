

public enum BonesCombinations {
    POKER("Покер!", 9), FOUR_OF_KIND("Каре!", 8), FULL_HOUSE("Фул-хаус!", 7),
    BIG_STREET("Большой стрит!", 6), SMALL_STREET("Малый стрит!", 5),
    THREE_OF_KIND("Тройка!", 4), TWO_PAIRS("Две пары!", 3),PAIR("Пара!", 2),
    HIGH_BONES("старшая кость", 1);

    private final String bonesCombinationName;
    private final int bonesCombinationPriority;

    BonesCombinations(String bonesCombinationName, int bonesCombinationPriority) {
        this.bonesCombinationName = bonesCombinationName;
        this.bonesCombinationPriority = bonesCombinationPriority;
    }

    public String getBonesCombinationName() {
        return bonesCombinationName;
    }

    public int getBonesCombinationPriority() {
        return bonesCombinationPriority;
    }
}



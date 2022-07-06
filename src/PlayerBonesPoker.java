import java.util.ArrayList;
import java.util.List;

public class PlayerBonesPoker {

    private final String name;
    private final List<Integer> bonesHand = new ArrayList<>();

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
}


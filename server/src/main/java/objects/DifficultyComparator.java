package objects;

import java.util.Comparator;

public class DifficultyComparator implements Comparator<Difficulty> {
    @Override
    public int compare(Difficulty o1, Difficulty o2) {
        if (o1 == o2) return 0;
        else if (o1.equals(Difficulty.EASY)) return 1;
        else if (o2.equals(Difficulty.EASY)) return -1;
        else if (o1.equals(Difficulty.TERRIBLE)) return 1;
        else return -1;
    }
}

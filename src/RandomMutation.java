import java.util.Random;

public class RandomMutation implements Hueristic{

    private final double IOM;
    private final Random random;
    RandomMutation(Random random, double IOM){
        this.random = random;
        this.IOM = IOM;
    }
    @Override
    public void applyHueristic(ProblemInstance problemInstance) {
        System.out.printf("mutating\n");
        for (int i = 0; i < ProblemInstance.mapToInt(this.IOM); i++) {
            problemInstance.getCurrentSolution().flipBit(random.nextInt(0, problemInstance.getSubsets().size()));
        }
    }
}

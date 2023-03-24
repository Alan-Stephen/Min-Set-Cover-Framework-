import java.util.Random;

public class RandomMutation implements Hueristic{
    private Random random;
    RandomMutation(Random random){
        this.random = random;
    }

    @Override
    public void applyHueristic(ProblemInstance problemInstance) {
        problemInstance.getCurrentSolution().flipBit(random.nextInt(0,problemInstance.getSubsets().size()));
    }
}

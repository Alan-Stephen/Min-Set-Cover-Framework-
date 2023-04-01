import java.util.Random;

public class RandomMutation implements Hueristic{

    private final double IOM;
    private final Random random;
    RandomMutation(Random random, double IOM){
        this.random = random;
        this.IOM = IOM;
    }

    int mapIOM(double IOM){
        if(IOM < 0.2 && IOM >= 0){
            return 1;
        } if(IOM < 0.4 && IOM >= 0.2) {
            return 2;
        } if(IOM < 0.6 && IOM >= 0.4){
            return 3;
        } if(IOM < 0.8 && IOM >= 0.6) {
            return 4;
        } if(IOM < 1.0 && IOM >= 0.8) {
            return 5;
        } if(IOM == 1.0){
            return 6;
        }
        System.out.println("ERROR: INVALID IOM");
        return -1;
    }

    @Override
    public void applyHueristic(ProblemInstance problemInstance) {
        for (int i = 0; i < mapIOM(this.IOM); i++) {
            problemInstance.getCurrentSolution().flipBit(random.nextInt(0, problemInstance.getSubsets().size()));
        }
    }
}

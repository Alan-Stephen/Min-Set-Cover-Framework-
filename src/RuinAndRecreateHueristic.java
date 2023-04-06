import java.util.ArrayList;
import java.util.Random;

public class RuinAndRecreateHueristic implements Hueristic{

    private double IOM;

    private Random random;
    RuinAndRecreateHueristic(double IOM, Random random){
        this.IOM = IOM;
        this.random = random;
    }
    @Override
    public void applyHueristic(ProblemInstance problemInstance) {
        ArrayList<Integer> usedIndexes = new ArrayList<>();
        ArrayList<Integer> unusedIndexes = new ArrayList<>();
        Solution currentSolution = problemInstance.getCurrentSolution();

        for(int i = 0; i < problemInstance.getSubsets().size(); i++){
            if(currentSolution.getBit(i))
                usedIndexes.add(i);
            else
                unusedIndexes.add(i);
        }

        // deuse used subsets
        for(int i = 0; i < ProblemInstance.mapToInt(IOM); i++){
            if(usedIndexes.size() == 0){
                break;
            }

            int randomIndex = random.nextInt(0,usedIndexes.size());
            currentSolution.flipBit(usedIndexes.get(randomIndex));

            unusedIndexes.add(usedIndexes.get(randomIndex));
            usedIndexes.remove(randomIndex);
        }

        // use unused subsets at random until everything satisified.

        while(!currentSolution.isSolutionComplete()) {
            if(unusedIndexes.size() == 0){
                break;
            }

            int randomIndex = random.nextInt(0,unusedIndexes.size());
            currentSolution.flipBit(randomIndex);

            unusedIndexes.remove(randomIndex);
        }
    }
}

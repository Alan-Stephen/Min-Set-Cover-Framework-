import java.util.ArrayList;
import java.util.Random;

public class RemoveRandomSet implements Hueristic{
    double IOM;
    Random random;
    RemoveRandomSet(double IntensityOfMutation, Random random){
        this.IOM = IntensityOfMutation;
        this.random = random;
    }
    @Override
    public void applyHueristic(ProblemInstance problemInstance) {
        Solution solution = problemInstance.getCurrentSolution();

        ArrayList<Integer> usedIndexs = new ArrayList<Integer>();
        for(int index = 0; index < problemInstance.getSubsets().size(); index++){
            if(solution.getBit(index)){
                usedIndexs.add(index);
            }
        }

        if(usedIndexs.size() == 0)
            return;
        for(int i = 0; i < ProblemInstance.mapToInt(IOM) % 3;i++) {
            int randomIndex = random.nextInt(0,usedIndexs.size());
            solution.flipBit(usedIndexs.get(randomIndex));
            usedIndexs.remove(randomIndex);
            if(usedIndexs.size() == 0)
                return;
        }
    }
}
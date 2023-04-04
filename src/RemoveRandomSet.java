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

        System.out.println(usedIndexs.toString());
        if(usedIndexs.size() == 0)
            return;
        solution.flipBit(usedIndexs.get(random.nextInt(0,usedIndexs.size())));
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Solution {
    private ArrayList<Boolean> bitString= new ArrayList<>();
    private final int NUM_VARIABLES;
    boolean[] x;

    ProblemInstance problemInstance;
    public int getObjectiveSolutionValue(){
        int count = 0;
        for(Boolean bit: bitString)
            if(bit)
                count++;
        return count;
    }

    public boolean isSolutionValid(){
        Arrays.fill(x, false);

        for(int i = 0; i < problemInstance.getSubsets().size(); i++){
            Subset currentSubset = problemInstance.getSubsets().get(i);
            if(!bitString.get(currentSubset.getId())){
                continue;
            }
            for(int y = 0; y < currentSubset.getSize(); y++){
                x[currentSubset.getElement(y) - 1] = true;
            }
        }

        for(boolean b: x)
            if(!b)
                return false;
        return true;
    }
    public void flipBit(int index){
        bitString.set(index,!(bitString.get(index)));
    }
    private void constructInitialSolution() {
        problemInstance.getSubsets().sort(Comparator.comparingInt(Subset::getSize));

        for(int i = 0; i < problemInstance.getSubsets().size();i++){
            bitString.add(false);
        }
        int currentSubsetIndex = problemInstance.getSubsets().size() - 1;
        while(!isSolutionValid()){
            System.out.println(problemInstance.getSubsets().get(currentSubsetIndex).getSize());
            int subsetId = problemInstance.getSubsets().get(currentSubsetIndex).getId();
            bitString.set(subsetId, true);
            currentSubsetIndex--;
        }
    }
    Solution(ProblemInstance problemInstance){
        x = new boolean[problemInstance.getNumElementsInX()];
        this.problemInstance = problemInstance;
        this.NUM_VARIABLES = problemInstance.getSubsets().size();
        this.constructInitialSolution();
        // FOR TESTING PURPOSES
        /*
        for(int i = 0; i < bitString.size();i++){
            bitString.set(i,true);
        }*/
    }

    @Override
    public String toString() {
        String returnString = "Solution { " + this.NUM_VARIABLES + " } : { ";
        for (Boolean aBoolean : bitString) {
            if (aBoolean) {
                returnString = returnString.concat("1 ");
            } else {
                returnString = returnString.concat("0 ");
            }
        }

        returnString = returnString + "}";

        return returnString;
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Solution {
    private ArrayList<Boolean> bitString= new ArrayList<>();
    private final int NUM_VARIABLES;
    int[] x;

    int currentObjectiveValue = Integer.MAX_VALUE;

    public ArrayList<Boolean> getBitString(){
        return bitString;
    }
    ProblemInstance problemInstance;
    public void updateObjectiveSolutionValue(){
        int count = 0;
        int value = NUM_VARIABLES + x.length;
        for(Boolean bit: bitString)
            if(!bit)
                count++;
        value -= count;
        value -= this.updateElementsSatisfied();

        currentObjectiveValue = value;
    }

    public int updateElementsSatisfied(){
        Arrays.fill(x, 0);

        for(int i = 0; i < problemInstance.getSubsets().size(); i++){
            Subset currentSubset = problemInstance.getSubsets().get(i);
            if(!bitString.get(currentSubset.getId())){
                continue;
            }
            for(int y = 0; y < currentSubset.getSize(); y++){
                x[currentSubset.getElement(y) - 1] += 1;
            }
        }
        int count = 0;
        for(int val: x){
            if(val != 0)
                count++;
        }

        return count;
    }

    /**
     * WARNING
     * note that calling this function will update through delta evaluation
     * so no need to call updateObjectiveSolutionValue
     *
     * */
    public void flipBit(int index){
        bitString.set(index,!(bitString.get(index)));
    }
    private void constructInitialSolution() {
        problemInstance.getSubsets().sort(Comparator.comparingInt(Subset::getSize));
        for(int i = 0; i < problemInstance.getSubsets().size();i++){
            bitString.add(false);
        }
        int currentSubsetIndex = problemInstance.getSubsets().size() - 1;
        while(updateElementsSatisfied() != x.length){
            int subsetId = problemInstance.getSubsets().get(currentSubsetIndex).getId();
            bitString.set(subsetId, true);
            currentSubsetIndex--;
        }
    }
    Solution(ProblemInstance problemInstance){
        x = new int[problemInstance.getNumElementsInX()];
        this.problemInstance = problemInstance;
        this.NUM_VARIABLES = problemInstance.getSubsets().size();
        this.constructInitialSolution();
        this.updateObjectiveSolutionValue();
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

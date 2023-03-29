import java.util.*;

public class Solution {
    private final Random random;
    private ArrayList<Boolean> bitString= new ArrayList<>();
    private final int NUM_VARIABLES;

    public int[] getX() {
        return x;
    }

    private int[] x;

    private final boolean RANDOMIZE = true;

    public int getSetsUsed() {
        return setsUsed;
    }

    private int setsUsed;

    int currentObjectiveValue = Integer.MAX_VALUE;

    public ArrayList<Boolean> getBitString(){
        return bitString;
    }
    private ProblemInstance problemInstance;

    // TODO : FIX OBJECTIVE VALUE FUNCTION
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

    // todo : REDO the entire valuation system for delta eval
    public void flipBit(int index){
        if(bitString.get(index)){
            Subset currentSubset = problemInstance.getSubsets().get(index);
            for(int i = 0; i < currentSubset.getSize(); i++){
                x[currentSubset.getElement(i) - 1]--;
            }
            this.setsUsed--;
        } else {
            Subset currentSubset = problemInstance.getSubsets().get(index);
            for(int i = 0; i < currentSubset.getSize(); i++){
                x[currentSubset.getElement(i) - 1]++;
            }
            this.setsUsed++;
        }
        bitString.set(index,!bitString.get(index));

        // do delta eval
        int count = 0;
        for(int i: x)
            if(i != 0)
                count++;
        this.currentObjectiveValue = (x.length + NUM_VARIABLES) - (count + (NUM_VARIABLES - setsUsed));
    }
    private void constructInitialSolution() {
        problemInstance.getSubsets().sort(Comparator.comparingInt(Subset::getSize));
        for(int i = 0; i < problemInstance.getSubsets().size();i++){
            bitString.add(false);
        }
        if(this.RANDOMIZE){
            for(int i = 0;i < bitString.size();i++) {
                if (random.nextBoolean()) {
                    this.flipBit(i);
                }
            }
        } else {
            int currentSubsetIndex = problemInstance.getSubsets().size() - 1;
            while (updateElementsSatisfied() != x.length) {
                int subsetId = problemInstance.getSubsets().get(currentSubsetIndex).getId();
                this.flipBit(subsetId);
                this.setsUsed++;
                currentSubsetIndex--;
            }
        }
    }
    Solution(ProblemInstance problemInstance, Random random) {
        this.random = random;
        x = new int[problemInstance.getNumElementsInX()];
        this.problemInstance = problemInstance;
        this.NUM_VARIABLES = problemInstance.getSubsets().size();

        this.constructInitialSolution();
        this.updateObjectiveSolutionValue();
        /*
        Collections.fill(bitString, true);
        this.setsUsed = bitString.size();
        this.updateObjectiveSolutionValue();
        */
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

import java.util.*;

// TOdo debug why there is one extra point added/minused to solution value;
// fix fucking objective solution thing agian.
public class Solution {
    private final Random random;
    private ArrayList<Boolean> bitString= new ArrayList<>();

    private final int NUM_VARIABLES;

    private int[] x;

    public int[] getX() {
        return x;
    }

    private final boolean RANDOMIZE = false;

    public void setSetsUsed(int setsUsed) {
        this.setsUsed = setsUsed;
    }

    private int setsUsed = 0;

    public void setX(int[] x) {
        this.x = x;
    }

    public int getCurrentObjectiveValue() {
        return currentObjectiveValue;
    }

    public void setCurrentObjectiveValue(int currentObjectiveValue) {
        this.currentObjectiveValue = currentObjectiveValue;
    }

    private int currentObjectiveValue = Integer.MAX_VALUE;

    private final ProblemInstance problemInstance;

    public int getSetsUsed() {
        return setsUsed;
    }

    public ArrayList<Boolean> getBitString(){
        return bitString;
    }

    public void updateObjectiveSolutionValue(){
        int count = 0;
        int value = NUM_VARIABLES + x.length;
        for(Boolean bit: bitString)
            if(bit)
                count++;

        value = value - (updateElementsSatisfied() + (NUM_VARIABLES - count));

        currentObjectiveValue = value;
    }

    boolean getBit(int i){
        return bitString.get(i);
    }

    void setBit(int i,boolean value){
        if(value == getBit(i)){
            return;
        }
        flipBit(i);
    }


    public boolean isSolutionComplete(){
        for(int num: x)
            if(num == 0){
                return false;
            }
        return true;
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
        Subset currentSubset = problemInstance.getSubsets().get(index);
        if(bitString.get(index)){
            for(int i = 0; i < currentSubset.getSize(); i++){
                x[currentSubset.getElement(i) - 1]--;
            }
            this.setsUsed--;
        } else {
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

    public int getNumVariables() {
        return NUM_VARIABLES;
    }

    private void constructInitialSolution() {
        problemInstance.getSubsets().sort(Comparator.comparingInt(Subset::getSize));
        for(int i = 0; i < problemInstance.getSubsets().size();i++){
            bitString.add(false);
        }
        if(this.RANDOMIZE){
            for(int i = 0;i < bitString.size();i++) {
                if (random.nextBoolean()) {
                    this.setsUsed++;
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

        Collections.fill(bitString, true);
        this.setsUsed = bitString.size();
        this.updateObjectiveSolutionValue();
    }

    @Override
    public String toString() {
        String returnString = "Solution { " + this.NUM_VARIABLES + " } : { ";
        for (Boolean aBoolean : bitString) {
            if (aBoolean) {
                returnString = returnString.concat("1");
            } else {
                returnString = returnString.concat("0");
            }
        }

        returnString = returnString + "}";

        return returnString;
    }
}

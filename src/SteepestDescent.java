public class SteepestDescent implements Hueristic{

    int DepthOfSearch;

    SteepestDescent(int DepthOfSearch){
        this.DepthOfSearch = DepthOfSearch;
    }
    @Override
    public void applyHueristic(ProblemInstance problemInstance) {
        int bestIndex = -1;
        int bestObjectiveValue = Integer.MAX_VALUE;
        Solution currentSolution = problemInstance.getCurrentSolution();
        for(int i = 0; i < problemInstance.getSubsets().size(); i++){
            currentSolution.flipBit(i);
            if(currentSolution.currentObjectiveValue <= bestObjectiveValue) {
                bestObjectiveValue = currentSolution.currentObjectiveValue;
                bestIndex = i;
            }
            currentSolution.flipBit(i);
        }
        currentSolution.flipBit(bestIndex);
    }
}

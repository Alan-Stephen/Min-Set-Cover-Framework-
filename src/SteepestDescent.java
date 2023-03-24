public class SteepestDescent implements Hueristic{

    int DepthOfSearch;

    SteepestDescent(int DepthOfSearch){
        this.DepthOfSearch = DepthOfSearch;
    }
    @Override
    public void applyHueristic(ProblemInstance problemInstance) {
        int bestIndex = -1;
        Solution currentSolution = problemInstance.getCurrentSolution();
        int bestObjectiveValue = currentSolution.currentObjectiveValue;
        for(int i = 0; i < problemInstance.getSubsets().size(); i++){
            currentSolution.flipBit(i);
            if(currentSolution.currentObjectiveValue <= bestObjectiveValue) {
                bestObjectiveValue = currentSolution.currentObjectiveValue;
                bestIndex = i;
            }
            currentSolution.flipBit(i);
        }
        if(bestIndex == -1) {
            System.out.println("nothing done");
            return;
        }
        currentSolution.flipBit(bestIndex);
    }
}

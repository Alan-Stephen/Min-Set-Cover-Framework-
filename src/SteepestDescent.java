// change this to use random permutations, right now it's prioritising subsets with lower indexes.
public class SteepestDescent implements Hueristic{
    double depthOfSearch;

    SteepestDescent(double depthOfSearch){
        this.depthOfSearch = depthOfSearch;
    }

    @Override
    public void applyHueristic(ProblemInstance problemInstance) {
        int numIterations = ProblemInstance.mapToInt(this.depthOfSearch);
        for (int x = 0; x < numIterations; x++) {
            int bestIndex = -1;
            Solution currentSolution = problemInstance.getCurrentSolution();
            int bestObjectiveValue = currentSolution.getCurrentObjectiveValue();
            for (int i = 0; i < problemInstance.getSubsets().size(); i++) {
                currentSolution.flipBit(i);
                if (currentSolution.getCurrentObjectiveValue() <= bestObjectiveValue) {
                    bestObjectiveValue = currentSolution.getCurrentObjectiveValue();
                    bestIndex = i;
                }
                currentSolution.flipBit(i);
            }
            if (bestIndex == -1) {
                return;
            }
            currentSolution.flipBit(bestIndex);
        }
    }
}

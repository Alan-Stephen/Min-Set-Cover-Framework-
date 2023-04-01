public class SteepestDescent implements Hueristic{
    double depthOfSearch;

    SteepestDescent(double depthOfSearch){
        this.depthOfSearch = depthOfSearch;
    }

    int mapDOS(double DOS){
        if(DOS < 0.2 && DOS >= 0){
            return 1;
        } if(DOS < 0.4 && DOS >= 0.2) {
            return 2;
        } if(DOS < 0.6 && DOS >= 0.4){
            return 3;
        } if(DOS < 0.8 && DOS >= 0.6) {
            return 4;
        } if(DOS < 1.0 && DOS >= 0.8) {
            return 5;
        } if(DOS == 1.0){
            return 6;
        }
        System.out.println("ERROR: INVALID DOS");
        return -1;
    }

    @Override
    public void applyHueristic(ProblemInstance problemInstance) {
        int numIterations = this.mapDOS(this.depthOfSearch);
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

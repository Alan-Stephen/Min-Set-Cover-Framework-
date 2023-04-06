import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

// todo: change this into a choice functoin.
public class ReinforcementHyperHuerisitc {
    private static final int TIME_TO_RUN = 10;
    private final Random random;
    private ArrayList<Integer> scores = new ArrayList<>();
    private ArrayList<Hueristic> hueristics = new ArrayList<>();
    private SimulatedAnnealingReheating coolingSchedule;

    private double depthOfSearch;
    private double intensityOfMutation;

    private final int plateauBoundary;

    private final ProblemInstance problemInstance;

    ReinforcementHyperHuerisitc(ProblemInstance problemInstance, double depthOfSearch, double intensityOfMutation, double alpha, double reheatingFactor,int plateauBoundary){
        double maxObjectiveValue = problemInstance.getNumElementsInX() + problemInstance.getSubsets().size();
        this.plateauBoundary = plateauBoundary;
        this.coolingSchedule = new SimulatedAnnealingReheating(maxObjectiveValue,alpha,reheatingFactor,problemInstance.random);
        this.random = problemInstance.random;
        this.problemInstance = problemInstance;
        this.depthOfSearch = depthOfSearch;
        this.intensityOfMutation = intensityOfMutation;

        //add all huerisitics
        hueristics.add(new SteepestDescent(depthOfSearch));
        hueristics.add(new RandomMutation(problemInstance.random, intensityOfMutation));
        hueristics.add(new BestSolutionBiasedCrossover(problemInstance.random, 0.3));
        hueristics.add(new RemoveRandomSet(intensityOfMutation,problemInstance.random));
        hueristics.add(new RuinAndRecreateHueristic(intensityOfMutation,random));

        for(int i = 0; i < hueristics.size(); i++){
            int INITIAL_SCORE = 5;
            scores.add(INITIAL_SCORE);
        }
    }

    public int getNextHeuristicIndex(){
        int max = 0;
        for(int score: scores)
            max += score;

        int randomResult = random.nextInt(0,max);
        int sum = 0;
        for(int i = 0; i < scores.size();i++){
            sum += scores.get(i);
            if(sum > randomResult)
                return i;
        }

        return scores.size() - 1;
    }

    public void applyHeuristic(int hueristicIndex){
        hueristics.get(hueristicIndex).applyHueristic(problemInstance);
    }

    public void updateHueristicScore(int hueristicIndex, int prevObjectiveValue, int postObjectiveValue){
        if(prevObjectiveValue <= postObjectiveValue){
            if(scores.get(hueristicIndex) == 1)
                return;
            scores.set(hueristicIndex,scores.get(hueristicIndex) - 1);
        } else {
            if(scores.get(hueristicIndex) == 20)
                return;
            scores.set(hueristicIndex,scores.get(hueristicIndex) + 1);
        }
    }

    public void run(){
        int bestObjectiveScore = Integer.MAX_VALUE;
        Solution currentSolution = problemInstance.getCurrentSolution();

        int plateauCounter = 0;

        long duration = 1000000000L * TIME_TO_RUN;
        long start = System.nanoTime();
        while((System.nanoTime()) - start < duration){

           int nextHeuristicIndex = getNextHeuristicIndex();

           int prev = currentSolution.getCurrentObjectiveValue();
           problemInstance.copySolution(currentSolution,problemInstance.getBackUpSolution());

           applyHeuristic(nextHeuristicIndex);

           int post = currentSolution.getCurrentObjectiveValue();


            if(prev == post) {
                plateauCounter++;
            }
           else {
                plateauCounter = 0;
            }

            if(!coolingSchedule.isMoveValid(prev,post)) {
                problemInstance.copySolution(problemInstance.getBackUpSolution(),currentSolution);
                scores.set(nextHeuristicIndex,scores.get(nextHeuristicIndex) - 1);
            }else {
                scores.set(nextHeuristicIndex,scores.get(nextHeuristicIndex) + 1);
            }

           if(post <= bestObjectiveScore && currentSolution.isSolutionComplete()) {
               bestObjectiveScore = post;
               problemInstance.setBestSolution(currentSolution);
           }

           if(plateauCounter == plateauBoundary){
               coolingSchedule.reheat();
           }
           coolingSchedule.advanceTemperture();
        }
        int count = 0;
        for(boolean bit: problemInstance.getBestSolution().getBitString()){
            if(bit)
                count++;
        }
    }
}
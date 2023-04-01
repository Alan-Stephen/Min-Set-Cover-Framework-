import java.util.ArrayList;
import java.util.Random;

// todo: change this into a choice functoin.
public class ReinforcementHyperHuerisitc {
    private static final int TIME_TO_RUN = 30;
    private final Random random;
    private ArrayList<Integer> scores = new ArrayList<>();
    private ArrayList<Hueristic> hueristics = new ArrayList<>();

    private double depthOfSearch;
    private double intensityOfMutation;

    private final ProblemInstance problemInstance;

    ReinforcementHyperHuerisitc(ProblemInstance problemInstance, double depthOfSearch, double intensityOfMutation, Random random){
        this.random = random;
        this.problemInstance = problemInstance;
        this.depthOfSearch = depthOfSearch;
        this.intensityOfMutation = intensityOfMutation;

        //add all huerisitics
        hueristics.add(new SteepestDescent(depthOfSearch));
        hueristics.add(new RandomMutation(random, intensityOfMutation));
        hueristics.add(new BestSolutionBiasedCrossover(random, 0.3));

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
        long start = System.nanoTime();
        long duration = 1000000000L * TIME_TO_RUN;
        int bestObjectiveScore = Integer.MAX_VALUE;
        Solution currentSolution = problemInstance.getCurrentSolution();
        while((System.nanoTime()) - start < duration){

           int nextHeuristicIndex = getNextHeuristicIndex();

           int prev = currentSolution.getCurrentObjectiveValue();
           applyHeuristic(nextHeuristicIndex);
           int post = currentSolution.getCurrentObjectiveValue();

           updateHueristicScore(nextHeuristicIndex, prev,post);

           System.out.print(currentSolution.getCurrentObjectiveValue());
           System.out.print(" Best : ");
           System.out.println(bestObjectiveScore);
           if(post <= bestObjectiveScore && currentSolution.isSolutionComplete()) {
               bestObjectiveScore = post;
               problemInstance.setBestSolution(currentSolution);
           }
        }
        System.out.println("BEST SOLUTION VALUE : ");
        System.out.println(problemInstance.getBestSolution().getCurrentObjectiveValue());
    }
}

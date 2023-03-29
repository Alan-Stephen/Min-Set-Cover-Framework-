import java.util.ArrayList;
import java.util.Random;

// todo: change this into a choice functoin.
public class ReinforcementHyperHuerisitc {
    private static final int TIME_TO_RUN = 10;
    private final int INITIAL_SCORE = 5;
    private final Random random;
    private ArrayList<Integer> scores = new ArrayList<>();
    private ArrayList<Hueristic> hueristics = new ArrayList<>();

    private double depthOfSearch;
    private double intensityOfMutation;

    private ProblemInstance problemInstance;

    ReinforcementHyperHuerisitc(ProblemInstance problemInstance, double depthOfSearch, double intensityOfMutation, Random random){
        this.random = random;
        this.problemInstance = problemInstance;
        this.depthOfSearch = depthOfSearch;
        this.intensityOfMutation = intensityOfMutation;

        //add all huerisitics
        hueristics.add(new SteepestDescent(depthOfSearch));
        hueristics.add(new RandomMutation(random, intensityOfMutation));

        for(int i = 0; i < hueristics.size(); i++){
            scores.add(this.INITIAL_SCORE);
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
            if(scores.get(hueristicIndex) == 10)
                return;
            scores.set(hueristicIndex,scores.get(hueristicIndex) + 1);
        }
    }

    public void run(){
        long start = System.nanoTime();
        long duration = 1000000000L * TIME_TO_RUN;
        while((System.nanoTime()) - start < duration){
           int nextHeuristicIndex = getNextHeuristicIndex();
           int prev = problemInstance.getObjectiveValueOfSolution(problemInstance.CURRENT_SOLUTION_INDEX);
           applyHeuristic(nextHeuristicIndex);
           int post = problemInstance.getObjectiveValueOfSolution(problemInstance.CURRENT_SOLUTION_INDEX);
           updateHueristicScore(nextHeuristicIndex, prev,post);
           System.out.println(problemInstance.getSolution(problemInstance.CURRENT_SOLUTION_INDEX).currentObjectiveValue);
        }
    }
}

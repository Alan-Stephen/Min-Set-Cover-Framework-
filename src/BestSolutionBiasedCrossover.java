import java.util.Random;

public class BestSolutionBiasedCrossover implements Hueristic{

    private final Random random;
    private final double bestParentBias;

    /**
     * @param bestParentBias has to be bewteen 0<= x <=0.5, decides how biased the
     *                   crossover will be towards the best solution parent.
     * */
    BestSolutionBiasedCrossover(Random random, double bestParentBias){
        this.bestParentBias = bestParentBias;
        this.random = random;
    }

    @Override
    public void applyHueristic(ProblemInstance problemInstance) {
//        System.out.println("apply crossover");
       Solution bestSolution = problemInstance.getBestSolution();
       Solution currentSolution = problemInstance.getCurrentSolution();

       for(int i = 0; i < currentSolution.getNumVariables(); i++){
           if(random.nextFloat() > 0.5 + bestParentBias){
               return;
           } else {
               currentSolution.setBit(i,bestSolution.getBit(i));
           }
       }
    }
}

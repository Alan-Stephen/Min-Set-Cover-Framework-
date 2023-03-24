import com.sun.jdi.event.StepEvent;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Random random = new Random(1);
        ProblemInstance problemInstance = new ProblemInstance("test_instances/d3_511_210.txt", random);
        System.out.println(problemInstance.getCurrentSolution().getBitString().size());
        SteepestDescent steepestDescent = new SteepestDescent(10);
        RandomMutation randomMutation = new RandomMutation(random);

        for(int i = 0; i < 1000; i++){
            for(int y = 0; y < 10; y++){
                randomMutation.applyHueristic(problemInstance);
            }
            System.out.println(problemInstance.getCurrentSolution());
            System.out.println(problemInstance.getCurrentSolution().currentObjectiveValue);
            System.out.println();
            for(int p = 0; p < 11; p++) {
                steepestDescent.applyHueristic(problemInstance);
            }
        }
    }
}
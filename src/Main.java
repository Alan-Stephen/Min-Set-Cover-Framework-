import com.sun.jdi.event.StepEvent;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ProblemInstance problemInstance = new ProblemInstance("test_instances/d4_2047_495.txt");
        System.out.println(problemInstance.getCurrentSolution().getBitString().size());
        SteepestDescent steepestDescent = new SteepestDescent(10);

        for(int i = 0; i < 1000; i++){
            System.out.println(problemInstance.getCurrentSolution());
            System.out.println(problemInstance.getCurrentSolution().currentObjectiveValue);
            System.out.println();
            steepestDescent.applyHueristic(problemInstance);
        }
    }
}
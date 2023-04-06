import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Random;

public class Runner {

    private ReinforcementHyperHuerisitc reinforcementHyperHuerisitc;

    private final int numTrials;

    private final Parameters parameters;

    private ProblemInstance problemInstance;

    private final String filePath;

    private int seed = 1000;

    private void resetReinforcementHyperHueristic(){
        Random random = new Random(seed);
        problemInstance = new ProblemInstance(filePath,random,2);
        ReinforcementHyperHuerisitc reinforcementHyperHuerisitc = new ReinforcementHyperHuerisitc(
                problemInstance,
                parameters.DOS,
                parameters.IOM,
                parameters.alpha,
                parameters.reheatingFactor,
                10
        );
        this.reinforcementHyperHuerisitc = reinforcementHyperHuerisitc;
    }

    Runner(Parameters parameters, int testID, int numTrials){
        this.numTrials = numTrials;
        this.parameters = parameters;

        String testLink = "";
        Random random = new Random();

        if (testID == 1)
            testLink = "d1_50_500.txt";
        else if(testID == 2)
                testLink = "d2_50_500.txt";
        else if(testID == 3)
                testLink = "d3_511_210.txt";
        else if(testID == 4)
            testLink = "d4_2047_495.txt";
        else {
            System.out.println("ERROR INVALID TEST ID");
            System.exit(1);
        }


        this.filePath = "test_instances/" + testLink;

        ProblemInstance problemInstance = new ProblemInstance(filePath,random,2);
        this.problemInstance = problemInstance;
        this.reinforcementHyperHuerisitc = new ReinforcementHyperHuerisitc(
                problemInstance,
                parameters.DOS,
                parameters.IOM,
                parameters.alpha,
                parameters.reheatingFactor,
                10
        );
    }

    private String bitStringToString(ArrayList<Boolean> bitString){
        String returnString = "";
        for(boolean bit: bitString){
            if(bit)
                returnString = returnString + "1";
            else
                returnString = returnString + "0";
        }
        return returnString;
    }
    public void run(){
        for(int i = 0; i < numTrials;i++) {
            reinforcementHyperHuerisitc.run();

            System.out.printf("TRIAL#%d\n",i);
            System.out.println(problemInstance.getBestSolution().getCurrentObjectiveValue());
            System.out.println(bitStringToString(problemInstance.getBestSolution().getBitString()));
            this.seed += 1;
            resetReinforcementHyperHueristic();
        }
    }
}

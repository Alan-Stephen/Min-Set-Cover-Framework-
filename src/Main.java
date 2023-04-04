import com.sun.jdi.event.StepEvent;

import java.util.Random;

public class Main {
    static int sumX(int[] x){
        int count = 0;
        for(int i: x)
            if(i != 0)
                count++;
        return count;
    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Random random = new Random(231);
        ProblemInstance problemInstance = new ProblemInstance("test_instances/d1_50_500.txt", random,2);
        // Low alpha (0.95 is good for instances where subsets are larger are there is a need for quicker convergence)
        // for
        ReinforcementHyperHuerisitc reinforcementHyperHuerisitc = new ReinforcementHyperHuerisitc(problemInstance,0.8,0.4,0.95,100,5,random);
        reinforcementHyperHuerisitc.run();
    }
}
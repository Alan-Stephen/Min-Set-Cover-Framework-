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
        // todo : refactor the whole solution system.
        System.out.println("Hello world!");
        Random random = new Random(23222);
        ProblemInstance problemInstance = new ProblemInstance("test_instances/d4_2047_495.txt", random,2);
        ReinforcementHyperHuerisitc reinforcementHyperHuerisitc = new ReinforcementHyperHuerisitc(problemInstance,1.0,0.1,0.97,200,5,random);
        reinforcementHyperHuerisitc.run();
    }
}
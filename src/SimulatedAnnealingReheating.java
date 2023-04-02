import java.util.Random;

public class SimulatedAnnealingReheating {
    double temp;
    double alpha;
    Random random;
    double reheatingFactor;

    SimulatedAnnealingReheating(double startingTemp, double startingAlpha, double reheatingFactor, Random random){
        this.temp = startingTemp;
        this.alpha = startingAlpha;
        this.reheatingFactor = reheatingFactor;
        this.random = random;
    }

    public void reheat(){
        temp *= reheatingFactor;
    }

    public void advanceTemperture(){
        temp *= alpha;
    }

    public boolean isMoveValid(int prev, int post){
        double delta = post - prev;
        double randomValue = random.nextDouble();
        if(delta <= 0 || randomValue < probabilityFunction(delta))
            return true;
        else
            return false;
    }

    private double probabilityFunction(double delta){
        return Math.exp((delta * -1) / temp);
    }
}

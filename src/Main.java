public class Main {
    static int sumX(int[] x){
        int count = 0;
        for(int i: x)
            if(i != 0)
                count++;
        return count;
    }

    public static void main(String[] args) {
        Runner runner = new Runner(
                new Parameters(0.4,0.8,0.98,150),
                2,
                4);
        runner.run();
    }
}
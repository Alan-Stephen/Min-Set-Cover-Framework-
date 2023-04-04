import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ProblemInstance {
    private Random random;
    private ArrayList<Subset> subsets = new ArrayList<Subset>();
    private Solution currentSolution;
    private Solution backUpSolution;
    private int elementsInX;
    private Solution bestSolution;

    public Solution getCurrentSolution() {
        return currentSolution;
    }

    public static int mapToInt(double value){
        if(value < 0.2 && value >= 0){
            return 1;
        } if(value < 0.4 && value >= 0.2) {
            return 2;
        } if(value < 0.6 && value >= 0.4){
            return 3;
        } if(value < 0.8 && value >= 0.6) {
            return 4;
        } if(value < 1.0 && value >= 0.8) {
            return 5;
        } if(value == 1.0){
            return 6;
        }
        System.out.println("ERROR: INVALID IOM");
        return -1;
    }

    public Solution getBackUpSolution() {
        return backUpSolution;
    }

    public void copySolution(Solution from, Solution to){
        for(int i = 0; i < subsets.size(); i++){
            to.getBitString().set(i,from.getBitString().get(i));
        }

        for(int i = 0; i < to.getX().length; i++){
            to.getX()[i] = from.getX()[i];
        }

        to.setSetsUsed(from.getSetsUsed());

        to.setCurrentObjectiveValue(from.getCurrentObjectiveValue());
    }
    public Solution getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(Solution solution) {
        copySolution(solution,this.bestSolution);
    }

    ProblemInstance(String filePath, Random random,int numSolutions){
        this.random = random;
        try {
            File problemInstance = new File(filePath);
            Scanner reader = new Scanner(problemInstance);

            int numSubsets;
            ArrayList<String> problemAttributes = new ArrayList<>(List.of(reader.nextLine().split("\\s+")));
            problemAttributes.removeAll(Arrays.asList("", null));
            System.out.println(problemAttributes.toString());
            numSubsets = Integer.parseInt(problemAttributes.get(0));
            elementsInX = Integer.parseInt(problemAttributes.get(1));

            for(int i = 0; i < numSubsets;i++){
                ArrayList<Integer> elements = new ArrayList<Integer>();
                int subsetSize = Integer.parseInt(reader.nextLine().trim());

                int elementsVisited = 0;
                while(elementsVisited < subsetSize) {
                    ArrayList<String> elementsOnCurrentLine = new ArrayList<>(List.of(reader.nextLine().split("\\s+")));
                    elementsOnCurrentLine.removeAll(Arrays.asList("", null));

                    for (String s : elementsOnCurrentLine) {
                        elements.add(Integer.parseInt(s));
                        elementsVisited++;
                    }
                }
                subsets.add(new Subset(i,elements));
            }

        } catch (FileNotFoundException e) {
            System.out.println("ERROR : FILE NOT FOUND");
            e.printStackTrace();
            System.exit(1);
        }

        currentSolution = new Solution(this,random);
        backUpSolution = new Solution(this,random);
        bestSolution = new Solution(this,random);
    }

    @Override
    public String toString() {
        return "ProblemInstance{" +
                "subsets=" + subsets +
                ", elementsInX=" + elementsInX +
                '}';
    }

    public ArrayList<Subset> getSubsets() {
        return subsets;
    }

    public int getNumElementsInX() {
        return elementsInX;
    }
}

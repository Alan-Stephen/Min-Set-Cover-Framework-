import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ProblemInstance {

    private Random random;
    private ArrayList<Subset> subsets = new ArrayList<Subset>();

    private ArrayList<Solution> solutions = new ArrayList<>();
    private int elementsInX;

    public final int CURRENT_SOLUTION_INDEX = 0;
    public final int BACKUP_SOLUTION_INDEX = 1;

    private Solution bestSolution;

    public int getObjectiveValueOfSolution(int solutionIndex) {
        return solutions.get(solutionIndex).currentObjectiveValue;
    }

    public void copySolution(Solution from, Solution to){
        for(int i = 0; i < subsets.size(); i++){
            to.getBitString().set(i,from.getBitString().get(i));
        }
        from.updateObjectiveSolutionValue();
        to.updateObjectiveSolutionValue();
    }
    public Solution getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(Solution bestSolution) {
        this.bestSolution = bestSolution;
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
        }

        for(int i = 0; i < numSolutions;i++)
            solutions.add(new Solution(this,random));
        bestSolution = new Solution(this,random);
    }

    public int getSubsetElement(int subsetID, int elementIndex){
        return subsets.get(subsetID).getElement(elementIndex);
    }

    public Solution getSolution(int index){
        return solutions.get(index);
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ProblemInstance {

    private Random random;
    private ArrayList<Subset> subsets = new ArrayList<Subset>();
    private int elementsInX;

    private Solution backUpSolution;
    private Solution currentSolution;
    private Solution bestSolution;

    public void copySolution(Solution from, Solution to){
        for(int i = 0; i < subsets.size(); i++){
            to.getBitString().set(i,from.getBitString().get(i));
        }
        from.updateObjectiveSolutionValue();
        to.updateObjectiveSolutionValue();
    }
    public Solution getBackUpSolution() {
        return backUpSolution;
    }

    public void setBackUpSolution(Solution backUpSolution) {
        this.backUpSolution = backUpSolution;
    }

    public Solution getCurrentSolution() {
        return currentSolution;
    }

    public void setCurrentSolution(Solution currentSolution) {
        this.currentSolution = currentSolution;
    }

    public Solution getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(Solution bestSolution) {
        this.bestSolution = bestSolution;
    }

    ProblemInstance(String filePath, Random random){
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
        currentSolution = new Solution(this);
        backUpSolution = new Solution(this);
        bestSolution = new Solution(this);
    }

    public int getSubsetElement(int subsetID, int elementIndex){
        return subsets.get(subsetID).getElement(elementIndex);
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

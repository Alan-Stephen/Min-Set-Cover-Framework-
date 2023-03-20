public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ProblemInstance problemInstance = new ProblemInstance("test_instances/d1_50_500.txt");

        Solution solution = new Solution(problemInstance);
        System.out.println(solution.toString());
        System.out.println(solution.isSolutionValid());
        System.out.println(solution.getObjectiveSolutionValue());
    }
}
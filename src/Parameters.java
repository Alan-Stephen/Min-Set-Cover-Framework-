public class Parameters {
    final double DOS;
    final double IOM;
    final double alpha;
    final double reheatingFactor;
    final int maxNumberOf = 1000;

    Parameters(double IOM, double DOS, double alpha, double reheatingFactor){
        this.IOM = IOM;
        this.DOS = DOS;
        this.alpha = alpha;
        this.reheatingFactor = reheatingFactor;
    }
}

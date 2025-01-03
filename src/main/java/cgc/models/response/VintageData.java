package cgc.models.response;

/**
 * A class to hold data for {@link Vintage} objects
 */
public class VintageData {
    /**
     * An array of {@link Vintage} objects
     */
    public Vintage[] vintages;
    /**
     * The ID representing the {@link Vintage} used
     */
    public String selectedBenchmark;
    /**
     * An array of {@link Vintage} objects
     */
    public Benchmark[] benchmarks;
    /**
     * Any errors from fetching (will be null if successful)
     */
    public String[] errors;
    /**
     * The error status code (will be null if successful)
     */
    public String status;
}

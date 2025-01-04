package cgc.models.response;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * The data used for the request
 */
public class LookupInput {
    /**
     * The address (as a {@link String}) or the split parts used
     */
    @JsonAlias("address")
    public LookupInputAddress lookupAddress;
    /**
     * The {@link Benchmark} used in the request
     */
    public Benchmark benchmark;
}

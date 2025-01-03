package cgc.models.response;

import com.fasterxml.jackson.annotation.JsonAlias;

public class LookupInput {
    @JsonAlias("address")
    public LookupInputAddress lookupAddress;
    public Benchmark benchmark;
}

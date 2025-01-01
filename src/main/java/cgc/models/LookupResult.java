package cgc.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public class LookupResult {
    public LookupData result;
    public String[] errors;
    @JsonAlias("status")
    public String errorStatus;
}

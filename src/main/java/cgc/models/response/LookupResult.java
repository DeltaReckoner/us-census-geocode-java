package cgc.models.response;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Wrapper class holding the response data, any errors, and error status codes
 */
public class LookupResult {
    /**
     * The response data
     */
    public LookupData result;
    /**
     * Errors that happened when doing the request
     */
    public String[] errors;
    /**
     * The error status code going with the error list
     */
    @JsonAlias("status")
    public String errorStatus;
}

package cgc.models.response;

/**
 * The data holding the request input and address matches
 */
public class LookupData {
    /**
     * The request input data
     */
    public LookupInput input;
    /**
     * The address matches from the request input
     */
    public Match[] addressMatches;
}

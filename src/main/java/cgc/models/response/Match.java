package cgc.models.response;

/**
 * The data for an address match
 */
public class Match {
    /**
     * The geographic spatial data (TIGER/Line)
     */
    public TigerLine tigerLine;
    /**
     * The coordinates for an address match
     */
    public Coordinates coordinates;
    /**
     * The data that makes up the address in this match
     */
    public AddressComponents addressComponents;
    /**
     * The matching address as a one-line {@link String}
     */
    public String matchedAddress;
}

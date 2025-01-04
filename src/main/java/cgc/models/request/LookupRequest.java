package cgc.models.request;

/**
 * A request object that can hold various properties to help with searching
 * <br><br>Uses a {@link LookupRequestBuilder} to construct requests
 */
public class LookupRequest {

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String urbanization;
    private Integer municipio;
    private String benchmarkId;

    /**
     * Gets the street name used in the request
     * @return {@link String}
     */
    public String getStreet() {
        return street;
    }

    /**
     * Gets the city name used in the request
     * @return {@link String}
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the state name used in the request
     * @return {@link String}
     */
    public String getState() {
        return state;
    }

    /**
     * Gets the zip code used in the request
     * @return {@link String}
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Gets the urbanization descriptor used in the request
     * @return {@link String}
     */
    public String getUrbanization() {
        return urbanization;
    }

    /**
     * Gets the municipio code used in the request
     * @return {@link String}
     */
    public Integer getMunicipio() {
        return municipio;
    }

    /**
     * Gets the benchmark ID used in the request
     * @return {@link String}
     */
    public String getBenchmarkId() {
        return benchmarkId;
    }

    private LookupRequest(LookupRequestBuilder builder) {
        street = builder.street;
        city = builder.city;
        state = builder.state;
        zipCode = builder.zipCode;
        urbanization = builder.urbanization;
        municipio = builder.municipio;
        benchmarkId = builder.benchmarkId;
    }

    public static class LookupRequestBuilder {
        private String address;
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String urbanization;
        private Integer municipio;
        private String benchmarkId;

        /**
         * Sets the street name to be used in the request
         * @param street {@link String}
         * @return {@link LookupRequestBuilder}
         */
        public LookupRequestBuilder setStreet(String street) {
            this.street = street;
            return this;
        }

        /**
         * Sets the city name to be used in the request
         * @param city {@link String}
         * @return {@link LookupRequestBuilder}
         */
        public LookupRequestBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        /**
         * Sets the state name to be used in the request
         * @param state {@link String}
         * @return {@link LookupRequestBuilder}
         */
        public LookupRequestBuilder setState(String state) {
            this.state = state;
            return this;
        }

        /**
         * Sets the zip code to be used in the request
         * @param zipCode {@link String}
         * @return {@link LookupRequestBuilder}
         */
        public LookupRequestBuilder setZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        /**
         * Sets the urbaniation descriptor to be used in the request
         * @param urbanization {@link String}
         * @return {@link LookupRequestBuilder}
         */
        public LookupRequestBuilder setUrbanization(String urbanization) {
            this.urbanization = urbanization;
            return this;
        }

        /**
         * Sets the municipio code to be used in the request
         * @param municipio {@link Integer}
         * @return {@link LookupRequestBuilder}
         */
        public LookupRequestBuilder setMunicipio(Integer municipio) {
            this.municipio = municipio;
            return this;
        }

        /**
         * Sets the benchmark ID to be used in the request
         * <br><br>You can find benchmarks by using getBenchmarkData()
         * @param benchmarkId {@link String}
         * @return {@link LookupRequestBuilder}
         */
        public LookupRequestBuilder setBenchmarkId(String benchmarkId) {
            this.benchmarkId = benchmarkId;
            return this;
        }

        /**
         * Constructs the request object to be used in searching.
         * @return {@link LookupRequestBuilder}
         */
        public LookupRequest build() {
            return new LookupRequest(this);
        }
    }
}
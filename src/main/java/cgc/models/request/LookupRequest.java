package cgc.models.request;

public class LookupRequest {

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String urbanization;
    private Integer municipio;
    private String benchmarkId;

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getUrbanization() {
        return urbanization;
    }

    public Integer getMunicipio() {
        return municipio;
    }

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

        public LookupRequestBuilder setStreet(String street) {
            this.street = street;
            return this;
        }

        public LookupRequestBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public LookupRequestBuilder setState(String state) {
            this.state = state;
            return this;
        }

        public LookupRequestBuilder setZipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public LookupRequestBuilder setUrbanization(String urbanization) {
            this.urbanization = urbanization;
            return this;
        }

        public LookupRequestBuilder setMunicipio(Integer municipio) {
            this.municipio = municipio;
            return this;
        }

        public LookupRequestBuilder setBenchmarkId(String benchmarkId) {
            this.benchmarkId = benchmarkId;
            return this;
        }

        public LookupRequest build() {
            return new LookupRequest(this);
        }
    }
}
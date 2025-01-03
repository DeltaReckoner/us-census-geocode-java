package cgc;

import cgc.models.request.LookupRequest;
import cgc.models.response.BenchmarkData;
import cgc.models.response.LookupResult;
import cgc.models.response.VintageData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CensusGeocodingClient {
    private HttpClient httpClient;
    private final String BASE_URL = "https://geocoding.geo.census.gov/geocoder";
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Default constructor
     */
    public CensusGeocodingClient() {
        httpClient = HttpClient.newBuilder().build();
    }

    /**
     * Gets the list of benchmarks maintained by the US Census
     * @return {@link BenchmarkData} Benchmark data
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    public BenchmarkData getBenchmarkData() throws ExecutionException, InterruptedException, JsonProcessingException {
        String requestUrl = BASE_URL + "/benchmarks";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("User-Agent", "census-geocoding-java")
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> httpResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
        String responseJson = httpResponse.thenApply(HttpResponse::body).get();
        return OBJECT_MAPPER.readValue(responseJson, BenchmarkData.class);
    }

    /**
     * Gets the list of vintages of geography maintained by the US Census
     * @return {@link VintageData} Vintage of geography data
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    public VintageData getVintageData(String benchmarkId) throws JsonProcessingException, ExecutionException, InterruptedException {
        String requestUrl = BASE_URL + "/vintages?benchmark=" + benchmarkId;
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("User-Agent", "census-geocoding-java")
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> httpResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
        String responseJson = httpResponse.thenApply(HttpResponse::body).get();
        return OBJECT_MAPPER.readValue(responseJson, VintageData.class);
    }

    /**
     * Gets the geocoding data for an address in {@link String} form.
     * <br>Example: 4600 Silver Hill Rd, Washington, DC 20233
     * <br><br>Puerto Rico addresses with NO urbanization and municipio are supported.
     *
     * @param address Address to look up.
     * @param benchMarkId Benchmark to reference. See getBenchmarkData()
     * @return Data for United States or Puerto Rico addresses (without urbanization and municipio)
     * @throws ExecutionException If there are any issues with running the request.
     * @throws InterruptedException If there are any interruptions with the request being processed.
     * @throws JsonProcessingException If there are any issues converting the Json response to a {@link LookupResult}.
     */
    public LookupResult getData(String address, String benchMarkId) throws ExecutionException, InterruptedException, JsonProcessingException {
        String requestUrl = BASE_URL + "/locations/onelineaddress?address="
                + formatString(address)
                + "&benchmark=" + benchMarkId
                + "&format=json";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("User-Agent", "census-geocoding-java")
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> httpResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
        String responseJson = httpResponse.thenApply(HttpResponse::body).get();
        return OBJECT_MAPPER.readValue(responseJson, LookupResult.class);
    }

    /**
     * Gets the geocoding data for an address in {@link LookupRequest} form. Puerto Rico addresses without urbanization and municipio are supported.
     * <br><br>Minimum requirements: (street and zip code) OR (street, city, and state)
     * @param request {@link LookupRequest}
     * @return Data for United States or Puerto Rico addresses (without urbanization and municipio)
     * @throws ExecutionException If there are any issues with running the request.
     * @throws InterruptedException If there are any interruptions with the request being processed.
     * @throws JsonProcessingException If there are any issues converting the Json response to a {@link LookupResult}.
     */
    public LookupResult getData(LookupRequest request) throws ExecutionException, InterruptedException, JsonProcessingException {
        String requestUrl = BASE_URL + "/locations/address?" + formatLookUpRequestToString(request);
        requestUrl = formatString(requestUrl);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("User-Agent", "census-geocoding-java")
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> httpResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
        String responseJson = httpResponse.thenApply(HttpResponse::body).get();
        return OBJECT_MAPPER.readValue(responseJson, LookupResult.class);
    }

    /**
     * Gets the geocoding data for an address in {@link LookupRequest} form. Only Puerto Rico addresses are supported.
     * <br><br>Minimum requirements:
     * <br> - Zip code must start with 006, 007, or 009
     * <br> - Address without urbanization: (street and zip code) OR (street, city, and state)
     * <br> - Address with urbanization: (street, urbanization, and municipio)
     * @param request {@link LookupRequest}
     * @return Data for Puerto Rico address
     * @throws ExecutionException If there are any issues with running the request.
     * @throws InterruptedException If there are any interruptions with the request being processed.
     * @throws JsonProcessingException If there are any issues converting the Json response to a {@link LookupResult}.
     */
    public LookupResult getPuertoRicoData(LookupRequest request) throws ExecutionException, InterruptedException, JsonProcessingException {
        String requestUrl = BASE_URL + "/locations/addressPR?" + formatLookUpRequestToString(request);
        requestUrl = formatString(requestUrl);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("User-Agent", "census-geocoding-java")
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> httpResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
        String responseJson = httpResponse.thenApply(HttpResponse::body).get();
        return OBJECT_MAPPER.readValue(responseJson, LookupResult.class);
    }

    private String formatString(String input) {
        return input.replace(" ", "+").replace(",", "%2C");
    }

    private String formatLookUpRequestToString(LookupRequest request) {
        StringBuilder builder = new StringBuilder();

        String street = !request.getStreet().isEmpty() ?
                "street=" + request.getStreet() + "&" : "";
        String city = !request.getCity().isEmpty() ?
                "city=" + request.getCity() + "&"  : "";
        String state = !request.getState().isEmpty() ?
                "state=" + request.getState() + "&"  : "";
        String zipCode = request.getZipCode() != null ?
                "zip=" + request.getZipCode() + "&"  : "";
        String urbanization = request.getUrbanization() != null ?
                "urb=" + request.getUrbanization() + "&"  : "";
        String municipio = request.getMunicipio() != null ?
                "municipio=" + request.getMunicipio() + "&"  : "";
        String benchmarkId = request.getBenchmarkId() != null ?
                "benchmark=" + request.getBenchmarkId() + "&"  : "";
        String format = "format=json";

        builder.append(street);
        builder.append(city);
        builder.append(state);
        builder.append(zipCode);
        builder.append(urbanization);
        builder.append(municipio);
        builder.append(benchmarkId);
        builder.append(format);

        return builder.toString();
    }
}

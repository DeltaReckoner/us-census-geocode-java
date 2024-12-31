package cgc;

import cgc.models.BenchmarkData;
import cgc.models.VintageData;
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

}

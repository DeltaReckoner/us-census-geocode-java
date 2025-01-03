import cgc.CensusGeocodingClient;
import cgc.models.request.LookupRequest;
import cgc.models.response.Benchmark;
import cgc.models.response.BenchmarkData;
import cgc.models.response.LookupResult;
import cgc.models.response.VintageData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CensusGeocodingClientTest {
    private CensusGeocodingClient censusGeocodingClient;

    @BeforeEach
    void setUp() {
        censusGeocodingClient = new CensusGeocodingClient();
    }

    @Test
    void testGetBenchmarkData() {
        try {
            BenchmarkData benchmarkList = censusGeocodingClient.getBenchmarkData();
            Assertions.assertNotNull(benchmarkList);
            Assertions.assertNotNull(benchmarkList.benchmarks);
            Assertions.assertTrue(benchmarkList.benchmarks.length != 0);
            Assertions.assertFalse(benchmarkList.benchmarks[0].benchmarkName.isEmpty());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testGetVintageData() {
        try {
            BenchmarkData benchmarkList = censusGeocodingClient.getBenchmarkData();
            Benchmark benchmark = benchmarkList.benchmarks[0];

            VintageData vintageList = censusGeocodingClient.getVintageData(benchmark.id);
            Assertions.assertNotNull(vintageList);
            Assertions.assertTrue(vintageList.vintages.length != 0);
            Assertions.assertFalse(vintageList.vintages[0].vintageName.isEmpty());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testGetInvalidVintageData() {
        try {
            VintageData vintageList = censusGeocodingClient.getVintageData("-999");
            Assertions.assertNotNull(vintageList);
            Assertions.assertNotNull(vintageList.errors);
            Assertions.assertFalse(vintageList.errors[0].isEmpty());
            Assertions.assertFalse(vintageList.status.isEmpty());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testGetLookupDataFromStringAddress() {
        try {
            BenchmarkData benchmarkData = censusGeocodingClient.getBenchmarkData();
            String benchmarkId = benchmarkData.benchmarks[0].id;
            LookupResult lookupResult = censusGeocodingClient.getData("4600 Silver Hill Rd, Washington, DC 20233", benchmarkId);

            Assertions.assertNotNull(lookupResult);
            Assertions.assertNotNull(lookupResult.result);
            Assertions.assertTrue(lookupResult.result.addressMatches.length != 0);
            Assertions.assertNotNull(lookupResult.result.addressMatches[0].coordinates);
            Assertions.assertNotNull(lookupResult.result.input);

            Assertions.assertEquals("4600 Silver Hill Rd, Washington, DC 20233", lookupResult.result.input.lookupAddress.address);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testGetLookupDataFromInvalidStringAddress() {
        try {
            LookupResult lookupResult = censusGeocodingClient.getData("invalid", "-999.0");

            Assertions.assertNotNull(lookupResult);
            Assertions.assertTrue(lookupResult.errors.length != 0);
            Assertions.assertFalse(lookupResult.errorStatus.isEmpty());
            Assertions.assertNull(lookupResult.result);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testGetLookupDataFromRequestObject() {
        try {
            BenchmarkData benchmarkData = censusGeocodingClient.getBenchmarkData();

            LookupRequest lookupRequest = new LookupRequest.LookupRequestBuilder()
                    .setStreet("4600 Silver Hill Rd")
                    .setCity("Washington")
                    .setState("DC")
                    .setBenchmarkId(benchmarkData.benchmarks[0].id)
                    .build();

            LookupResult lookupResult = censusGeocodingClient.getData(lookupRequest);

            Assertions.assertNotNull(lookupResult);
            Assertions.assertNotNull(lookupResult.result);
            Assertions.assertTrue(lookupResult.result.addressMatches.length != 0);
            Assertions.assertNotNull(lookupResult.result.addressMatches[0].coordinates);
            Assertions.assertNotNull(lookupResult.result.input);
            Assertions.assertNotNull(lookupResult.result.input.lookupAddress);
            Assertions.assertEquals("4600 Silver Hill Rd", lookupResult.result.input.lookupAddress.street);
            Assertions.assertEquals("Washington", lookupResult.result.input.lookupAddress.city);
            Assertions.assertEquals("DC", lookupResult.result.input.lookupAddress.state);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testGetLookupDataFromInvalidRequestObject() {
        try {
            BenchmarkData benchmarkData = censusGeocodingClient.getBenchmarkData();

            LookupRequest lookupRequest = new LookupRequest.LookupRequestBuilder()
                    .setStreet("invalid")
                    .setCity("invalid")
                    .setState("invalid")
                    .setBenchmarkId(benchmarkData.benchmarks[0].id)
                    .build();

            LookupResult lookupResult = censusGeocodingClient.getData(lookupRequest);

            Assertions.assertNotNull(lookupResult);
            Assertions.assertNotNull(lookupResult.result);
            Assertions.assertTrue(lookupResult.result.addressMatches.length == 0);
            Assertions.assertNotNull(lookupResult.result.input);
            Assertions.assertNotNull(lookupResult.result.input.lookupAddress);
            Assertions.assertEquals("invalid", lookupResult.result.input.lookupAddress.street);
            Assertions.assertEquals("invalid", lookupResult.result.input.lookupAddress.city);
            Assertions.assertEquals("invalid", lookupResult.result.input.lookupAddress.state);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testGetLookupDataFromInvalidRequestObjectNoBenchmark() {
        try {
            LookupRequest lookupRequest = new LookupRequest.LookupRequestBuilder()
                    .setStreet("invalid")
                    .setCity("invalid")
                    .setState("invalid")
                    .build();

            LookupResult lookupResult = censusGeocodingClient.getData(lookupRequest);

            Assertions.assertNotNull(lookupResult);
            Assertions.assertNotNull(lookupResult.errors);
            Assertions.assertTrue(!lookupResult.errorStatus.isEmpty());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testGetPuertoRicoLookupDataFromRequestObject() {
        /*try {
            BenchmarkData benchmarkData = censusGeocodingClient.getBenchmarkData();

            LookupRequest lookupRequest = new LookupRequest.LookupRequestBuilder()
                    .setStreet("Flamboyan")
                    .setCity("Jaguas")
                    .setState("PR")
                    .setZipCode("00638")
                    .setBenchmarkId(benchmarkData.benchmarks[0].id)
                    .build();

            LookupResult lookupResult = censusGeocodingClient.getPuertoRicoData(lookupRequest);

            Assertions.assertNotNull(lookupResult);
            Assertions.assertNotNull(lookupResult.result);
            Assertions.assertTrue(lookupResult.result.addressMatches.length != 0);
            Assertions.assertNotNull(lookupResult.result.addressMatches[0].coordinates);
            Assertions.assertNotNull(lookupResult.result.input);
            Assertions.assertNotNull(lookupResult.result.input.lookupAddress);
            Assertions.assertEquals("4600 Silver Hill Rd", lookupResult.result.input.lookupAddress.street);
            Assertions.assertEquals("Washington", lookupResult.result.input.lookupAddress.city);
            Assertions.assertEquals("PR", lookupResult.result.input.lookupAddress.state);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }*/
    }

    @Test
    void testGetPuertoRicoLookupDataFromInvalidRequestObject() {
        try {
            BenchmarkData benchmarkData = censusGeocodingClient.getBenchmarkData();

            LookupRequest lookupRequest = new LookupRequest.LookupRequestBuilder()
                    .setStreet("invalid")
                    .setCity("invalid")
                    .setState("invalid")
                    .setBenchmarkId(benchmarkData.benchmarks[0].id)
                    .build();

            LookupResult lookupResult = censusGeocodingClient.getPuertoRicoData(lookupRequest);

            Assertions.assertNotNull(lookupResult);
            Assertions.assertNotNull(lookupResult.result);
            Assertions.assertTrue(lookupResult.result.addressMatches.length == 0);
            Assertions.assertNotNull(lookupResult.result.input);
            Assertions.assertNotNull(lookupResult.result.input.lookupAddress);
            Assertions.assertEquals("invalid", lookupResult.result.input.lookupAddress.street);
            Assertions.assertEquals("invalid", lookupResult.result.input.lookupAddress.city);
            Assertions.assertEquals("PR", lookupResult.result.input.lookupAddress.state);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testGetPuertoRicoLookupDataFromInvalidRequestObjectNoBenchmark() {
        try {
            LookupRequest lookupRequest = new LookupRequest.LookupRequestBuilder()
                    .setStreet("invalid")
                    .setCity("invalid")
                    .setState("invalid")
                    .build();

            LookupResult lookupResult = censusGeocodingClient.getPuertoRicoData(lookupRequest);

            Assertions.assertNotNull(lookupResult);
            Assertions.assertNotNull(lookupResult.errors);
            Assertions.assertTrue(!lookupResult.errorStatus.isEmpty());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}

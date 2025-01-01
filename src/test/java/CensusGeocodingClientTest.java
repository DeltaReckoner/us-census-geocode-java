import cgc.CensusGeocodingClient;
import cgc.models.Benchmark;
import cgc.models.BenchmarkData;
import cgc.models.Format;
import cgc.models.LookupResult;
import cgc.models.VintageData;
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
            LookupResult lookupResult = censusGeocodingClient.getLookupResult("4600 Silver Hill Rd, Washington, DC 20233", benchmarkId, Format.Json);

            Assertions.assertNotNull(lookupResult);
            Assertions.assertNotNull(lookupResult.result);
            Assertions.assertTrue(lookupResult.result.addressMatches.length != 0);
            Assertions.assertNotNull(lookupResult.result.addressMatches[0].coordinates);
            Assertions.assertNotNull(lookupResult.result.input);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void testGetLookupDataFromInvalidStringAddress() {
        try {
            LookupResult lookupResult = censusGeocodingClient.getLookupResult("invalid", "-999.0", Format.Json);

            Assertions.assertNotNull(lookupResult);
            Assertions.assertTrue(lookupResult.errors.length != 0);
            Assertions.assertFalse(lookupResult.errorStatus.isEmpty());
            Assertions.assertNull(lookupResult.result);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}

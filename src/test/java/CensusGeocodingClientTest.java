import cgc.CensusGeocodingClient;
import cgc.models.Benchmark;
import cgc.models.BenchmarkData;
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
}

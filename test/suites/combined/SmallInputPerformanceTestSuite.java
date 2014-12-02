package suites.combined;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import performance.AbstractPerformanceTest;
import performance.BlockNestedLoopPerformanceTest;
import performance.SortFilterSkylinePerformanceTest;

/**
 *
 * @author Dimitris Kanellis
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({SortFilterSkylinePerformanceTest.class, BlockNestedLoopPerformanceTest.class})
public class SmallInputPerformanceTestSuite {

    private final static String DATASETS_FOLDER = "data/datasets/small";
    private final static String EXPECTED_RESULTS_FOLDER = "data/expected results/small";

    public SmallInputPerformanceTestSuite() {
    }

    @BeforeClass
    public static void setUpClass() {
        AbstractPerformanceTest.init(DATASETS_FOLDER, EXPECTED_RESULTS_FOLDER, "SmallInputPerformanceTestSuite");
    }

    @AfterClass
    public static void tearDownClass() throws InterruptedException {
        AbstractPerformanceTest.stopSparkContext();
        AbstractPerformanceTest.printResults();
    }
}
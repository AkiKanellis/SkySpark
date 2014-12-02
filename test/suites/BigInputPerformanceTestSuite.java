package suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import performance.blocknestedloop.AbstractPerformanceTest;
import performance.blocknestedloop.BlockNestedLoopPerformanceTest;
import performance.blocknestedloop.SortFilterSkylinePerformanceTest;

/**
 *
 * @author Dimitris Kanellis
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BlockNestedLoopPerformanceTest.class, SortFilterSkylinePerformanceTest.class})
public class BigInputPerformanceTestSuite extends AbstractPerformanceTest {

    private final static String DATASETS_FOLDER = "data/datasets/big";
    private final static String EXPECTED_RESULTS_FOLDER = "data/expected results/big";

    @BeforeClass
    public static void setUpClass() {
        AbstractPerformanceTest.init(DATASETS_FOLDER, EXPECTED_RESULTS_FOLDER, "BigInputPerformanceTestSuite");
    }

    @AfterClass
    public static void tearDownClass() {
        AbstractPerformanceTest.stopSparkContext();
        AbstractPerformanceTest.printResults();
    }
}
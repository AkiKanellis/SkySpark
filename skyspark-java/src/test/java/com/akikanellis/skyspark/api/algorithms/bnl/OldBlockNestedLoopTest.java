package com.akikanellis.skyspark.api.algorithms.bnl;

import com.akikanellis.skyspark.api.test_utils.SparkContextRule;
import com.akikanellis.skyspark.api.test_utils.data_mocks.FlagPointPairsMock;
import org.apache.spark.api.java.JavaPairRDD;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static com.akikanellis.skyspark.api.test_utils.assertions.MoreAssertions.assertThat;


public class OldBlockNestedLoopTest {
    @Rule public SparkContextRule sc = new SparkContextRule();

    private OldBlockNestedLoop oldBlockNestedLoop;

    @Before
    public void setUp() {
        oldBlockNestedLoop = new OldBlockNestedLoop();
    }

    @Test
    public void returnTheSameRddUnsorted() {
        JavaPairRDD<OldPointFlag, Point2D> expectedRdd = sc.parallelizePairs(FlagPointPairsMock.getFlagPointPairsUnsorted());

        JavaPairRDD<OldPointFlag, Point2D> actualRdd = oldBlockNestedLoop.sortRdd(expectedRdd);

        assertThat(actualRdd).containsOnlyElementsOf(expectedRdd);
    }

    @Test
    public void isASkyline_willAddInTheSkylineList() {
        Point2D candidateGlobalSkylinePoint = new Point2D.Double(7594.778386003634, 2038.5448103420463);
        List<Point2D> expectedSkylines = getGlobalSkylines();
        expectedSkylines.add(candidateGlobalSkylinePoint);

        List<Point2D> actualSkylines = getGlobalSkylines();
        oldBlockNestedLoop.globalAddDiscardOrDominate(actualSkylines, candidateGlobalSkylinePoint);

        assertThat(actualSkylines).containsOnlyElementsOf(expectedSkylines);
    }

    @Test
    public void isNotASkyline_wontAddInTheSkylineList() {
        List<Point2D> expectedSkylines = getGlobalSkylines();
        Point2D candidateGlobalSkylinePoint = new Point2D.Double(5300.723604353442, 6586.544252547646);

        List<Point2D> actualSkylines = getGlobalSkylines();
        oldBlockNestedLoop.globalAddDiscardOrDominate(actualSkylines, candidateGlobalSkylinePoint);

        assertThat(actualSkylines).containsOnlyElementsOf(expectedSkylines);
    }

    @Test
    public void addInTheSkylineListAndRemoveOneElement() {
        Point2D pointToBeRemoved = new Point2D.Double(5413.326818883628, 4253.422322942394);
        Point2D candidateGlobalSkylinePoint = new Point2D.Double(5400.723604353442, 4100.426522944594);
        List<Point2D> expectedSkylines = getGlobalSkylines();
        expectedSkylines.remove(pointToBeRemoved);
        expectedSkylines.add(candidateGlobalSkylinePoint);

        List<Point2D> actualSkylines = getGlobalSkylines();
        oldBlockNestedLoop.globalAddDiscardOrDominate(actualSkylines, candidateGlobalSkylinePoint);

        assertThat(actualSkylines).containsOnlyElementsOf(expectedSkylines);
    }

    @Test
    public void addInTheSkylineListAndRemoveAll() {
        Point2D candidateGlobalSkylinePoint = new Point2D.Double(100.723604353442, 200.426522944594);
        List<Point2D> expectedSkylines = new ArrayList<>();
        expectedSkylines.add(candidateGlobalSkylinePoint);

        List<Point2D> actualSkylines = getGlobalSkylines();
        oldBlockNestedLoop.globalAddDiscardOrDominate(actualSkylines, candidateGlobalSkylinePoint);

        assertThat(actualSkylines).containsOnlyElementsOf(expectedSkylines);
    }

    private List<Point2D> getGlobalSkylines() {
        List<Point2D> globalSkylines = new ArrayList<>();
        globalSkylines.add(new Point2D.Double(5413.326818883628, 4253.422322942394));
        globalSkylines.add(new Point2D.Double(6543.712935561507, 3075.282119298148));
        globalSkylines.add(new Point2D.Double(5225.776905052095, 4418.640443578733));
        globalSkylines.add(new Point2D.Double(7032.0677945754105, 2556.957955903529));
        globalSkylines.add(new Point2D.Double(7816.246529527959, 1874.5766294965456));

        return globalSkylines;
    }

    @Test
    public void toString_returnName() {
        String expectedName = "OldBlockNestedLoop";

        String actualName = oldBlockNestedLoop.toString();

        assertThat(actualName).isEqualTo(expectedName);
    }
}

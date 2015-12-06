package com.github.dkanellis.skyspark.api.algorithms.templates;

import com.github.dkanellis.skyspark.api.algorithms.sparkimplementations.SkylineAlgorithm;
import com.github.dkanellis.skyspark.api.math.point.FlagPointPairProducer;
import com.github.dkanellis.skyspark.api.math.point.PointFlag;
import com.github.dkanellis.skyspark.api.math.point.PointUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BlockNestedLoopTemplate implements SkylineAlgorithm, Serializable {

    private FlagPointPairProducer flagPointPairProducer;

    @Override
    public final List<Point2D> getSkylinePoints(JavaRDD<Point2D> points) {
        flagPointPairProducer = createFlagPointPairProducer(points);

        JavaPairRDD<PointFlag, Iterable<Point2D>> localSkylinePointsByFlag = divide(points);
        JavaRDD<Point2D> skylinePoints = merge(localSkylinePointsByFlag);

        return skylinePoints.collect();
    }

    private FlagPointPairProducer createFlagPointPairProducer(JavaRDD<Point2D> points) {
        Point2D medianPoint = PointUtils.getMedianPointFromRDD(points);
        return new FlagPointPairProducer(medianPoint);
    }

    private JavaPairRDD<PointFlag, Iterable<Point2D>> divide(JavaRDD<Point2D> points) {
        JavaPairRDD<PointFlag, Point2D> flagPointPairs = points.mapToPair(p -> flagPointPairProducer.getFlagPointPair(p));
        JavaPairRDD<PointFlag, Iterable<Point2D>> pointsGroupedByFlag = flagPointPairs.groupByKey();
        JavaPairRDD<PointFlag, Iterable<Point2D>> flagsWithLocalSkylines
                = pointsGroupedByFlag.mapToPair(fp -> new Tuple2(fp._1(), getLocalSkylinesWithBnl(fp._2())));

        return flagsWithLocalSkylines;
    }

    private Iterable<Point2D> getLocalSkylinesWithBnl(Iterable<Point2D> pointIterable) {
        List<Point2D> localSkylines = new ArrayList<>();
        for (Point2D point : pointIterable) {
            localAddDiscardOrDominate(localSkylines, point);
        }
        return localSkylines;
    }

    protected void localAddDiscardOrDominate(List<Point2D> localSkylines, Point2D candidateSkylinePoint) {
        for (Iterator it = localSkylines.iterator(); it.hasNext(); ) {
            Point2D pointToCheckAgainst = (Point2D) it.next();
            if (PointUtils.dominates(pointToCheckAgainst, candidateSkylinePoint)) {
                return;
            } else if (PointUtils.dominates(candidateSkylinePoint, pointToCheckAgainst)) {
                it.remove();
            }
        }
        localSkylines.add(candidateSkylinePoint);
    }

    protected JavaRDD<Point2D> merge(JavaPairRDD<PointFlag, Iterable<Point2D>> localSkylinesGroupedByFlag) {
        JavaPairRDD<PointFlag, Point2D> ungroupedLocalSkylines = localSkylinesGroupedByFlag.flatMapValues(point -> point);
        JavaPairRDD<PointFlag, Point2D> sortedLocalSkylines = sortRDD(ungroupedLocalSkylines);

        JavaRDD<List<Tuple2<PointFlag, Point2D>>> groupedByTheSameId = groupByTheSameId(sortedLocalSkylines);
        JavaRDD<Point2D> globalSkylinePoints
                = groupedByTheSameId.flatMap(singleList -> getGlobalSkylineWithBNLAndPrecomparisson(singleList));

        return globalSkylinePoints;
    }

    protected abstract JavaPairRDD<PointFlag, Point2D> sortRDD(JavaPairRDD<PointFlag, Point2D> flagPointPairs);

    private JavaRDD<List<Tuple2<PointFlag, Point2D>>> groupByTheSameId(JavaPairRDD<PointFlag, Point2D> ungroupedLocalSkylines) {
        JavaPairRDD<PointFlag, Point2D> mergedInOnePartition = ungroupedLocalSkylines.coalesce(1);
        JavaRDD<List<Tuple2<PointFlag, Point2D>>> groupedByTheSameId = mergedInOnePartition.glom();
        return groupedByTheSameId;
    }

    private List<Point2D> getGlobalSkylineWithBNLAndPrecomparisson(List<Tuple2<PointFlag, Point2D>> flagPointPairs) {
        List<Point2D> globalSkylines = new ArrayList<>();
        for (Tuple2<PointFlag, Point2D> flagPointPair : flagPointPairs) {
            PointFlag flag = flagPointPair._1();
            if (!passesPreComparisson(flag)) {
                continue;
            }

            Point2D point = flagPointPair._2();
            globalAddDiscardOrDominate(globalSkylines, point);
        }
        return globalSkylines;
    }

    private boolean passesPreComparisson(PointFlag flagToCheck) {
        PointFlag rejectedFlag = new PointFlag(1, 1);
        return !flagToCheck.equals(rejectedFlag);
    }

    protected abstract void globalAddDiscardOrDominate(
            List<Point2D> globalSkylines, Point2D candidateGlobalSkylinePoint);
}

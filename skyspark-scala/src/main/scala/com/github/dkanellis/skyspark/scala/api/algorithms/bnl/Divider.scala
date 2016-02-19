package com.github.dkanellis.skyspark.scala.api.algorithms.bnl

import com.github.dkanellis.skyspark.scala.api.algorithms.Point
import org.apache.spark.rdd.RDD

object Divider {

  def divide(points: RDD[Point]) = {
    val numOfDimensions = points.first().size()
    val median = MedianFinder.getMedian(points, numOfDimensions)
    val flagProducer = new FlagProducer(median)

    val flagPoints = points.map(p => (flagProducer.calculateFlag(p), p))
    val groupedByFlag = flagPoints.groupByKey()

    groupedByFlag.flatMapValues(BnlAlgorithm.computeSkylinesWithoutPreComparison)
  }
}

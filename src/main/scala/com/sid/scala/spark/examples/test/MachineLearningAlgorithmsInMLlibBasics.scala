package com.sid.scala.spark.examples.test

import com.sid.scala.spark.common.GetSparkSession
import org.apache.spark.sql.functions._
object MachineLearningAlgorithmsInMLlibBasics {
  def printMe( ) : Unit = {
    val path="/FileStore/tables"
    val spark=GetSparkSession.getSession()
    val staticDataFrame = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(path+"/data/retail-data/by-day/*.csv")
    staticDataFrame.createOrReplaceTempView("retail_data")

    staticDataFrame.rdd.getNumPartitions

    val preppedDataFrame = staticDataFrame
      .na.fill(0)
      .withColumn("day_of_week", date_format(col("InvoiceDate"), "EEEE"))
      .coalesce(5)
    preppedDataFrame.rdd.getNumPartitions

    //preppedDataFrame.repartition(15)
    







  }
}

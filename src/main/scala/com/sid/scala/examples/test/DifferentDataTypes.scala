package com.sid.scala.examples.test

import com.sid.scala.common.GetSparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql._

object DifferentDataTypes {
  def printMe( ) : Unit = {
    val path="/FileStore/tables"
    val spark=GetSparkSession.getSession()
    val df = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(path+"/data/retail-data/by-day/2010_12_02-cddbc.csv")
    df.printSchema()
    df.createOrReplaceTempView("dfTable")
    df.select(lit(5), lit("five"), lit(5.0)).limit(5).show()

    df.withColumn("isExpensive", not(col("UnitPrice").leq(250)))
      .filter("isExpensive")
      .select("Description", "UnitPrice").show(5)
    df.withColumn("isExpensive", expr("NOT UnitPrice <= 250"))
      .filter("isExpensive")
      .select("Description", "UnitPrice").show(5)
  }
}

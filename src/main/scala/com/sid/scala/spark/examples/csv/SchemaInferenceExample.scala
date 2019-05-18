package com.sid.scala.spark.examples.csv

import com.sid.scala.spark.common.GetSparkSession
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.desc

object SchemaInferenceExample {
  def printMe( ) : Unit = {
    val spark=GetSparkSession.getSession()
    val flightData2015 = GetSparkSession.getSession()
      .read
      .option("inferSchema", "true")
      .option("header", "true")
      .csv("/user/sid/data/flight-data/csv/2015-summary.csv")

    val sc=flightData2015.schema

    println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n***-----------------------***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )
    flightData2015.show()
    println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n***-----------------------***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )
    val myArray: Array[Row] =flightData2015.take(5)
    println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n***-----------------------***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )
    flightData2015.sort("count").explain()
    println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n***-----------------------***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )
    flightData2015.createOrReplaceTempView("flight_data_2015")

    val sqlWay = GetSparkSession.getSession().sql("""
    SELECT DEST_COUNTRY_NAME, count(1)
    FROM flight_data_2015
    GROUP BY DEST_COUNTRY_NAME
    """)
    sqlWay.explain()
    val dataFrameWay = flightData2015.groupBy("DEST_COUNTRY_NAME").count()

    println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n***-----------------------***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )
    val maxSql = spark.sql("""
    SELECT DEST_COUNTRY_NAME, sum(count) as destination_total
    FROM flight_data_2015
    GROUP BY DEST_COUNTRY_NAME
    ORDER BY sum(count) DESC
    LIMIT 5
    """)

    maxSql.explain()


    val maxFrameWay=flightData2015.groupBy("DEST_COUNTRY_NAME").sum("count")
      .withColumnRenamed("sum(count)", "destination_total")
      .sort(desc("destination_total"))

    maxSql.show()
    maxFrameWay.explain()

  }
}

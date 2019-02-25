package com.sid.scala.fst
import com.sid.scala.common.StopSparkSession
import com.sid.scala.examples.csv.SchemaInferenceExample
import com.sid.scala.examples.test.RangeExample
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf


object Driver {

  def main(args : Array[String]) {
    println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n***Start***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )

    //RangeExample.rangeExample()
    //println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n***-----------------------***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )
    SchemaInferenceExample.printMe()

    StopSparkSession.stop()
    println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n***End***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )
  }
}

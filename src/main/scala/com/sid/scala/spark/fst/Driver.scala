package com.sid.scala.spark.fst

import com.sid.scala.spark.common.StopSparkSession
import com.sid.scala.spark.examples.csv.SchemaInferenceExample


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

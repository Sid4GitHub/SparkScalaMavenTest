package com.sid.scala.spark.examples.test

import com.sid.scala.spark.common.GetSparkSession

object RangeExample {
  def rangeExample(): Unit ={
    val spark=GetSparkSession.getSession()
    val myRange = spark.range(10,10000,5).toDF("number")
    myRange.show()
    //myRange.collect.foreach(println)
    val myRange2 = myRange.where("number % 2 = 0")
    val myRange3 = myRange2.where("number % 4 = 0")
    println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n*    * * ------ ---------------- -***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )
    //myRange3.show()
    println(myRange3.count())
  }
}

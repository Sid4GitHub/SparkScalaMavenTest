  package com.sid.scala.examples.csv

  import com.sid.scala.common.GetSparkSession

  object SchemaInferenceExample {
    def printMe( ) : Unit = {
      val flightData2015 = GetSparkSession.getSession()
        .read
        .option("inferSchema", "true")
        .option("header", "true")
        .csv("/user/p2991042/data/flight-data/csv/2015-summary.csv")
      println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n***-----------------------***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )
      println("()()()()()"+flightData2015.take(5))
      println( "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n***-----------------------***\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" )
      flightData2015.show()
    }
  }

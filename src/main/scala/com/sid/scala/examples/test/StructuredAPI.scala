package com.sid.scala.examples.test

import com.sid.scala.common.GetSparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql._

object StructuredAPI {
  def printMe( ) : Unit = {
    val path="/FileStore/tables"
    val spark=GetSparkSession.getSession()
    val myManualSchema = StructType(Array(
      StructField("DEST_COUNTRY_NAME", StringType, true),
      StructField("ORIGIN_COUNTRY_NAME", StringType, true),
      StructField("count", LongType, false)
    ))
    var dfManualSchema = spark.read.format("json").schema(myManualSchema).load(path+"/data/flight-data/json/*.json")
    dfManualSchema.printSchema()
    var dfManualSchemaDef=dfManualSchema.schema
    dfManualSchema.show(4)
    var cou=dfManualSchema.agg(max(col("count"))).head()
    dfManualSchema.col("count")
    //var cou=dfManualSchema.agg(max(expr("count"))).head()
    var x=dfManualSchema.selectExpr("(((count + 5) * 200) - 6) < count")
    var y=dfManualSchema.where((((col("count") + 5) * 200) - 6) < col("count"))

    x.show(3)
    y.show(3)

    val myRow = Row("Hello", null, 1, false)

    var q=myRow(0) // type Any
    var q1=myRow(0).asInstanceOf[String] // String
    var q2=myRow.getString(0) // String
    var q3=myRow.getInt(2) // Int

    val myManualSchema2 = new StructType(Array(
      new StructField("some", StringType, true),
      new StructField("col", StringType, true),
      new StructField("names", LongType, false)))
    val myRows2 = Seq(Row("Hello", null, 1L))
    val myRDD2 = spark.sparkContext.parallelize(myRows2)
    var par2=myRDD2.getNumPartitions
    val myDf2 = spark.createDataFrame(myRDD2, myManualSchema2)
    myDf2.show()


  }
}

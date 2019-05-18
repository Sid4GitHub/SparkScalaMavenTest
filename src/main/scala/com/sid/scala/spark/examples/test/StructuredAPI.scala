package com.sid.scala.spark.examples.test

import com.sid.scala.spark.common.GetSparkSession
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

    /*
    Only In Console
    val myDF3 = Seq(("Hello", 2, 1L)).toDF("col1", "col2", "col3")
    myDF3.show()*/

    val df = spark.read.format("json").load(path+"/data/flight-data/json/2015_summary-ebaee.json")

    df.select(
      df.col("DEST_COUNTRY_NAME"),
      col("DEST_COUNTRY_NAME"),
      column("DEST_COUNTRY_NAME"),
      //'DEST_COUNTRY_NAME,
      //$"DEST_COUNTRY_NAME",
      expr("DEST_COUNTRY_NAME"))
      .show(2)

    df.selectExpr(
      "*" // include all original columns
      ,"(DEST_COUNTRY_NAME = ORIGIN_COUNTRY_NAME) as withinCountry"
    ).show(2)

    df.withColumn("withinCountry", expr("ORIGIN_COUNTRY_NAME == DEST_COUNTRY_NAME"))
      .show(2)

    df.selectExpr("avg(count)", "count(distinct(DEST_COUNTRY_NAME))").show(2)

    df.select(expr("*"), lit(1).as("One")).show(2)

    df.withColumn("Destination", expr("DEST_COUNTRY_NAME")).columns

    df.withColumnRenamed("DEST_COUNTRY_NAME", "dest").show(2)

    df.withColumnRenamed("DEST_COUNTRY_NAME", "dest").show(2)

    val dfWithLongColName = df.withColumn(
      "This Long Column-Name",
      expr("ORIGIN_COUNTRY_NAME"))
    dfWithLongColName.show(2)

    dfWithLongColName.selectExpr(
      "`This Long Column-Name`",
      "`This Long Column-Name` as `new col`")
      .show(2)

    dfWithLongColName.createOrReplaceTempView("dfTableLong")

    var x1=dfWithLongColName.select(col("This Long Column-Name")).columns

    var y1=dfWithLongColName.select(expr("`This Long Column-Name`")).columns

    var x2=df.columns
    var y2=df.drop("ORIGIN_COUNTRY_NAME").columns

    df.withColumn("count2", col("count").cast("string")).schema

    df.filter(col("count") < 2).show(2)
    df.where("count < 2").show(2)

    df.select("ORIGIN_COUNTRY_NAME", "DEST_COUNTRY_NAME").distinct().count()
    df.select( "DEST_COUNTRY_NAME").distinct().count()
    df.select("ORIGIN_COUNTRY_NAME").distinct().count()

    df.rdd.getNumPartitions


  }
}

package com.sid.scala.examples.test

import com.sid.scala.common.GetSparkSession
import org.apache.spark.sql.functions._

object StructuredStreaming {
  def printMe( ) : Unit = {
    val path="/FileStore/tables"
    val spark=GetSparkSession.getSession()
    val staticDataFrame = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(path+"/data/retail-data/by-day/*.csv")
    staticDataFrame.createOrReplaceTempView("retail_data")
    val staticSchema = staticDataFrame.schema

    val countOfStaticDataFrame=staticDataFrame.count()


    var df1=staticDataFrame.where(col("UnitPrice")<0 || col("Quantity")<0)//spark.sql("select * from retail_data where UnitPrice<0 or Quantity<0 limit 5")
    df1.show()
    staticDataFrame
      .selectExpr(
        "CustomerId",
        "(UnitPrice * Quantity) as total_cost",
        "InvoiceDate")
      .groupBy(
        col("CustomerId"), window(col("InvoiceDate"), "1 day"))
      .sum("total_cost")
      .show(5)
    var isStream=staticDataFrame.isStreaming
    val streamingDataFrame = spark.readStream
      .schema(staticSchema)
      .option("maxFilesPerTrigger", 1)
      .format("csv")
      .option("header", "true")
      .load(path+"/data/retail-data/by-day/*.csv")
    isStream=streamingDataFrame.isStreaming


    val purchaseByCustomerPerHour = streamingDataFrame
      .selectExpr(
        "CustomerId",
        "(UnitPrice * Quantity) as total_cost",
        "InvoiceDate")
      .groupBy(
        col("CustomerId"), window(col("InvoiceDate"), "1 day"))
      .sum("total_cost")

    purchaseByCustomerPerHour.writeStream
      .format("memory") // memory = store in-memory table
      .queryName("customer_purchases") // the name of the in-memory table
      .outputMode("complete") // complete = all the counts should be in the table
      .start()

    spark.sql("""
    SELECT *
    FROM customer_purchases
    ORDER BY `sum(total_cost)` DESC
    """).show(5)

  }
}

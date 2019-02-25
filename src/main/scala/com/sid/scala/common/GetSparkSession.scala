package com.sid.scala.common

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object GetSparkSession {
  def getSession(): SparkSession ={
    val conf = new SparkConf().setAppName("Sid").setMaster("yarn")
    //val sc=new SparkContext(conf)

    val spark = SparkSession.builder()
      .config(conf)
      .getOrCreate()
    return spark
  }
}

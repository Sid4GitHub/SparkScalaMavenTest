package com.sid.scala.common

object StopSparkSession {
  def stop(): Unit ={
    GetSparkSession.getSession().stop()
  }
}

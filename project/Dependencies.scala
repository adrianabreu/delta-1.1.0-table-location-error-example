import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.11" 

  lazy val sparkCore = "org.apache.spark" %% "spark-core" % "3.2.1"
  lazy val sparkSQL = "org.apache.spark" %% "spark-sql" % "3.2.1"
  lazy val sparkHive = "org.apache.spark" %% "spark-hive" % "3.2.1"
  lazy val deltaLake = "io.delta" %% "delta-core" % "1.1.0"

  val spark = Seq(
    sparkCore % Provided,
    sparkSQL % Provided,
    sparkHive % Provided,
    deltaLake % Provided
  )
}

package example
import org.apache.spark.sql.SparkSession
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfter
import java.io.File

class HelloSpec extends AnyFlatSpec with Matchers with BeforeAndAfter {

  val spark = SparkSession
  .builder()
  .enableHiveSupport()
  .master("local")
  .appName("test-session")
  .getOrCreate()

  before {
    spark.sql("""DROP TABLE IF EXISTS bronze.users""")
    spark.sql("""DROP DATABASE IF EXISTS bronze""")
  }

  "The table path" should "be the specified one" in {
    spark.sql("""CREATE DATABASE IF NOT EXISTS bronze""")
    spark.sql("""CREATE TABLE bronze.users (
    |  name string,
    |  active boolean
    |)
    |USING delta
    |LOCATION 'spark-warehouse/users'""".stripMargin)

    val data = spark.sql("SHOW TABLE EXTENDED FROM bronze LIKE 'users'").select("information").first().getString(0).split("\n")

    val expectedLocation = "spark-warehouse/users"
    val actualLocation = data.filter(_.startsWith("Location:")).head.split("delta-1.1/")(1)

    assert(expectedLocation == actualLocation)
  }
}

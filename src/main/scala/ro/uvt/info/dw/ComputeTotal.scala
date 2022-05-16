package ro.uvt.info.dw

import com.datastax.spark.connector.{SomeColumns, _}
import org.apache.spark.{SparkConf, SparkContext}

object ComputeTotal {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("Data Warehouses - Compute Total")
    val sc = new SparkContext(conf)
    sc.cassandraTable("pop1", "time_series_data")
      .select("asset_id", "time_series_definition_id", "business_date_year",
        "business_date", "system_time", "values_double")
      .filter("QUANDL.WIKI" == _.getString("time_series_definition_id")).keyBy(row => (row.getString("asset_id"), row.getInt("business_date_year"))).map { case (key, value) => (key, 1) } // map phase
      .reduceByKey(_ + _) // reduce phase
      .map { case ((asset_id, business_date_year), cnt) => (asset_id,
        business_date_year, cnt)
      }
      .saveToCassandra("pop1", "totals", SomeColumns("asset_id",
        "business_date_year", "cnt"))
  }
}
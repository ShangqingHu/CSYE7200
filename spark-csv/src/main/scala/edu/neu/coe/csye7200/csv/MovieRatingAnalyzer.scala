package edu.neu.coe.csye7200.csv

import org.apache.spark.sql.{DataFrame, SparkSession, functions}
case class MovieRatingAnalyzer(resource: String) {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("MovieRating")
    .master("local[*]")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  def apply(resource: String): MovieRatingAnalyzer = new MovieRatingAnalyzer(resource)

  private val df: DataFrame = spark.read.format("csv").option("header", "true")
    .load(getClass.getResource(resource).getPath)
  df.show()

  def movieRatingMean(): Double = {
    val mean = df.select(functions.avg("imdb_score"))
    mean.show()
    mean.first().getDouble(0)
  }

  def movieRatingSD(): Double = {
    val sd = df.select(functions.stddev("imdb_score"))
    sd.show()
    sd.first().getDouble(0)
  }
}

object MovieRatingAnalyzer extends App {
  val movie = MovieRatingAnalyzer("/movie_metadata.csv")
  movie.movieRatingMean()
  movie.movieRatingSD()
}

package edu.neu.coe.csye7200.csv

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class MovieRatingAnalyzerTest extends AnyFlatSpec with Matchers {

  behavior of "movie rating statistics"
  it should "get movie rating mean and standard deviation" in {

    val movie = MovieRatingAnalyzer("/movie_metadata.csv")
    movie.movieRatingMean() shouldBe 6.453200745804848
    movie.movieRatingSD() shouldBe 0.9988071293753289
  }


}

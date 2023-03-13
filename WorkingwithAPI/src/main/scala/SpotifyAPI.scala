import scalaj.http.{Http, HttpResponse}
import net.liftweb.json._

object SpotifyAPI {
  case class Artist(id: String, name: String, followers: Int)

  def main(args: Array[String]): Unit = {
    val playlistId = "5Rrf7mqN8uus2AaQQQNdc1"
    val accessToken = "BQAt2MFlWkbL6IPurDk0Ip9-Xsr4INO4E-hrmlRS01C8rD8klyqIpks2bmGGrNnOeBMum3ELsj0N-a5CuB4bexVyl7Fz8cneRg0coC-If30c7JyuOc8Cd4BHGubWuZdRMV3iLsZ2jsdmIXTbfscM6Bk_HI-kk88kf6KELEKTVsT3RyqD6QoTDxX0JFjezPXcRBY-"

    val response: HttpResponse[String] = Http(s"https://api.spotify.com/v1/playlists/$playlistId")
      .header("Authorization", s"Bearer $accessToken")
      .asString

    val json: JValue = parse(response.body)

    val tracks = (json \ "tracks" \ "items").children
    val sortedTracks = tracks.sortBy(track => -(track \ "track" \ "duration_ms").asInstanceOf[JInt].num)
    val top10Tracks = sortedTracks.take(10)

    println("Top 10 longest tracks:")
    for ((track, index) <- top10Tracks.zipWithIndex) {
      val name = (track \ "track" \ "name").asInstanceOf[JString].s
      val duration = (track \ "track" \ "duration_ms").asInstanceOf[JInt].num
      println(s"${index + 1}. $name, $duration ms")
    }

    val artistIds = top10Tracks.flatMap(track =>
      (track \ "track" \ "artists").children.map(artist =>
        (artist \ "id").asInstanceOf[JString].s
      )
    )

    val artists = artistIds.map { artistId =>
      val artistResponse: HttpResponse[String] = Http(s"https://api.spotify.com/v1/artists/$artistId")
        .header("Authorization", s"Bearer $accessToken")
        .asString
      val artistJson: JValue = parse(artistResponse.body)
      val name = (artistJson \ "name").asInstanceOf[JString].s
      val followers = (artistJson \ "followers" \ "total").asInstanceOf[JInt].num.toInt
      Artist(artistId, name, followers)
    }

    println("Top artists by follower count:")
    artists.sortBy(_.followers).foreach(artist =>
      println(s"${artist.name}: ${artist.followers}")
    )
  }
}

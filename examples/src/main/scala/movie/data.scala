// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package examples
package movie

import scala.scalajs.js
import org.scalajs.dom.experimental._
import concurrent._
import js.JSConverters._
import concurrent.ExecutionContext.Implicits.global

@js.native
trait Movie extends js.Object {
  val id: String = js.native
  val title: String = js.native
  val overview: String = js.native
  val poster_path: String = js.native
}

@js.native
trait ConfigImages extends js.Object {
  val poster_sizes: js.Array[String] = js.native
  val secure_base_url: String = js.native
  val base_url: String = js.native
}

@js.native
trait Config extends js.Object {
  val images: ConfigImages = js.native
}


@js.native
trait Movies extends js.Object {
  val results: js.Array[Movie] = js.native
}

object data {
  val TMDB_API_PATH = "https://api.themoviedb.org/3"
  val TMDB_API_KEY = "762954999d09f9db6ffc6c0e6f37d509"

  def fetchConfig() =
    Fetch
      .fetch(s"$TMDB_API_PATH/configuration?api_key=$TMDB_API_KEY")
      .toFuture
      .flatMap { _.json().toFuture.map(_.asInstanceOf[Config]) }

  val config = RichSimpleCacheProvider(SimpleCacheProvider).createResource(fetchConfig())
  //val config = SimpleCacheProvider.createResource(fetchConfig())
  
  def searchMovies(query:String) =
    Fetch
      .fetch(s"$TMDB_API_PATH/search/movie?api_key=${TMDB_API_KEY}&query=${query}&include_adult=false")
      .toFuture
      .flatMap { _.json().toFuture.map(_.asInstanceOf[Movies]) }

  val movieSearchResults = RichSimpleCacheProvider(SimpleCacheProvider).createResource(searchMovies(_))

  def fetchMovie(id: String) =
    Fetch
      .fetch(s"$TMDB_API_PATH/movie/${id}?api_key=$TMDB_API_KEY")
      .toFuture
      .flatMap { _.json().toFuture.map(_.asInstanceOf[Movie]) }

  val movie = RichSimpleCacheProvider(SimpleCacheProvider).createResource(fetchMovie(_))

}

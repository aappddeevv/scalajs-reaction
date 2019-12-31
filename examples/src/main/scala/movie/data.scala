// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples
package movie

import scala.scalajs.js
import js.|
import org.scalajs.dom.experimental._
import concurrent._
import js.JSConverters._
//import js.Thenable.Implicits._
import concurrent.ExecutionContext.Implicits.global

// We are not consistent in UndefOr[]/nulls...lazy.
// @js.native
// trait Movie extends js.Object {
//   val id: Int                      = js.native
//   val title: js.UndefOr[String]    = js.native
//   val overview: js.UndefOr[String] = js.native
//   /* can be null */
//   val poster_path: js.UndefOr[String]    = js.native
//   val backdrop_path: js.UndefOr[String]  = js.native
//   val original_language: String          = js.native
//   val popularity: Double                 = js.native
//   val genre_ids: js.Array[Int]           = js.native
//   val adult: Boolean                     = js.native
//   val original_title: js.UndefOr[String] = js.native
//   val release_date: String               = js.native // YYYY-MM-DD
//   val video: Boolean                     = js.native
//   val vote_count: Int                    = js.native
// }

// /**
//   * _sizes have w<number>|"original" values.
//   */
// @js.native
// trait ConfigImages extends js.Object {
//   val poster_sizes: js.Array[String]  = js.native
//   val logo_sizes: js.Array[String]    = js.native
//   val profile_sizes: js.Array[String] = js.native
//   val still_sizes: js.Array[String]   = js.native
//   val secure_base_url: String         = js.native
//   val base_url: String                = js.native
// }

// @js.native
// trait Config extends js.Object {
//   val images: ConfigImages = js.native
// }

// @js.native
// trait Movies extends js.Object {
//   val page: js.UndefOr[Int]                = js.native
//   val total_pages: js.UndefOr[Int]         = js.native
//   val total_results: js.UndefOr[Int]       = js.native
//   val results: js.UndefOr[js.Array[Movie]] = js.native
// }

// object data {
//   // docs at https://www.themoviedb.org/documentation/api
//   val TMDB_API_PATH = "https://api.themoviedb.org/3"
//   val TMDB_API_KEY  = "762954999d09f9db6ffc6c0e6f37d509"

//   def fetchConfig(ckey: String) =
//     Fetch
//       .fetch(s"$TMDB_API_PATH/configuration?api_key=$TMDB_API_KEY")
//       .toFuture
//       .flatMap { _.json().toFuture.map(_.asInstanceOf[Config]) }

//   val config = createResource(fetchConfig(_))

//   def searchMovies(query: String) =
//     Fetch
//       .fetch(
//         s"$TMDB_API_PATH/search/movie?api_key=${TMDB_API_KEY}&query=${query}&include_adult=false")
//       .toFuture
//       .flatMap { _.json().toFuture.map(_.asInstanceOf[Movies]) }

//   val movieSearchResults = createResource[String, Movies](searchMovies(_))

//   /** The pure JS way. */
//   val searchMoviesJS: js.Function1[String, js.Thenable[Movies]] = (query: String) =>
//     Fetch
//       .fetch(
//         s"$TMDB_API_PATH/search/movie?api_key=${TMDB_API_KEY}&query=${query}&include_adult=false")
//       .`then`[Movies](
//         { (r: Response) =>
//           r.json().asInstanceOf[js.Thenable[Movies]]
//         },
//         js.undefined
//     )

//   val movieSearchResultsJS =
//     SimpleCacheProvider.createResource[String, Movies, String](searchMoviesJS, js.undefined)

//   def fetchMovie(id: String) =
//     Fetch
//       .fetch(s"$TMDB_API_PATH/movie/${id}?api_key=$TMDB_API_KEY")
//       .toFuture
//       .flatMap { _.json().toFuture.map(_.asInstanceOf[Movie]) }

//   val movie = createResource(fetchMovie(_))

// }

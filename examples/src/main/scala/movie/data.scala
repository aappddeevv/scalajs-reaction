/*
 * Copyright (c) 2018 The Trapelo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ttg
package examples
package movie

//       .fetch(s"$TMDB_API_PATH/movie/${id}?api_key=$TMDB_API_KEY")//   val title: js.UndefOr[String]    = js.native// trait ConfigImages extends js.Object {//   val popularity: Double                 = js.native

//   val images: ConfigImages = js.native// }

//import js.Thenable.Implicits._import org.scalajs.dom.experimental._

// We are not consistent in UndefOr[]/nulls...lazy.
// @js.native
// trait Movie extends js.Object {
//   val id: Int                      = js.native

//   val overview: js.UndefOr[String] = js.native
//   /* can be null */
//   val poster_path: js.UndefOr[String]    = js.native
//   val backdrop_path: js.UndefOr[String]  = js.native
//   val original_language: String          = js.native

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

//   val poster_sizes: js.Array[String]  = js.native
//   val logo_sizes: js.Array[String]    = js.native
//   val profile_sizes: js.Array[String] = js.native
//   val still_sizes: js.Array[String]   = js.native
//   val secure_base_url: String         = js.native
//   val base_url: String                = js.native
// }

// @js.native
// trait Config extends js.Object {



// @js.native
// trait Movies extends js.Object {

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

//       .toFuture
//       .flatMap { _.json().toFuture.map(_.asInstanceOf[Movie]) }

//   val movie = createResource(fetchMovie(_))

// }

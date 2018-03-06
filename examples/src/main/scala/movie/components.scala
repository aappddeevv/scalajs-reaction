// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package examples
package movie

import scala.scalajs.js
import org.scalajs.dom
import concurrent._
import concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.Thenable.Implicits._
import js.JSConverters._

import elements._
import implicits._
import vdom._
import tags._
import context._ // new react context
import SimpleCacheProvider._
import fabric._
import fabric.styling._

object Image {
  private def loadImage(src: String) = {
    val image = new dom.html.Image {}
    val p = Promise[String] // scala Promise which is like "js Deferred"
    image.onload = js.Any.toFunction1(e => p.success(src))
    image.src = src
    p.future
  }

  private val readImage = RichSimpleCacheProvider(SimpleCacheProvider).createResource(loadImage(_))
  //private val readImage = SimpleCacheProvider.createResource(loadImage(_))

  private def makeImage(cache: Cache, src: String, props: Option[ImgProps] = None) = {
    val s: String = src // inference was bad, found js.UndefOr[String] for some reason
    val moreProps = new ImgProps { src = readImage.read(cache, s) }
    img(props.map(merge(_, moreProps)).getOrElse[ImgProps](moreProps))()
  }

  def make(src: String, props: Option[ImgProps] = None) =
    SimpleCache.makeConsumer(makeImage(_, src, props))
}

object Header {
  val header = statelessComponent("Header")
  import header.ops._
  def make() =
    render{ _ => "Movie Search" }
}

object PosterThumbnail {
  val poster = statelessComponent("PosterThumbnail")
  import poster.ops._

  def make(src: String) =
    render{ self =>
        TimeoutWithFallback.make(0)(
          "Loading",
          Image.make(src)
        )
    }
}

object FullPoster {
  val poster = statelessComponent("FullPoster")
  import poster.ops._

  def make(cache: Cache, movie: Movie) =
    render{ self => 
        val path = Option(movie.poster_path)
        if(path.isEmpty) null
        else {
          val config = data.config.read(cache, ())
          val size = config.images.poster_sizes(2)
          val baseURL =
            if(dom.document.location.protocol == "https:") config.images.secure_base_url
            else config.images.base_url
          val w = size.replace(" ", "").toDouble
          val src = s"$baseURL/$size/${movie.poster_path}"
          Timeout.make(2000){ _ => Image.make(src, Some(new ImgProps{ width = w}))}
        }}
}

object Results {
  val results = statelessComponent("Results")
  import results.ops._

  def make(cache: Cache, query: Option[String], onActiveResultUpdate: Unit, activeResult: Option[Movie]=None) =
    render{ self =>
        val results = data.movieSearchResults.read(cache, query.getOrElse[String]("")).results
        if(query.isEmpty) "Search for something"
        else {
          results.take(5).map(m =>
            div(new DivProps{ key = m.id })(Result.make(cache, m, true)))
        }
    }
}

object Result {
  val result = statelessComponent("Result")
  import result.ops._

  def make(cache: Cache, result: Movie, isActive: Boolean) =
    render { self =>
        val config = data.config.read(cache, ())
        val size = config.images.poster_sizes(0)
        val baseURL =
          if(dom.document.location.protocol == "https:") config.images.secure_base_url
          else config.images.base_url
        val w = size.replace(" ", "").toDouble
        val h = w / 27.0 * 40.0
        val cn = getClassNames(w, h, isActive)
        button(new ButtonProps{ className=cn.button})(
          div(new DivProps{ className = cn.content})(
            PosterThumbnail.make(src=s"${baseURL}/size/${result.poster_path}"),
            h2(new HProps{className = cn.header})(result.title)
          ))
      }

  @js.native
  private trait ResultClassNames extends js.Object {
    val button: String = js.native
    val content: String = js.native
    val thumbnail: String = js.native
    val header: String = js.native
  }

  private def _getClassNames(w: Double, h: Double, isActive: Boolean = false) =
    FabricStyling.mergeStyleSets[ResultClassNames](
      styleset(
        "button" -> new IRawStyle {
          background = "transparent"
          textAlign = "start"
          display = "flex"
          width = "auto"
          outline = "none"
          border = "1px solid rgba(0,0,0,0.2)"
          cursor = "pointer"
          padding = 0
          selectors = selectorset(
          )
        },
        "content" -> new IRawStyle {
          display = "flex"
          flexGrow = "1"
          position = "relative"
        },
        "thumbnail" -> new IRawStyle {
          width = w
          height = h
        },
        "header" -> new IRawStyle {
          fontSize = 16
        }
      ))

  private def getClassNames = fabric.UtilitiesNS.memoizeFunction(js.Any.fromFunction3(_getClassNames))
}

object Search {
  val search = statelessComponent("Search")
  import search.ops._

  def make(query: Option[String] = None, onQueryUpdate: Option[String] => Unit) =
    render{ self =>
        input(new InputProps {
          // wrap in Option to help detect nulls
          onChange = js.defined(e => onQueryUpdate(toSafeOption(e.target.value)))
          value = query.getOrElse[String]("")
        })()
      }
}

object MovieInfo {
  val info = statelessComponent("MovieInfo")
  import info.ops._

  def make(cache: Cache, movie: Movie) =
    render{ self => 
        val fullResult = data.movie.read(cache, movie.id)
        fragmentElement()(
          FullPoster.make(cache, movie),
          h2(movie.title),
          div(movie.overview)
        )
      }
}

object Details {
  val details = statelessComponent("Details")
  import details.ops._

  def make(cache: Cache, result: Movie) =
    render{ self => 
        fragmentElement()(
          div(button("Back")),
           MovieInfo.make(cache, result)
        )
      }
}

object MasterDetail {
  val c = ttg.react.elements.statelessComponent("MasterDetail")
  import c.ops._

  def make(header: ReactNode, search: ReactNode, results: ReactNode,
    details: ReactNode, showDetails: Boolean) =
    render{ self => 
        val cn = getClassNames(showDetails)
        div(new DivProps{className = cn.root})(
          div(header),
          div(new DivProps { className = cn.content})(
            div(new DivProps{className= cn.search})(search),
            div(new DivProps{ className = cn.results})(results),
            div(new DivProps{className = cn.details})(details)
          ))
      }

  @js.native
  private trait MasterDetailClassNames extends js.Object {
    val root: String = js.native
    val content: String = js.native
    val results: String = js.native
    val search: String = js.native
    val details: String = js.native
  }

  private def _getClassNames(showDetails: Boolean = false) =
    FabricStyling.mergeStyleSets[MasterDetailClassNames](
      styleset(
        "root" -> new IRawStyle {
          margin = "0 auto"
          width= 500
          height = "100vh"
          display = "grid"
          gridTemplateRows = "min-content auto"
        },
        "content" -> new IRawStyle {
          width = 1000
          position = "relative"
          display = "grid"
          gridTemplateColumns = "1fr 1fr"
          gridTemplateRows = "36px auto"
          gridTemplateAreas = """search details
results details"""
          transition = "transform 350ms ease-in-out"
          overflow = "hidden"
        },
        "search" -> new IRawStyle {
          gridArea = "search"
        },
        "results" -> new IRawStyle {
          gridArea = "results"
          overflow = "auto"
        },
        "details" -> new IRawStyle {
          gridArea = "details"
          overflow = "auto"
        }
      )
    )

  private val getClassNames = fabric.UtilitiesNS.memoizeFunction(js.Any.fromFunction1(_getClassNames))

}

object MoviesImpl {
  sealed trait Action
  case class UpdateQuery(query: Option[String] = None) extends Action
  case class ActiveResult(activeResult: Option[Movie] = None) extends Action
  case object Clear extends Action

  case class State(query: Option[String] = None, activeResult: Option[Movie] = None)
  val defaultStateValue = State()

  val movies = reducerComponent[State, Action]("Movies")
  import movies.ops._

  val AsyncValue = new AsyncValueTemplate[State] { }

  def makeMoviesImpl(cache: Cache) =
    movies.copy(new methods {
      val initialState = _ => defaultStateValue

      val reducer = (action, state, gen) => {
        action match {
          case UpdateQuery(qopt) => gen.update(state.copy(query = qopt))
          case ActiveResult(r) => gen.update(state.copy(activeResult = r))
          case Clear => gen.update(state.copy(activeResult = None))
          case _ => gen.skip
        }
      }

      val render = self => {
        div(new DivProps{})(
          AsyncValue.make(self.state, defaultStateValue) { asyncState =>
            div(new DivProps { className = "blah" })(
              MasterDetail.make(
                header = Header.make(),
                search = div(Search.make(self.state.query, chg => self.send(UpdateQuery(chg)))),
                results = Results.make(cache, self.state.query, (), self.state.activeResult),
                details =
                  if(asyncState.activeResult.isDefined)
                    Details.make(cache, asyncState.activeResult.get)
                  else
                    null,
                showDetails = asyncState.activeResult.isDefined
              ))
          })
      }
    })


  def make() =
    SimpleCache.makeConsumer(makeMoviesImpl(_))

}


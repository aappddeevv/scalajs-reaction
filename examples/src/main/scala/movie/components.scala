// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package examples
package movie

import scala.scalajs.js
import js.Dynamic.{literal => lit}
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
import fabric.UtilitiesNS.memoizeFunction

object Image {

  private def loadImage(src: String) = {
    // js "deferred" vs a js Promise
    val p = concurrent.Promise[String]()
    // use an image for the load and prime the browser cache
    // the image el is only used to let us know when the load completes
    // not sure why this does *not* work
    // val image = new dom.html.Image() {
    //   onload = _ => p success src
    //   src = src
    // }
    val image = dom.document.createElement("img").asInstanceOf[dom.html.Image]
    image.onload = _ => p success src
    image.src = src
    p.future
  }

  private val readImage = createResource[String, String](loadImage(_))

  private def makeImage(cache: Cache, src: String, props: Option[ImgProps] = None) = {
    val s: String = src // inference was bad, found js.UndefOr[String] for some reason
    val moreProps = new ImgProps { src = readImage(cache, s) }
    img(props.map(merge(_, moreProps)).getOrElse[ImgProps](moreProps))()
  }

  def make(src: String, props: Option[ImgProps] = None) =
    SimpleCache.makeConsumer(makeImage(_, src, props))
}

object Header {
  val header = statelessComponent("Header")
  import header.ops._
  def make() =
    render { _ =>
      div(new DivProps { style = lit("display" -> "block") })(
        div("Movie Search (powered by TMDb)"),
        div(a(new AProps {
          href = "https://codesandbox.io/s/5zk7x551vk"
          target = "_blank"
        })("javascript version")))
    }
}

object Search {
  val search = statelessComponent("Search")
  import search.ops._

  def make(query: Option[String] = None, onQueryUpdate: Option[String] => Unit) =
    render { self =>
      input(new InputProps {
        // wrap in Option to help detect nulls
        onChange = js.defined(e => onQueryUpdate(toSafeOption(e.target.value)))
        value = query.getOrElse[String]("")
      })()
    }
}

object PosterThumbnail {
  val poster = statelessComponent("PosterThumbnail")
  import poster.ops._

  def make(src: String) =
    render { self =>
      TimeoutWithFallback.make(1000)(
        "thumbnail placeolder",
        Image.make(src)
      )
    }
}

object FullPoster {
  val poster = statelessComponent("FullPoster")
  import poster.ops._

  def make(cache: Cache, movie: Movie) =
    render { self =>
      val path = toSafeOption(movie.poster_path)
      if (path.isEmpty) null
      else {
        val config = data.config(cache, configKey)
        val size   = config.images.poster_sizes(2)
        val baseURL =
          if (dom.document.location.protocol == "https:") config.images.secure_base_url
          else config.images.base_url
        val w   = size.replace("w", "").toDouble
        val src = s"$baseURL/$size/${movie.poster_path}"
        TimeoutWithFallback.make(3000)(
          "full poster placeholder",
          Image.make(src, Some(new ImgProps { width = w }))
        )
      }
    }
}

object Results {
  val results = statelessComponent("Results")
  import results.ops._

  def make(
      cache: Cache,
      query: Option[String],
      onActiveResultUpdate: Option[Movie] => Unit,
      activeResult: Option[Movie] = None) =
    render { self =>
      println(s"results.make $query")
      query match {
        case Some(qstr) =>
          // this causes an error at root el when the promise is thrown! why?
          val results = data.movieSearchResults(cache, cleanQueryString(qstr))
          val arr     = results.results.getOrElse(js.Array())
          //val arr = Seq[Movie]()
          //"blah"
          js.Dynamic.global.console.log("results", arr)
          div(new DivProps { className = cn.root })(
            arr
              .take(5)
              .map(
                m =>
                  div(new DivProps { key = m.id.toString() })(
                    Result.make(cache,
                                m,
                                onActiveResultUpdate,
                                activeResult.map(_.id == m.id).getOrElse(false)))))
        case _ => "Search for something"
      }
    }

  @js.native
  private trait ResultsClassNames extends js.Object {
    val root: String = js.native
  }

  // we could just use inline styles or "mergeStyles" vs "mergeStyleSets"
  private def getClassNames() =
    FabricStyling.mergeStyleSets[ResultsClassNames](
      styleset(
        "root" -> new IRawStyle {
          display = "flex"
          flexDirection = "column"
        }
      ))

  private val cn = getClassNames()
}

object Result {
  val result = statelessComponent("Result")
  import result.ops._

  def make(
      cache: Cache,
      result: Movie,
      onActiveResultUpdate: Option[Movie] => Unit,
      isActive: Boolean) =
    render { self =>
      val config = data.config(cache, configKey)
      val size   = config.images.poster_sizes(0)
      val baseURL =
        if (dom.document.location.protocol == "https:") config.images.secure_base_url
        else config.images.base_url
      val w  = size.replace("w", "").toDouble
      val h  = w / 27.0 * 40.0
      val cn = getClassNames(w, h, isActive)
      button(new ButtonProps {
        className = cn.button;
        onClick = js.defined(_ => onActiveResultUpdate(Some(result)))
      })(div(new DivProps { className = cn.content })(
        div(new DivProps { className = cn.thumbnail })(result.poster_path.toNonNullOption
          .map(p => PosterThumbnail.make(src = s"${baseURL}/$size/${p}"))
          .getOrElse(null)),
        //div(new DivProps { className = cn.thumbnail})("thumbnail"),
        h2(new HProps { className = cn.header })(result.title.getOrElse[String]("untitled"))
      ))
    }

  @js.native
  private trait ResultClassNames extends js.Object {
    val button: String    = js.native
    val content: String   = js.native
    val thumbnail: String = js.native
    val header: String    = js.native
  }

  private def _getClassNames(w: Double, h: Double, isActive: Boolean = false) =
    FabricStyling.mergeStyleSets[ResultClassNames](
      styleset(
        "button" -> stylearray(
          new IRawStyle {
            background = "transparent"
            textAlign = "start"
            display = "flex"
            width = "auto"
            outline = "none"
            border = "1px solid rgba(0,0,0,0.2)"
            cursor = "pointer"
            padding = 0
            selectors = selectorset(
              ":hover" -> new IRawStyle { background = "lightgray" },
              ":focus" -> new IRawStyle { background = "lightblue" }
            )
          },
          if (isActive) new IRawStyle {
            background = "blue"
            selectors = selectorset(
              ":focus" -> new IRawStyle { background = "blue" }
            )
          }
          else null
        ),
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

  private def getClassNames = memoizeFunction(js.Any.fromFunction3(_getClassNames))
}

object MovieInfo {
  val info = statelessComponent("MovieInfo")
  import info.ops._

  def make(cache: Cache, movie: Movie) =
    render { self =>
      val fullResult = data.movie(cache, movie.id.toString())
      fragmentElement()(
        FullPoster.make(cache, movie),
        h2(movie.title.getOrElse[String]("untitled")),
        div(movie.overview.getOrElse(null))
      )
    }
}

object Details {
  val details = statelessComponent("Details")
  import details.ops._

  def make(cache: Cache, result: Movie, clearActiveResult: () => Unit) =
    render { self =>
      fragmentElement()(
        div(button(new ButtonProps { onClick = js.defined(_ => clearActiveResult()) })("Back")),
        MovieInfo.make(cache, result)
      )
    }
}

object MasterDetail {
  val c = ttg.react.elements.statelessComponent("MasterDetail")
  import c.ops._

  def make(
      header: ReactNode,
      search: ReactNode,
      results: ReactNode,
      details: ReactNode,
      showDetails: Boolean) =
    render { self =>
      val cn = getClassNames(showDetails)
      div(new DivProps { className = cn.root })(
        div(new DivProps { className = cn.header  })(header),
        div(new DivProps { className = cn.content })(
          div(new DivProps { className = cn.search  })(search),
          div(new DivProps { className = cn.results })(results),
          div(new DivProps { className = cn.details })(details)
        )
      )
    }

  @js.native
  private trait MasterDetailClassNames extends js.Object {
    val root: String    = js.native
    val content: String = js.native
    val header: String  = js.native
    val results: String = js.native
    val search: String  = js.native
    val details: String = js.native
  }

  private def _getClassNames(showDetails: Boolean = false) =
    FabricStyling.mergeStyleSets[MasterDetailClassNames](
      styleset(
        "root" -> new IRawStyle {
          margin = "0 auto"
          width = 500
          overflow = "hidden"
          height = "100vh"
          display = "grid"
          gridTemplateRows = "min-content auto"
        },
        "content" -> stylearray(
          new IRawStyle {
            width = 1000
            position = "relative"
            display = "grid"
            gridTemplateColumns = "1fr 1fr"
            gridTemplateRows = "36px auto"
            gridTemplateAreas = "'search details'" +
              "'results details'"
            transition = "transform 350ms ease-in-out"
            transform = "translateX(0%)"
            overflow = "hidden"
          },
          if (showDetails) lit("transform" -> "translateX(-50%)")
          else null
        ),
        "header" -> new IRawStyle {
          marginBottom = 5
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

  private val getClassNames = memoizeFunction(js.Any.fromFunction1(_getClassNames))

}

object MoviesImpl {
  sealed trait Action
  case class UpdateQuery(query: Option[String] = None)        extends Action
  case class ActiveResult(activeResult: Option[Movie] = None) extends Action
  case object Clear                                           extends Action
  case object Error                                           extends Action

  case class State(
      query: Option[String] = None,
      activeResult: Option[Movie] = None,
      error: Boolean = false)
  val defaultStateValue = State()

  val movies = reducerComponent[State, Action]("Movies")
  import movies.ops._

  val AsyncValue = new AsyncValueTemplate[State] {}

  def makeMoviesImpl(cache: Cache) =
    movies.copy(new methods {
      val initialState = _ => defaultStateValue

      val reducer = (action, state, gen) => {
        action match {
          case UpdateQuery(qopt) => gen.update(state.copy(query = qopt))
          case ActiveResult(r)   => gen.update(state.copy(activeResult = r))
          case Clear             => gen.update(state.copy(activeResult = None))
          case Error             => gen.update(state.copy(error = true))
        }
      }

      didCatch = js.defined { (self, err, einfo) =>
        println("MovieImpl.didCatch:")
        js.Dynamic.global.console.log(err, einfo)
        self.send(Error)
      }

      val render = self => {
        TimeoutWithFallback.make(2000)(
          "Movies What?",
          if (self.state.error) "Error encountered, see dev console. Refresh browser tab."
          else
            AsyncValue.make(self.state, defaultStateValue) {
              asyncState =>
                MasterDetail.make(
                  header = Header.make(),
                  search = div(Search.make(self.state.query, chg => self.send(UpdateQuery(chg)))),
                  results = Results.make(cache,
                                         self.state.query,
                                         ar => self.send(ActiveResult(ar)),
                                         self.state.activeResult),
                  details =
                    //if(asyncState.activeResult.isDefined)
                    if (self.state.activeResult.isDefined)
                      //Details.make(cache, asyncState.activeResult.get, () => self.send(Clear))
                      Details.make(cache, self.state.activeResult.get, () => self.send(Clear))
                    else
                      null,
                  //showDetails = asyncState.activeResult.isDefined
                  showDetails = self.state.activeResult.isDefined
                )
            }
        )
      }
    })

  def make() =
    SimpleCache.makeConsumer(makeMoviesImpl(_))

}

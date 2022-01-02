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
package atmoache

// A copy of the app at: http//github.com/gladimdim/atmoache although greatly
// changed in almost every way :-)

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import js.JSConverters._
import js.annotation._
import org.scalajs.dom
import react.*
import react.syntax.*
import react.conversions.given
import vdom._
import fabric._
import fabric.components._
import util.control._
import jshelpers.syntax.any.*

object atmoacheNS extends js.Object

object CStyles {
 @js.native
 @JSImport("Examples/atmoache/styles.css", JSImport.Namespace)
 val styles: js.Object with js.Dynamic = js.native
}
import CStyles._

// Text input box and button. Click or ENTER to get weather.
// We don't use a form though.
object Controls {
  type State = Option[String]

  val Name = "Controls"

  trait Props extends js.Object {
    var onCitySet: Option[String] => Unit
    var cityName: Option[String]
  }

  // props version
  def apply(props: Props) = render.elementWith(props)

  // arglist version
  def apply(
    onCitySet_ : Option[String] => Unit,
    cityName_ : Option[String]
  ) =
    render.elementWith(new Props {
      var onCitySet = onCitySet_
      var cityName  = cityName_
    })

  val render: ReactFC[Props] = props => {
    val (state, setState) = useStateStrictDirect[State](None)
    div(new DivProps {
      className = styles.controls.asUndefString
    })(
      TextField(new TextField.Props {
        placeholder = "City Name (e.g. Boston, New York, or Los Angeles,US. See openweather.org)"
        autoFocus = true
        // We just need change events but not track each change
        defaultValue = props.cityName.getOrElse[String]("")
        onKeyPress = js.defined(e => if (e.which == dom.ext.KeyCode.Enter) props.onCitySet(state))
        onChange = TextField.OnChangeInput((_, v: js.UndefOr[String]) => setState(v.toOption))
      }),
      Button.Primary(new Button.Props {
        text = "Get Weather Summary"
        disabled = state.map(_.size == 0).getOrElse[Boolean](true)
        onClick = js.defined(_ => props.onCitySet(state))
      })()
    )
  }
  render.displayName(Name)
}

object DailyWeatherSummary {
  val Name = "DailyWeatherSummary"

  def convertCondition(apiCondition: String): String =
    // I am mxing labels and descriptions, need to fix
    apiCondition.toLowerCase() match {
      case "shower rain" | "rain"                                                  => "rainy"
      case "snow"                                                                  => "snowy"
      case "clouds" | "mist" | "broken clouds" | "scattered clouds" | "few clouds" => "cloudy"
      case "thunderstorm"                                                          => "stormy"
      case "clear"                                                                 => "sunny"
      case _                                                                       => "sunny"
    }

  val layout     = new StyleAttr   {}
  val labelProps = new Label.Props {}

  trait Props extends js.Object {
    var summary: Daily
    var key: String
  }

  def apply(props: Props)        = render.elementWith(props)
  def apply(s: Daily, k: String) = render.elementWith(new Props { var summary = s; var key = k })
  
  val render: ReactFC[Props] = argx => {
    val cond = argx.summary.facets.headOption.map(f => convertCondition(f.label)).orUndefined
    val props = new ReactWeatherDisplayProps {
      temperature = argx.summary.temp
      currentTemperature = argx.summary.temp
      width = 150
      height = 150
      condition = cond
      currentCondition = cond
    }
    div(new DivProps {
      className = "daily"
      style = layout
    })(
      Label(labelProps)(argx.summary.dateStr),
      ReactWeatherDisplay(props)
    )
    }
  render.displayName(Name)
}

object app {

  case class State(
    cityName: Option[String] = None,
    // result of the fetch
    weather: WeatherList = emptyWeatherList,
    // whether the fetch failed
    failed: Boolean = false,
    // error message if failure
    errorMessage: Option[String] = None
  )

  sealed trait Action
  case class WeatherLoaded(p: WeatherList)                                   extends Action
  case class FailedToGetCity(errorMessage: String, cityName: Option[String]) extends Action
  case class UpdateCity(cityName: Option[String])                            extends Action

  val Name = "PressureApp"

  def renderError(
    message: Option[String] = None
  ) =
    div(new DivProps {})(
      s"""Cannot find weather forecast, the error is: ${message
        .getOrElse[String]("<no message>")}"""
    )

  def renderResults(
    weather: WeatherList
  ) =
    div(new DivProps {
      style = new StyleAttr {
        display = "flex"
        flexWrap = "wrap"
      }
    })(
      weather.zipWithIndex.map {
        case (p, i) =>
          DailyWeatherSummary(p, i.toString())
      }
    )

  def apply() = createElement(render, null)

  val render: ReactFC0 = () => {
    val (state, dispatch) = useReducer[State, Action](
      (s, a) =>
        a match {
          case UpdateCity(cityNameOpt) =>
            s.copy(cityName = cityNameOpt)
          case WeatherLoaded(daily) =>
            s.copy(weather = daily, failed = false, errorMessage = None)
          case FailedToGetCity(emsg, cname) =>
            State(failed = true, errorMessage = Some(emsg), cityName = cname)
        },
      State()
    )

    useEffect(unsafeDeps(state.cityName)) { () =>
      state.cityName match {
        case Some(name) =>
          dao
            .fetch(name)
            .map {
              _ match {
                case Left(e)  => dispatch(FailedToGetCity(e, state.cityName))
                case Right(r) => dispatch(WeatherLoaded(r))
              }
            }
            .recover {
              case NonFatal(e) =>
                dispatch(FailedToGetCity(e.getMessage().nn, state.cityName))
            }
        case None => dispatch(WeatherLoaded(emptyWeatherList))
      }
      ()
    }

    Fragment(
      Controls(newCity => dispatch(UpdateCity(newCity)), state.cityName),
      state.errorMessage.fold(
        renderResults(state.weather)
      )(
        _ => renderError(state.errorMessage)
      )
    )
  }
  render.displayName(Name)
}

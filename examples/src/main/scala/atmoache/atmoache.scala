// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package examples
package atmoache

// A copy of the app at: http//github.com/gladimdim/atmoache although greatly
// changed in almost every way :-)

import scala.scalajs.js
import js.JSConverters._
import js.annotation._
import org.scalajs.dom
import ttg.react._
import React.Fragment
import implicits._

import ttg.react.vdom._
import tags._
import fabric._
import fabric.components._

import concurrent.ExecutionContext.Implicits.global
import util.control._

@js.native
@JSImport("Examples/atmoache/styles.css", JSImport.Namespace)
object atmoacheNS extends js.Object

object CStyles {
  val styles = atmoacheNS.asInstanceOf[js.Dynamic]
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
  def apply(props: Props) = sfc(props)

  // arglist version
  def apply(
    onCitySet_ : Option[String] => Unit,
    cityName_ : Option[String]
  ) = sfc(new Props {
    var onCitySet = onCitySet_
    var cityName = cityName_
  })

  val sfc = SFC1[Props] { props =>
    React.useDebugValue("Controls")
    val (state, setState) = React.useStateStrictDirect[State](None)
    div(new DivProps {
      className = styles.controls.asString
    })(
      TextField(new TextField.Props {
        placeholder =
          "City Name (e.g. Boston, New York, or Los Angeles,US. See openweather.org)"
        autoFocus = true
        // We just need change events but not track each change
        defaultValue = props.cityName.getOrElse[String]("")
        onKeyPress =
          js.defined(e =>
            if (e.which == dom.ext.KeyCode.Enter) props.onCitySet(state))
        onChangeInput = js.defined((_, v:String) => setState(Option(v)))
      })(),
      Button.Primary(new Button.Props {
        text = "Get Weather Summary"
        disabled = state.map(_.size == 0).getOrElse[Boolean](true)
        onClick = js.defined(_ => props.onCitySet(state))
      })()
    )
  }
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

  val layout = new StyleAttr {}
  val labelProps = new Label.Props {}

  trait Props extends js.Object {
    var summary: Daily
    var key: String
  }

  def apply(props: Props) = sfc(props)
  def apply(s: Daily, k: String) = sfc(new Props { var summary = s; var key = k })

  val sfc = SFC1[Props] { arg =>
    val cond = arg.summary.facets.headOption.map(f =>
      convertCondition(f.label)).orUndefined
    val props = new ReactWeatherDisplayProps {
      temperature = arg.summary.temp
      currentTemperature = arg.summary.temp
      width = 150
      height = 150
      condition = cond
      currentCondition = cond
    }
    div(new DivProps {
      className = "daily"
      style = layout
    })(
      Label(labelProps)(arg.summary.dateStr),
      ReactWeatherDisplay(props)
    )
  }
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
  ) = {
    div(new DivProps {})(
      s"""Cannot find weather forecast, the error is: ${message
          .getOrElse[String]("<no message>")}"""
    )
  }

  def renderResults(
    weather: WeatherList,
  ) = {
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
  }

  def apply() = sfc

  val sfc = SFC0 {
    React.useDebugValue(Name)
    val (state, dispatch) = React.useReducer[State,Action](
      (s,a) => a match {
        case UpdateCity(cityNameOpt) =>
          s.copy(cityName = cityNameOpt)
        case WeatherLoaded(daily) =>
          s.copy(weather = daily, failed = false, errorMessage = None)
        case FailedToGetCity(emsg, cname) =>
          State(failed = true, errorMessage = Some(emsg), cityName = cname)
      },
      State()
    )

    React.useEffect(state.cityName){() =>
      state.cityName match {
        case Some(name) =>
          dao.fetch(name)
            .map {
              _ match {
                case Left(e)  => dispatch(FailedToGetCity(e, state.cityName))
                case Right(r) => dispatch(WeatherLoaded(r))
              }
            }
            .recover {
              case NonFatal(e) =>
                dispatch(FailedToGetCity(e.getMessage(), state.cityName))
            }
        case None => dispatch(WeatherLoaded(emptyWeatherList))
      }
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
}

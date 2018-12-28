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
import elements._
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

object Controls {
  type State = Option[String]
  sealed trait Action
  case class Changed(hasText: Option[String]) extends Action

  val c = reducerComponent[State, Action]("Controls")
  import c.ops._

  def make(onCitySet: Option[String] => Unit, cityName: Option[String]) =
    c.copy(new methods {
      val initialState = _ => None
      val reducer = (action, state, gen) => {
        action match {
          case Changed(nopt) => gen.update(nopt)
          case _             => gen.skip
        }
      }
      val render = self => {
        div(new DivProps {
          className = styles.controls.asString
        })(
          TextField(new TextField.Props {
            placeholder =
              "City Name (e.g. Boston, New York, or Los Angeles,US. See openweather.org)"
            autoFocus = true
            // We don't need to set the value ourselves, just have it available
            // for onCitySet, so we just need change events.
            defaultValue = cityName.getOrElse[String]("")
            onKeyPress =
              js.defined(e => if (e.which == dom.ext.KeyCode.Enter) onCitySet(self.state))
            onChangeInput = js.defined((_, v:String) => self.send(Changed(Option(v))))
          })(),
          Button.Primary(new Button.Props {
            text = "Get Weather Summary"
            disabled = self.state.map(_.size == 0).getOrElse[Boolean](true)
            onClick = js.defined(_ => onCitySet(self.state))
          })()
        )
      }
    })
}

object DailyWeatherSummary {

  val c = statelessComponent("DailyWeatherSummary")
  import c.ops._

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

  def make(summary: Daily) =
    render { self =>
      val cond = summary.facets.headOption.map(f => convertCondition(f.label)).orUndefined
      val props = new ReactWeatherDisplayProps {
        temperature = summary.temp
        currentTemperature = summary.temp
        width = 150
        height = 150
        condition = cond
        currentCondition = cond
      }
      div(new DivProps {
        className = "daily"
        style = layout
      })(
        Label(labelProps)(summary.dateStr),
        ReactWeatherDisplay.make(props)
      )
    }
}

object app {
  case class State(
      cityName: Option[String] = None,
      weather: WeatherList = emptyWeatherList,
      failed: Boolean = false,
      errorMessage: Option[String] = None
  )

  sealed trait Action
  case class WeatherLoaded(p: WeatherList)                                   extends Action
  case class FailedToGetCity(errorMessage: String, cityName: Option[String]) extends Action
  case class UpdateCity(cityName: Option[String])                            extends Action
  case object LoadPressure                                                   extends Action

  val c = reducerComponent[State, Action]("PressureApp")
  import c.ops._

  def renderMessage(self: Self) = {
    div(
      Controls.make(newCity => self.send(UpdateCity(newCity)), self.state.cityName),
      div(new DivProps {})(
        s"""Cannot find weather forecast, the error is: ${self.state.errorMessage
          .getOrElse[String]("<no message>")}"""
      )
    )
  }

  def renderResults(self: Self) = {
    div(
      Controls.make(newCity => self.send(UpdateCity(newCity)), self.state.cityName),
      div(new DivProps {
        style = new StyleAttr {
          display = "flex"
          flexWrap = "wrap"
        }
      })(
        self.state.weather.zipWithIndex.map {
          case (p, i) =>
            createElement(DailyWeatherSummary.make(p), key = Some(i.toString()))
        }
      )
    )
  }

  def make() =
    c.copy(new methods {
      val initialState = _ => State()

      val reducer = (action, state, gen) =>
        action match {
          case UpdateCity(cityNameOpt) =>
            gen.updateAndEffect(state.copy(cityName = cityNameOpt)){self =>
              self.send(LoadPressure)
            }

          case WeatherLoaded(daily) =>
            gen.update(state.copy(weather = daily, failed = false, errorMessage = None))

          case LoadPressure if (state.cityName.isDefined) =>
            import dom.experimental._
            gen.effect {
              self =>
                self.state.cityName.fold(self.send(FailedToGetCity("No city name provided", None)))(
                  cname =>
                    dao
                      .fetch(cname)
                      .map {
                        _ match {
                          case Left(e)  => self.send(FailedToGetCity(e, self.state.cityName))
                          case Right(r) => self.send(WeatherLoaded(r))
                        }
                      }
                      .recover {
                        case NonFatal(e) =>
                          self.send(FailedToGetCity(e.getMessage(), self.state.cityName))
                    }
                )
            }

          case FailedToGetCity(emsg, cname) =>
            gen.update(State(failed = true, errorMessage = Some(emsg), cityName = cname))

          case _ => gen.skip
      }

      val render = self => {
        self.state.errorMessage.fold(renderResults(self))(_ => renderMessage(self))
      }
    })
}

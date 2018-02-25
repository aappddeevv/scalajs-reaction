// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package examples
package atmoache

import scala.scalajs.js
import org.scalajs.dom.experimental._
import concurrent._
import concurrent.ExecutionContext.Implicits.global
import util.control.NonFatal

@js.native
trait Main extends js.Object {
  val pressure: Float   = js.native
  val temp: Float       = js.native
  val temp_min: Float   = js.native
  val temp_max: Float   = js.native
  val sealevel: Float   = js.native
  val grnd_level: Float = js.native
  val humidity: Float   = js.native
  val temp_kf: Float    = js.native
}

@js.native
trait WeatherSummary extends js.Object {
  val id: String          = js.native
  val main: String        = js.native
  val description: String = js.native
  val icon: String        = js.native
}

@js.native
trait Wind extends js.Object {
  val speed: Float = js.native
  val deg: Float   = js.native
}

@js.native
trait WeatherItem extends js.Object {
  val dt: Double                        = js.native
  val dt_txt: String                    = js.native
  val main: Main                        = js.native
  val weather: js.Array[WeatherSummary] = js.native
  val wind: Wind                        = js.native
}

@js.native
trait WeatherResponse extends js.Object {
  val cod: String                 = js.native // code?
  val cnt: Int                    = js.native
  val list: js.Array[WeatherItem] = js.native
}

object dao {

  type Result = Either[String, WeatherList]

  // cache url
  val cache = collection.mutable.HashMap[String, WeatherList]()

  def fetch(
      cityName: String,
      ndays: Int = 7,
      units: Option[String] = Some("imperial")): Future[Result] = {
    val url =
      s"""https://api.openweathermap.org/data/2.5/forecast?q=${cityName}&units=${units.getOrElse(
        "imperial")}&appid=4b80108eceb046e99b71d8018873bb0b"""
    if (cache.contains(url))
      Future.successful(Right(cache(url)))
    else
      Fetch
        .fetch(url)
        .toFuture
        .recoverWith {
          // Communicaton or other external-to-weather-server error, exception => failed
          case NonFatal(e) => Future.failed(new Exception(s"Fetch failed: ${e.getMessage()}"))
        }
        .flatMap { r =>
          js.Dynamic.global.console.log("response", r)
          if (r.status == 200)
            r.json()
              .toFuture
              .recover {
                case NonFatal(e) =>
                  Left(s"Unable to convert body json to results: ${e.getMessage()}")
              }
              .map { body =>
                val list =
                  body.asInstanceOf[WeatherResponse].list.map { witem =>
                    Daily(
                      witem.dt.toLong,
                      witem.dt_txt,
                      witem.main.temp,
                      witem.main.temp_min,
                      witem.main.temp_max,
                      witem.main.humidity,
                      witem.main.pressure,
                      witem.weather.map(ws => Facet(ws.main, ws.description))
                    )
                  }
                cache.put(url, list) // add to cache
                Right(list)
              }
          else if (r.status == 404)
            Future.successful(Left(s"City not found."))
          else
            Future.successful(Left(s"Unexpected return status ${r.status}."))
        }
  }
}

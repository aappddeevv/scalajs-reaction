
package ttg
package examples

import scala.scalajs.js

package object atmoache {

  case class Facet(label: String, description: String)

  case class Daily(
      date: Long,
      dateStr: String,
      temp: Float,
      temp_low: Float,
      temp_high: Float,
      humidity: Float,
      pressure: Float,
      facets: Seq[Facet] = Seq())

  type WeatherList = Seq[Daily]
  val emptyWeatherList: WeatherList = Seq()
}

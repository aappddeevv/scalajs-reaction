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

import scala.scalajs.js
import js.|
import js.annotation._
import org.scalajs.dom
import js.JSConverters._
import org.scalajs.dom

package object plotlyjs {
  type Root = String | dom.html.Element
  type Color = String | Int
  type ColorScaleItem = js.Tuple2[Double, String] // e.g. String => js.Array(0.2, "rgb(245,195,157)")
  type Datum = Double | Int | String | js.Date | Null
  type AxisRange = js.Date | String | Double

  /** Create a datum array. */
  def datumArray(args: Datum*) = args.toJSArray

  /** List of Datums */
  type DatumArray = js.Array[Datum]

  val emptyDatumArray: DatumArray = js.Array()

  /** Helper to create traces array. */
  def traces(traces: Trace*) = traces.toJSArray

  @js.native
  @JSImport("plotly.js", "Plots")
  object Plots extends js.Object {
    def resize(root: Root): Unit = js.native
  }
}

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
package addressmanager

import scala.scalajs.js

import js.Dynamic.literal
import js.JSConverters._
import js.annotation.JSImport

import _root_.react._

@js.native
@JSImport("JSExamples/AddressSummary", JSImport.Namespace)
private object AddressSummaryNS extends js.Object {
  val AddressSummary: ReactJSComponent = js.native
}

object AddressSummary {
  def apply(className: Option[String] = None, address: Option[Address] = None) = {
    val props = literal(
      "className" -> className.orUndefined,
      "address"   -> address.orUndefined
    )
    createElement0(AddressSummaryNS.AddressSummary, props)
  }
}

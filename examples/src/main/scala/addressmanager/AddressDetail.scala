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
import js.Dynamic.{ literal => lit }
import js.JSConverters._
import js.annotation._
import js.|
import org.scalajs.dom
import react._
import react.implicits._
import vdom._
import fabric._
import fabric.components._
import ReactContentLoaderComponents._
import react_redux._
import styles._

/** The detail display is driven by the parent and *not* by the global redux
 * state although it could be hooked directly up to redux.
 */
object AddressDetail {

    import examples.Contexts._

  val Name = "AddressDetail"

  trait Props extends js.Object {
    val address: Option[Address]
  }

  def apply(address_ : Option[Address]) = 
    render.elementWith(new Props { val address = address_ })

  val render: ReactFC[Props] = props => {
    //val log = React.useContext[ConsoleLog](Contexts.logContext)
    //log(props.address.getOrElse("<no detail address provided>"))
    div(new DivProps { className = amstyles.detail.asString })(
      Label(s"""Name: ${props.address.flatMap(_.name.toOption).getOrElse("")}"""),
      Label(s"""City: ${props.address.flatMap(_.city.toOption).getOrElse("")}"""),
      Label(s"""State/Province: ${props.address
        .flatMap(_.stateorprovince.toOption)
        .getOrElse("")}"""),
      Label(s"""Zipcode: ${props.address.flatMap(_.postalcode.toOption).getOrElse("")}"""),
      Label(s"""Country: ${props.address.flatMap(_.country.toOption).getOrElse("")}""")
    )
  }
  render.displayName(Name)
}

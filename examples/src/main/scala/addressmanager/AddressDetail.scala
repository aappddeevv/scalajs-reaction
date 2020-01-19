
package ttg
package examples
package addressmanager

import scala.scalajs.js
import js.Dynamic.{literal => lit}
import js.annotation._
import js.JSConverters._
import js.|

import org.scalajs.dom
import _root_.react._
import implicits._
import react_redux._
import vdom._
import vdom.tags._
import fabric._
import fabric.components._

import ReactContentLoaderComponents._

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

  def apply(address_ : Option[Address]) = sfc(new Props{ val address = address_ })

  val sfc = SFC1[Props]{ props =>
    useDebugValue(Name)
    //val log = React.useContext[ConsoleLog](Contexts.logContext)
    //log(props.address.getOrElse("<no detail address provided>"))
    div(new DivProps { className = amstyles.detail.asString })(
      Label(s"""Name: ${props.address.flatMap(_.name.toOption).getOrElse("")}"""),
      Label(s"""City: ${props.address.flatMap(_.city.toOption).getOrElse("")}"""),
      Label(s"""State/Province: ${props.address
                  .flatMap(_.stateorprovince.toOption)
                  .getOrElse("")}"""),
      Label(s"""Zipcode: ${props.address.flatMap(_.postalcode.toOption).getOrElse("")}"""),
      Label(s"""Country: ${props.address.flatMap(_.country.toOption).getOrElse("")}"""),
    )
  }
}

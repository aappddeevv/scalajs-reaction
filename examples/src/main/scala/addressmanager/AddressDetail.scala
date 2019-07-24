// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package examples
package addressmanager

import scala.scalajs.js
import js.Dynamic.{literal => lit}
import js.annotation._
import js.JSConverters._
import js.|

import org.scalajs.dom
import ttg.react
import ttg.react._
import elements._
import reactdom._
import react.implicits._
import redux._
import react.redux
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
  import context._

  val Name = "AddressDetail"

  trait Props extends js.Object {
    val address: Option[Address]
  }

  def apply(address_ : Option[Address]) = sfc(new Props{ val address = address_ })

  val sfc = SFC1[Props]{ props =>
    React.useDebugValue(Name)
    //val log = React.useContext[ConsoleLog](Contexts.logContext)
    //log(props.address.getOrElse("<no detail address provided>"))
    div(new DivProps { className = amstyles.detail.asString })(
      Label()(s"""Name: ${props.address.flatMap(_.name.toOption).getOrElse("")}"""),
      Label()(s"""City: ${props.address.flatMap(_.city.toOption).getOrElse("")}"""),
      Label()(s"""State/Province: ${props.address
                  .flatMap(_.stateorprovince.toOption)
                  .getOrElse("")}"""),
      Label()(s"""Zipcode: ${props.address.flatMap(_.postalcode.toOption).getOrElse("")}"""),
      Label()(s"""Country: ${props.address.flatMap(_.country.toOption).getOrElse("")}"""),
    )
  }
}

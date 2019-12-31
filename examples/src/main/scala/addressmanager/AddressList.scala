// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

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

object AddressList {
  import IColumn.toCol

  val icolumns = js.Array[js.Dynamic](
    lit("key" -> "name", "name" -> "Name", "fieldName" -> "name", "minWidth"              -> 150),
    lit("key" -> "id", "name"   -> "Id", "fieldName"   -> "customeraddressid", "minWidth" -> 150),
  )

  val Name = "AddressList"

  trait Props extends js.Object {
    var sel: ISelection[Address]
    var addresses: AddressList
    // This does not properly signal deselect without additional state
    // so use this for just notifying that a change occurred.
    //var activeChanged: js.UndefOr[Address] => Unit
    var ifx: Option[Int]
    var shimmer: Boolean
  }

  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props]{ props =>
    val listopts = new Details.Shimmered.Props[Address] {
      items = props.addresses.toJSArray
      className = amstyles.list.asString
      selectionPreservedOnEmptyClick = true
      columns = icolumns
      getKey = getAddressKey
      //initialFocusedIndex = 2 //ifx.orUndefined
      selection = props.sel
      layoutMode = Details.List.LayoutMode.fixedColumns
      constrainMode = Details.List.ConstrainMode.unconstrained
      // onRenderDetailsHeader = js.defined { (props, defaultRender) =>
      //   Sticky()(defaultRender.fold[ReactNode]("...render me...")(_(props)))
      // }
      onShouldVirtualize = js.defined(_ => false)
      //enableShimmer = props.shimmer
      //shimmerLines = 5
    }
    div.merge(lit("data-is-scrollable" -> true))(new DivProps {
      className = amstyles.master.asString
    })(
      ScrollablePane()(Details.Shimmered[Address](listopts)())
    )
  }
}

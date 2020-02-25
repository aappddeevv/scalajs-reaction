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

import implicits._

import vdom._
import vdom.tags._

import fabric._
import fabric.components._

import ReactContentLoaderComponents._
import react_redux._
import styles._

object AddressList {import IColumn.toCol

  val icolumns = js.Array[js.Dynamic](
    lit("key" -> "name", "name" -> "Name", "fieldName" -> "name", "minWidth"              -> 150),
    lit("key" -> "id", "name"   -> "Id", "fieldName"   -> "customeraddressid", "minWidth" -> 150)
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

  val sfc = SFC1[Props] { props =>
    val listopts = new Details.Shimmered.Props[Address] {
      items = props.addresses.toJSArray
      className = amstyles.list.asString
      selectionPreservedOnEmptyClick = true
      columns = icolumns
      getKey = getAddressKey
      //initialFocusedIndex = 2 //ifx.orUndefined
      selection = props.sel
      layoutMode = Details.LayoutMode.fixedColumns
      constrainMode = Details.ConstrainMode.unconstrained
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
      ScrollablePane()(Details.Shimmered[Address](listopts))
    )
  }
}

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
import js.JSConverters.*
import js.annotation.*
import org.scalajs.dom
import react.*
import react.syntax.*
import jshelpers.syntax.*
import vdom.*
import fabric.*
import fabric.components.*
import ReactContentLoaderComponents.*
import react_redux.*
import styles.*

object AddressList:
  
  val icolumns = js.Array[IColumn](
    new IColumn {
        val key = "name"
        val name = "Name"
        fieldName = "name"
        val minWidth = 150
    },
    new IColumn {
        val key = "id"
        val name = "Id"
        fieldName = "customeraddressid"
        val minWidth = 150
    }
  )

  val Name = "AddressList"

  val shouldVirtualize: js.Function1[js.Object, Boolean] = _ => false
  
  trait Props extends js.Object {
    var sel: ISelection[Address]
    var addresses: AddressList
    var ifx: Option[Int]
    var shimmer: Boolean
  }
  
  def apply(props: Props) = render.elementWith(props)

  val render: ReactFC[Props] = props => {
    val listopts = new Details.Shimmered.Props[Address] {
      val items = props.addresses.toJSArray
      className = amstyles.list
      selectionPreservedOnEmptyClick = true
      columns = icolumns
      getKey = getAddressKeyJS
      //initialFocusedIndex = 2 //ifx.orUndefined
      selection = props.sel
      layoutMode = Details.LayoutMode.fixedColumns
      constrainMode = Details.ConstrainMode.unconstrained
      // onRenderDetailsHeader = js.defined { (props, defaultRender) =>
      //   Sticky()(defaultRender.fold[ReactNode]("...render me...")(_(props)))
      // }
      // if constant function, define outside the component, don't use useCallback
      onShouldVirtualize = shouldVirtualize
    }
    div.merge(lit("data-is-scrollable" -> true))(new DivProps {
      className = amstyles.master
    })(
      ScrollablePane(Details.Shimmered[Address](listopts))
    )
  }
  render.displayName(Name)


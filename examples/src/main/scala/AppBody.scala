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

import scala.scalajs.js

import js.Dynamic.{ literal => lit, global => g }
import js.JSConverters._
import js.annotation._
import js.|

import org.scalajs.dom

import react._

import implicits._

import vdom._
import vdom.tags._

import fabric._
import fabric.merge_styles._
import fabric.styling._

object AppBody {

  trait Props extends js.Object {
    var rootClassName: js.UndefOr[String]                              = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    val nav: ReactNode
    val content: ReactNode
  }

  val Name                = "AppBody"
  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props] { props =>
    React.useDebugValue(Name)
    val cn = getClassNames(new StyleProps {
      className = props.rootClassName
    }, props.styles)
    divWithClassname(
      cn.root,
      divWithClassname(cn.nav, props.nav),
      divWithClassname(cn.content, props.content)
    )
  }

  @js.native
  trait ClassNames extends IClassNamesTag {
    val root: String    = js.native
    val nav: String     = js.native
    val content: String = js.native
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle]    = js.undefined
    var nav: js.UndefOr[IStyle]     = js.undefined
    var content: js.UndefOr[IStyle] = js.undefined
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
  }

  val getStyles = stylingFunction[StyleProps, Styles] { props =>
    new Styles {
      root = stylearray(
        "ttg-AppBody",
        new IRawStyle {
          display = "flex"
          flexWrap = "nowrap"
          alignItems = "stretch"
          height = "calc(100% - 48px)" // subtract off header height
        },
        props.className
      )
      nav = stylearray("ttg-AppBody-nav", new IRawStyle {
        flex = "0 0 auto"
      })
      content = stylearray("ttg-AppBody-content", new IRawStyle {
        flex = "1 1 auto"
        overflowY = "auto"
      })
    }
  }

  // this causes an error, not sure why
  // val getClassNames1 =
  //   getClassNamesFunction[StyleProps, Styles, ClassNames](
  //     (p, s) => mergeStyleSets(concatStyleSetsWithProps[StyleProps, ClassNames](p, getStyles, s)
  //     ))

  import merge_styles._
  val getClassNames: GetClassNamesFn[StyleProps, Styles, ClassNames] =
    (p, s) => mergeStyleSets(concatStyleSetsWithProps(p, getStyles, s))

}

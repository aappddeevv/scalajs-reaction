// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package examples

import scala.scalajs.js
import js.|
import js.annotation._
import js.Dynamic.{literal => lit, global => g}
import js.JSConverters._

import org.scalajs.dom
import ttg.react
import react._
import react.elements._
import react.reactdom._
import react.implicits._

import vdom._
import vdom.tags._

import fabric.styling._
import fabric.merge_styles._

object AppBody {

  trait Props extends js.Object {
    var rootClassName: js.UndefOr[String] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    val nav: ReactNode
    val content: ReactNode
  }

  val Name = "AppBody"
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
    val root: String = js.native
    val nav: String = js.native
    val content: String = js.native
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var nav: js.UndefOr[IStyle] = js.undefined
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

  // this causes an error
  val getClassNames1 =
    getClassNamesFunction[StyleProps, Styles, ClassNames](
      (p, s) => mergeStyleSets(concatStyleSetsWithProps[StyleProps, ClassNames](p, getStyles, s)
      ))

  val getClassNames:
      GetClassNamesFn[StyleProps, Styles, ClassNames] =
    (p, s) => mergeStyleSets(concatStyleSetsWithProps[StyleProps, ClassNames](p, getStyles, s))

}

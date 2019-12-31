// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package jss

import scala.scalajs.js
import js.Dynamic.{literal => jsobj}
import js.annotation._
import react._
import react.syntax.jsobject._

/** Return a useStyles() hook. The return value is a js function to call with
 * prop arguments.
 */
@js.native
@JSImport("react-jss", "createUseStyles")
object createUseStyles extends js.Object {
  def apply[P, CN<:ClassNamesTag](styles: RuleTag): js.Function1[P, CN] = js.native
}

/** Return a useStyles() hook. The return value is a js function to call with no
 * arguments.
 */
@js.native
@JSImport("react-jss", "createUseStyles")
object createUseStyles0 extends js.Object {
  def apply[CN <: ClassNamesTag](styles: RuleTag): js.Function0[CN] = js.native
}

@js.native
trait Theme extends js.Object {
  // ???
}

@js.native
@JSImport("theming", JSImport.Namespace)
object theming extends js.Object {
  val ThemeProvider: ReactJsComponent = js.native
  val ThemeContext: ReactContext[_root_.jss.ThemeProvider.Props] = js.native
  def useTheme(): Theme = js.native
}

object ThemeProvider {

  trait Props extends js.Object {
    var theme: js.UndefOr[Theme] = js.undefined
    val children: ReactNode
  }

  def apply(props: Props = null)(child: ReactNode) =
    sfc(props.combineDynamic(jsobj("children" -> child)))

  // will children be pickeup in props correctly anyway? not sure...
  val sfc = SFC1[Props] { props =>
    React.createElement(theming.ThemeProvider, props)(props.children)
  }
}

@js.native
@JSImport("react-jss", JSImport.Namespace)
object ReactJSS extends js.Object {
  // same as theming.useTheme
  def useTheme(): Theme = js.native
}

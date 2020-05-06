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

package jss

import scala.scalajs.js

import js.Dynamic.{ literal => jsobj }
import js.annotation._

import react._
import react.implicits._

/** Return a useStyles() hook. The return value is a js function to call with
 * prop arguments.
 */
@js.native
@JSImport("react-jss", "createUseStyles")
object createUseStyles extends js.Object {
  def apply[P, CN <: ClassNamesTag](styles: RuleTag): js.Function1[P, CN] = js.native
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
  val ThemeProvider: ReactJSComponent                            = js.native
  val ThemeContext: ReactContext[_root_.jss.ThemeProvider.Props] = js.native
  def useTheme(): Theme                                          = js.native
}

object ThemeProvider {

  trait Props extends js.Object {
    var theme: js.UndefOr[Theme] = js.undefined
    val children: ReactNode
  }

  def apply(props: Props)(child: ReactNode) =
    render.elementWith(props.combineDynamic(jsobj("children" -> child)))

  // will children be pickeup in props correctly anyway? not sure...
  val render: Props => ReactNode = props => {
    createElement0(theming.ThemeProvider, props)
  }
}

@js.native
@JSImport("react-jss", JSImport.Namespace)
object ReactJSS extends js.Object {
  // same as theming.useTheme
  def useTheme(): Theme = js.native
}

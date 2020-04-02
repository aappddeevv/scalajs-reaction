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

package mui

import scala.scalajs.js

import js.annotation._

import react._

import jss._

@js.native
@JSImport("@material-ui/styles/ThemeProvider", JSImport.Namespace)
object Theme extends js.Object {
  @JSName("ThemeContext")
  val Context: ReactContext[js.Dynamic] = js.native
}

/** The styles context. The StylesProvider module exports also exports
 * as a default the StylesProvider.
 */
@js.native
@JSImport("@material-ui/styles/StylesProvider", "StylesContext")
object StylesContext extends ReactContext[StylesProvider.Props]

@js.native
@JSImport("@material-ui/styles", JSImport.Namespace)
object styles extends js.Object {
  val StylesProvider: ReactJsComponent = js.native
  val ThemeProvider: ReactJsComponent  = js.native
  def jssPreset(): JssOptions          = js.native
  // more...
}

object StylesProvider {

  trait Props extends js.Object {
    var disableGeneration: js.UndefOr[Boolean] = js.undefined
    var injectFirst: js.UndefOr[Boolean]       = js.undefined
    @JSName("jss")
    var _jss: js.UndefOr[JSS] = js.undefined
    // generateClassName...
  }

  // not being null causes an error, why?
  def apply(props: Props = null)(child: ReactNode) =
    createElementN(styles.StylesProvider, props)(child)
}

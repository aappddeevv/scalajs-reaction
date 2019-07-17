// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package mui

import scala.scalajs.js
import js.|
import js.annotation._
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
  val ThemeProvider: ReactJsComponent = js.native
  def jssPreset(): JssOptions = js.native
  // more...
}

object StylesProvider {

  trait Props extends js.Object {
    var disableGeneration: js.UndefOr[Boolean] = js.undefined
    var injectFirst: js.UndefOr[Boolean] = js.undefined
    var jss: js.UndefOr[JSS] = js.undefined
    // generateClassName...
  }

  // not being null causes an error, why?
  def apply(props: Props = null)(child: ReactNode) =
    React.createElement(styles.StylesProvider, props)(child)
}

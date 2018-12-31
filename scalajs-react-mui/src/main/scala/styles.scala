// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package mui

import scala.scalajs.js
import js.|
import js.annotation._

import ttg.react

// is this efficient in all usage scenarios?
/**
 * {{{
 * import js.Dynamic.literal
 * @js.native
 * trait ClassNames extends ClassNamesTag { val root: String }
 * trait Styles extends StyleSetTag { val root: js.Object }
 * val Styled = StyleMaker[Styles, ClassNames](new Styles {
 *    root = literal("background" -> "blue")
 * })
 * ...
 * Styled { msr => ... msr.classes.root ... }
 * }}}
 */
case class makeStyled[S <: StyleSetTag, T <: ClassNamesTag](sobj: S) {
  val c = createStyled(sobj)
  def apply(thunk: MakeStylesResult[T] => ReactNode) =
    react.elements.wrapJsForScala(c, null, js.Any.fromFunction1(thunk).asInstanceOf[ReactNode])
}

@js.native
trait MakeStylesResult[T <: js.Object] extends js.Object {
  val classes: T = js.native
  val theme: js.UndefOr[js.Dynamic] = js.native
}

// NOT usng core/styles! so must use new styles package
@js.native
@JSImport("@material-ui/styles/install", JSImport.Default)
object install extends js.Object {
  def apply(): Unit = js.native
}

/**
 * Not sure that createStyled will still part of @material-ui/styles: so do we
 * always need `npm install --save material-ui-render-props-styles`?
 */
@js.native
//@JSImport("material-ui-render-props-style", JSImport.Default)
@JSImport("@material-ui/styles/createStyled", JSImport.Default)
object createStyled extends js.Object {
  def apply(sobj: js.Object|js.Dynamic): ReactJsComponent = js.native
}

@js.native
@JSImport("@material-ui/styles/ThemeProvider", JSImport.Namespace)
object Theme extends js.Object {
  @JSName("ThemeContext")
  val ThemeContext: ReactContext[js.Dynamic] = js.native
  @JSName("default") // does this work reliable?
  val ThemeProvider: ReactJsComponent = js.native
}

@js.native
@JSImport("@material-ui/styles/StylesProvider", JSImport.Namespace)
object Styles extends js.Object {
  @JSName("StylesContext")
  val Context: ReactContext[js.Dynamic] = js.native
  @JSName("default") // does this work reliably
  val Provider: ReactJsComponent = js.native
}

object StylesProvider {
  import ttg.react.elements.wrapJsForScala

  trait Props extends js.Object {
    var jss: js.UndefOr[JSS] = js.undefined
    // disableGeneration
    // generateClassNames
    // sheetsCache
    // sheetsManager
    // sheetsRegistry
  }

  def apply(props: Props = null)(child: ReactNode) =
    wrapJsForScala(Styles.Provider, props, child)
}

trait JssOptions extends js.Object {
  var plugins: js.UndefOr[js.Array[js.Any]] = js.undefined
  var virtual: js.UndefOr[String] = js.undefined
  var insertionPoint: js.UndefOr[String] = js.undefined
  //var createGenerateId: js.UndefOr[] = js.undefined
}

@js.native
@JSImport("@material-ui/styles/jssPreset", JSImport.Default)
object muiJssPreset extends js.Object {
  def apply(): JssOptions = js.native
}

@js.native
trait JSS extends js.Object {
  // ???
}

@js.native
@JSImport("jss", JSImport.Namespace)
object JSS extends js.Object {
  def create(config: JssOptions): JSS = js.native
}

@js.native
@JSImport("jss-plugin-cache", JSImport.Default)
object JssPluginCache extends js.Object {
  def apply(): js.Any = js.native
}

@js.native
@JSImport("jss-plugin-rule-value-function", JSImport.Default)
object JssPluginRuleValueFunction extends js.Object {
  def apply(): js.Any = js.native
}

@js.native
@JSImport("jss-plugin-global", JSImport.Default)
object JssPluginGlobal extends js.Object {
  def apply(): js.Any = js.native
}

@js.native
@JSImport("jss-plugin-extend", JSImport.Default)
object JssPluginExtend extends js.Object {
  def apply(): js.Any = js.native
}

@js.native
@JSImport("jss-plugin-nested", JSImport.Default)
object JssPluginNested extends js.Object {
  def apply(): js.Any = js.native
}

@js.native
@JSImport("jss-plugin-compose", JSImport.Default)
object JssPluginCompose extends js.Object {
  def apply(): js.Any = js.native
}

@js.native
@JSImport("jss-plugin-camel-case", JSImport.Default)
object JssPluginCamelCase extends js.Object {
  def apply(): js.Any = js.native
}

@js.native
@JSImport("jss-plugin-default-unit", JSImport.Default)
object JssPluginDefaultUnit extends js.Object {
  def apply(): js.Any = js.native
}

@js.native
@JSImport("jss-plugin-expand", JSImport.Default)
object JssPluginExpand extends js.Object {
  def apply(): js.Any = js.native
}

@js.native
@JSImport("jss-plugin-vendor-prefixer", JSImport.Default)
object JssPluginVendorPrefixer extends js.Object {
  def apply(): js.Any = js.native
}

@js.native
@JSImport("jss-plugin-props-sort", JSImport.Default)
object JssPluginPropsSort extends js.Object {
  def apply(): js.Any = js.native
}

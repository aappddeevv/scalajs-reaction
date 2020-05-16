package fabric

import scala.scalajs.js
import styling._
import js.JSConverters._
import js.annotation._
import js.|
import org.scalajs.dom
import org.scalajs.dom.window.localStorage
import react._
import react.implicits._
import scala.util.control._
import Exception._
import styling.module.concatStyleSets
import utilities._
import merge_styles._ 

object hooks extends UseCustomizableHooks

trait UseCustomizableHooks {

  /** Hook to get customized theme in a function component. It's the same as
    * using a `customizable` decorator or the `styled` HOC. `fields` automatically
    * has `theme` and `styles` added so the return value has those values.
    *
    * @see https://github.com/microsoft/fluentui/issues/9858
    */
  def useCustomizable[SP <: js.Object, S <: IStyleSetTag](
      fields: Seq[String] = Nil,
      scope: js.UndefOr[String] = js.undefined
      // removed styles arg
    ) = {
    val (_, update) = useStateStrict[Boolean](true)
    val inCustomizerContext = useRef(false)
    val cb = useCallbackMounting(() => update(!_))
    useEffect(inCustomizerContext) { () =>
      if (!inCustomizerContext.current) Customizations.observe(cb)
      () => if (!inCustomizerContext.current) Customizations.unobserve(cb)
    }
    val context = useContext[CustomizerContext](fabric.utilities.module.CustomizerContext)
    inCustomizerContext.current = context.customizations.inCustomizerContext.getOrElse(false)
    val customizations = useMemo(scope, fields.hashCode)(() =>
      Customizations
        .getSettings[StandardSettingsWithTheme[SP, S]](
          (fields ++ Seq("theme", "styles")).toJSArray,
          scope,
          context.customizations))
    customizations
  }

  /** Get the theme via Customizations which is different than the
    * global theme obtained through `getTheme()` apparently.
    */
  def useTheme[SP <: js.Object, S <: IStyleSetTag]() = {
    val customizations = useCustomizable[SP, S]()
    customizations.theme
  }

}



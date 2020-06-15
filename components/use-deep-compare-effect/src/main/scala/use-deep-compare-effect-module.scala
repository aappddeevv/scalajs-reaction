import scala.scalajs.js
import js.|
import js.annotation._
import js.JSConverters._
import react._

package object use_deep_compare_effect {
  @js.native
  @JSImport("use-deep-compare-effect", JSImport.Default)
  def useDeepCompareEffect_(didUpdate: EffectArg, dependencies: Dependencies): Unit = js.native
  
  def useDeepCompareEffectA(dependencies: Dependencies)(didUpdate: EffectArg) =
    useDeepCompareEffect_(didUpdate, dependencies)

  def useDeepCompareEffect(dependencies: AllType*)(didUpdate: EffectArg) =
    useDeepCompareEffect_(
      didUpdate,
      dependencies.toJSArray
    )

}

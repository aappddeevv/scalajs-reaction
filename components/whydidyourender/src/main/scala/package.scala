import react._
import scala.scalajs.js
import js.|

package object whydidyourender {

  import whydidyourender._
  def apply(reactModule: ReactModule, config: Options | js.Object | js.Dynamic) = module.apply(reactModule, config)

  implicit class RichFunction[P <: js.Object](private val f: js.Function1[P,ReactNode]) extends AnyVal {
     private def force = f.asInstanceOf[js.Dynamic].__proto__
     def whyEnable: Unit = force.whyDidYouRender = true
     def whyOptions(opts: ComponentOptions) = force.whyDidYouRender = opts 
  }
}

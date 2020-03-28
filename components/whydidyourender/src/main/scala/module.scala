package whydidyourender

import react._
import scala.scalajs.js
import js.|
import js.annotation._

// needed for some debugging
@js.native
@JSImport("@welldone-software/why-did-you-render", JSImport.Default)
object module extends js.Object {
  def apply(reactModule: ReactModule, config: Options | js.Object | js.Dynamic): Unit = js.native
}

trait Options extends js.Object {
  /*
trackExtraHooks: []
notifier: ({Component, displayName, prevProps, prevState, nextProps, nextState, reason, options}) => void
   */
  var collapseGroups: js.UndefOr[Boolean] = js.undefined
  var diffNameColor: js.UndefOr[String] = js.undefined
  var diffPathColor: js.UndefOr[String] = js.undefined
  var exclude: js.UndefOr[js.Array[js.RegExp]] = js.undefined
  var hotReloadBufferMs: js.UndefOr[Int] = js.undefined
  var include: js.UndefOr[js.Array[js.RegExp]] = js.undefined
  var logOnDifferentValues: js.UndefOr[Boolean] = js.undefined
  var onlyLogs: js.UndefOr[Boolean] = js.undefined
  var titleColor: js.UndefOr[String] = js.undefined
  var trackHooks: js.UndefOr[Boolean] = js.undefined
  var trackAllPureComponents: js.UndefOr[Boolean] = js.undefined
}

/** DO NOT USE. DOES NOT WORK! NEED TO ACCESS CONSTRUCTOR
 *
 *  Use to cast a SFC to and set the individual component's value. 
 *
 * `def apply[T<:js.Object](props: Props[T]) = sfc[T].tap{c => c.run.asInstanceOf[Why].whyDidYouRender = true}.apply(props)`
 *
 */
  @js.native
  trait Why extends js.Object {
    var whyDidYouRender: Boolean | ComponentOptions = js.native
  }

/** Use these options when modifying an individual function component. */
trait ComponentOptions extends js.Object {
  var logOnDifferentValues: js.UndefOr[Boolean] = js.undefined
  var customName: js.UndefOr[String] = js.undefined
}

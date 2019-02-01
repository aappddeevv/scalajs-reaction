// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native
package styling

import scala.scalajs.js
import scala.scalajs.js.annotation._
import js.|
import js.JSConverters._

// there are better answers... 
// https://github.com/vitalets/react-native-extended-stylesheet

/** General registered type, used for tagging a couple of styling related
 * objects.
 */
sealed trait Registered extends js.Object

/** Tag for returning from StyleSheet.create. Each member should be a
  * RegisteredStyle. In recent react-native, this is no longer true. The
  * returned value from StyleSheet.create is tagged with this trait.
 */
//trait RegisteredStyleSet extends Registered

/** When a styleset object is passed through Stylesheet.create, each member is
  * logically converted to this opaque type. In reality, in recent react-native
  * releases the style object passed in is unchanged. You *don't* have to use
  * this for each member of your style object. Technically you should as this
 * conforms to the API better.
 */
//@js.native
//trait RegisteredStyle extends Registered

/** StyleSet tag. Extend your style classes from this trait for better type
 * inference. 
 */
trait StyleSet extends js.Object

/** Apply the StyleSet tag to an object or a list of pairs. */
object StyleSet {
  def apply[T <: js.Object](obj: js.Object): T with StyleSet =
    obj.asInstanceOf[T with StyleSet]

  def apply[T <: js.Object](stylePairs: (String, Style)*): T with StyleSet =
  js.Dictionary[Style](stylePairs:_*).asInstanceOf[T with StyleSet]
}

/** Drive type inference when combining styles for a component's style property. */
object stylelist {
  def apply[T <: js.Object](styles: StyleProp[T]*): StyleProp[T] =
    styles.toJSArray.asInstanceOf[StyleProp[T]]
}

/** This only checks for valid style properties, it does not "register" or
 * optimize anymore.
 */
@js.native
@JSImport("react-native", "StyleSheet")
object StyleSheet extends js.Object {
  @JSName("create")
  def unsafeCreate[T <: js.Object](styleSet: T): RegisteredStyleSet[T] = js.native
  def create[T <: StyleSet](styles: T): RegisteredStyleSet[T] = js.native
  // this operates an the style level, not styleset. func cannot take var args!
  def flatten[T <: js.Object](style: T | js.Array[T] | Style): js.UndefOr[T] = js.native

  /** Return the single object if either is null. Otherwise return array of styles. */
  // this does not seem to work well for typing...who makes this stuff up?
  //def compose[T <: js.Object](style1: T | Style, style2: T | Style): T | js.Array[T] = js.native

  val hairlineWidth: Double = js.native
  val absoluteFillObject: AbsoluteFillStyle = js.native
  val absoluteFill: RegisteredStyleSet[AbsoluteFillStyle] = js.native
}

@js.native
trait AbsoluteFillStyle extends js.Object {
  val position: js.UndefOr[String] = js.undefined
  val left: js.UndefOr[Int] = js.undefined
  val right: js.UndefOr[Int] = js.undefined
  val top: js.UndefOr[Int] = js.undefined
  val bottom: js.UndefOr[Int] = js.undefined
}

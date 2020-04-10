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

package fabric
package utilities

//import react.deriveForwardingTrait
import scala.scalajs.js

import js.annotation._
import js.|

import fabric.styling._

/**
 * office-ui-fabric-react/lib/Utilities == @uifabric/utilities
 */
@js.native
trait module_base extends js.Object {

  /** This is another tough one to type in scala.js. If you use a scala function
   * to define f, an implicit will convert it to a js function automatically. Or
   * you can use `js.Any.fromFunctionN()` to be explicit which you sometimes
   * need to do to get the scala function to js function conversion correct.
   */
  def memoizeFunction[I, O](f: js.Function1[I, O]): js.Function1[I, O] = js.native

  /** string, serializable (has toString), dictionary, null, undefined, boolean...*/
  def css(various: js.Any*): String = js.native

  /** Set to the type of return value you want. */
  def getNativeProps[T <: js.Object](
    props: js.Object,
    allowedPropNames: js.Array[String],
    excludedPropNames: js.UndefOr[js.Array[String]] = js.undefined
  ): T =
    js.native

  val baseElementEvents: js.Array[String] = js.native
  val baseElementProperties: js.Array[String] = js.native
  val htmlElementProperties: js.Array[String] = js.native
  val anchorProperties: js.Array[String] = js.native
  val buttonProperties: js.Array[String] = js.native
  val divProperties: js.Array[String] = js.native
  val inputProperties: js.Array[String] = js.native
  val textAreaProperties: js.Array[String] = js.native
  val imageProperties: js.Array[String] = js.native

  /**
   * Create a function that takes a function and props, calls that function with
   * the props then calls mergeStyleSets on the result. Weakly typed function
   * signature since scala does not have structural typing. We can do better at
   * some point even without structural typing. This does not work on a straight
   * `IStyleSet` since that's set as a dictionary (string->IStyle) but instead
   * an object type `IStyleSetTag`. Ultimately, given a style function or style
   * object and a set of properties that are either input into the style
   * function or are properties to be merged with the first argument if its an
   * object and not a function, return a set of classnames that represent style
   * processing via 'mergeStyleSets'.
   *
   * You usually call this inside your component object with a styles function
   * (props=>styles) and properties passed in as props so that they are all
   * merged together to generate your classnames linked to stylesheet styles.
   * You need to tag your js.Object derived traits with IStyleSetTag and
   * IClassNamesTag to drive type recognition.
   *
   * You are probably better off just defining your own `getClassNames` (and
   * memoizing it) and calling `mergeStyleSets` yourself. Your getClassNames
   * can take a styles (function or object) parameter.
   */
  def classNamesFunction[P <: js.Object, SS <: IStyleSetTag, CN <: IClassNamesTag](
    ): js.Function2[js.UndefOr[IStyleFunctionOrObject[P, SS]], js.UndefOr[P], CN] = js.native

  def mergeSettings(
    oldSettings: Settings,
    newSettings: Settings | SettingsFunction
  ): Settings = js.native
  def mergeScopedSettings(
    oldeSettings: Settings,
    newSettings: Settings | SettingsFunction
  ): Settings = js.native

  //def mergeCustomizations:

  def filteredAssign(
    isAllowed: js.Function1[String, Boolean],
    target: js.Any,
    args: js.Any*
  ): js.Any = js.native

  /** Format using {0} type placement specifiers. */
  def format(
    s: String,
    values: js.Any*
  ): String = js.native

  /** Deep merge. */
  def merge[T <: js.Object](args: T | js.Object | js.Dynamic | Null | Unit*): T = js.native
}

@js.native
@JSImport("@uifabric/utilities", JSImport.Namespace)
object module extends module_base with customizer_base {
  val SELECTION_CHANGE: String = js.native
}

//@deriveForwardingTrait(module)
//trait module_exports

trait AddEventListenerOptions extends js.Object {}

@js.native
@JSImport("@uifabric/utilities", "EventGroup")
class EventGroup(parent: js.Any) extends js.Object {
  def dispose(): Unit = js.native
  def onAll(
    target: js.Any,
    events: js.Dictionary[js.Function1[js.Any, Unit]],
    useCapture: js.UndefOr[Boolean] = js.undefined
  ): Unit = js.native
  def on(
    target: js.Any,
    eventName: String,
    callback: js.Function1[js.UndefOr[js.Any], Unit],
    options: js.UndefOr[Boolean | AddEventListenerOptions] = js.undefined): Unit = js.native
  def off(
    target: js.UndefOr[js.Any] = js.undefined,
    eventName: js.UndefOr[String] = js.undefined,
    callback: js.UndefOr[js.Function1[js.UndefOr[js.Any], Unit]] = js.undefined,
    options: js.UndefOr[Boolean | AddEventListenerOptions] = js.undefined): Unit = js.native
  def raise(
    eventName: String,
    args: js.UndefOr[js.Any] = js.undefined,
    bubbleEvent: js.UndefOr[Boolean] = js.undefined
  ): js.UndefOr[Boolean] = js.native
  def declare(event: String | js.Array[String]): Unit = js.native

}

@js.native
@JSImport("@uifabric/utilities", "EventGroup")
object EventGroup extends js.Object {
  def isObserved(target: js.Any, eventName: String): Boolean = js.native
  def isDeclared(target: js.Any, eventName: String): Boolean = js.native
  def stopPropagation(event: js.Any): Unit = js.native
  def raise(
    target: js.Any,
    eventName: String,
    eventArgs: js.UndefOr[js.Any] = js.undefined,
    bubbleEvent: js.UndefOr[Boolean] = js.undefined): js.UndefOr[Boolean] = js.native
}

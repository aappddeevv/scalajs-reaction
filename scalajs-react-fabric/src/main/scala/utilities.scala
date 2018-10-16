// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT
package ttg
package react
package fabric

import scala.scalajs.js
import js.|
import js.annotation._
import js.Dynamic.{literal => lit}
import ttg.react.vdom._
import js.JSConverters._

import styling._

/** 
 * office-ui-fabric-react/lib/Utilities == @uifabric/utilities
 */
@js.native
@JSImport("office-ui-fabric-react/lib/Utilities", JSImport.Namespace)
object Utilities extends js.Object {

  /** This is another tough one to type in scala.js. If you use a scala function,
   * an implicit will convert it to a js function automatically or you can use
   * `js.Any.fromFunctionN()` to be explicit.
   */
  def memoizeFunction[T <: js.Function](f: T): T = js.native

  /** string, serializable (has toString), dictionary, null, undefined, boolean...*/
  def css(various: js.Any*): String = js.native

  def getNativeProps[T <: js.Object](
      props: js.Object,
      allowedPropNames: js.Array[String],
      excludedPropNames: js.UndefOr[js.Array[String]] = js.undefined): T =
    js.native

  val baseElementEvents: js.Array[String]     = js.native
  val baseElementProperties: js.Array[String] = js.native
  val htmlElementProperties: js.Array[String] = js.native
  val anchorProperties: js.Array[String]      = js.native
  val buttonProperties: js.Array[String]      = js.native
  val divProperties: js.Array[String]         = js.native
  val inputProperties: js.Array[String]       = js.native
  val textAreaProperties: js.Array[String]    = js.native
  val imageProperties: js.Array[String]       = js.native

  /**
   * Weakly typed function signature. We can do better at some point even
   * without structural typing. This does not work on a straight `IStyleSet`
   * since that's set as a dictionary (string->IStyle) but instead an object
   * type `IStyleSetTag`. Ultimately, given a style function or style object and
   * a set of properties that are either input into the style function or are
   * properties to be merged with the first argument if its an object and not a
   * function, return a set of classnames that represent style processing via
   * 'mergeStyleSets'.
   * 
   * You usually call this inside your class with a styles function
   * (props=>styles) and properties passed in as props so that they are all
   * merged together to generate your classnames linked to stylesheet styles.
   * You need to tag your js.Object derived traits with IStyleSetTag and
   * IClassNamesTag to drive type recognition.
   */
  def classNamesFunction[P <: js.Object, SST <: IStyleSetTag](
    getStyles: js.UndefOr[IStyleFunctionOrObject[P, SST]],
    styleProps: js.UndefOr[P] = js.undefined
  ): js.Function2[IStyleFunctionOrObject[P, SST], P, IClassNamesTag] = js.native
}

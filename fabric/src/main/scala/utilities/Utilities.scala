// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric

import scala.scalajs.js
import js.|
import js.annotation._
import js.Dynamic.{literal => lit}
import react.vdom._
import js.JSConverters._

import fabric.styling._

/** 
 * office-ui-fabric-react/lib/Utilities == @uifabric/utilities
 */
@js.native
@JSImport("@uifabric/utilities", JSImport.Namespace)
object Utilities extends js.Object {

  /** This is another tough one to type in scala.js. If you use a scala function
   * to define f, an implicit will convert it to a js function automatically. Or
   * you can use `js.Any.fromFunctionN()` to be explicit which you sometimes
   * need to do to get the scala function to js function conversion correct.
   */
  def memoizeFunction[I,O](f: js.Function1[I,O]): js.Function1[I,O] = js.native

  /** string, serializable (has toString), dictionary, null, undefined, boolean...*/
  def css(various: js.Any*): String = js.native

  /** Set to the type of return value you want. */
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
  def classNamesFunction[P <: js.Object, SS <: IStyleSetTag, CN <: IClassNamesTag]():
      js.Function2[js.UndefOr[IStyleFunctionOrObject[P, SS]], js.UndefOr[P], CN] = js.native
}

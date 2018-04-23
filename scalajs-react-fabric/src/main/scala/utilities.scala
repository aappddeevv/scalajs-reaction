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

/** office-ui-fabric-react/lib/Utilities */
@js.native
@JSImport("office-ui-fabric-react/lib/Utilities", JSImport.Namespace)
object Utilities extends js.Object {
  /** This is another tough one to type in scala.js. */
  def memoizeFunction[T <: js.Function](f: T): T = js.native

  /** string, serializable (has toString), dictionary, null, undefined, boolean...*/
  def css(various: js.Any*): String = js.native

  def getNativeProps[T <: js.Object](props: js.Object,
    allowedPropNames: js.Array[String],
    excludedPropNames: js.UndefOr[js.Array[String]] = js.undefined): T =
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
}

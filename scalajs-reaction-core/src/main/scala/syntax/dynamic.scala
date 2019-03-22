// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._

final case class JsDynamicOps(val jsdyn: js.Dynamic) {
  def asJsAny: js.Any         = jsdyn.asInstanceOf[js.Any]
  def asString: String        = jsdyn.asInstanceOf[String]
  def asInt: Int              = jsdyn.asInstanceOf[Int]
  def asArray[A]: js.Array[A] = jsdyn.asInstanceOf[js.Array[A]]
  def asBoolean: Boolean      = jsdyn.asInstanceOf[Boolean]
  def as[T <: js.Object] = jsdyn.asInstanceOf[T]

  ///** @deprecated use asJsObj */
  //@inline def asJSObj: js.Object = jsdyn.asInstanceOf[js.Object]
  // was just asJsObj does the cast help? can we remove asJsObjSub
  def asJsObj: js.Object          = jsdyn.asInstanceOf[js.Object]
  def asDict[A]: js.Dictionary[A] = jsdyn.asInstanceOf[js.Dictionary[A]]
  // variance annotation needed?
  def asUndefOr[A]: js.UndefOr[A] = jsdyn.asInstanceOf[js.UndefOr[A]]
  def asJsObjSub[A <: js.Object]  = jsdyn.asInstanceOf[A] // assumes its there!
  def asJsArray[A <: js.Object]   = jsdyn.asInstanceOf[js.Array[A]]

  /** Uses truthiness to determine None */
  def toOption[T <: js.Object]: Option[T] =
    if (js.DynamicImplicits.truthValue(jsdyn)) Some(jsdyn.asInstanceOf[T])
    else None

  /** Not sure this works... */
  def toNonNullOption[T <: js.Object]: Option[T] =
    Option(jsdyn.asInstanceOf[T])
    //JsUndefOrOps(asUndefOr).toNonNullOption
  def combine(that: js.Dynamic)                  = mergeJSObjects(jsdyn, that)
  def toTruthy: Boolean                          = js.DynamicImplicits.truthValue(jsdyn)
}

trait JsDynamicSyntax {
  implicit def jsDynamicOpsSyntax(jsdyn: js.Dynamic) = JsDynamicOps(jsdyn)
}

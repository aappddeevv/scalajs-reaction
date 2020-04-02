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

package jshelpers 

import scala.scalajs.js

final class JsDynamicOps(private val jsdyn: js.Dynamic) extends AnyVal {
  def asJsAny: js.Any         = jsdyn.asInstanceOf[js.Any]
  def asString: String        = jsdyn.asInstanceOf[String]
  def asInt: Int              = jsdyn.asInstanceOf[Int]
  def asArray[A]: js.Array[A] = jsdyn.asInstanceOf[js.Array[A]]
  def asBoolean: Boolean      = jsdyn.asInstanceOf[Boolean]
  def as[T <: js.Object]      = jsdyn.asInstanceOf[T]

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
  def combine(that: js.Dynamic) = js.Object.assign(jsdyn.asInstanceOf[js.Object], that.asInstanceOf[js.Object]).asInstanceOf[js.Dynamic]
  def toTruthy: Boolean         = js.DynamicImplicits.truthValue(jsdyn)
}

trait JsDynamicSyntax {
  @inline implicit def jsDynamicOpsSyntax(jsdyn: js.Dynamic) = new JsDynamicOps(jsdyn)
}

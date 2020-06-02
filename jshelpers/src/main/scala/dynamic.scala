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
import js.|

final class JsDynamicOps(private val jsdyn: js.Dynamic) extends AnyVal {
  def asJsAny: js.Any = jsdyn.asInstanceOf[js.Any]
  def asArray[A]: js.Array[A] = jsdyn.asInstanceOf[js.Array[A]]

  def asNumber: Number = jsdyn.asInstanceOf[Number]
  
  def asString: String = jsdyn.asInstanceOf[String]
  def asNullString = jsdyn.asInstanceOf[String|Null]
  def asUndefString = jsdyn.asInstanceOf[js.UndefOr[String]]
  def asUndefNullString= jsdyn.asInstanceOf[js.UndefOr[String|Null]]
    
  def asInt: Int = jsdyn.asInstanceOf[Int]
  def asNullInt = jsdyn.asInstanceOf[Int|Null]
  def asUndefInt = jsdyn.asInstanceOf[js.UndefOr[Int]]
  def asUndefNullInt= jsdyn.asInstanceOf[js.UndefOr[Int|Null]]
    
  def asDouble: Double = jsdyn.asInstanceOf[Double]
  def asNullDouble = jsdyn.asInstanceOf[Double|Null]
  def asUndefDouble = jsdyn.asInstanceOf[js.UndefOr[Double]]
  def asUndefNullDouble = jsdyn.asInstanceOf[js.UndefOr[Double|Null]]
  
  def asBoolean: Boolean = jsdyn.asInstanceOf[Boolean]
  def asNullBoolean = jsdyn.asInstanceOf[Boolean|Null]
  def asUndefBoolean = jsdyn.asInstanceOf[js.UndefOr[Boolean]]
  def asUndefNullBoolean = jsdyn.asInstanceOf[js.UndefOr[Boolean|Null]]
    
  def asJSDate = jsdyn.asInstanceOf[js.Date]
  def asNullJSDate= jsdyn.asInstanceOf[js.Date|Null]
  def asUndefJSDate= jsdyn.asInstanceOf[js.UndefOr[js.Date]]
  def asUndefNullJSDate= jsdyn.asInstanceOf[js.UndefOr[js.Date|Null]]    
  
  /** This value could be T or null. This does not check if its undefined. */
  def asOrNull[T <: scala.Any] = jsdyn.asInstanceOf[T | Null]

  /** As potentially T, null or undefined. The safest cast. */
  def asUndefOrNull[T <: scala.Any] =
    jsdyn.asInstanceOf[js.UndefOr[T | Null]]

  /** Short version of `.asInstanceOf`. */
  def as[T] = jsdyn.asInstanceOf[T]

  def asJsObj: js.Object = jsdyn.asInstanceOf[js.Object]

  def asDict[A]: js.Dictionary[A] = jsdyn.asInstanceOf[js.Dictionary[A]]

  def asJsObjSub[A <: js.Object] = jsdyn.asInstanceOf[A] // assumes its there!

  def asJsArray[A <: js.Object] = jsdyn.asInstanceOf[js.Array[A]]

  /** Uses truthiness to determine None, you may not want this. */
  def toOption[T <: js.Object]: Option[T] =
    if (js.DynamicImplicits.truthValue(jsdyn)) Some(jsdyn.asInstanceOf[T])
    else None

  /** Null and undefined => None, otherwise Some. The safest conversion. */
  def toNonNullOption[T <: js.Object]: Option[T] =
    if (jsdyn == null || jsdyn.asInstanceOf[js.UndefOr[T]].isEmpty) None
    else Option(jsdyn.asInstanceOf[T])

  /** Shallow combine into existing value. */
  def combine(that: js.Dynamic) =
    js.Object.assign(jsdyn.asInstanceOf[js.Object], that.asInstanceOf[js.Object]).asInstanceOf[js.Dynamic]

  /** Combine and cast. */
  def combineTo[B](that: js.Dynamic) =
    js.Object.assign(jsdyn.asInstanceOf[js.Object], that.asInstanceOf[js.Object]).asInstanceOf[B]

  /** Determine if truthy. Very tricky! */
  def toTruthy: Boolean = js.DynamicImplicits.truthValue(jsdyn)

  /** Duplicate using `js.Object.assign` */
  def duplicate = js.Object.assign(new js.Object, jsdyn.asInstanceOf[js.Object]).asInstanceOf[js.Dynamic]

  /** Duplicate and combine. */
  def duplicateCombine(that: js.Dynamic) =
    js.Object
      .assign(new js.Object, jsdyn.asInstanceOf[js.Object], that.asInstanceOf[js.Object])
      .asInstanceOf[js.Dynamic]

  /** Duplicate, combine and cast! */
  def duplicateCombineTo[B](that: js.Dynamic) =
    js.Object.assign(new js.Object, jsdyn.asInstanceOf[js.Object], that.asInstanceOf[js.Object]).asInstanceOf[B]

  /** Combine with general js.Object */
  def combineGeneric(that: js.UndefOr[js.Object]) =
    js.Object.assign(jsdyn.asInstanceOf[js.Object], that.asInstanceOf[js.Object]).asInstanceOf[js.Dynamic]

  /** Combine with general js.Object and cast. */
  def combineGenericTo[B](that: js.UndefOr[js.Object]) =
    js.Object.assign(jsdyn.asInstanceOf[js.Object], that.asInstanceOf[js.Object]).asInstanceOf[B]

}

trait JsDynamicSyntax {
  @inline implicit def jsDynamicOpsSyntax(jsdyn: js.Dynamic) = new JsDynamicOps(jsdyn)
}

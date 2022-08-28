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
package syntax

import scala.annotation.targetName
import scala.scalajs.js

/** Most of these should be in `js.Any`. Anything extending js.Dynamic is double unsafe and dangerous. */
object dynamic:
  extension (jsdyn: js.Dynamic)
    // /** @deprecated Use `asJSAny` */
    // def asJsAny: js.Any = jsdyn.asInstanceOf[js.Any]
    // def asJSAny: js.Any = jsdyn.asInstanceOf[js.Any]
    
    // def asJSArray[A]: js.Array[A] = jsdyn.asInstanceOf[js.Array[A]]
    // /** @deprecated Use `asJSAny` */
    // def asJsArray[A <: js.Object] = jsdyn.asInstanceOf[js.Array[A]]
    // /** @deprecated Use `asJSAny` */
    // def asArray[A]: js.Array[A] = jsdyn.asInstanceOf[js.Array[A]]

    // def asNumber: Number = jsdyn.asInstanceOf[Number]

    // def asString: String = jsdyn.asInstanceOf[String]
    // def asNullString = jsdyn.asInstanceOf[String | Null]
    // def asUndefString = jsdyn.asInstanceOf[js.UndefOr[String]]
    // def asUndefNullString = jsdyn.asInstanceOf[js.UndefOr[String | Null]]

    // def asInt: Int = jsdyn.asInstanceOf[Int]
    // def asNullInt = jsdyn.asInstanceOf[Int | Null]
    // def asUndefInt = jsdyn.asInstanceOf[js.UndefOr[Int]]
    // def asUndefNullInt = jsdyn.asInstanceOf[js.UndefOr[Int | Null]]

    // def asDouble: Double = jsdyn.asInstanceOf[Double]
    // def asNullDouble = jsdyn.asInstanceOf[Double | Null]
    // def asUndefDouble = jsdyn.asInstanceOf[js.UndefOr[Double]]
    // def asUndefNullDouble = jsdyn.asInstanceOf[js.UndefOr[Double | Null]]

    // def asBoolean: Boolean = jsdyn.asInstanceOf[Boolean]
    // def asNullBoolean = jsdyn.asInstanceOf[Boolean | Null]
    // def asUndefBoolean = jsdyn.asInstanceOf[js.UndefOr[Boolean]]
    // def asUndefNullBoolean = jsdyn.asInstanceOf[js.UndefOr[Boolean | Null]]

    // def asJSDate = jsdyn.asInstanceOf[js.Date]
    // def asNullJSDate = jsdyn.asInstanceOf[js.Date | Null]
    // def asUndefJSDate = jsdyn.asInstanceOf[js.UndefOr[js.Date]]
    // def asUndefNullJSDate = jsdyn.asInstanceOf[js.UndefOr[js.Date | Null]]

    /** This value could be T or null. This does not check if its undefined. */
    def asOrNull[T <: scala.Any] = jsdyn.asInstanceOf[T | Null]

    /** As potentially T, null or undefined. The safest cast. */
    def asUndefOrNull[T <: scala.Any] =
      jsdyn.asInstanceOf[js.UndefOr[T | Null]]

    /** Short version of `.asInstanceOf`. */
    // @targetName("asJSDynamic")
    // def as[T] = jsdyn.asInstanceOf[T]

    // /** @deprecated Use `asJSObj` */
    // def asJsObj: js.Object = jsdyn.asInstanceOf[js.Object]
    // def asJSObj: js.Object = jsdyn.asInstanceOf[js.Object]
    // def asJSObject: js.Object = jsdyn.asInstanceOf[js.Object]

    // def asDict[A]: js.Dictionary[A] = jsdyn.asInstanceOf[js.Dictionary[A]]

    // /** Assume a subclass of `js.Object`. */
    // def asJSObjSub[A <: js.Object] = jsdyn.asInstanceOf[A] // assumes its there!
    
    /** Uses truthiness to determine None, you may not want this. */
    def toOption[T <: js.Object]: Option[T] =
      if js.DynamicImplicits.truthValue(jsdyn) then Some(jsdyn.asInstanceOf[T])
      else None

    // /** Null and undefined => None, otherwise Some. The safest conversion. */
    // def toNonNullOption[T <: js.Object]: Option[T] =
    //   if (jsdyn == null || jsdyn.asInstanceOf[js.UndefOr[T]].isEmpty) None
    //   else Option(jsdyn.asInstanceOf[T])

    // /** Shallow combine into existing value. */
    // def combine(that: js.Dynamic*) =
    //   js.Object.assign(jsdyn.asInstanceOf[js.Object], that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[js.Dynamic]

    // /** Combine and cast. */
    // def combineTo[B](that: js.Dynamic*) =
    //   js.Object.assign(jsdyn.asInstanceOf[js.Object], that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[B]

    /** Determine if truthy. Shorthand to avoid `js.DynamicImplicits.truthValue` syntax. */
    // @targetName("toTruthyDynamic")
    // def toTruthy: Boolean = js.DynamicImplicits.truthValue(jsdyn)

    /** Duplicate using `js.Object.assign` */
    // @targetName("duplicateJSDynamic")
    // def duplicate: js.Dynamic = js.Object.assign(new js.Object {}, jsdyn.asInstanceOf[js.Object]).asInstanceOf[js.Dynamic]
    
    // /** Duplicate and combine. */
    // def duplicateCombine(that: js.Dynamic) =
    //   js.Object
    //     .assign(new js.Object, jsdyn.asInstanceOf[js.Object], that.asInstanceOf[js.Object])
    //     .asInstanceOf[js.Dynamic]

    // /** Duplicate, combine and cast! */
    // def duplicateCombineTo[B](that: js.Dynamic) =
    //   js.Object.assign(new js.Object, jsdyn.asInstanceOf[js.Object], that.asInstanceOf[js.Object]).asInstanceOf[B]

    // /** Combine with general js.Object */
    // def combineGeneric(that: js.Object | Unit*) =
    //   js.Object.assign(jsdyn.asInstanceOf[js.Object], that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[js.Dynamic]

    // /** Combine with general js.Object and cast. */
    // def combineGenericTo[B](that: js.Object | Unit*) =
    //   js.Object.assign(jsdyn.asInstanceOf[js.Object], that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[B]
  end extension
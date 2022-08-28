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

import scala.scalajs.js
import scala.annotation.targetName

/** Much unsafeness here. Assumes you know what you are doing. */
object any:
  import scala.scalajs.js
  import scala.language.unsafeNulls
  
  /** Totally unsafe. */
  extension (a: scala.Any)
    /** Totally unsafe, use at your own risk! */
    def unsafeAsJsAny = a.asInstanceOf[js.Any]

    /** Use this one, not the lowercase one. */
    //def unsafeAsJSAny = a.asInstanceOf[js.Any]

  /** All of these are unsafe. */
  extension [T <: js.Any](a: T)
    /** Convert T => T|Null. */
    def maybeNull = a.asInstanceOf[T | Null]

    /** If T is js.Any, this may be redundent. */
    def asJsAny: js.Any = a.asInstanceOf[js.Any]
    def asJsObj: js.Object = a.asInstanceOf[js.Object]
    def asJSObj: js.Object = a.asInstanceOf[js.Object]
    def asJSObject: js.Object = a.asInstanceOf[js.Object]
    /** Assume a subclass of `js.Object`. */
    def asJSObjSub[A <: js.Object] = a.asInstanceOf[A] // assumes its there!

    def asDyn: js.Dynamic = a.asInstanceOf[js.Dynamic]
    def asJSDyn: js.Dynamic = a.asInstanceOf[js.Dynamic]
    def asDict[A]: js.Dictionary[A] = a.asInstanceOf[js.Dictionary[A]]
    def asJSDict[A]: js.Dictionary[A] = a.asInstanceOf[js.Dictionary[A]]

    def asString = a.asInstanceOf[String]
    def asNullString = a.asInstanceOf[String | Null]
    def asUndefString = a.asInstanceOf[js.UndefOr[String]]
    def asUndefNullString = a.asInstanceOf[js.UndefOr[String | Null]]

    def asNumber: Number = a.asInstanceOf[Number]

    def asInt = a.asInstanceOf[Int]
    def asNullInt = a.asInstanceOf[Int | Null]
    def asUndefInt = a.asInstanceOf[js.UndefOr[Int]]
    def asUndefNullInt = a.asInstanceOf[js.UndefOr[Int | Null]]

    def asFloat = a.asInstanceOf[Float]
    def asNullFloat = a.asInstanceOf[Float | Null]
    def asUndefFloat = a.asInstanceOf[js.UndefOr[Float]]
    def asUndefNullFloat = a.asInstanceOf[js.UndefOr[Float | Null]]

    def asDouble = a.asInstanceOf[Double]
    def asNullDouble = a.asInstanceOf[Double | Null]
    def asUndefDouble = a.asInstanceOf[js.UndefOr[Double]]
    def asUndefNullDouble = a.asInstanceOf[js.UndefOr[Double | Null]]

    def asBoolean = a.asInstanceOf[Boolean]
    def asNullBoolean = a.asInstanceOf[Boolean | Null]
    def asUndefBoolean = a.asInstanceOf[js.UndefOr[Boolean]]
    def asUndefNullBoolean = a.asInstanceOf[js.UndefOr[Boolean | Null]]

    def asJSDate = a.asInstanceOf[js.Date]
    def asNullJSDate = a.asInstanceOf[js.Date | Null]
    def asUndefJSDate = a.asInstanceOf[js.UndefOr[js.Date]]
    def asUndefNullJSDate = a.asInstanceOf[js.UndefOr[js.Date | Null]]

    def asJsArray[A]: js.Array[A] = a.asInstanceOf[js.Array[A]]
    def asJSArray[A]: js.Array[A] = a.asInstanceOf[js.Array[A]]

    def asJson: String = js.JSON.stringify(a.asInstanceOf[js.Object])

    /** `.asInstanceOf[T]` but shorter. Very dangerous! */
    @targetName("asJSAny")
    def as[A]: A = a.asInstanceOf[A]

    /** Call the toString method after casting to js.Any. Not sure
     *  casting makes any difference though.
     */
    def toStringJs = a.asInstanceOf[js.Any].toString()

    /** Internal null values become undefined. */
    def filterNull: js.UndefOr[T] = toNonNullUndefOr

    /** If value is null or undefined be undefined, otherwise defined. Could be called "filterNull". */
    def toNonNullUndefOr: js.UndefOr[T] =
      // we keep this so that it works when needed
      if a == null || js.isUndefined(a) then js.undefined
      else js.defined(a)

    /** If value is null or undefined be None, else Some. */
    def toNonNullOption: Option[T] =
      // also defined in react package, repeated here
      if js.isUndefined(a) || a == null then None
      else Option(a)

    /** Equivalent `!!x` for some javascript value x. */
    @targetName("toTruthyJSAny")
    def toTruthy: Boolean = js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])

    /** Unlike `Option` use truthiness to determine if the value should be None. */
    def toTruthyOption: Option[T] = Option(a).filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))

    /**
     * Wow, a mouthful! If its a javascript truthy=true, its defined, otherwise
     * undef. Takes into account 0, "" and [] javascript idioms i.e. takes into account
     * the FP zero.
     * @example {{{
     *  val s = "" // s.toTruthyUndefOr[String] => js.undefined
     *  val s = "blah" // s.toTurthyUndefOr[String] => defined "blah"
     *  val n = 0  // n.toTruthyUndefOr[Int] => js.undefined
     *  val n1 = 1 // n1.toTruthyUndefOr[Int] => defined 1
     * }}}
     */
    def toTruthyUndefOr: js.UndefOr[T] =
      if js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]) then js.defined(a)
      else js.undefined
  end extension

  /**
   * Intended for directly mapped scala types, not scala.Any in general. Know what
   * you are doing!!! Very dangerous!
   */
  // extension [T <: scala.Any](a: T)
  //   ///** Very dangerous! You should know what you are doing. */
  //   def unsafeAsJsObject = a.asInstanceOf[js.Object]

  extension [A <: Int|Float|Double](a: A)
    def toLocaleString(
      locale: js.UndefOr[String | js.Array[String]] = js.undefined,
      options: js.UndefOr[js.Object] = js.undefined): String = 
        a.asInstanceOf[js.Object].toLocaleString()  
  end extension
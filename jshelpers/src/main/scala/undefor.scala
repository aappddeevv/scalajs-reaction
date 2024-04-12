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

/** Support for `js.UndefOr`.
 * 
 * @see https://github.com/scala-js/scala-js/blob/main/test-suite/js/src/test/scala/org/scalajs/testsuite/library/UnionTypeTest.scala
 * 
 */
object undefor:
  /** Add Option-like methods to js.UndefOr. Note that js.Undef.orNull exists in scala.js 1.0 */
  extension [A](_a: js.UndefOr[A])
    /** Tests for overall nullness which is different than `.isEmpty|.nonEmpty`. */
    def isNull: Boolean = _a == null

    /** Like `UndefOr.isEmpty` but this checks for null and undefined as well. 
     * This is the closest semantically to js `!!`.
    */
    def isTotalEmpty: Boolean = isNull || !_a.isDefined

    /** This could also be `_.toOption.filter(_ != null)` but below is slightly faster. */
    @targetName("toNonNullOptionA")
    def toNonNullOption =
      if _a.isEmpty || _a == null then None
      else _a.toOption

    /** Calls toString. I'm not sure this is needed at all. */
    def toStringJs: String = _a.asInstanceOf[js.Any].toString()

    // /** null => undefined, else the value remains. */
    // def filterNull: js.UndefOr[A] = _a.filter(_ != null)

    /** Changes what's inside but preserves UndefOr. This should be `unsafeAs`. */
    @targetName("asUndefOr")
    def as[B]: js.UndefOr[B] = _a.asInstanceOf[js.UndefOr[B]] // _a.map(_.asInstanceOf[B])

    /** Convert UndefOr[A] => A|Null. Much like stdlib `orNull` but keeps type signature. */
    def toNull: A | Null = if _a.isDefined then _a.asInstanceOf[A | Null] else null

    /** Same as `.getOrElse` just shorter. */
    @targetName("getOrElseUndefOrNull2")
    def ??[B >: A](default: => B): B = _a.getOrElse(default)

    /** To help with the "uh-oh." Thought it was `js.UndefOr[A]` but you need to say its a
     * `js.UndefOr[A|Null]` because the docs were wrong :-).
     */
    @targetName("asUndefToUndefOrNull")
    def asUndefOrNull: js.UndefOr[A | Null] = _a.asInstanceOf[js.UndefOr[A | Null]]

  end extension

  // extension [A <: JSAnyConversionSource](_a: js.UndefOr[A])
  //   /** Equivalent to js `!!someJSValue`. */
  //   @targetName("toTruthyUndefOrJSAny")
  //   def toTruthy: Boolean = js.DynamicImplicits.truthValue(_a.asInstanceOf[js.Dynamic])

  //   // /** Keep the value if its truthy, otherwise return undefined. */
  //   // @targetName("filterTruthyUndefOrJSAny")  
  //   // def filterTruthy: js.UndefOr[A] =
  //   //   _a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))
  // end extension


  /** Comman case of managing Null and truthy values. */
  extension [T <: js.Any | String](a: js.UndefOr[T | Null])
    // /** T|Null may still have T not being truthy, so absorb null and non-truthiness => js.undefined. */
    // def absorbNullKeepTruthy: js.UndefOr[T] = a.flatMap { value =>
    //   if (value == null) js.undefined
    //   //else new JsUndefOrOps(a.asInstanceOf[js.UndefOr[T]]).filterTruthy
    //   else a.asInstanceOf[js.UndefOr[T]].filterTruthy
    // }

    /** Drop the `Null` and filter  non-truthy values to `js.undefined`. */
    @targetName("filterTruthyUndefOrNull")
    def filterTruthy: js.UndefOr[T] =
      if a == null || js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]) then a.asInstanceOf[js.UndefOr[T]]
      else js.undefined
  end extension

  /** Handle js.UndefOr[T|Null] directly versus needing to flatmap into it. Don't forget that
   * scala.js has `anUndefOr.orNull` to extract the value or return null which is *not*
   * what the methods below do. Note that the input is really `T|Null|Unit`.
   * 
   * Note that `T` is not constrained to be a string or js value.
   */
  extension [T](a: js.UndefOr[T | Null])
    inline private def forceGet: T = a.asInstanceOf[T]

    /** Determine if is defined including the value not being null. */
    @targetName("isDefinedUndefOrNull")
    def isDefined: Boolean = if !js.isUndefined(a) && a != null then true else false

    // /** Convenience. */
    @targetName("isEmptyUndefOrNull")
    def isEmpty: Boolean = !isDefined

    //def isEmpty: Boolean = !a.asInstanceOf[js.UndefOr[T]].isDefined

    /** Treat null as undefined and change type from `js.UndefOr[T|Null]` to `js.UndefOr[T]`. */
    @targetName("undefAbsorbUndefOrNull")
    def absorbNull: js.UndefOr[T] = 
      if js.isUndefined(a) || a == null then js.undefined
      else a.asInstanceOf[js.UndefOr[T]]

    /** Collapse everything at once. */
    // @targetName("toNonNullOptionUndefOrNull")
    // def toNonNullOption: Option[T] =
    //   a.fold(Option.empty[T])(value => if (value != null) Option(value.asInstanceOf[T]) else Option.empty[T])

    // /** Flatten the UndefOr and Null to UndefOr only. */
    // def flatten: js.UndefOr[T] = absorbNull

    /** Absorb the `js.UndefOr` leaving `T|Null`. */
    def absorbUndef: T | Null =
      if a.isEmpty then null.asInstanceOf[T | Null] else a.asInstanceOf[T | Null]

    /** `flatten` but leave the UndefOr. Same as `absorbUndef`. */
    def flattenUndefOr: T | Null = absorbUndef

    /** Natural transformation. */
    @targetName("swapUndefOrNull")
    def swap: js.UndefOr[T] | Null =
      if a != null && a.isDefined then a.asInstanceOf[js.UndefOr[T] | Null]
      else ().asInstanceOf[js.UndefOr[T] | Null]

    /** Understands UndefOr and Null to do the orElse. */
    @targetName("getOrElseUndefOrNull")
    def getOrElse[B >: T](default: => T): T =
      if a == null || a.isEmpty then default else a.asInstanceOf[T]

    /** Alias for getOrElse. */
    @targetName("getOrElse1")
    def ??[B >: T](default: => T): T = getOrElse[B](default)

    /** May be undefined or null or something. Throws exception. */
    @targetName("undefGet")
    inline def undefGet: T =
      if a == null || a.isEmpty then throw new NoSuchElementException("get on UndefOr[T|Null]")
      else forceGet

    /** Only works with another js.UndefOr[T|Null] and takes into account null. */
    def orDeepElse(that: js.UndefOr[T | Null]) = if a.isDefined && a != null then a else that
  end extension

  extension [A <: js.Object](a: js.UndefOr[A])
    /** Duplicate inner value if it exists. Saves you a `.map`. */
    def duplicate = a.map(value => js.Object.assign(new js.Object{}, value.asInstanceOf[js.Object]).asInstanceOf[A])

  extension [A, B](tuple: (js.UndefOr[A], js.UndefOr[B]))
    @targetName("undefMapX2")
    def mapX[T](f: (A, B) => T): js.UndefOr[T] =
      if tuple._1.isDefined && tuple._2.isDefined then js.defined(f(tuple._1.get, tuple._2.get))
      else js.undefined

  extension [A, B, C](tuple: (js.UndefOr[A], js.UndefOr[B], js.UndefOr[C]))
    @targetName("undefMapX3")
    def mapX[T](f: (A, B, C) => T): js.UndefOr[T] =
      if tuple._1.isDefined && tuple._2.isDefined && tuple._3.isDefined then
        js.defined(f(tuple._1.get, tuple._2.get, tuple._3.get))
      else js.undefined

  extension [A, B, C, D](tuple: (js.UndefOr[A], js.UndefOr[B], js.UndefOr[C], js.UndefOr[D]))
    @targetName("undefMapX4")
    def mapX[T](f: (A, B, C, D) => T): js.UndefOr[T] =
      if tuple._1.isDefined && tuple._2.isDefined && tuple._3.isDefined && tuple._4.isDefined then
        js.defined(f(tuple._1.get, tuple._2.get, tuple._3.get, tuple._4.get))
      else js.undefined

  type JSAnyConversionSourceBase = Double|String|Boolean|Int|Double|Float|Byte|Long|js.Object|js.Function|js.Dynamic
  type JSAnyConversionSource = JSAnyConversionSourceBase | js.Array[JSAnyConversionSourceBase] | js.UndefOr[JSAnyConversionSourceBase]

  /** Apply `js.DynamicImplicits.isTruthy` to some common js value types. */
  extension (a: JSAnyConversionSource)
    @targetName("toTruthyValues")
    def toTruthy: Boolean = js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])
  end extension

  /** Convert any of the allowed source types `js.Any`. */
  extension (a: js.UndefOr[JSAnyConversionSource])
    /** Cast. */
    def asJSAny: js.Any = a.asInstanceOf[js.Any]
  end extension

  /** Unsafely convert anything that is wrapped in a `js.UndefOr`. */
  extension [A](a: js.UndefOr[A])
    /** Unsafe cast. */
    def unsafeAsJSAny: js.Any = a.asInstanceOf[js.Any]
  end extension

  extension (a: js.UndefOr[Boolean])
    /** Get the value or return true. */
    def orTrue: Boolean = a.getOrElse(true)

    /** Get the value or return false. */
    def orFalse: Boolean = a.getOrElse(false)

    // /** Flip the boolean if defined. */
    @targetName("flipUndefOrBoolean")
    def flip: js.UndefOr[Boolean] = 
      a match
        case f: Boolean => !f
        case _ => js.undefined
  end extension


  extension (a: js.UndefOr[String])
    /** Return string's "zero" which is an empty string. */
    def orEmpty: String = a.getOrElse("")

    /** Filter out empty string and null. Same as filterTruthy. 
      * What about "all spaces strings"?
      */
    @targetName("filterEmptyUndefOrString")
    def filterEmpty: js.UndefOr[String] = 
      a match
        //case s: String => if s != "" && s != null then s else js.undefined
        case s: String => if s != "" then s else js.undefined
        case _ => js.undefined
  end extension

end undefor
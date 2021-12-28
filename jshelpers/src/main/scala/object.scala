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

object jsobject:
  /** The "combine" methods are shallow, mutable merges, this may not be what you want. */
  extension [A <: js.Object](o: A)
    // def asDict[B]: js.Dictionary[B] = o.asInstanceOf[js.Dictionary[B]]
    // def asAnyDict: js.Dictionary[js.Any] = o.asInstanceOf[js.Dictionary[js.Any]]
    // def asDyn: js.Dynamic = o.asInstanceOf[js.Dynamic]
    // def asJSDyn: js.Dynamic = o.asInstanceOf[js.Dynamic]
    // def asUndefOr: js.UndefOr[A] = js.defined(o)

    // def toTruthy: Boolean = js.DynamicImplicits.truthValue(o.asInstanceOf[js.Dynamic])

    /** Shallow merge with a similar object. */
    def combine(that: A | Unit*): A =
      js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[A]

    /** Combine with a js.Dynamic explicitly. */
    def combineDynamic(that: js.Dynamic*): A =
      js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[A]

    /** Combine with a generic js object or undefined. */
    def combineGeneric(that: js.Object | Unit*): A =
      js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[A]

    /** Combine with something!?! */
    def unsafeCombine(that: js.Any*): A =
      js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[A]

    /** Combine with a generic js object and cast. */
    def combineGenericTo[B](that: js.Object | Unit*): B =
      js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[B]

    /** Combine with a dynamic and cast. */
    def combineDynamicTo[B](that: js.Dynamic*): B =
      js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[B]

    /** `.asInstanceOf[T]` but shorter. Very dangerous! */
    @targetName("asJSObject")
    def as[T]: T = o.asInstanceOf[T]

    /** Duplicate using `js.Object.assign` */
    @targetName("duplicateJSObject")
    def duplicate: A = js.Object.assign(new js.Object{}, o).asInstanceOf[A]

    /** Duplicate then combineDynamic */
    def duplicateCombine(that: js.Dynamic): A =
      js.Object.assign(new js.Object, o, that.asInstanceOf[js.Object]).asInstanceOf[A]

    /** Duplicate then combineDynamic then cast :-). */
    def duplicateCombineTo[B](that: js.Dynamic*): B =
      js.Object.assign(new js.Object, o, that.asInstanceOf[js.Object]).asInstanceOf[B]
  end extension

  /** Dictionary casts. */
  extension [T <: js.Any](self: js.Dictionary[T])
    // def asJsObj = self.asInstanceOf[js.Object]
    // def asDyn = self.asInstanceOf[js.Dynamic]
    def asUndefOr = js.defined(self)

    /** `.asInstanceOf[T]` but shorter. Very dangerous! */
    @targetName("asJSDictionary")
    def as[B <: js.Object]: B = self.asInstanceOf[B]

    /** Duplicate. */
    def duplicate: js.Dictionary[T] =
      js.Object
        .assign(
          new js.Object,
          self.asInstanceOf[js.Object]
        )
        .asInstanceOf[js.Dictionary[T]]
  end extension
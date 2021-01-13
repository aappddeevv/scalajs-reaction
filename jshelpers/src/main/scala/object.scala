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

/** The "combine" methods are shallow, mutable merges, this may not be what you want. */
final class JsObjectOps[A <: js.Object](private val o: A) extends AnyVal {
  @inline def asDict[B] = o.asInstanceOf[js.Dictionary[B]]
  @inline def asAnyDict = o.asInstanceOf[js.Dictionary[js.Any]]
  @inline def asDyn = o.asInstanceOf[js.Dynamic]
  @inline def asUndefOr: js.UndefOr[A] = js.defined(o)

  /** Shallow merge.
   */
  @inline def combine(that: A | Unit*) =
    js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[A]

  /** Combine with a js.Dynamic explicitly. */
  @inline def combineDynamic(that: js.Dynamic*) =
    js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[A]

  /** Combine with a generic js object or undefined. */
  @inline def combineGeneric(that: js.Object | Unit*) =
    js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[A]

  /** Combine with something!?! */
  @inline def unsafeCombine(that: js.Any*) =
    js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[A]

  /** Combine with a generic js object and cast. */
  @inline def combineGenericTo[B](that: js.Object | Unit*) =
    js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[B]

  /** Combine with a dynamic and cast. */
  @inline def combineDynamicTo[B](that: js.Dynamic*) =
    js.Object.assign(o, that.asInstanceOf[Seq[js.Object]]: _*).asInstanceOf[B]

  /** `.asInstanceOf[T]` but shorter. Very dangerous! */
  @inline def as[T] = o.asInstanceOf[T]

  /** Duplicate using `js.Object.assign` */
  @inline def duplicate = js.Object.assign(new js.Object, o).asInstanceOf[A]

  /** Duplicate then combineDynamic */
  @inline def duplicateCombine(that: js.Dynamic) =
    js.Object.assign(new js.Object, o, that.asInstanceOf[js.Object]).asInstanceOf[A]

  /** Duplicate then combineDynamic then cast :-). */
  @inline def duplicateCombineTo[B](that: js.Dynamic*) =
    js.Object.assign(new js.Object, o, that.asInstanceOf[js.Object]).asInstanceOf[B]
}

/** Dictionary casts. */
final class JsDictionaryOps[T <: js.Any](private val self: js.Dictionary[T]) extends AnyVal {
  @inline def asJsObj = self.asInstanceOf[js.Object]
  @inline def asDyn = self.asInstanceOf[js.Dynamic]
  @inline def asUndefOr = js.defined(self)

  /** `.asInstanceOf[T]` but shorter. Very dangerous! */
  @inline def as[B <: js.Object] = self.asInstanceOf[B]

  /** Duplicate. */
  @inline def duplicate =
    js.Object
      .assign(
        new js.Object,
        self.asInstanceOf[js.Object]
      )
      .asInstanceOf[js.Dictionary[T]]
}

trait JsObjectSyntax {
  @inline implicit def jsObjectOpsSyntax[A <: js.Object](a: A): JsObjectOps[A] = new JsObjectOps(a)
  @inline implicit def jsDictionaryOpsSyntax[T <: js.Any](a: js.Dictionary[T]): JsDictionaryOps[T] = new JsDictionaryOps[T](a)
}

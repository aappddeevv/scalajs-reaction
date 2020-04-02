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

/*
String | Unit  => Option[String]: provides a string when Some and js.undefined when None
x.fold[String | Unit](())(x => x)
 */

/**
  * It is common in interop code to model a value as A or null but not undefined
  * even though null and undefined may both mean "absent value." See `|.merge`
  *
  * Note that chaining many `js.|` together probably not work like you think and
  * sometimes its better to create a new target type then target implicits to
  * convert from each individual type (in the or) to the new target type. You
  * must model your type as `A|Null` for this implicit to be picked up.
  * Note that `js.|.orNull` exist in scala.js 1.0 so use that instead of
  * `.get`, `.absorbNull` or `.??`.
  *
  * These methods exist to try and stop a conversion to `UndefOr` or `Option`
  * as part of null processing. Hey! Every bit counts!
  *
  * @todo Perhaps the forge get methods should throw since `.orNull` exists
  * in the scalajs standard library now.
  */
final class OrNullOps[A](private val a: A | Null) extends AnyVal { self =>
  @inline private def forceGet: A = a.asInstanceOf[A]

  /** Convert an A|Null to a well formed Option. Should we check or undefined? */
  @inline def toNonNullOption: Option[A] =
    Option(forceGet)

  /** Like .toNonNullOption */
  @inline def toOption: Option[A] =
    Option(forceGet)

  /** If Null, then false, else true. */
  @inline def toTruthy: Boolean =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) true
    else false

  /** null => undefined, otherwise A. */
  @inline def toUndefOr: js.UndefOr[A] =
    if (a == null) js.undefined
    else js.defined(forceGet)

  @inline def toTruthyUndefOr: js.UndefOr[A] =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]))
      js.defined(forceGet)
    else js.undefined

  @inline def toTruthyOption: Option[A] =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]))
      Option(forceGet)
    else None

  /** Absorb the null and change A|Null => A */
  @inline def absorbNull: A = forceGet

  /** Just get the value, may be null! This will not throw an error. */
  @inline def get = forceGet

  /** Same as get and absorbNull. */
  @inline def ?? = forceGet

  @inline final def collect[B](pf: PartialFunction[A, B]): B | Null =
    if (a != null && pf.isDefinedAt(forceGet)) pf.apply(forceGet).asInstanceOf[B | Null]
    else null.asInstanceOf[B | Null]

  def contains[A1 >: A](elem: A1): Boolean = !isEmpty && forceGet == elem

  @inline final def exists(p: A => Boolean): Boolean = !isEmpty && p(forceGet)

  @inline final def forall(p: A => Boolean): Boolean = isEmpty || p(forceGet)

  @inline def foreach[U](f: A => U): Unit = if (a != null) f(forceGet) else ()

  @inline def isEmpty: Boolean = a == null

  @inline def isDefined: Boolean = a != null

  @inline def isNotDefined = !isDefined

  @inline def knownSize: Int = if (isEmpty) 0 else 1

  @inline final def getOrElse[B >: A](default: => B): B =
    if (isEmpty) default else forceGet

  @inline final def orNull[A1 >: A]: A1 =
    if (a == null) null.asInstanceOf[A1] else forceGet.asInstanceOf[A1]

  @inline def fold[B](ifNull: => B)(ifTrue: A => B) =
    if (a == null) ifNull else ifTrue(forceGet)

  def flatten[B](implicit ev: A <:< |[B, Null]): B | Null =
    if (a == null) null.asInstanceOf[B | Null] else ev(forceGet)

  /** Collapse A|Null => A but the value may be null! You are on your own.
    * Should be called `unsafeMerge`.
    */
  @inline def merge: A = forceGet

  @inline def orElse[B >: A](alternative: => B | Null): B | Null =
    if (isEmpty) alternative else a

  @inline def flatMap[B](f: A => B | Null): B | Null =
    if (a != null) f(forceGet) else null.asInstanceOf[B | Null]

  @inline def map[B](f: A => B): B | Null =
    if (a != null) f(forceGet).asInstanceOf[B | Null] else null.asInstanceOf[B | Null]

  @inline def filter(p: A => Boolean): A | Null =
    if (isEmpty || p(forceGet)) a else null.asInstanceOf[A | Null]

  @inline def withFilter(p: A => Boolean): OrNullOps.WithFilter[A] = new OrNullOps.WithFilter[A](a, p)

  final def zip[A1 >: A, B](that: B | Null): (A1, B) | Null =
    if (a == null || that == null)
      null.asInstanceOf[(A1, B) | Null]
    else
      (forceGet, that.asInstanceOf[B]).asInstanceOf[(A1, B) | Null]

  final def unzip[A1, A2](implicit asPair: A <:< (A1, A2)): (A1 | Null, A2 | Null) =
    if (isEmpty)
      (null.asInstanceOf[A1 | Null], null.asInstanceOf[A2 | Null])
    else {
      val e = asPair(forceGet)
      (e._1.asInstanceOf[A1 | Null], e._2.asInstanceOf[A2 | Null])
    }

  def iterator: collection.Iterator[A] =
    if (isEmpty) collection.Iterator.empty else collection.Iterator.single(forceGet)

  def toList: List[A] = if (isEmpty) List() else new ::(forceGet, Nil)

}

object OrNullOps {
  @inline implicit def localOrNullSyntax[A](a: A | Null): OrNullOps[A] = new OrNullOps[A](a)

  class WithFilter[A](
      self: A | Null,
      p: A => Boolean) {
    def localFilter(p: A => Boolean): A | Null =
      if (self == null || p(self.asInstanceOf[A])) self else null.asInstanceOf[A | Null]
    def map[B](f: A => B): B | Null = localFilter(p).map(f)
    def flatMap[B](f: A => B | Null): B | Null = localFilter(p).flatMap(f)
    def foreach[U](f: A => U): Unit = localFilter(p).foreach(f)
    def withFilter(q: A => Boolean): WithFilter[A] = new WithFilter[A](self, x => p(x) && q(x))
  }
}

trait OrNullSyntax {
  @inline implicit def orNullSyntax[A](a: A | Null): OrNullOps[A] = new OrNullOps[A](a)
}

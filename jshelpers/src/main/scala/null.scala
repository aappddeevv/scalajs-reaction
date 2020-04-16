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
 *
 * These methods exist to try and stop a conversion to `UndefOr` or `Option`
 * as part of null processing. Hey! Every bit counts!
 *
 * @todo Perhaps the force get methods should throw since `.orNull` exists
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

  /** Uh-oh, thought it was `A|Null` but you need to say its a
   * `js.UndefOr[A|Null]` because the docs were wrong :-).
   */
  @inline def toUndefOrNull: js.UndefOr[A | Null] = js.defined(a)

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

  /** Absorb the null and change A|Null => A. Value could still be null but
   * it will no longer by typed that way.
   */
  @inline def absorbNull: A = forceGet

  /** Just get the value, may be null! This will not throw an error. */
  @inline def get = if (a == null) throw new NoSuchElementException("get T|Null") else forceGet

  /** Same as get and absorbNull. */
  //@inline def ? = forceGet

  /** getOrElse but less typing. */
  @inline def ??[B >: A](default: => B): B =
    if (isEmpty) default else forceGet

  /** getOrElse but less typing. */
  @inline def !?[B >: A](default: => B) = getOrElse[B](default)

  /** Experimental */
  @inline def ???[B >: A](other: B | Null): B | Null =
    if (a == null) other else a

  /** Experimental */
  @inline def ????[B >: A](next: A => B | Null): B | Null =
    if (a != null) next(forceGet) else a

  @inline def orElse[B >: A](other: B | Null): B | Null =
    if (a == null) other else a

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

  @inline def fold[B](ifNull: => B)(f: A => B) =
    if (a == null) ifNull else f(forceGet)

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

  class WithFilter[A](self: A | Null, p: A => Boolean) {
    def localFilter(p: A => Boolean): A | Null =
      if (self == null || p(self.asInstanceOf[A])) self else null.asInstanceOf[A | Null]
    def map[B](f: A => B): B | Null = localFilter(p).map(f)
    def flatMap[B](f: A => B | Null): B | Null = localFilter(p).flatMap(f)
    def foreach[U](f: A => U): Unit = localFilter(p).foreach(f)
    def withFilter(q: A => Boolean): WithFilter[A] = new WithFilter[A](self, x => p(x) && q(x))
  }
}

final class Null2[A, B](private val tuple: (A | Null, B | Null)) extends AnyVal {
  def mapX[T](f: (A, B) => T): T | Null =
    if (tuple._1 != null && tuple._2 != null) f(tuple._1.asInstanceOf[A], tuple._2.asInstanceOf[B])
    else null
}

final class Null3[A, B, C](private val tuple: (A | Null, B | Null, C | Null)) extends AnyVal {
  def mapX[T](f: (A, B, C) => T): T | Null =
    if (tuple._1 != null && tuple._2 != null && tuple._3 != null)
      f(tuple._1.asInstanceOf[A], tuple._2.asInstanceOf[B], tuple._3.asInstanceOf[C])
    else null
}

final class Null4[A, B, C, D](private val tuple: (A | Null, B | Null, C | Null, D | Null)) extends AnyVal {
  def mapX[T](f: (A, B, C, D) => T): T | Null =
    if (tuple._1 != null && tuple._2 != null && tuple._3 != null &&
        tuple._4 != null)
      f(tuple._1.asInstanceOf[A], tuple._2.asInstanceOf[B], tuple._3.asInstanceOf[C], tuple._4.asInstanceOf[D])
    else null
}

trait NullLowerOrderImplicits {
  @inline implicit def NullTuple2[A, B](a: (A | Null, B | Null)) =
    new Null2[A, B](a)
  @inline implicit def NullTuple3[A, B, C](a: (A | Null, B | Null, C | Null)) =
    new Null3[A, B, C](a)
  @inline implicit def NullTuple4[A, B, C, D](a: (A | Null, B | Null, C | Null, D | Null)) =
    new Null4[A, B, C, D](a)
}

final class OrUndefOrNullOps[A](private val a: js.UndefOr[A] | Null) extends AnyVal { self =>

  /** Natural transformation. */
  @inline def swap: js.UndefOr[A | Null] =
    if (a == null || a == js.undefined) ().asInstanceOf[js.UndefOr[A | Null]]
    else a.asInstanceOf[js.UndefOr[A | Null]]
}

class OrNullStringOps(private val a: String | Null) extends AnyVal {

  /** Return string's "zero" which is an empty string. */
  @inline def orEmpty: String = if (a == null) "" else a.asInstanceOf[String]
}

trait OrNullSyntax extends NullLowerOrderImplicits {
  @inline implicit def orNullStringOps(a: String | Null) = new OrNullStringOps(a)
  @inline implicit def orNullSyntax[A](a: A | Null) = new OrNullOps[A](a)
  @inline implicit def orUndefOrNullSyntax[A](a: js.UndefOr[A] | Null) = new JsUndefOrNullOps[A](a)
}

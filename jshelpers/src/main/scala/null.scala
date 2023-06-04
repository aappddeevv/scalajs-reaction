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

/*
String | Unit  => Option[String]: provides a string when Some and js.undefined when None
x.fold[String | Unit](())(x => x)
 */

object jsnull:
  /**
   * These extensions are best used when `-Yexplicit-nulls` is used during 
   * compilation because other wise `Null` is a bottom type on all ref-types
   * versus being a direct subtype of Any. If the compiler option is not used
   * then these extensions may be picked up and override `scala-js` ops
   * methods and cause hard-to-diagnose syntax errors. This package typically
   * must be imported separately from the other syntax packages.
   *
   * These methods exist to try and stop a conversion to `UndefOr` or `Option`
   * as part of null processing. Hey! Every bit counts!
   *
   * @todo Perhaps the force get methods should throw since `.orNull` exists
   * in the scalajs standard library now.
   */
  extension [A](a: A | Null)
    private def forceGet: A = a.asInstanceOf[A]

    /** Convert an A|Null to a well formed Option. Should we check for undefined?
     * This method is not really needed here as `.toOption` is safe but
     * we have this signature on other helpers so it shere for consistency.
     */
    // @targetName("toNonNullOptionOrNull")
    // def toNonNullOption: Option[A] = Option(forceGet)

    /** Convert to `Option[A]` but takes into account possible null value. */
    @targetName("nullToNonNullOption")
    def toOptionFilterNull: Option[A] = {
      val g = forceGet
      if g != null then Option(g)
      else None
    }

    /** If Null, then false, else true. */
    @targetName("toTruthyOrNull")
    def toTruthy: Boolean =
      if js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]) then true
      else false

    /** Uh-oh, thought it was `A|Null` but you need to say its a
     * `js.UndefOr[A|Null]` because the docs were wrong :-).
     */
    @targetName("nullToUndefOrNull")
    def toUndefOrNull: js.UndefOr[A | Null] = js.defined(a)

    /** null => undefined, otherwise A. */
    def toUndefOr: js.UndefOr[A] =
      if a == null then js.undefined
      else js.defined(forceGet)

    def toTruthyUndefOr: js.UndefOr[A] =
      if js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]) then
        js.defined(forceGet)
      else js.undefined

    def toTruthyOption: Option[A] =
      if js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]) then
        Option(forceGet)
      else None

    @targetName("filterTruthOrNull")
    def filterTruthy: A | Null =
      if js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]) then a
      else null.asInstanceOf[A | Null]

    /** Absorb the null and change A|Null => A. Value could still be null,
     * which is valid in scala, but it will no longer by typed that way.
     */
    @targetName("nullAbsorbNull")
    def absorbNull: A = forceGet

    /** Get the value or throw if its null. */
    @targetName("nullGet")
    def nullGet: A = 
      if a == null then
        throw new NoSuchElementException("get T|Null") 
      else forceGet

    /** Same as get and absorbNull. */
    //inline def ? = forceGet

    /** getOrElse but less typing. */
    @targetName("getOrElseOrNull2")
    def ??[B >: A](default: => B): B =
      if isEmpty then default else forceGet

    // /** getOrElse but less typing. */
    // inline def !?[B >: A](default: => B): B = getOrElse[B](default)

    // /** Experimental */
    // inline def ???[B >: A](other: B | Null): B | Null =
    //   if (a == null) other else a

    // /** Experimental */
    // inline def ????[B >: A](next: A => B | Null): B | Null =
    //   if (a != null) next(forceGet) else a

    def orElse[B >: A](other: B | Null): B | Null =
      if a == null then other else a

    def collect[B](pf: PartialFunction[A, B]): B | Null =
      if a != null && pf.isDefinedAt(forceGet) then pf.apply(forceGet).asInstanceOf[B | Null]
      else null.asInstanceOf[B | Null]

    def contains[A1 >: A](elem: A1): Boolean = !isEmpty && forceGet == elem

    def exists(p: A => Boolean): Boolean = !isEmpty && p(forceGet)

    def forall(p: A => Boolean): Boolean = isEmpty || p(forceGet)

    def foreach[U](f: A => U): Unit = if a != null then f(forceGet) else ()

    def isEmpty: Boolean = a == null

    def isDefined: Boolean = a != null

    def isNull: Boolean = a == null
    def isNotNull: Boolean = !isNull

    def isNotDefined: Boolean = !isDefined
    def knownSize: Int = if isEmpty then 0 else 1

    @targetName("nullGetOrElse")
    def getOrElse[B >: A](default: => B): B =
      if isEmpty then default else forceGet

    def orNull[A1 >: A]: A1 =
      if a == null then null.asInstanceOf[A1] else forceGet.asInstanceOf[A1]

    def fold[B](ifNull: => B)(f: A => B): B =
      if a == null then
        ifNull 
      else f(forceGet)

    def flatten[B](implicit ev: A <:< |[B, Null]): B | Null =
      if a == null then null.asInstanceOf[B | Null] else ev(forceGet)

    /** Collapse A|Null => A but the value may be null! You are on your own.
     * Should be called `unsafeMerge`.
     */
    def merge: A = forceGet

    def orElse[B >: A](alternative: => B | Null): B | Null =
      if isEmpty then alternative else a

    def flatMap[B](f: A => B | Null): B | Null =
      if a != null then f(forceGet) else null.asInstanceOf[B | Null]

    def map[B](f: A => B): B | Null =
      if a != null then f(forceGet).asInstanceOf[B | Null] else null.asInstanceOf[B | Null]

    def filter(p: A => Boolean): A | Null =
      if isEmpty || p(forceGet) then a else null.asInstanceOf[A | Null]

    def withFilter(p: A => Boolean): OrNull.WithFilter[A] = 
      new OrNull.WithFilter[A](a, p)

    def zip[A1 >: A, B](that: B | Null): (A1, B) | Null =
      if a == null || that == null then
        null.asInstanceOf[(A1, B) | Null]
      else
        (forceGet, that.asInstanceOf[B]).asInstanceOf[(A1, B) | Null]

    def unzip[A1, A2](implicit asPair: A <:< (A1, A2)): (A1 | Null, A2 | Null) =
      if isEmpty then
        (null.asInstanceOf[A1 | Null], null.asInstanceOf[A2 | Null])
      else {
        val e = asPair(forceGet)
        (e._1.asInstanceOf[A1 | Null], e._2.asInstanceOf[A2 | Null])
      }

    def iterator: collection.Iterator[A] =
      if isEmpty then collection.Iterator.empty else collection.Iterator.single(forceGet)

    def toList: List[A] = if isEmpty then List() else new ::(forceGet, Nil)

  object OrNull:
    class WithFilter[A](self: A | Null, p: A => Boolean):
      def localFilter(p: A => Boolean): A | Null =
        if self == null || p(self.asInstanceOf[A]) then self else null.asInstanceOf[A | Null]
      def map[B](f: A => B): B | Null = localFilter(p).map(f)
      def flatMap[B](f: A => B | Null): B | Null = localFilter(p).flatMap(f)
      def foreach[U](f: A => U): Unit = localFilter(p).foreach(f)
      def withFilter(q: A => Boolean): WithFilter[A] = new WithFilter[A](self, x => p(x) && q(x))

  // extension [A, B](tuple: (A | Null, B | Null))
  //   @targetName("nullMapX2")
  //   def mapX[T](f: (A, B) => T): T | Null =
  //     if (tuple._1 != null && tuple._2 != null) f(tuple._1.asInstanceOf[A], tuple._2.asInstanceOf[B])
  //     else null

  // extension [A, B, C](tuple: (A | Null, B | Null, C | Null))
  //   @targetName("nullMapX3")
  //   def mapX[T](f: (A, B, C) => T): T | Null =
  //     if (tuple._1 != null && tuple._2 != null && tuple._3 != null)
  //       f(tuple._1.asInstanceOf[A], tuple._2.asInstanceOf[B], tuple._3.asInstanceOf[C])
  //     else null

  // extension [A, B, C, D](tuple: (A | Null, B | Null, C | Null, D | Null))
  //   @targetName("nullMapX4")
  //   def mapX[T](f: (A, B, C, D) => T): T | Null =
  //     if (tuple._1 != null && tuple._2 != null && tuple._3 != null &&
  //         tuple._4 != null)
  //       f(tuple._1.asInstanceOf[A], tuple._2.asInstanceOf[B], tuple._3.asInstanceOf[C], tuple._4.asInstanceOf[D])
  //     else null

  extension [A](a: js.UndefOr[A] | Null)
    /** Swap `js.UndefOr[A|Null]` for `js.UndefOr[A]|Null`. */
    @targetName("nullSwap")
    inline def swap: js.UndefOr[A | Null] =
      if a == null || a == js.undefined then ().asInstanceOf[js.UndefOr[A | Null]]
      else a.asInstanceOf[js.UndefOr[A | Null]]

  extension (a: String | Null)
    /** Return string's "zero" which is an empty string. Could be called orBlank. */
    @targetName("nullOrEmpty")
    inline def orEmpty: String = if a == null then "" else a.asInstanceOf[String]
    
end jsnull
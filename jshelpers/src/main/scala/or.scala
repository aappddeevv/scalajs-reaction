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

/** Hmm...we could really tighten this up by doing |.merge and typing the picks. */
final case class JsOrOps[A, B](o: A | B) {
  def pickLeft = o.asInstanceOf[A]
  def pickRight = o.asInstanceOf[B]

//   def as[A](implicit ev: A <:< |[A,B]) = (o.merge: scala.Any) match {
//     case a: A if o.instanceOf[A] => Left(a)
//     case b: B if o.instanceOf[B] => Right(b)
//   }
}

trait OrSyntax {
  implicit def jsOrSyntax[A, B](a: A | B): JsOrOps[A,B] = new JsOrOps(a)
}

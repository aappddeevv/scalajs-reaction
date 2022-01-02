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

package ttg
package examples
package helloworld

import scala.scalajs.js
import js.annotation.*
import react.*
import react.conversions.given
import vdom.*
import vdom.styling.*

object EqualityTest {

  @js.native
  @JSImport("Assets/equalitytesting", JSImport.Namespace)
  object EqualityTestingJS extends js.Object {
    def equality2(l: scala.AnyRef, r: scala.AnyRef): Boolean = js.native
    def equality3(l: scala.AnyRef, r: scala.AnyRef): Boolean = js.native
  }

  trait Test1 extends js.Object {
    val intv: Int
    val strv: String
  }

  object Test1 {
    def apply(i: Int, s: String) = new Test1 {
      val intv = i
      val strv = s
    }
  }

  val t1 = Test1(10, "blah")
  val t2 = Test1(10, "blah")
  val t3 = Test1(10, "blah-hah")

  case class STest(intv: Int, strv: String)

  val s1 = STest(10, "blah")
  val s2 = STest(10, "blah")
  val s3 = STest(10, "blah-hah")

  val sc1  = s1.copy(strv = "changed")
  val sc12 = s1.copy(strv = "blah")

  def doit(): ReactNode =
    Fragment(
      p("Equality testing"),
      p(s"js: t1 == t1: ${EqualityTestingJS.equality2(t1, t1)}"),
      p(s"js: t1 == t2: ${EqualityTestingJS.equality2(t1, t2)}"),
      p(s"js: t1 === t1: ${EqualityTestingJS.equality3(t1, t1)}"),
      p(s"js: t1 === t2: ${EqualityTestingJS.equality3(t1, t2)}"),
      p(s"js w/ scala: s1 == s1: ${EqualityTestingJS.equality2(s1, s1)}"),
      p(s"js w/ scala: s1 == s2: ${EqualityTestingJS.equality2(s1, s2)}"),
      p(s"js w/ scala: s1 === s1: ${EqualityTestingJS.equality3(s1, s1)}"),
      p(s"js w/ scala: s1 === s2: ${EqualityTestingJS.equality3(s1, s2)}"),
      p(s"js w/ scala: s1 == sc1: ${EqualityTestingJS.equality2(s1, sc1)}"),
      p(s"js w/ scala: s1 === sc1: ${EqualityTestingJS.equality3(s1, sc1)}"),
      p(s"js w/ scala: s1 == sc12: ${EqualityTestingJS.equality2(s1, sc12)}"),
      p(s"js w/ scala: s1 === sc12: ${EqualityTestingJS.equality3(s1, sc12)}")
    )

}

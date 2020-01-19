
package ttg
package examples
package helloworld

import scala.scalajs.js
import js.annotation._

import react._
import react.implicits._
import vdom._
import vdom.styling._
import vdom.tags._

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

  case class STest(
    intv: Int,
    strv: String)

  val s1 = STest(10, "blah")
  val s2 = STest(10, "blah")
  val s3 = STest(10, "blah-hah")

  val sc1 = s1.copy(strv = "changed")
  val sc12 = s1.copy(strv = "blah")

  def doit(): ReactNode =
    React.Fragment(
      p("Equality testing"),
      p(s"js: t1 == t1: ${EqualityTestingJS.equality2(t1,t1)}"),
      p(s"js: t1 == t2: ${EqualityTestingJS.equality2(t1,t2)}"),
      p(s"js: t1 === t1: ${EqualityTestingJS.equality3(t1,t1)}"),
      p(s"js: t1 === t2: ${EqualityTestingJS.equality3(t1,t2)}"),

      p(s"js w/ scala: s1 == s1: ${EqualityTestingJS.equality2(s1,s1)}"),
      p(s"js w/ scala: s1 == s2: ${EqualityTestingJS.equality2(s1,s2)}"),
      p(s"js w/ scala: s1 === s1: ${EqualityTestingJS.equality3(s1,s1)}"),
      p(s"js w/ scala: s1 === s2: ${EqualityTestingJS.equality3(s1,s2)}"),

      p(s"js w/ scala: s1 == sc1: ${EqualityTestingJS.equality2(s1,sc1)}"),
      p(s"js w/ scala: s1 === sc1: ${EqualityTestingJS.equality3(s1,sc1)}"),
      p(s"js w/ scala: s1 == sc12: ${EqualityTestingJS.equality2(s1,sc12)}"),
      p(s"js w/ scala: s1 === sc12: ${EqualityTestingJS.equality3(s1,sc12)}"),
    )

}

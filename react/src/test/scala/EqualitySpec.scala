
package react

import scala.scalajs.js
import js._
import org.scalatest._

trait T extends js.Object {
  var str: String
}

trait S  {
  var str:  String
}

/** No equals+hash defined. */
class C(val str: String)

/** Equals+hash automatically defined. */
case class CC(val str: String)

class EqualitySpec
    extends FlatSpec
    with Matchers
    with OptionValues {

  val t1 = new T {
    var str = "blah"
  }
  val t2 = new T {
    var str = "blah"
  }
  val t3 = new T {
    var str = "moon"
  }
  val t4 = t1

  val s1 = new S {
    var str = "blah"
  }
  val s2 = new S {
    var str = "blah"
  }
  val s3 = new S {
    var str = "moon"
  }
  val s4 = s1

  val c1 = new C("blah")
  val c2 = new C("blah")
  val c3 = new C("moon")
  val c4 = c1

  val cc1 = new CC("blah")
  val cc2 = new CC("blah")
  val cc3 = new CC("moon")
  val cc4 = cc1

  "JS Equality" should "follow JS semantics for js non-native trait objects and ==" in {
    (t1 == t1) &&
    (t1 != t2) &&
    (t1.equals(t1)) &&
    !(t1.equals(t2)) && 
    (t1 == t4) && 
    (t2 != t4) shouldBe true
  }

  it should "follow scala semantics for scala trait objects" in {
    (s1 == s1) &&
    (s1 != s2) &&
    (s1.equals(s1)) &&
    !(s1.equals(s2)) && 
    (s1 == s4) && 
    (s2 != s4) shouldBe true
  }

  it should "follow scala semantics for scala class objects" in {
    (c1 == c1) &&
    (c1 != c2) &&
    (c1 != c3) &&
    (c1.equals(c1)) &&
    !(c1.equals(c2)) && 
    (c1 == c4) && 
    (c2 != c4) shouldBe true
  }

  it should "follow scala semantics for scala case class objects" in {
    (cc1 == cc1) &&
    (cc1 == cc2) &&
    (cc1 != cc3) &&
    (cc1.equals(cc1)) &&
    (cc1.equals(cc2)) && 
    (cc1 == cc4) && 
    (cc2 == cc4) shouldBe true
  }

  it should "compare keys" in {
    1 == 2 shouldBe false
  }

}


package jshelpers  

import scala.scalajs.js
import utest._

import jshelpers.syntax.jsobject.*
import jshelpers.syntax.dynamic.*
import jshelpers.syntax.any.*


val x = js.Dynamic.literal(a="blah").duplicate

object JSObjectTests extends TestSuite:
  import scala.language.unsafeNulls
  val tests = Tests {
    test("duplicate") {
      val v: js.Dynamic = js.Dynamic.literal(a = "blah")
      assert(v != null)
      val dupe: js.Dynamic = v.asJSObject.duplicate.asJSDyn
      val dupe2: js.Dynamic = js.Object.assign(new js.Object{}, v.asInstanceOf[js.Object]).asInstanceOf[js.Dynamic]
      assert(dupe2 != null)
      assert(!js.isUndefined(dupe2))
      assert(dupe != null)
      assert(!js.isUndefined(dupe))
      assert(dupe.toTruthy)
      assert(dupe.asInstanceOf[Foo].a == "blah")
      assert(dupe.a.asString == "blah")
    }
    test("dynamic cast") {
      val vAsFoo: Foo = js.Dynamic.literal(a = "blah").asInstanceOf[Foo]
      vAsFoo.a = "hah"
      assert(vAsFoo.a == "hah")
    }
  }

@js.native
trait Foo extends js.Object:
  var a: String


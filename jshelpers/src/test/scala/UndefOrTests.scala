package jshelpers  

import scala.scalajs.js
import utest._

import jshelpers.syntax.jsundefor.given

object UndefBooleanTests extends TestSuite:
  val tests = Tests {
    test("boolean or{True,False,flip}"){
      val v: js.UndefOr[Boolean] = js.defined(true) // or = true
      val vundef: js.UndefOr[Boolean] = js.undefined
      v.flip ==> false
      assert(!vundef.flip.isDefined)
      v.orTrue ==> true
      v.orFalse ==> true
      vundef.orTrue ==> true
      vundef.orFalse ==> false
      v.isNull ==> false
      vundef.isNull ==> false
      vundef.orNull ==> null
    }    
  }

object UndefStringTests extends TestSuite:
  val tests = Tests {
    test("string") { 
      val v: js.UndefOr[String] = js.defined("blah") // or = "blah"
      val vundef: js.UndefOr[String] = js.undefined
      v.orEmpty ==> "blah"
      v.filterEmpty ==> "blah"
      vundef.orEmpty ==> ""
      assert(!vundef.filterEmpty.isDefined)
    }
  }

object UndefTests extends TestSuite:
  val tests = Tests {
    test("toTruthy") {
      val v: js.UndefOr[Int] = 10      
      val vundef: js.UndefOr[Int] = js.undefined
      v.toTruthy ==> true
      vundef.toTruthy ==> false
    }
    test("null") { 
      val v: js.UndefOr[Int] = 10
      // just needs to compile
      val v2: js.UndefOr[Int|Null] = v.toUndefOrNull
      val v3: Int | Null =  v.toNull
    }
    test("istotalEmpty") {
      val v: js.UndefOr[Int] = 10
      val vundef: js.UndefOr[Int] = js.undefined
      val vnull: js.UndefOr[Int] = null.asInstanceOf[js.UndefOr[Int]]
      v.isTotalEmpty ==> false
      vundef.isTotalEmpty ==> true
      vnull.isTotalEmpty ==> true
    }
  }  

object UndefMapXTests extends TestSuite:
  val tests = Tests { 
    test("mapx") {
      val v1: js.UndefOr[Int] = 10
      val v2: js.UndefOr[String] = "blah"
      val v3: js.UndefOr[Boolean] = true
      val v4: js.UndefOr[Double] = 10.0

      (v1,v2).mapX { (i, s) => assert(i == 10 && s == "blah") }
      (v1,v2,v3).mapX { (i, s, b) => assert(i == 10 && s == "blah" && b == true) }
      (v1,v2,v3,v4).mapX { (i, s, b, d) => assert(i == 10 && s == "blah" && b == true && d == 10.0) }
    }
  }

object JSObjectTests extends TestSuite:
  import jshelpers.syntax.jsdynamic.given
  val tests = Tests {
    test("duplicate") {
      val v: js.Dynamic = js.Dynamic.literal(a = "blah")
      val vAsFoo: Foo = v.asInstanceOf[Foo]
      val dupe: js.Dynamic = v.duplicate
      assert(dupe != null && dupe.toTruthy)
      vAsFoo.a = "hah"
      assert(dupe.asInstanceOf[Foo].a == "blah")
      assert(dupe.a.asInstanceOf[String] == "blah")
      assert(vAsFoo.a == "hah")
    }
  }

@js.native
trait Foo extends js.Object:
  var a: String


package jshelpers  

import scala.scalajs.js
import utest.*

import jshelpers.syntax.jsnull.*

object NullTests extends TestSuite:

  val tests = Tests {
    test("basics") {            
      val y: Int|Null = null
      val z: Int|Null = 10
      assert(y.toOptionFilterNull == None && z.toOptionFilterNull == Some(10))
    }
    test("isEmpty") {
      val y: String | Null = null
      val x: String = ""
      assert(y.isEmpty && x.nn.isEmpty)
    }
    test("plain UndefOr[T|Null].isEmpty, no syntax imports") {
      val z: js.UndefOr[String | Null] = js.undefined
      //println(s"blah: $z")
      assert(js.isUndefined(z))
      //assert(z.isEmpty)
    }
    test("absorbNull") {
      val y: String | Null = null
      val x: String | Null = "blah"
      assert(x.absorbNull == "blah")
      // will not compile, under -Yexplicit-nulls
      //assert(y.absorbNull == null)
    }
  }
  
object OrNullStringTests extends TestSuite:
  val tests = Tests {
    test("string with null") {
      val v: String | Null = null
      val v2: String | Null = "blah"
      assert(v.orEmpty == "" && v2.orEmpty != "")
    }
  }



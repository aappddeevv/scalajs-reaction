package jshelpers  

import scala.scalajs.js
import utest.*

import jshelpers.syntax.jsnull.*
//import scala.language.unsafeNulls

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
  }
  
object OrNullStringTests extends TestSuite:
  val tests = Tests {
    test("string with null") {
      val v: String | Null = null
      val v2: String | Null = "blah"
      assert(v.orEmpty == "" && v2.orEmpty != "")
    }
  }



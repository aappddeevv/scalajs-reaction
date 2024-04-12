package jshelpers  

import scala.scalajs.js
import utest.*

import jshelpers.syntax.undefor.*

object UndefBooleanTests extends TestSuite:

  val tests = Tests {
    test("undef") {      
      import jshelpers.syntax.jsnull.*
      () ==> js.undefined
      val x: js.UndefOr[Int|Null] = js.undefined
      x ==> js.undefined
      val y: Int|Null = null
      // won't compile with -Yexplicit-nulls,must have `Int|Null`
      //val z: Int = null
    }

    test("boolean or{True,False,flip}"){
      import scala.language.unsafeNulls
      val v: js.UndefOr[Boolean] = js.defined(true) // or = true
      val vundef: js.UndefOr[Boolean] = js.undefined // same as the value ()
      v.flip ==> false
      //v.map(a => !a).getOrElse(true) ==> false
      assert(!vundef.flip.isDefined)
      v.orTrue ==> true
      v.orFalse ==> true
      vundef.orTrue ==> true
      vundef.orFalse ==> false
      v.isNull ==> false
      vundef.isNull ==> false
      // fails tt runtime! why?
      //().orNull ==> null
      //vundef.orNull ==> null
    }    
  }

object UndefStringTests extends TestSuite:
  val tests = Tests {
    test("string") {
      val v: js.UndefOr[String] = js.defined("blah") // or = "blah"
      val v2: js.UndefOr[String] = js.defined("")
      val vundef: js.UndefOr[String] = js.undefined
      v.orEmpty ==> "blah"
      v.filterEmpty ==> "blah"
      v2.filterEmpty ==> js.undefined
      vundef.orEmpty ==> ""
      assert(!vundef.filterEmpty.isDefined)
    }
  }
  
object UndefTests extends TestSuite:
  val tests = Tests {
    test("basics") { 
      val v: js.UndefOr[Int] = null.asInstanceOf[js.UndefOr[Int]]
      v.isNull ==> true
    }
    test("toTruthy") {
      val v: js.UndefOr[Int] = 10
      val vundef: js.UndefOr[Int] = js.undefined
      v.toTruthy ==> true
      vundef.toTruthy ==> false
    }
    test("null") { 
      import jshelpers.syntax.jsnull.*
      val v: js.UndefOr[Int] = 10
      // just needs to compile
      val v2: js.UndefOr[Int|Null] = v.toUndefOrNull
      val v3: Int | Null =  v.toNull
      val v4: js.UndefOr[Int | Null] = js.defined(null)
      assert(v4.isEmpty)
    }
    test("istotalEmpty") {
      val v: js.UndefOr[Int] = 10
      val vundef: js.UndefOr[Int] = js.undefined
      val vnull: js.UndefOr[Int] = null.asInstanceOf[js.UndefOr[Int]]
      v.isTotalEmpty ==> false
      vundef.isTotalEmpty ==> true
      vnull.isTotalEmpty ==> true
    }
    test("toTruthy") {
      (js.defined("blah"):js.UndefOr[String]).toTruthy ==> true
      (js.defined(""):js.UndefOr[String]).toTruthy ==> false
      (null.asInstanceOf[js.UndefOr[String]]).toTruthy ==> false
      (js.defined(0):js.UndefOr[Int]).toTruthy ==> false
      (js.defined(1):js.UndefOr[Int]).toTruthy ==> true
    }
    test("js.Undefor[T|Null].absorbNull, requires import jshelpers.syntax.undefor.*") {      
      val y: js.UndefOr[String | Null] = null
      val x: js.UndefOr[String | Null] = "blah"
      val z: js.UndefOr[String | Null] = js.undefined
      assert(!z.absorbNull.isDefined && !z.isDefined && z.isEmpty && y.isEmpty && x.absorbNull == "blah")
    }
    test("toNonNullOption") {
      val v: js.UndefOr[Int] = 10
      val vundef: js.UndefOr[Int] = js.undefined
      v.toNonNullOption ==> Some(10)
      vundef.toNonNullOption ==> None
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

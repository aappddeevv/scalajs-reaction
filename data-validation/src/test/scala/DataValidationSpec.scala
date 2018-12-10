// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.data
package validation

import org.scalatest._

import cats._
import cats.data._
import cats.implicits._

trait ValidatedEntity {
  val field1: Result[String]
}

class DataValidationSpecs extends FlatSpec
    with OptionValues
    with Matchers {

  import StrictApplicativeAlgebra._
  type FF[T] = Either[NonEmptyChain[String], T]
  type EE = NonEmptyChain[String]
  val ops = ValidationAlgebra[FF, EE]

  import ops._

  val str1 = "blah hah"

  "Validation" should "work with Either[NonEmptyChain[String], T]" in {
    val r0v = startsWith("blah")(str1)
    r0v shouldBe Right("blah hah")

    val r1v = startsWith("hah") //(str1)
    val r1 = r1v(str1)
    r1 shouldBe Left(NonEmptyChain.one("must start with 'hah'"))
    println(s"startswith: $r0v, $r1")
    val r2 = notBlank(str1)
    val r3 = notBlank("")
    println(s"notblank: $r2, $r3")
    r2 shouldBe Right(str1)
    r3 shouldBe Left(NonEmptyChain.one("must not be blank"))

    val r4 = notNull(null)
    val r5 = notNull("blah")
    println(s"notnull: $r4, $r5")
    r4 shouldBe Left(NonEmptyChain.one("is null"))
    r5 shouldBe Right("blah")

    // fail faster
    val add1 = (r0v, r1).mapN(_ + " - " + _) // Some(blah hah - blah hah)
    println(s"add1: $add1")
    add1 shouldBe Left(NonEmptyChain.one("must start with 'hah'"))
    val add2 = (r0v, r2).mapN(_ + " - " + _) // None
    println(s"add2: $add2")
    add2 shouldBe Right("blah hah - blah hah")
    val add3 = (r0v, r2).parMapN(_ + " - " + _) // Some(...)
      println(s"add3: $add3")
    add3 shouldBe Right("blah hah - blah hah")
    val add4 = (startsWith("hah")(str1), startsWith("foo")(str1))
      .parMapN(_ + " - " + _) // Some(...)
      println(s"add4: $add4")
    add4 shouldBe Left(NonEmptyChain("must start with 'hah'", "must start with 'foo'"))
  }

  it should "work in parallel" in {
    // parallel
    val invalid = "choose a place"
    val valid = "choo"
    val validator1 =
      (startsWith("ch")(valid), maxLength(5)(valid), notBlank(valid))
        .mapN((v1, v2, v3) => v1)
    println(s"validator1: $validator1")
    validator1 shouldBe Right("choo")
//    val validator2 = (startsWith("ch"), maxLength(5), notBlank)
//      .mapN((v1,v2,v3) => v1)

    // sequential composition
    val rule1 = startsWith("ch") andThen maxLength(5) andThen notBlank
    val rule1result1 = rule1(valid)
    val rule1result2 = rule1(invalid)
    println(s"rules: $rule1result1, $rule1result2")
    rule1result1 shouldBe Right("choo")
    rule1result2 shouldBe Left(NonEmptyChain.one("length must be > 5"))
  }

  it should "work with lists of and conjunctions" in {
    val andrule1 = andAll(startsWith("blah"), notBlank)(str1)
    val andrule2 = andAll(startsWith("hah"), notBlank)(str1)
    val andrule3 = andAll(startsWith("hah"), notBlank, notBlank)("")
    val andrule4 = andAll(startsWith("hah"),
                          notBlank,
                          notBlank,
                          andAll(startsWith("foo"), notBlank))("")
    println(s"andAll:\n1: $andrule1,\n2: $andrule2,\n3: $andrule3,\n4: $andrule4")
    andrule1 shouldBe Right("blah hah")
    andrule2 shouldBe Left(NonEmptyChain.one("must start with 'hah'"))
    andrule3 shouldBe Left(NonEmptyChain("must start with 'hah'",
      "must not be blank", "must not be blank"))
    andrule4 shouldBe Left(NonEmptyChain("must start with 'hah'",
      "must not be blank", "must not be blank",
      "must start with 'foo'", "must not be blank"))
  }

  it should "work with lists of or conjunctions" in {
    val orrule1 = orAll(startsWith("blah"), notBlank)(str1) // pass
    val orrule2 = orAll(blank, startsWith("blah"))(str1) // pass
    val orrule3 = orAll(blank, startsWith("blah"))("hah") // fail
    val orrule4 = orAll(blank, startsWith("blah"), startsWith("hah"))("hah") // pass
    val orrule5 =
      orAll(
        blank,
        startsWith("blah"),
        andAll(startsWith("hah"), notBlank)
      )("hah") // pass
    println(
      s"orAll:\n1: $orrule1,\n2: $orrule2,\n3: $orrule3,\n4: $orrule4,\n5: $orrule5")
    orrule1 shouldBe Right("blah hah")
    orrule2 shouldBe Right("blah hah")
    orrule3 shouldBe Left(NonEmptyChain("must be blank", "must start with 'blah'"))
    orrule4 shouldBe Right("hah")
    orrule5 shouldBe Right("hah")

    // val x = new ValidatedEntity {
    //   val field1 = Validated.Valid("blah")
    // }
    // println(s"object test ${x.field1}")

  }
}

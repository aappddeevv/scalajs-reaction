package jshelpers

import scala.scalajs.js
import js.|

/**
  * A data type commonly found when working with dropdown/labels.
  */
final case class StringIntMiscOrSyntax(a: String | Int) {
  @inline def asInt: Int = a.asInstanceOf[Int]
  @inline def asString: String = a.asInstanceOf[String]
}

trait MiscOrSyntax {
  @inline implicit def stringIntMiscOrSyntax(a: String | Int) = StringIntMiscOrSyntax(a)
}


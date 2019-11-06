// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._

/**
  * A data type commonly found when working with dropdown/labels.
  */
final case class StringIntMiscOrSyntax(a: String | Int) {
  @inline def asInt: Int       = a.asInstanceOf[Int]
  @inline def asString: String = a.asInstanceOf[String]
}

trait MiscOrSyntax {
  @inline implicit def stringIntMiscOrSyntax(a: String | Int) = StringIntMiscOrSyntax(a)
}

final case class JsRefOps[T](v: ReactRef[T]) {
  @inline def toDyn: js.Dynamic = refToJs[T](v)
}

trait JsRefSyntax {
  implicit def refToJsRefOps[T](v: ReactRef[T]) = JsRefOps(v)
}

// order matters here based on implicit search through hierarchy
trait AllSyntax
    extends ComponentSyntax
    with ValueSyntax
    with JsDynamicSyntax
    with JsObjectSyntax
    with JsAnySyntax
    with OrNullSyntax
    with ScalaMappedSyntax
    with OptionSyntax
    with JsUndefOrSyntax
    with MiscOrSyntax
    with JsRefSyntax
    with JSPromiseSyntax

object syntax {
  object all         extends AllSyntax
  object component   extends ComponentSyntax
  object value   extends ValueSyntax
  object jsdynamic   extends JsDynamicSyntax
  object jsundefor   extends JsUndefOrSyntax
  object jsobject    extends JsObjectSyntax
  object jsany       extends JsAnySyntax
  object scalamapped extends ScalaMappedSyntax
  object ornull      extends OrNullSyntax
  object option      extends OptionSyntax
  object miscor      extends MiscOrSyntax
  object jsref extends JsRefSyntax
  object jspromise extends JSPromiseSyntax
}

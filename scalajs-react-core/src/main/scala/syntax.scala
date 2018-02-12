// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._
import js.JSConverters._
import js.Dynamic.{literal => lit}

final case class JsAnyOps(a: js.Any) {
  def asJsObj: js.Object        = a.asInstanceOf[js.Object]
  def asDyn: js.Dynamic         = a.asInstanceOf[js.Dynamic]
  def asString: String          = a.asInstanceOf[String]
  def asNumer: Number           = a.asInstanceOf[Number]
  def asInt: Int                = a.asInstanceOf[Int]
  def asDouble: Double          = a.asInstanceOf[Double]
  def asBoolean: Boolean        = a.asInstanceOf[Boolean]
  def asJsArray[A]: js.Array[A] = a.asInstanceOf[js.Array[A]]
  def asJson: String            = js.JSON.stringify(a)
}

trait JsAnySyntax {
  implicit def jsAnyOpsSyntax(a: js.Any) = new JsAnyOps(a)
}

final case class JsObjectOps(o: js.Object) {
  def asDict[A] = o.asInstanceOf[js.Dictionary[A]]
  def asAnyDict = o.asInstanceOf[js.Dictionary[js.Any]]
  def asDyn     = o.asInstanceOf[js.Dynamic]
}

final case class JsDictionaryOps(o: js.Dictionary[_]) {
  def asJsObj = o.asInstanceOf[js.Object]
  def asDyn   = o.asInstanceOf[js.Dynamic]
}

trait JsObjectSyntax {
  implicit def jsObjectOpsSyntax(a: js.Object)           = new JsObjectOps(a)
  implicit def jsDictonaryOpsSyntax(a: js.Dictionary[_]) = new JsDictionaryOps(a)
}

final case class JsUndefOrStringOps(a: UndefOr[String]) {
  def orEmpty: String = a.getOrElse("")
}

/** Not sure this is really going to do much for me. */
final case class JsUndefOrOps[A](a: UndefOr[A]) {
  def isNull  = a == null
  def isEmpty = isNull || !a.isDefined
}

trait JsUndefOrSyntax {
  implicit def jsUndefOrOpsSyntax[A](a: UndefOr[A])   = JsUndefOrOps(a)
  implicit def jsUndefOrStringOps(a: UndefOr[String]) = JsUndefOrStringOps(a)
}

final case class JsDynamicOps(val jsdyn: js.Dynamic) {
  def asString: String            = jsdyn.asInstanceOf[String]
  def asInt: Int                  = jsdyn.asInstanceOf[Int]
  def asArray[A]: js.Array[A]     = jsdyn.asInstanceOf[js.Array[A]]
  def asBoolean: Boolean          = jsdyn.asInstanceOf[Boolean]
  /** @deprecated use asJsObj */
  def asJSObj: js.Object          = jsdyn.asInstanceOf[js.Object]
  // was just asJsObj does the cast help? can we remove asJsObjSub
  def asJsObj: js.Object          = jsdyn.asInstanceOf[js.Object]
  def asDict[A]: js.Dictionary[A] = jsdyn.asInstanceOf[js.Dictionary[A]]
  // variance annotation needed?
  def asUndefOr[A]: js.UndefOr[A] = jsdyn.asInstanceOf[js.UndefOr[A]]
  def asJsObjSub[A <: js.Object]  = jsdyn.asInstanceOf[A] // assumes its there!
  def asJsArray[A <: js.Object]   = jsdyn.asInstanceOf[js.Array[A]]
}

trait JsDynamicSyntax {
  implicit def jsDynamicOpsSyntax(jsdyn: js.Dynamic) = JsDynamicOps(jsdyn)
}

final case class ComponentOps[S, RP, A](c: Component[_, _, _, _]) {
  @inline def toEl: ReactElement = elements.element(c)
  @inline def toEl(key: Option[String] = None, ref: Option[RefCb] = None) =
    elements.element(c, key, ref)
}

trait ComponentSyntax {
  implicit def componentOpsSyntax[S, RP, A](c: Component[_, _, _, _]) =
    ComponentOps[S, RP, A](c)
}

trait ElementConversionSyntax {
  @inline implicit def stringToElementImpl(v: String): ReactNode = stringToElement(v)
  // this is actually a direct conversion, move to a separate syntax to be explicit?
  @inline implicit def arrToElementImpl(v: js.Array[ReactNode]): ReactNode = arrayToElement(v)
}

trait AllSyntax
    extends ComponentSyntax
    with ElementConversionSyntax
    with JsDynamicSyntax
    with JsUndefOrSyntax
    with JsObjectSyntax
    with JsAnySyntax

object syntax {
  object all extends AllSyntax
  object component extends ComponentSyntax
  object element extends ElementConversionSyntax
  object jsdynamic     extends JsDynamicSyntax
  object jsundefor     extends JsUndefOrSyntax
  object jsobject      extends JsObjectSyntax
  object jsany         extends JsAnySyntax
}

trait C2E {
  @inline implicit def c2e(c: Component[_, _, _, _]): ReactNode = elements.element(c)
}

trait AllInstances extends C2E

object instances {
  object all extends AllInstances
  object component extends C2E
}

object implicits extends AllSyntax with AllInstances

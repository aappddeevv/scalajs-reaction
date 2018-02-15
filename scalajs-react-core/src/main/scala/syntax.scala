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
  @inline def asJsObj: js.Object = a.asInstanceOf[js.Object]
  @inline def asDyn: js.Dynamic = a.asInstanceOf[js.Dynamic]
  @inline def asString: String = a.asInstanceOf[String]
  @inline def asNumer: Number = a.asInstanceOf[Number]
  @inline def asInt: Int = a.asInstanceOf[Int]
  @inline def asDouble: Double = a.asInstanceOf[Double]
  @inline def asBoolean: Boolean = a.asInstanceOf[Boolean]
  @inline def asJsArray[A]: js.Array[A] = a.asInstanceOf[js.Array[A]]
  @inline def asJson: String = js.JSON.stringify(a)
  @inline def toStringJs = a.asInstanceOf[js.Any].toString()
  @inline def toNonNullOption[T <: js.Any]: Option[T] = {
    // also defined in react package, repeated here
    if (js.isUndefined(a)) None
    else Option(a.asInstanceOf[T])
  }
}

trait JsAnySyntax {
  implicit def jsAnyOpsSyntax(a: js.Any) = new JsAnyOps(a)
}

final case class JsObjectOps(o: js.Object) {
  @inline def asDict[A] = o.asInstanceOf[js.Dictionary[A]]
  @inline def asAnyDict = o.asInstanceOf[js.Dictionary[js.Any]]
  @inline def asDyn = o.asInstanceOf[js.Dynamic]
}

final case class JsDictionaryOps(o: js.Dictionary[_]) {
  @inline def asJsObj = o.asInstanceOf[js.Object]
  @inline def asDyn = o.asInstanceOf[js.Dynamic]
}

trait JsObjectSyntax {
  implicit def jsObjectOpsSyntax(a: js.Object) = new JsObjectOps(a)
  implicit def jsDictonaryOpsSyntax(a: js.Dictionary[_]) = new JsDictionaryOps(a)
}

final case class JsUndefOrStringOps(a: UndefOr[String]) {
  @inline def orEmpty: String = a.getOrElse("")
}

final case class JsUndefOrBooleanOps(a: UndefOr[Boolean]) {
  @inline def orTrue: Boolean = a.getOrElse(true)
  @inline def orFalse: Boolean = a.getOrElse(false)
}

final case class JsUndefOrOps[A](a: UndefOr[A]) {
  @inline def isNull = a == null
  @inline def isEmpty = isNull || !a.isDefined
  @inline def toNonNullOption = if (a.isEmpty) None else a.toOption
  @inline def toStringJs = a.asInstanceOf[js.Any].toString()
}

trait JsUndefOrSyntax {
  implicit def jsUndefOrOpsSyntax[A](a: UndefOr[A]) = JsUndefOrOps(a)
  implicit def jsUndefOrStringOps(a: UndefOr[String]) = JsUndefOrStringOps(a)
  implicit def jsUndefOrBooleanOps(a: UndefOr[Boolean]) = JsUndefOrBooleanOps(a)
}

final case class JsDynamicOps(val jsdyn: js.Dynamic) {
  def asString: String = jsdyn.asInstanceOf[String]
  def asInt: Int = jsdyn.asInstanceOf[Int]
  def asArray[A]: js.Array[A] = jsdyn.asInstanceOf[js.Array[A]]
  def asBoolean: Boolean = jsdyn.asInstanceOf[Boolean]

  /** @deprecated use asJsObj */
  def asJSObj: js.Object = jsdyn.asInstanceOf[js.Object]
  // was just asJsObj does the cast help? can we remove asJsObjSub
  def asJsObj: js.Object = jsdyn.asInstanceOf[js.Object]
  def asDict[A]: js.Dictionary[A] = jsdyn.asInstanceOf[js.Dictionary[A]]
  // variance annotation needed?
  def asUndefOr[A]: js.UndefOr[A] = jsdyn.asInstanceOf[js.UndefOr[A]]
  def asJsObjSub[A <: js.Object] = jsdyn.asInstanceOf[A] // assumes its there!
  def asJsArray[A <: js.Object] = jsdyn.asInstanceOf[js.Array[A]]
  def asOption[T <: js.Object]: Option[T] =
    if (js.DynamicImplicits.truthValue(jsdyn)) Some(jsdyn.asInstanceOf[T])
    else None
}

trait JsDynamicSyntax {
  implicit def jsDynamicOpsSyntax(jsdyn: js.Dynamic) = JsDynamicOps(jsdyn)
}

final case class ComponentOps[S, RP, A](c: Component[_, _, _, _, _, _]) {
  @inline def toEl: ReactElement = elements.element(c)
  @inline def toEl(key: Option[String] = None, ref: Option[RefCb] = None) =
    elements.element(c, key, ref)
}

trait ComponentSyntax {
  implicit def componentOpsSyntax[S, RP, A](c: Component[_, _, _, _, _, _]) =
    ComponentOps[S, RP, A](c)
}

// trait ElementConversionSyntax {
//   @inline implicit def stringToElementImpl(v: String): ReactNode = stringToElement(v)
//   // this is actually a direct conversion, move to a separate syntax to be explicit?
//   @inline implicit def arrToElementImpl(v: js.Array[ReactNode]): ReactNode = arrayToElement(v)
// }

trait AllSyntax
    extends ComponentSyntax
    //with ElementConversionSyntax
    with JsDynamicSyntax
    with JsUndefOrSyntax
    with JsObjectSyntax
    with JsAnySyntax

object syntax {
  object all extends AllSyntax
  object component extends ComponentSyntax
  //object element extends ElementConversionSyntax
  object jsdynamic extends JsDynamicSyntax
  object jsundefor extends JsUndefOrSyntax
  object jsobject extends JsObjectSyntax
  object jsany extends JsAnySyntax
}

trait C2E {
  @inline implicit def c2e(c: ComponentAny): ReactNode = elements.element(c)
}

/**
  * Mostly evil converters. Watch out for these automatic conversions. All are
  * prefixed with _ so you can define your own and not get tripped on namespace.
  */
trait MiscConverters {
  @inline implicit def _jsArrayToElement[T <: ReactNode](arr: js.Array[T]) = arrayToElement(arr)
  @inline implicit def _stringToElement(s: String): ReactNode = stringToElement(s)

  @inline implicit def _seqToElement[T <: ReactNode](s: Seq[T]) = arrayToElement(s)
  @inline implicit def _intToElement(i: Int): ReactNode = i.asInstanceOf[ReactNode]
  @inline implicit def _doubleToElement(d: Double): ReactNode = d.asInstanceOf[ReactNode]
  @inline implicit def _floatToElement(f: Float): ReactNode = f.asInstanceOf[ReactNode]
  @inline implicit def _booleanToElement(b: Boolean): ReactNode = b.asInstanceOf[ReactNode]
  @inline implicit def _optToElement(s: Option[ReactElement]): ReactNode =
    s.getOrElse(null.asInstanceOf[ReactNode])
  @inline implicit def _iterableToElement[T](s: Iterable[T])(implicit cv: T => ReactNode): ReactNode = {
    s.map(cv).toJSArray.asInstanceOf[ReactElement]
  }
  // keep separate for *just* component conversion.
  //@inline implicit def _componentToElement(c: ComponentAny): ReactElement = elements.element(c)
}

trait AllInstances extends C2E with MiscConverters

/** Instances are the wrong word, converters would be better. */
object instances {
  object all extends AllInstances
  object component extends C2E
  object any extends MiscConverters
}

object implicits extends AllSyntax with AllInstances

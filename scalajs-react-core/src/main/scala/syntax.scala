// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._
import js.JSConverters._
import js.Dynamic.{literal => lit}

/**
 * It is common in interop code to model a value as A or null but not undefined.
 */
final case class OrNullOps[A <: js.Any](a: A | Null) {
  /** Convert an A|Null to a well formed option. */
  @inline def toNonNullOption: Option[A] =
    if(a == null) Option.empty[A]
    else Some(a.asInstanceOf[A])

  /** If Null, then false, else true. */
  @inline def toTruthy: Boolean =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) true
    else false

  /** null => undefined, otherwise A. */
  @inline def toUndefOr: js.UndefOr[A] =
    if(a == null) js.undefined
    else js.defined(a.asInstanceOf[A])
}

trait OrNullSyntax{
  implicit def orNullSyntax[A <: js.Any](a: A|Null): OrNullOps[A] = OrNullOps[A](a)
}

// /**
//  * It is common in interop code to model a value as A, null or undefined so
//  * model it explicitly vs trying to let clients *not* have to use a bunch of
//  * '.to*' calls in a row. Will this conflict with plain ole js.UndefOr syntax?
//  */
// final case class UndefOrNullOps[A <: js.Any|Null](a: js.UndefOr[A]) {
//   /** Convert an js.UndefOr[A|Null] to a well formed option. */
//   @inline def toNonNullOption: Option[A] =
//     if(a.asInstanceOf[js.Any] == null) Option.empty[A]
//     else Some(a.asInstanceOf[A])
// }

// trait UndefOrNullSyntax{
//   implicit def undefOrNullSyntax[A|Null <: js.Any|Null](a: A|Null): UndefOrNullOps[A] = UndefOrNullOps[A](a)
// }

final case class JsAnyOps(a: js.Any) {
  @inline def asJsObj: js.Object        = a.asInstanceOf[js.Object]
  @inline def asDyn: js.Dynamic         = a.asInstanceOf[js.Dynamic]
  @inline def asString: String          = a.asInstanceOf[String]
  @inline def asNumer: Number           = a.asInstanceOf[Number]
  @inline def asInt: Int                = a.asInstanceOf[Int]
  @inline def asDouble: Double          = a.asInstanceOf[Double]
  @inline def asBoolean: Boolean        = a.asInstanceOf[Boolean]
  @inline def asJsArray[A]: js.Array[A] = a.asInstanceOf[js.Array[A]]
  @inline def asJson: String            = js.JSON.stringify(a)
  @inline def toStringJs                = a.asInstanceOf[js.Any].toString()
  @inline def toNonNullOption[T <: js.Any]: Option[T] = {
    // also defined in react package, repeated here
    if (js.isUndefined(a) || a==null) None
    else Option(a.asInstanceOf[T])
  }
  @inline def toTruthy: Boolean =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) true
    else false
}

trait JsAnySyntax {
  implicit def jsAnyOpsSyntax(a: js.Any): JsAnyOps = JsAnyOps(a)
}

final case class JsObjectOps[A <: js.Object](o: A) {
  @inline def asDict[B]                   = o.asInstanceOf[js.Dictionary[B]]
  @inline def asAnyDict                   = o.asInstanceOf[js.Dictionary[js.Any]]
  @inline def asDyn                       = o.asInstanceOf[js.Dynamic]
  @inline def asUndefOr[A]: js.UndefOr[A] = o.asInstanceOf[js.UndefOr[A]]  
  @inline def combine[B <: js.Object](that: js.Object)        = react.merge(o, that).asInstanceOf[B]
  //@inline def combine(that: js.Dictionary[_]) = merge(o, that.asInstanceOf[js.Object])
}

final case class JsDictionaryOps(o: js.Dictionary[_]) {
  @inline def asJsObj = o.asInstanceOf[js.Object]
  @inline def asDyn   = o.asInstanceOf[js.Dynamic]
  // @inline def combine(that: js.Dictionary[_]) =
  //   merge(o.asInstanceOf[js.Object], that.asInstanceOf[js.Object]).asInstanceOf[js.Dictionary[_]]
}

trait JsObjectSyntax {
  implicit def jsObjectOpsSyntax[A <: js.Object](a: A)           = new JsObjectOps(a)
  implicit def jsDictionaryOpsSyntax(a: js.Dictionary[_]) = new JsDictionaryOps(a)
}

final case class JsUndefOrStringOps(a: UndefOr[String]) {
  @inline def orEmpty: String = a.getOrElse("")
}

final case class JsUndefOrBooleanOps(a: UndefOr[Boolean]) {
  @inline def orTrue: Boolean  = a.getOrElse(true)
  @inline def orFalse: Boolean = a.getOrElse(false)
}

final case class JsUndefOrOps[A](a: UndefOr[A]) {
  @inline def isNull          = a == null
  @inline def isEmpty         = isNull || !a.isDefined
  @inline def toNonNullOption =
    if (a.isEmpty || a == null) None
    else a.toOption
  @inline def toStringJs      = a.asInstanceOf[js.Any].toString()
  @inline def toTruthy: Boolean =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) true
    else false  
}

trait JsUndefOrSyntax {
  implicit def jsUndefOrOpsSyntax[A](a: UndefOr[A])     = JsUndefOrOps(a)
  implicit def jsUndefOrStringOps(a: UndefOr[String])   = JsUndefOrStringOps(a)
  implicit def jsUndefOrBooleanOps(a: UndefOr[Boolean]) = JsUndefOrBooleanOps(a)
}

final case class JsDynamicOps(val jsdyn: js.Dynamic) {
  def asString: String        = jsdyn.asInstanceOf[String]
  def asInt: Int              = jsdyn.asInstanceOf[Int]
  def asArray[A]: js.Array[A] = jsdyn.asInstanceOf[js.Array[A]]
  def asBoolean: Boolean      = jsdyn.asInstanceOf[Boolean]

  /** @deprecated use asJsObj */
  def asJSObj: js.Object = jsdyn.asInstanceOf[js.Object]
  // was just asJsObj does the cast help? can we remove asJsObjSub
  def asJsObj: js.Object          = jsdyn.asInstanceOf[js.Object]
  def asDict[A]: js.Dictionary[A] = jsdyn.asInstanceOf[js.Dictionary[A]]
  // variance annotation needed?
  def asUndefOr[A]: js.UndefOr[A] = jsdyn.asInstanceOf[js.UndefOr[A]]
  def asJsObjSub[A <: js.Object]  = jsdyn.asInstanceOf[A] // assumes its there!
  def asJsArray[A <: js.Object]   = jsdyn.asInstanceOf[js.Array[A]]
  def toOption[T <: js.Object]: Option[T] =
    if (js.DynamicImplicits.truthValue(jsdyn)) Some(jsdyn.asInstanceOf[T])
    else None
  @inline def toNonNullOption[T <: js.Object]: Option[T] = JsUndefOrOps(asUndefOr).toNonNullOption
  @inline def combine(that: js.Dynamic) = mergeJSObjects(jsdyn, that)
  @inline def toTruthy: Boolean =
        if (js.DynamicImplicits.truthValue(jsdyn)) true
        else false
}

trait JsDynamicSyntax {
  implicit def jsDynamicOpsSyntax(jsdyn: js.Dynamic) = JsDynamicOps(jsdyn)
}

final case class ComponentOps(c: Component) {
  @inline def toEl: ReactElement = elements.element(c)
  @inline def toEl(key: Option[String] = None, ref: Option[RefCb] = None) =
    elements.element(c, key, ref)
}

trait ComponentSyntax {
  implicit def componentOpsSyntax(c: Component) =
    ComponentOps(c)
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
    with OrNullSyntax
    //with UndefOrNullSyntax

object syntax {
  object all       extends AllSyntax
  object component extends ComponentSyntax
  //object element extends ElementConversionSyntax
  object jsdynamic extends JsDynamicSyntax
  object jsundefor extends JsUndefOrSyntax
  object jsobject  extends JsObjectSyntax
  object jsany     extends JsAnySyntax
  object ornull extends OrNullSyntax
  //object undefornull extends UndefOrNullSyntax
}

trait Component2Elements {
  @inline implicit def c2E(c: Component): ReactElement = elements.element(c)
  @inline implicit def cSeq2E(c: Seq[Component]): ReactNode =
    arrayToElement(c.map(elements.element(_)))
}

/**
  * Mostly evil converters. Watch out for these automatic conversions. All are
  * prefixed with _ so you can define your own and not get tripped on namespace.
  */
trait ValueConverters {
  @inline implicit def jsArrayToElement[T <: ReactNode](arr: js.Array[T]) = react.arrayToElement(arr)
  @inline implicit def _stringToElement(s: String): ReactNode              = react.stringToElement(s)

  @inline implicit def seqToElement[T <: ReactNode](s: Seq[T]) = react.arrayToElement(s)
  @inline implicit def intToElement(i: Int): ReactNode         = i.asInstanceOf[ReactNode]
  @inline implicit def doubleToElement(d: Double): ReactNode   = d.asInstanceOf[ReactNode]
  @inline implicit def floatToElement(f: Float): ReactNode     = f.asInstanceOf[ReactNode]
  @inline implicit def booleanToElement(b: Boolean): ReactNode = b.asInstanceOf[ReactNode]
  @inline implicit def optToElement(s: Option[ReactElement]): ReactNode =
    s.getOrElse(null.asInstanceOf[ReactNode])
  @inline implicit def iterableToElement[T](s: Iterable[T])(
      implicit cv: T => ReactNode): ReactNode = {
    s.map(cv).toJSArray.asInstanceOf[ReactElement]
  }
  // keep separate for *just* component conversion.
  //@inline implicit def _componentToElement(c: ComponentAny): ReactElement = elements.element(c)


  @inline implicit def undefOrReactNodeToReactNode(n: js.UndefOr[ReactNode]): ReactNode =
    n.getOrElse(null)
  @inline implicit def undefOrReactNodeArrayToReactNode(n: js.UndefOr[js.Array[ReactNode]]): ReactNode =
    n.map(i => iterableToElement(i)).getOrElse(null)
}

trait AllInstances extends Component2Elements with ValueConverters

/** Instances are the wrong word, converters would be better. */
object instances {
  object all       extends AllInstances
  object component extends Component2Elements
  object value       extends ValueConverters
}

object implicits extends AllSyntax with AllInstances

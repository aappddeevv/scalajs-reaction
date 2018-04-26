// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._
import js.JSConverters._
import js.Dynamic.{literal => lit}

/** Is this really doing anything with double hops? */
object jsany {
  /**
   * Not syntax per-se, but helps drive type inference when we need to hop
   * through 2 implicit conversions for non reference types. It's similar in
   * usage as `js.undefined`. scala predef includes String and other
   * mapped type conversions to js.Any, this makes it explicit.
   * {{{
   * val str = "blah"
   * val x = jsany(str).toTruthyUndefOr
   * }}}
   */  
  def apply[A <: String|Boolean|Int|Float|Double|Short|Byte|Unit](a: A): js.Any = a.asInstanceOf[js.Any]
}

// /**
//  * It is common in interop code to model a value as A or null but not undefined even
//  * though null and undefined may both mean "absent value." See `|.merge` as well.
//  */
// final case class OrNullOps[A <: js.Any](a: A | Null) {
//   /** Convert an A|Null to a well formed option. */
//   @inline def toNonNullOption: Option[A] =
//     if(a == null) Option.empty[A]
//     else Some(a.asInstanceOf[A])

//   /** If Null, then false, else true. */
//   @inline def toTruthy: Boolean =
//     if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) true
//     else false

//   /** null => undefined, otherwise A. */
//   @inline def toUndefOr: js.UndefOr[A] =
//     if(a == null) js.undefined
//     else js.defined(a.asInstanceOf[A])
// }

// trait OrNullSyntax{
//   @inline implicit def orNullSyntax[A <: js.Any](a: A|Null): OrNullOps[A] = OrNullOps[A](a)
// }

trait AnyOps[T] {
  def a: T

  @inline def asJsAny: js.Any = a.asInstanceOf[js.Any]
  @inline def asJsObj: js.Object        = a.asInstanceOf[js.Object]
  @inline def asDyn: js.Dynamic         = a.asInstanceOf[js.Dynamic]
  @inline def asString: String          = a.asInstanceOf[String]
  @inline def asNumber: Number           = a.asInstanceOf[Number]
  @inline def asInt: Int                = a.asInstanceOf[Int]
  @inline def asDouble: Double          = a.asInstanceOf[Double]
  @inline def asBoolean: Boolean        = a.asInstanceOf[Boolean]
  @inline def asJsArray[A]: js.Array[A] = a.asInstanceOf[js.Array[A]]
  @inline def asJson: String            = js.JSON.stringify(a.asInstanceOf[js.Object])
  @inline def toStringJs                = a.asInstanceOf[js.Any].toString()

  /** If value is null or undefined be undefined, otherwise defined. */
  @inline def toNonNullUndefOr: js.UndefOr[T] =
    if(a == null || js.isUndefined(a)) js.undefined
    else js.defined(a)

  /** If value is null or undefined be None, else Some. */
  @inline def toNonNullOption: Option[T] = {
    // also defined in react package, repeated here
    if (js.isUndefined(a) || a==null) None
    else Option(a)
  }

  /** Equivalent `!!x` for some javascript value x. */
  @inline def toTruthy: Boolean = js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])

  /** 
   * Wow, a mouthful! If its a javascript truthy=true, its defined, otherwise
   * undef. Takes into account 0, "" and [] javascript idioms i.e. takes into account
   * the FP zero.
   * @example {{{
   *  val s = "" // s.toTruthyUndefOr[String] => js.undefined
   *  val s = "blah" // s.toTurthyUndefOr[String] => defined "blah"
   *  val n = 0  // n.toTruthyUndefOr[Int] => js.undefined
   *  val n1 = 1 // n1.toTruthyUndefOr[Int] => defined 1
   * }}}
   */
  @inline def toTruthyUndefOr: js.UndefOr[T] =
    if(js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) js.defined(a)
    else js.undefined  
}

final case class JsAnyOps[T <: js.Any](val a: T) extends AnyOps[T] {}

trait JsAnySyntax {
  @inline implicit def jsAnyOpsSyntax[T <: js.Any](a: T): JsAnyOps[T] = JsAnyOps(a)
}

/** 
 * Intended for directly mapped scala types, not scala.Any in general. Know what 
 * you are doing!!! Very dangerous!
 */
final case class ScalaMappedOps[T <: scala.Any](val a: T) extends AnyOps[T] {}

trait ScalaMappedSyntax {
  @inline implicit def stringScalaOpsSyntax[String](a: String): ScalaMappedOps[String] = ScalaMappedOps[String](a)
  // these seem to conflict as scala things String and Boolean, Byte, etc. lead to ambiguous implicits
  //implicit def booleanScalaOpsSyntax[Boolean](a: Boolean): ScalaMappedOps[Boolean] = ScalaMappedOps[Boolean](a)
  //implicit def byteScalaOpsSyntax[Byte](a: Byte): ScalaMappedOps[Byte] = ScalaMappedOps[Byte](a)
  //implicit def shortScalaOpsSyntax[Short](a: Short): ScalaMappedOps[Short] = ScalaMappedOps[Short](a)
  //implicit def intScalaOpsSyntax[Int](a: Int): ScalaMappedOps[Int] = ScalaMappedOps[Int](a)
  //implicit def floatScalaOpsSyntax[Float](a: Float): ScalaMappedOps[Float] = ScalaMappedOps[Float](a)
  //implicit def doubleScalaOpsSyntax[Double](a: Double): ScalaMappedOps[Double] = ScalaMappedOps[Double](a)
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
  @inline implicit def jsObjectOpsSyntax[A <: js.Object](a: A)           = new JsObjectOps(a)
  @inline implicit def jsDictionaryOpsSyntax(a: js.Dictionary[_]) = new JsDictionaryOps(a)
}

final case class JsUndefOrStringOps(a: UndefOr[String]) {
  @inline def orEmpty: String = a.getOrElse("")
  @inline def filterTruthy: js.UndefOr[String] = a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))  
}

final case class JsUndefOrBooleanOps(a: UndefOr[Boolean]) {
  @inline def orTrue: Boolean  = a.getOrElse(true)
  @inline def orFalse: Boolean = a.getOrElse(false)
  @inline def filterTruthy: js.UndefOr[Boolean] = a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))  
}

/** Note that js.UdefoOr already has a `.orNull` method. */
final case class JsUndefOrOps[A](a: UndefOr[A]) {
  /** Tests for overall nullness which is different than `.isEmpty|.nonEmpty`. */
  @inline def isNull          = a == null
  @inline def isEmpty         = isNull || !a.isDefined
  @inline def toNonNullOption =
    if (a.isEmpty || a == null) None
    else a.toOption
  @inline def toStringJs      = a.asInstanceOf[js.Any].toString()
  @inline def toTruthy: Boolean = js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])
  @inline def filterTruthy: js.UndefOr[A] = a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))
}

trait JsUndefOrSyntax {
  @inline implicit def jsUndefOrOpsSyntax[A](a: UndefOr[A])     = JsUndefOrOps(a)
  @inline implicit def jsUndefOrStringOps(a: UndefOr[String])   = JsUndefOrStringOps(a)
  @inline implicit def jsUndefOrBooleanOps(a: UndefOr[Boolean]) = JsUndefOrBooleanOps(a)
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
  @inline def toTruthy: Boolean =js.DynamicImplicits.truthValue(jsdyn)
}

trait JsDynamicSyntax {
  @inline implicit def jsDynamicOpsSyntax(jsdyn: js.Dynamic) = JsDynamicOps(jsdyn)
}

final case class OptionOps[T](val a: Option[T]) {
  /** If Some and value is truthy according to JS, then keep it, otherwise become a None. */
  @inline def filterTruthy: Option[T] = a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))
}

trait OptionSyntax {
  @inline implicit def optionSyntax[T](a: Option[T]): OptionOps[T] = OptionOps(a)
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

// order matters here based on implicit search through hierarchy
trait AllSyntax
    extends ComponentSyntax
    with JsDynamicSyntax
    with JsObjectSyntax
    with JsAnySyntax
    //with OrNullSyntax
    with ScalaMappedSyntax
    with OptionSyntax
    with JsUndefOrSyntax

object syntax {
  object all       extends AllSyntax
  object component extends ComponentSyntax
  object jsdynamic extends JsDynamicSyntax
  object jsundefor extends JsUndefOrSyntax
  object jsobject  extends JsObjectSyntax
  object jsany     extends JsAnySyntax
  object scalamapped  extends ScalaMappedSyntax
  //object ornull extends OrNullSyntax
  object option extends OptionSyntax
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
  @inline implicit def _jsArrayToElement[T <: ReactNode](arr: js.Array[T]) = react.arrayToElement(arr)
  @inline implicit def _stringToElement(s: String): ReactNode              = react.stringToElement(s)

  @inline implicit def _seqToElement[T <: ReactNode](s: Seq[T]) = react.arrayToElement(s)
  @inline implicit def _intToElement(i: Int): ReactNode         = i.asInstanceOf[ReactNode]
  @inline implicit def _doubleToElement(d: Double): ReactNode   = d.asInstanceOf[ReactNode]
  @inline implicit def _floatToElement(f: Float): ReactNode     = f.asInstanceOf[ReactNode]
  @inline implicit def _booleanToElement(b: Boolean): ReactNode = b.asInstanceOf[ReactNode]
  @inline implicit def _optToElement(s: Option[ReactElement]): ReactNode =
    s.getOrElse(null.asInstanceOf[ReactNode])
  @inline implicit def _iterableToElement[T](s: Iterable[T])(
      implicit cv: T => ReactNode): ReactNode = {
    s.map(cv).toJSArray.asInstanceOf[ReactElement]
  }

  @inline implicit def _undefOrReactNodeToReactNode(n: js.UndefOr[ReactNode]): ReactNode =
    n.getOrElse(null)
  @inline implicit def _undefOrReactNodeArrayToReactNode(n: js.UndefOr[js.Array[ReactNode]]): ReactNode =
    n.map(i => _iterableToElement(i)).getOrElse(null)
}

trait AllInstances extends Component2Elements with ValueConverters

/** 
 * Instances is the wrong concept here as these are not typeclass instances--but close enough as
 * they are not syntax extensions "'element' converters" would be better.
 */
object instances {
  object all       extends AllInstances
  object component extends Component2Elements
  object value       extends ValueConverters
}

object implicits extends AllSyntax with AllInstances

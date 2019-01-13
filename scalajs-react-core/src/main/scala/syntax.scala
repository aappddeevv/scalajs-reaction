// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._
import js.JSConverters._
import js.Dynamic.{literal => lit}

/*
String | Unit  => Option[String]: provides a string when Some and js.undefined when None
x.fold[String | Unit](())(x => x)
 */

/**
  * It is common in interop code to model a value as A or null but not undefined
  * even though null and undefined may both mean "absent value." See `|.merge`
  * as well.  Note that chaining many `js.|` together probably not work like you
  * think and sometimes its better to create a new target type then target
  * implicits to convert from each individual type (in the or) to the new target
  * type.
  */
final case class OrNullOps[A <: js.Any](a: A | Null) {

  /** Convert an A|Null to a well formed option. */
  @inline def toNonNullOption: Option[A] =
    if (a == null) Option.empty[A]
    else Some(a.asInstanceOf[A])

  /** If Null, then false, else true. */
  @inline def toTruthy: Boolean =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) true
    else false

  /** null => undefined, otherwise A. */
  @inline def toUndefOr: js.UndefOr[A] =
    if (a == null) js.undefined
    else js.defined(a.asInstanceOf[A])

  @inline def toTruthyUndefOr: js.UndefOr[A] =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]))
      js.defined(a.asInstanceOf[A])
    else js.undefined
}

trait OrNullSyntax {
  @inline implicit def orNullSyntax[A <: js.Any](a: A | Null): OrNullOps[A] = OrNullOps[A](a)
}

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

trait AnyOps[T] {
  def a: T

  /** If T is js.Any, this may be redundent. */
  @inline def asJsAny: js.Any           = a.asInstanceOf[js.Any]
  @inline def asJsObj: js.Object        = a.asInstanceOf[js.Object]
  @inline def asDyn: js.Dynamic         = a.asInstanceOf[js.Dynamic]
  @inline def asString: String          = a.asInstanceOf[String]
  @inline def asNumber: Number          = a.asInstanceOf[Number]
  @inline def asInt: Int                = a.asInstanceOf[Int]
  @inline def asDouble: Double          = a.asInstanceOf[Double]
  @inline def asBoolean: Boolean        = a.asInstanceOf[Boolean]
  @inline def asJsArray[A]: js.Array[A] = a.asInstanceOf[js.Array[A]]
  @inline def asJson: String            = js.JSON.stringify(a.asInstanceOf[js.Object])
  @inline def toStringJs                = a.asInstanceOf[js.Any].toString()

  /** Internal null values become undefined. */
  @inline def filterNull = toNonNullUndefOr

  /** If value is null or undefined be undefined, otherwise defined. Could be called "filterNull". */
  @inline def toNonNullUndefOr: js.UndefOr[T] =
    if (a == null || js.isUndefined(a)) js.undefined
    else js.defined(a)

  /** If value is null or undefined be None, else Some. */
  @inline def toNonNullOption: Option[T] = {
    // also defined in react package, repeated here
    if (js.isUndefined(a) || a == null) None
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
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) js.defined(a)
    else js.undefined
}

final case class JsAnyOps[T <: js.Any](val a: T) extends AnyOps[T] {}

trait JsAnySyntax {
  // this does not seem to pick p scala.js mapped types, ask gitter
  @inline implicit def jsAnyOpsSyntax[T <: js.Any](a: T): JsAnyOps[T] = JsAnyOps(a)
}

/**
  * Intended for directly mapped scala types, not scala.Any in general. Know what
  * you are doing!!! Very dangerous!
  */
final case class ScalaMappedOps[T <: scala.Any](val a: T) extends AnyOps[T] {
  ///** Very dangerous! You should know what you are doing. */
  //@inline def asJsAny: js.Any = a.asInstanceOf[js.Any]
}

trait ScalaMappedSyntax {
  @inline implicit def stringScalaOpsSyntax[String](a: String): ScalaMappedOps[String] =
    ScalaMappedOps[String](a)
  // all of these seem to conflict with the first String def above and are these needed if they are <: js.Any
  // these seem to conflict as scala things String and Boolean, Byte, etc. lead to ambiguous implicits
  //implicit def booleanScalaOpsSyntax[Boolean](a: Boolean): ScalaMappedOps[Boolean] = ScalaMappedOps[Boolean](a)
  //implicit def byteScalaOpsSyntax[Byte](a: Byte): ScalaMappedOps[Byte] = ScalaMappedOps[Byte](a)
  //implicit def shortScalaOpsSyntax[Short](a: Short): ScalaMappedOps[Short] = ScalaMappedOps[Short](a)
  //implicit def intScalaOpsSyntax[Int](a: Int): ScalaMappedOps[Int] = ScalaMappedOps[Int](a)
  //implicit def floatScalaOpsSyntax[Float](a: Float): ScalaMappedOps[Float] = ScalaMappedOps[Float](a)
  //implicit def doubleScalaOpsSyntax[Double](a: Double): ScalaMappedOps[Double] = ScalaMappedOps[Double](a)
}

final case class JsObjectOps[A <: js.Object](o: A) {
  @inline def asDict[B]                                = o.asInstanceOf[js.Dictionary[B]]
  @inline def asAnyDict                                = o.asInstanceOf[js.Dictionary[js.Any]]
  @inline def asDyn                                    = o.asInstanceOf[js.Dynamic]
  @inline def asUndefOr[A]: js.UndefOr[A]              = o.asInstanceOf[js.UndefOr[A]]
  @inline def combine[B <: js.Object](that: js.Object) = react.merge(o, that).asInstanceOf[B]
}

/** These should be picked by `<: js.Object` but don't seem to be. */
final case class JsDictionaryOps(o: js.Dictionary[_]) {
  @inline def asJsObj = o.asInstanceOf[js.Object]
  @inline def asDyn   = o.asInstanceOf[js.Dynamic]
}

trait JsObjectSyntax {
  @inline implicit def jsObjectOpsSyntax[A <: js.Object](a: A)    = new JsObjectOps(a)
  @inline implicit def jsDictionaryOpsSyntax(a: js.Dictionary[_]) = new JsDictionaryOps(a)
}

trait UndefOrCommon[A] {
  val a: UndefOr[A]

  /** Tests for overall nullness which is different than `.isEmpty|.nonEmpty`. */
  @inline def isNull = a == null

  /** This may override `UndefOr.isEmpty` but these semantics are different. */
  @inline def isEmpty = isNull || !a.isDefined

  /** This could also be `_.toOption.filter(_ != null)` but below is slightly faster. */
  @inline def toNonNullOption =
    if (a.isEmpty || a == null) None
    else a.toOption

  /** Calls toString. I'm not sure this is needed at all. */
  @inline def toStringJs = a.asInstanceOf[js.Any].toString()

  /** Equivalent to !!someJSValue */
  @inline def toTruthy: Boolean = js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])

  /** Keep the value if its truthy, otherwise return undefined. */
  @inline def filterTruthy: js.UndefOr[A] =
    a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))

  /** null => undefined, else the value remains. */
  @inline def filterNull: js.UndefOr[A] = a.filter(_ != null)

  /** undefined => null, else the value remains. */
  @inline def orElseNull: js.UndefOr[A] = a orElse js.defined(null.asInstanceOf[A])
}

final case class JsUndefOrStringOps(val a: UndefOr[String]) extends UndefOrCommon[String] {

  /** Return string's "zero" which is an empty string. */
  @inline def orEmpty: String = a.getOrElse("")

  /** Return null or the value. */
  //@inline def orNull: String = a.getOrElse(null)

  /** Filter out empty string and null. */
  @inline def filterEmpty = a.filter(str => str != "" && str != null)
}

final case class JsUndefOrBooleanOps(val a: UndefOr[Boolean]) extends UndefOrCommon[Boolean] {
  @inline def orTrue: Boolean  = a.getOrElse(true)
  @inline def orFalse: Boolean = a.getOrElse(false)
  @inline def notUndef: UndefOr[Boolean] = a.map(!_)
}

/** Note that js.UdefoOr already has a `.orNull` method. */
final case class JsUndefOrOps[A](a: UndefOr[A]) extends UndefOrCommon[A] {}

trait JsUndefOrSyntax {
  @inline implicit def jsUndefOrOpsSyntax[A](a: UndefOr[A])     = JsUndefOrOps(a)
  @inline implicit def jsUndefOrStringOps(a: UndefOr[String])   = JsUndefOrStringOps(a)
  @inline implicit def jsUndefOrBooleanOps(a: UndefOr[Boolean]) = JsUndefOrBooleanOps(a)
}

final case class JsDynamicOps(val jsdyn: js.Dynamic) {
  @inline def asJsAny: js.Any         = jsdyn.asInstanceOf[js.Any]
  @inline def asString: String        = jsdyn.asInstanceOf[String]
  @inline def asInt: Int              = jsdyn.asInstanceOf[Int]
  @inline def asArray[A]: js.Array[A] = jsdyn.asInstanceOf[js.Array[A]]
  @inline def asBoolean: Boolean      = jsdyn.asInstanceOf[Boolean]

  /** @deprecated use asJsObj */
  @inline def asJSObj: js.Object = jsdyn.asInstanceOf[js.Object]
  // was just asJsObj does the cast help? can we remove asJsObjSub
  @inline def asJsObj: js.Object          = jsdyn.asInstanceOf[js.Object]
  @inline def asDict[A]: js.Dictionary[A] = jsdyn.asInstanceOf[js.Dictionary[A]]
  // variance annotation needed?
  @inline def asUndefOr[A]: js.UndefOr[A] = jsdyn.asInstanceOf[js.UndefOr[A]]
  @inline def asJsObjSub[A <: js.Object]  = jsdyn.asInstanceOf[A] // assumes its there!
  @inline def asJsArray[A <: js.Object]   = jsdyn.asInstanceOf[js.Array[A]]

  /** Uses truthiness to determine None */
  @inline def toOption[T <: js.Object]: Option[T] =
    if (js.DynamicImplicits.truthValue(jsdyn)) Some(jsdyn.asInstanceOf[T])
    else None

  /** Not sure this works... */
  @inline def toNonNullOption[T <: js.Object]: Option[T] = JsUndefOrOps(asUndefOr).toNonNullOption
  @inline def combine(that: js.Dynamic)                  = mergeJSObjects(jsdyn, that)
  @inline def toTruthy: Boolean                          = js.DynamicImplicits.truthValue(jsdyn)
}

trait JsDynamicSyntax {
  @inline implicit def jsDynamicOpsSyntax(jsdyn: js.Dynamic) = JsDynamicOps(jsdyn)
}

/** If you want js.UndefOr, use JSConverters and `.orUndefined`. */
final case class OptionOps[T](val a: Option[T]) {

  /** If Some and value is truthy according to JS, then keep it, otherwise become a None. */
  @inline def filterTruthy: Option[T] =
    a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))

  /** Filter nulls out in case it *might* be null.
    * @deprecated USe [[filterNull]].
    */
  @inline def toNonNullOption = a.filter(_ != null)

  /** Filter nulls out in case it *might* be null. */
  @inline def filterNull = a.filter(_ != null)

  /** If Some, keep the value, else set the value to null. */
  @inline def orElseNull = a orElse Some(null.asInstanceOf[T])
}

trait OptionSyntax {
  @inline implicit def optionSyntax[T](a: Option[T]): OptionOps[T] = OptionOps(a)
}

/** Given a component, provide some syntax for easily converting it into an
 * element.  This is equivalent to using JSX syntax.
 */
final case class ComponentOps(c: Component) {
  /** Create an element with no key or ref. */
  @inline def toEl: ReactElement = elements.element(c)

  /** Create an element with an optional key and an optional ref. */
  @inline def toEl(key: Option[String] = None, ref: Option[Ref[js.Any]] = None) =
    elements.element(c, key, ref)
}

trait ComponentSyntax {
  implicit def componentOpsSyntax(c: Component) =
    ComponentOps(c)
}

final case class ValueOps[T <: scala.Any](v: T) {
  @inline def toNode: ReactNode = v.asInstanceOf[ReactNode]
}

trait ValueSyntax {
  implicit def stringValueOpsSyntax(v: String) = ValueOps[String](v)
  implicit def intValueOpsSyntax(v: Int) = ValueOps[Int](v)
  implicit def floatValueOpsSyntax(v: Float) = ValueOps[Float](v)
  implicit def doubleValueOpsSyntax(v: Double) = ValueOps[Double](v)
  implicit def booleanValueOpsSyntax(v: Boolean) = ValueOps[Boolean](v)  
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
}

/** Convert components to elements. Components are like templates that describe
  * how to create an element. Elements are instances. These implicits are key
  * for ease-of-use because they take a component into an element automatically
  * much like JSX syntax.
 */
trait Component2Elements {
  @inline implicit def c2E(c: Component): ReactElement = elements.element(c)
  @inline implicit def cSeq2E(c: Seq[Component]): ReactNode =
    arrayToElement(c.map(elements.element(_)))
}

/** Mostly evil converters so you can have a variety of children types ond have
 * them convert to ReactNode/ReactElements as needed.  Watch out for these
 * automatic conversions. All are prefixed with _ so you can define your own and
 * not get tripped on namespace. Watch out for values thtat need to pass through
 * 2 implicits as that is not supported by scala. So you may think that these
 * conversions should apply but an `UndefOr` wrapper on the target type means
 * either you use js.defined and these implicit conversions or you should use
 * `.toNode` to convert the ReactNode value then let the general implicit create
 * an `UndefOr[T]`.
  */
trait ValueConverters {
  @inline implicit def _jsArrayToElement[T <: ReactNode](arr: js.Array[T]) =
    react.arrayToElement(arr)
  @inline implicit def _stringToElement(s: String): ReactNode = react.stringToElement(s)

  // shouldn't these just be collapsed into a scala.AnyVal?
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
  @inline implicit def _undefOrReactNodeArrayToReactNode(
      n: js.UndefOr[js.Array[ReactNode]]): ReactNode =
    n.map(i => _iterableToElement(i)).getOrElse(null)

  /** Since null is a valid react node, convert an optional string to null. */
  @inline implicit def _optionStringToNull(n: Option[String]): ReactNode = n.getOrElse(null)

  /** Convert scala.AnyVal into a react node. */
  @inline implicit def _optionAnyValToNull(n: Option[scala.AnyVal]): ReactNode =
    n.getOrElse(null).asInstanceOf[ReactNode]

  /** js.UndefOr to null. */
  @inline implicit def _undefOrStringToNull(n: js.UndefOr[String]) =
    n.getOrElse(null).asInstanceOf[ReactNode]

  // highly contraversial...this allows you to put any thing in for the react children and 
  //@inline implicit def _scalaObject(o: scala.AnyRef): ReactNode = o.asInstanceOf[ReactNode]

  // contraversial but highly useful for classname
  @inline implicit def _optionStringToUndefOrString(n: Option[String]): js.UndefOr[String] = n.orUndefined
}

trait AllInstances extends Component2Elements with ValueConverters

/** Instances is the wrong concept here as these are not typeclass
  * instances--but close enough as they are not syntax extensions "'element'
  * converters" would be better.
  */
object instances {
  object all       extends AllInstances
  object component extends Component2Elements
  object value     extends ValueConverters
}

/* Include these to get automatic type conversions. They are optional but
 * exceptionally helpful.  These can be included ala carte.
 */
object implicits extends AllSyntax with AllInstances

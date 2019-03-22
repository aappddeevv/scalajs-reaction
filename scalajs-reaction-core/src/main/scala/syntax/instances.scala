// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._
import js.JSConverters._

/** Convert components to elements. Components are like templates that describe
  * how to create an element. Elements are instances. These implicits are key
  * for ease-of-use because they take a component into an element automatically
  * much like JSX syntax.
 */
trait Component2Elements {
  implicit def c2E(c: Component): ReactElement = elements.element(c)
  implicit def cSeq2E(c: Seq[Component]): ReactNode =
    arrayToElement(c.map(elements.element(_)))
}

/** Mostly obvious converters so you can have a variety of children types and
 * have them convert to ReactNode/ReactElements as needed. Watch out for values
 * that need to pass through 2 implicits since double implicit resolution is not
 * supported by scala. So you may think that these conversions should apply but
 * an `UndefOr` wrapper on the target type means either you use js.defined and
 * these implicit conversions or you should use `.toNode` to convert the
 * ReactNode value then let the general implicit create an `UndefOr[T]`.
  */
trait ValueConverters {
  implicit def _jsArrayToElement[T <: ReactNode](arr: js.Array[T]) =
    react.arrayToElement(arr)

  // shouldn't these just be collapsed into a scala.AnyVal?
  implicit def _seqToElement[T <: ReactNode](s: Seq[T]) = react.arrayToElement(s)

  //
  // direct value conversions as these are value react nodes directly
  //
  // @inline implicit def _intToElement(i: Int): ReactNode         = i.asInstanceOf[ReactNode]
  // @inline implicit def _doubleToElement(d: Double): ReactNode   = d.asInstanceOf[ReactNode]
  // @inline implicit def _floatToElement(f: Float): ReactNode     = f.asInstanceOf[ReactNode]
  // @inline implicit def _booleanToElement(b: Boolean): ReactNode = b.asInstanceOf[ReactNode]
  implicit def _anyValToElement(v: AnyVal): ReactNode = v.asInstanceOf[ReactNode]
  implicit def _stringToElement(s: String): ReactNode = s.asInstanceOf[ReactNode]

  implicit def _optToElement(s: Option[ReactElement]): ReactNode =
    s.getOrElse(null.asInstanceOf[ReactNode])
  implicit def _iterableToElement[T](s: Iterable[T])(
      implicit cv: T => ReactNode): ReactNode = {
    s.map(cv).toJSArray.asInstanceOf[ReactElement]
  }

  implicit def _undefOrReactNodeToReactNode(n: js.UndefOr[ReactNode]): ReactNode =
    n.getOrElse(null)
  implicit def _undefOrReactNodeArrayToReactNode(
      n: js.UndefOr[js.Array[ReactNode]]): ReactNode =
    n.map(i => _iterableToElement(i)).getOrElse(null)

  /** Since null is a valid react node, convert an optional string to null. */
  implicit def _optionStringToNull(n: Option[String]): ReactNode =
    n.map(_.asInstanceOf[ReactNode]).getOrElse(nullElement)

  /** Convert scala.AnyVal into a react node. */
  implicit def _optionAnyValToNull(n: Option[scala.AnyVal]): ReactNode =
    n.getOrElse(null).asInstanceOf[ReactNode]

  /** js.UndefOr to null. */
  implicit def _undefOrStringToNull(n: js.UndefOr[String]) =
    n.getOrElse(null).asInstanceOf[ReactNode]

  // highly contraversial...this allows you to put any thing in for the react children and 
  //@inline implicit def _scalaObject(o: scala.AnyRef): ReactNode = o.asInstanceOf[ReactNode]

  // contraversial but highly useful for classname
  implicit def _optionStringToUndefOrString(n: Option[String]): js.UndefOr[String] = n.orUndefined
}

trait AllInstances extends Component2Elements with ValueConverters

/** Instances is the wrong concept here as these are not typeclass
  * instances--but close enough as they are not syntax extensions "'element'
  * converters" would be better similiar to `JSConverters` in scala.js.
  */
object instances {
  object all       extends AllInstances
  object component extends Component2Elements
  object value     extends ValueConverters
}

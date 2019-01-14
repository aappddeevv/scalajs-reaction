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
  //@inline implicit def _stringToElement(s: String): ReactNode = react.stringToElement(s)

  // shouldn't these just be collapsed into a scala.AnyVal?
  @inline implicit def _seqToElement[T <: ReactNode](s: Seq[T]) = react.arrayToElement(s)
  // dupes
  // @inline implicit def _intToElement(i: Int): ReactNode         = i.asInstanceOf[ReactNode]
  // @inline implicit def _doubleToElement(d: Double): ReactNode   = d.asInstanceOf[ReactNode]
  // @inline implicit def _floatToElement(f: Float): ReactNode     = f.asInstanceOf[ReactNode]
  // @inline implicit def _booleanToElement(b: Boolean): ReactNode = b.asInstanceOf[ReactNode]
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

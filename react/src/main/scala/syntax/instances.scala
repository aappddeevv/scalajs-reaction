/*
 * Copyright (c) 2018 The Trapelo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package react

import scala.scalajs.js

import js.JSConverters._
import js._

/** Mostly obvious converters so you can have a variety of children types and
 * have them convert to ReactNode/ReactElements as needed. Watch out for values
 * that need to pass through 2 implicits since double implicit resolution is not
 * supported by scala. So you may think that these conversions should apply but
 * an `UndefOr` wrapper on the target type means either you need to use
 * js.defined first and then these implicit conversions will apply automatically
 * or you should use `.toNode` to convert the ReactNode value then let the
 * general implicit create an `UndefOr[T]`.
 */
trait ValueConverters {
  implicit def _jsArrayToElement[T <: ReactNode](arr: js.Array[T]) = arrayToElement(arr)

  // shouldn't these just be collapsed into a scala.AnyVal?
  implicit def _seqToElement[T <: ReactNode](s: Seq[T]) = arrayToElement(s.toJSArray)

  implicit def _anyValToElement(v: AnyVal): ReactNode = v.asInstanceOf[ReactNode]

  implicit def _stringToElement(s: String): ReactNode = s.asInstanceOf[ReactNode]

  implicit def _optToElement(s: Option[ReactElement]): ReactNode =
    s.getOrElse(null.asInstanceOf[ReactNode])

  /** Elements should have key set. */
  implicit def _iterableToElement[T](s: Iterable[T])(implicit cv: T => ReactNode): ReactNode =
    s.map(cv).toJSArray.asInstanceOf[ReactElement]

  //implicit def _iterableReactElementToNode(s: Iterable[ReactElement]) = s.toJSArray.asInstanceOf[ReactNode]

  implicit def _undefOrReactNodeToReactNode(n: js.UndefOr[ReactNode]): ReactNode =
    n.getOrElse(null)

  implicit def _undefOrReactNodeArrayToReactNode(n: js.UndefOr[js.Array[ReactNode]]): ReactNode =
    n.map(i => _iterableToElement(i)).getOrElse(null)

  /** Since null is a valid react node, convert an optional string to null. */
  implicit def _optionStringToNull(n: Option[String]): ReactNode =
    n.map(_.asInstanceOf[ReactNode]).getOrElse(nullElement)

  /** Convert scala.AnyVal into a react node. */
  implicit def _optionAnyValToNull(n: Option[scala.AnyVal]): ReactNode =
    n.getOrElse(null).asInstanceOf[ReactNode]

  /** js.UndefOr String to null. */
  implicit def _undefOrStringToNull(n: js.UndefOr[String]) =
    n.getOrElse(null).asInstanceOf[ReactNode]

  /** js.UndefOr[String] to js.UndefOr[ReactNode]. */
  implicit def _undefOrStringToNode(n: js.UndefOr[String]) =
    n.getOrElse(null).asInstanceOf[js.UndefOr[ReactNode]]

  /** js.UndefOr[String] to js.UndefOr[ReactNode]. */
  implicit def _stringToUndefOrNode(n: String) =
    js.defined(n).asInstanceOf[js.UndefOr[ReactNode]]

  /** Totally controversial as well. */
  //implicit def _undefOrObjectToNull(n: js.UndefOr[Object]) = n.getOrElse(null).asInstanceOf[ReactNode]

  // controversial but highly useful for classname-like arguments.
  implicit def _optionStringToUndefOrString(n: Option[String]): js.UndefOr[String] = n.orUndefined
}

trait AllInstances extends ValueConverters

/** Instances is the wrong concept here as these are not typeclass
 * instances--but close enough as they are not syntax extensions "'element'
 * converters" would be better similiar to `JSConverters` in scala.js.
 */
object instances {
  object all   extends AllInstances
  object value extends ValueConverters
}

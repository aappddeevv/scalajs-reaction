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

import scala.util.*
import scala.scalajs.js
import js.JSConverters.*
import js.*

/** Encode a value to a ReactNode. This can only be used for
 * values that are themselves directly renderable because if you want
 * to create a ReactNode from a component, you must call `createElement`
 * with some props, and this typeclass does not have props anywhere!
 */
trait ReactNodeEncoder[A] {
  def toNode(a: A): ReactNode
}

object ReactNodeEncoder {

  /** Summoner */
  def apply[A](implicit instance: ReactNodeEncoder[A]) = instance

  def instance[A](f: A => ReactNode) = new ReactNodeEncoder[A] {
    def toNode(a: A) = f(a)
  }

  //implicit class RichReactNodeEncoder[A](private val a: A) extends AnyVal {
  //  def toNode(implicit tc: ReactNodeEncoder[A]) = tc.toNode(a)
  //}
}

trait ValueInstances {

  implicit val stringEncoder: ReactNodeEncoder[String] =
    ReactNodeEncoder.instance[String](stringToElement(_))

  implicit def undefOrEncoder[T](implicit tc: ReactNodeEncoder[T]): ReactNodeEncoder[js.UndefOr[T]] =
    ReactNodeEncoder.instance[js.UndefOr[T]](_.map(tc.toNode(_)).getOrElse(nullNode))
}

/** Mostly obvious converters so you can have a variety of children types and
 * have them convert to ReactNodes as needed.
 *
 * Watch out for values
 * that need to pass through 2 implicits since double implicit resolution is not
 * supported by scala. So you may think that these conversions should apply but
 * an `UndefOr` wrapper on the target type means either you need to use
 * js.defined first and then these implicit conversions will apply automatically
 * or you should use `.toNode` to convert the ReactNode value then let the
 * general implicit create an `UndefOr[T]`. Just be mindful of implicit conversion
 * rules in scala.
 *
 * @todo Restructure as a typeclass using an "encoding" pattern, see the trait above :-).
 */
trait ValueConverters {
  implicit def jsArrayOfNodeToElement[T <: ReactNode](arr: js.Array[T]): ReactNode = arrayToElement(arr)

  /** Critical to handling varargs in component children lists. */
  implicit val seqOfNodeToElement: Conversion[Seq[ReactNode], ReactNode] = s => arrayToElement(s.toJSArray)

  implicit val iterableOfNodeToElement: Conversion[Iterable[ReactNode], ReactNode] = x => arrayToElement(x.toJSArray)

  implicit val anyValToElement: Conversion[AnyVal, ReactNode] = _.asInstanceOf[ReactNode]

  implicit val undefOrAnyValToElement: Conversion[js.UndefOr[AnyVal], ReactNode] = 
    v => v.map(_.asInstanceOf[ReactNode]).getOrElse(nullNode)

  implicit val nullOrAnyValToElement: Conversion[AnyVal | Null, ReactNode] = 
    v => if (v == null) nullNode else v.asInstanceOf[ReactNode]

  implicit def _stringToElement(s: String): ReactNode = s.asInstanceOf[ReactNode]

  implicit def _stringToUndefOrElement(s: String): js.UndefOr[ReactNode] =
    js.defined(s.asInstanceOf[ReactNode])

  implicit def stringToUndefOrNode(n: js.UndefOr[String]): ReactNode =
    n.getOrElse(null).asInstanceOf[ReactNode]

//   implicit def stringOrNullToNode(n: String|Null): ReactNode =
//      if(n == null) nullNode else n.asInstanceOf[Node]

  /** Since null is a valid react node, convert an optional string to null. */
  implicit val optionStringToElement: Conversion[Option[String], ReactNode] =
    n => n.map(_.asInstanceOf[ReactNode]).getOrElse(nullNode)

  /** Since null is a valid react node, convert an optional string to null. */
  implicit val optionStringToUndefOrElement: Conversion[Option[String], js.UndefOr[ReactNode]] =
    n => js.defined(n.map(_.asInstanceOf[ReactNode]).getOrElse(nullNode))

  /** Implicit conversion from Option[String] => js.UndefOr[String]. */
  implicit def optionStringToUndefOr(n: Option[String]): js.UndefOr[String] = n.orUndefined

  implicit def undefNullStringToNode(v: js.UndefOr[String|Null]): ReactNode = v.asInstanceOf[ReactNode]
  implicit def undefNullIntToNode(v: js.UndefOr[Int|Null]): ReactNode = v.asInstanceOf[ReactNode]
  implicit def undefNullDoubleToNode(v: js.UndefOr[Double|Null]): ReactNode = v.asInstanceOf[ReactNode]

  implicit def optToElement(s: Option[ReactNode]): ReactNode =
    s.getOrElse(nullNode)

  implicit def undefOrReactNodeToReactNode(n: js.UndefOr[ReactNode]): ReactNode =
    n.getOrElse(nullNode)

  /** Elements should have key set. Requires an implicit to perform the conversion. */
  implicit def iterableWithConversionToElement[T](s: Iterable[T])(implicit cv: T => ReactNode): ReactNode =
    s.map(cv).toJSArray.asInstanceOf[ReactNode]

  implicit def undefOrReactNodeArrayToReactNode(n: js.UndefOr[js.Array[ReactNode]]): ReactNode =
    n.map(i => iterableToElement(i)).getOrElse(nullNode)

  /** Convert scala.AnyVal into a react node. */
  implicit def optionAnyValToNull(n: Option[scala.AnyVal]): ReactNode =
    n.getOrElse(null).asInstanceOf[ReactNode]

  implicit def eitherRightAnyToNode[T <: scala.Any](n: Either[_, T])(implicit cvt: T => ReactNode): ReactNode =
    n.fold(_ => nullNode, v => cvt(v))

  implicit def eitherRightReactNodeToNode(n: Either[_, ReactNode]): ReactNode =
    n.fold(_ => nullNode, n => n)

  implicit def throwableToNode(t: Throwable): ReactNode = t.getMessage.asInstanceOf[ReactNode]

  implicit def tryToNode[T](t: Try[T])(implicit cvt: T => ReactNode): ReactNode = t match {
    case Failure(t) => t.getMessage.asInstanceOf[ReactNode]
    case Success(v) => cvt(v)
  }

  implicit def func0[T](f: () => Unit): js.UndefOr[js.Function0[Unit]] = js.defined(js.Any.fromFunction0(f))
  implicit def func1[A, T](f: A => T): js.UndefOr[js.Function1[A, T]] = js.defined(js.Any.fromFunction1(f))
  implicit def func2[A, A2, T](f: (A, A2) => T): js.UndefOr[js.Function2[A, A2, T]] =
    js.defined(js.Any.fromFunction2(f))
  implicit def func3[A, A2, A3, T](f: (A, A2, A3) => T): js.UndefOr[js.Function3[A, A2, A3, T]] =
    js.defined(js.Any.fromFunction3(f))
  implicit def func4[A, A2, A3, A4, T](f: (A, A2, A3, A4) => T): js.UndefOr[js.Function4[A, A2, A3, A4, T]] =
    js.defined(js.Any.fromFunction4(f))

}

trait AllInstances extends jshelpers.AllInstances with ValueConverters with ValueInstances

/** Instances is the wrong concept here as these are not typeclass
 * instances--but close enough as they are not syntax extensions "'element'
 * converters" would be better similiar to `JSConverters` in scala.js.
 */
object instances extends jshelpers.InstancesTrait {
  object all extends AllInstances
  object value extends ValueConverters with ValueInstances
}

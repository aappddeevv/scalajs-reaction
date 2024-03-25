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
package conversions

import scala.util.*
import scala.scalajs.js
import js.JSConverters.*
import js.*

// /** Encode a value to a ReactNode. This can only be used for
//  * values that are themselves directly renderable because if you want
//  * to create a ReactNode from a component, you must call `createElement`
//  * with some props, and this typeclass does not have props anywhere.
//  */
// trait ReactNodeEncoder[A]:
//   def convert(a: A): ReactNode
//   extension (a: A)
//     def toNode: ReactNode = convert(a)

// given primimitiveToReactNode: ReactNodeEncoder[String|Int|Long|Double|Float|Boolean|Byte|Unit] with
//     def convert(a: String|Int|Long|Double|Float|Boolean|Byte|Unit): ReactNode = a.asInstanceOf[ReactNode]

// /** Convert anything to a node. Very unsafe. */
// trait UnsafeReactNodeEncoder[A]:
//   def convert(a: A): ReactNode
//   extension (a: A)
//     def unsafeToNode: ReactNode = convert(a)

// given UnsafeReactNodeEncoder[scala.Any|Unit] with
//     def convert(a: scala.Any|Unit): ReactNode = a.asInstanceOf[ReactNode]

/** Given a function component and an arg, expressed as tuple, convert to ReactElement. */
given func2Element[P <: js.Object]: Conversion[(ScalaJSFunctionComponent1, P), ReactNode] =
  f => ReactJS.createElement(f._1, f._2)

/** Given a function component and an arg with children, expressed as a tuple, convert to ReactElement. */
given funcChild2Element[P <: js.Object]: Conversion[(ScalaJSFunctionComponent1, P, ReactNode), ReactNode] =
  f => ReactJS.createElement(f._1, f._2, f._3)

/** Slightly evil. Auto conversion of no-props function component to `ReactNode`. */
given sfc02Node: Conversion[js.Function0[ReactNode], ReactNode] = f => ReactJS.createElement(f, null)

/** Evil! Auto type conversion from a no-arg function. */
given thunkToSFC: Conversion[() => ReactNode, ReactNode] = f => ReactJS.createElement(js.Any.fromFunction0(f), null)

given arrayToElement: Conversion[js.Array[? <: ReactNode], ReactNode] = _.asInstanceOf[ReactNode]

/** Critical to handling varargs in component children lists. */
given seqReactNodeToNode: Conversion[Seq[ReactNode], ReactNode] = v => v.toJSArray.asInstanceOf[ReactNode]

given iterableOfNodeToElement: Conversion[Iterable[ReactNode], ReactNode] = v => v.toJSArray.asInstanceOf[ReactNode]

/** Removen the need to use `toNode`. */
given anyValToElement: Conversion[AnyVal, ReactNode] = _.asInstanceOf[ReactNode]

given undefOrAnyValToElement: Conversion[js.UndefOr[AnyVal], ReactNode] = _.map(_.asInstanceOf[ReactNode]).getOrElse(nullNode)

given nullOrAnyValToElement: Conversion[AnyVal | Null, ReactNode] = 
  v => if v == null then nullNode else v.asInstanceOf[ReactNode]

given null2Node: Conversion[Null, ReactNode] = _.asInstanceOf[ReactNode]  

//
// Basic type conversions. No | type used to keep everything very specific.
//
//given stringToElement: Conversion[String, ReactNode] = _.asInstanceOf[ReactNode]
//given stringToUndefOrNode: Conversion[js.UndefOr[String], ReactNode] = _.getOrElse(null).asInstanceOf[ReactNode]
given intToNode: Conversion[Int, ReactNode] = _.asInstanceOf[ReactNode]
given uintToNode: Conversion[js.UndefOr[Int], ReactNode] = _.asInstanceOf[ReactNode]
given longToNode: Conversion[Long, ReactNode] = _.asInstanceOf[ReactNode]
given ulongToNode: Conversion[js.UndefOr[Long], ReactNode] = _.asInstanceOf[ReactNode]
given doubleToNode: Conversion[Double, ReactNode] = _.asInstanceOf[ReactNode]
given udoubleToNode: Conversion[js.UndefOr[Double], ReactNode] = _.asInstanceOf[ReactNode]
given floatToNode: Conversion[Float, ReactNode] = _.asInstanceOf[ReactNode]
given ufloatToNode: Conversion[js.UndefOr[Float], ReactNode] = _.asInstanceOf[ReactNode]
given byteToNode: Conversion[Byte, ReactNode] = _.asInstanceOf[ReactNode]
given ubyteToNode: Conversion[js.UndefOr[Byte], ReactNode] = _.asInstanceOf[ReactNode]
given booleanToNode: Conversion[Boolean, ReactNode] = _.asInstanceOf[ReactNode]
given ubooleanToNode: Conversion[js.UndefOr[Boolean], ReactNode] = _.asInstanceOf[ReactNode]

// UndefOr T | Null
given undefNullStringToNode: Conversion[js.UndefOr[String|Null], ReactNode] = _.asInstanceOf[ReactNode]
given undefNullIntToNode: Conversion[js.UndefOr[Int|Null], ReactNode] = _.asInstanceOf[ReactNode]
given undefNullDoubleToNode: Conversion[js.UndefOr[Double|Null], ReactNode] = _.asInstanceOf[ReactNode]

// T | Null
given stringOrNullToElement: Conversion[String|Null, ReactNode] = _.asInstanceOf[ReactNode]


/** Since null is a valid react node, convert an optional string to null. */
given optionStringToElement: Conversion[Option[String], ReactNode] =
  n => n.map(_.asInstanceOf[ReactNode]).getOrElse(nullNode)

/** Since null is a valid react node, convert an optional string to null. */
given optionStringToUndefOrElement: Conversion[Option[String], js.UndefOr[ReactNode]] =
  n => js.defined(n.map(_.asInstanceOf[ReactNode]).getOrElse(nullNode))

/** Implicit conversion from Option[String] => js.UndefOr[String]. */
given optionStringToUndefOr: Conversion[Option[String], js.UndefOr[String]] = _.orUndefined


given optToElement: Conversion[Option[ReactNode], ReactNode] = _.getOrElse(nullNode)

given undefOrReactNodeToReactNode: Conversion[js.UndefOr[ReactNode], ReactNode] = _.getOrElse(nullNode)

/** Elements should have key set. Requires an implicit to perform the conversion. */
given iterableWithConversionToElement[T](using cv: T => ReactNode): Conversion[Iterable[T], ReactNode] =
  _.map(cv).toJSArray.asInstanceOf[ReactNode]

/**  Convert an array of nodes or undefined values. */
given undefOrReactNodeArrayToReactNode: Conversion[js.UndefOr[js.Array[ReactNode]], ReactNode] =
  _.map(i => iterableToElement(i)).getOrElse(nullNode)

/** Convert scala.AnyVal into a react node. */
given optionAnyValToNull: Conversion[Option[scala.AnyVal], ReactNode] = _.getOrElse(null).asInstanceOf[ReactNode]

given eitherRightAnyToNode[T <: scala.Any](using cvt: T => ReactNode): Conversion[Either[?, T], ReactNode] =
  _.fold(_ => nullNode, v => cvt(v))

given eitherRightReactNodeToNode: Conversion[Either[?, ReactNode], ReactNode] = _.fold(_ => nullNode, n => n)

/** Throwable's `getMessage` is converted to a node. */
given throwableToNode: Conversion[Throwable, ReactNode] = _.getMessage.asInstanceOf[ReactNode]

/** Success's value is converted. Failure's `getMessage` is converted. */
given tryToNode[T](using cvt: T => ReactNode): Conversion[Try[T], ReactNode] = 
  _ match
    case Failure(t) => t.getMessage.asInstanceOf[ReactNode]
    case Success(v) => cvt(v)

//
// Are these really needed?
//

given func0: Conversion[() => Unit, js.UndefOr[js.Function0[Unit]]] = f => js.defined(js.Any.fromFunction0(f))

given func1[A, T]: Conversion[A => T, js.UndefOr[js.Function1[A, T]]] = f => js.defined(js.Any.fromFunction1(f))

given func2[A, A2, T]: Conversion[(A, A2) => T, js.UndefOr[js.Function2[A, A2, T]]] =
  f => js.defined(js.Any.fromFunction2(f))

given func3[A, A2, A3, T]: Conversion[(A, A2, A3) => T, js.UndefOr[js.Function3[A, A2, A3, T]]] =
  f => js.defined(js.Any.fromFunction3(f))

given func4[A, A2, A3, A4, T]: Conversion[(A, A2, A3, A4) => T, js.UndefOr[js.Function4[A, A2, A3, A4, T]]] =
  f => js.defined(js.Any.fromFunction4(f))

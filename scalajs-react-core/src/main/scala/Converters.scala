// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.JSConverters._

/**
  * Import these via `ttg.react.Converters._` to have implicit conversion.
  * These are kept separate from `ttg.react.implicits` and `ttg.react.syntax`
  * in order to force you to explicitly include implicit conversions to scope.
  */
object Converters {
  @inline implicit def arrayToElement[T <: ReactNode](arr: js.Array[T]) = arr.asInstanceOf[ReactNode]
  @inline implicit def arrayToElement[T <: ReactNode](s: Seq[T]) = (s.toJSArray).asInstanceOf[ReactNode]
  @inline implicit def stringToElement(s: String): ReactNode = s.asInstanceOf[ReactNode]
  @inline implicit def intToElement(i: Int): ReactNode = i.asInstanceOf[ReactNode]
  @inline implicit def doubleToElement(d: Double): ReactNode = d.asInstanceOf[ReactNode]
  @inline implicit def floatToElement(f: Float): ReactNode = f.asInstanceOf[ReactNode]
  @inline implicit def booleanToElement(b: Boolean): ReactNode = b.asInstanceOf[ReactNode]
  @inline implicit def optToElement(s: Option[ReactElement]): ReactNode =
    s.getOrElse(null.asInstanceOf[ReactNode])
  @inline implicit def iterableToElement[T](s: Iterable[T])(implicit cv: T => ReactNode): ReactNode = {
    s.map(cv).toJSArray.asInstanceOf[ReactElement]
  }
  @inline implicit def componentToElement(c: Component[_, _, _, _]): ReactElement = elements.element(c)
}

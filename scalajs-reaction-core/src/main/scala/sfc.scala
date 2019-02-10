// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js

class SFC0(val run: js.Function0[ReactNode]) {
  def toEl = SFC0.toRE(this)
}

object SFC0 {
  def apply(f: () => ReactNode) = new SFC0(f)

  implicit def toRE(s: SFC0): ReactNode =
    ReactJS.createElement(s.run, null)
}

class SFC1[T](val run: js.Function1[T, ReactElement]/*, val defaultValue: Option[T] = None*/) {
  def apply(props: T) = toEl(props)
  //def apply() = toEl(defaultValue)
  def toEl(props: T) = ReactJS.createElement(run, props.asInstanceOf[js.Any])
}

object SFC1 {
  def apply[T](f: T => ReactElement) = new SFC1[T](f)
  // def apply[T](defaultValue: T)(f: T => ReactElement) =
  //   new SFC1[T](f, defaultValue)
}


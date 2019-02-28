// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js

/**
 * A stateless functional component that does not any arguments.  You can export
 * the `.run` function for use in reactjs.
 */
class SFC0(val run: js.Function0[ReactNode]) {
  /** Convert this SFC to a reactjs element. */
  def toEl = SFC0.toRE(this)
}

object SFC0 {
  /** Create an SFC0 from a parameterless scala function. */
  def apply(f: () => ReactNode) = new SFC0(f)

  /** Create a reactjs component directly from this SFC. */
  implicit def toRE(s: SFC0): ReactNode =
    ReactJS.createElement(s.run, null)
}

/** A stateless functional component with one arg, the props. You can export the
 * `.run` function for use in reactjs.
 */
class SFC1[T](val run: js.Function1[T, ReactElement]) {
  /** Create a reactjs component with props. */
  def apply(props: T) = toEl(props)

  /** Create an reactjs component directly from this SFC. */
  def toEl(props: T) = ReactJS.createElement(run, props.asInstanceOf[js.Any])
}

object SFC1 {
  /** Create a SFC1 from a single parameter scala function. */
  def apply[T](f: T => ReactElement) = new SFC1[T](f)
}

/** A stateless functional component with two args, the props and something
 * else. You would use SFC2 to create a SFC that takes a second argument, like a
 * `Ref`, for use in `React.forwardRef`. You can export the `.run` function for
 * use in reactjs.
 * 
 * @see https://reactjs.org/docs/hooks-reference.html#useref
 */
class SFCWithRef[T, R](val run: js.Function2[T, Ref[R], ReactElement])

object SFCWithRef {
  /** Create a SFC2 from 2 parameters. */
  def apply[T, R](f: (T,Ref[R]) => ReactElement) = new SFCWithRef[T,R](f)
}


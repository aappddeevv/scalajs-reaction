// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.|

/**
 * A functional component with zero arguments.  You can export the `.run`
 * function for use in reactjs.
 */
class SFC0(val run: js.Function0[ReactNode]) {
  /** Convert this SFC to a reactjs element. */
  def toEl = ReactJS.createElement(run, null)
}

object SFC0 {
  /** Create an SFC0 from a parameterless scala function. */
  def apply(f: => ReactNode) = new SFC0(() => f)

  /** Create a reactjs component directly from this SFC. */
  implicit def sfc0ToEl(s: SFC0): ReactNode =
    ReactJS.createElement(s.run, null)
}

/** A functional component with one arg, the props. You can export the `.run`
 * function for use in reactjs.
 * @tparam P Props type. Use `js.UndefOr[P]` for optional args.
 */
class SFC1[P](val run: js.Function1[P, ReactElement]) {
  /** Create a reactjs component given some props. */
  def apply(props: P) = toEl(props)

  /** Create an reactjs component directly from this SFC. */
  def toEl(props: P) = ReactJS.createElement(run, props)
}

object SFC1 {
  /** Create a SFC1 from a single parameter scala function taking a js.Object
   * props type.
   */
  def apply[P <: js.Object](f: P => ReactElement) = new SFC1[P](f)

  /** Make it explicit to create a SFC1 that takes a scala object for props. */
  def scala[P](f: P => ReactElement) = new SFC1[P](f)
}

/** A stateless functional component with two args, the props and something
 * else. You would use SFC2 to create a SFC that takes a second argument, like a
 * `Ref`, for use in `React.forwardRef`. You can export the `.run` function for
 * use in reactjs.
 * 
 * @see https://reactjs.org/docs/hooks-reference.html#useref
 */
class SFCWithRef[P, R](val run: js.Function2[P, Ref[R], ReactNode]) {
  /** Create an reactjs component directly from this SFC. */
  def toEl(props: P) = ReactJS.createElement(run.asInstanceOf[ReactJsFunctionComponent], props)  
}

object SFCWithRef {
  /** Create a SFC2 from 2 parameters, the 2nd being a react ref created via
   * `createRef`.
   */
  def apply[P <: js.Object, R](f: (P,Ref[R]) => ReactElement) = new SFCWithRef[P,R](f)

  def scala[P, R](f: (P,Ref[R]) => ReactElement) = new SFCWithRef[P,R](f)
}


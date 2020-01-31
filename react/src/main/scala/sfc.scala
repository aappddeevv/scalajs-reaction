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
}

/** A functional component with one arg, the props. You can export the `.run`
 * function for use in reactjs. If you are defining convenience methods for your
 * component, you can also call `react.createElement` on `run` directly becauseq it is
 * considered a function component by reactjs.
 *
 * @tparam P Props type.
 */
class SFC1[P <: js.Object](val run: SFC1.RunArg[P]) {

  /** Create a reactjs component given some props. */
  def apply(props: P) = toEl(props)

  /** Create a reactjs component given some dynamic props. Experts only! */
  def apply(props: js.Dynamic) = toEl(props.asInstanceOf[P])

  /** Create an reactjs component directly from this SFC. */
  def toEl(props: P): ReactElement = ReactJS.createElement(run, props)
}

object SFC1 {
  /** Type of `run`. This must be usable as a function component in js. */
  type RunArg[P <: js.Object] = js.Function1[P, ReactNode]

  /** Create a SFC1 always taking a single props parameter. */
  def apply[P <: js.Object](f: P => ReactNode) = new SFC1[P](f)
}

/** A stateless functional component with two args, the props and something
 * else. You would use SFC2 to create a SFC that takes a second argument, like a
 * `Ref`, for use in `React.forwardRef`. You can export the `.run` function for
 * use in reactjs.
 *
 * @see https://reactjs.org/docs/hooks-reference.html#useref
 */
class SFCWithRef[P <: js.Object, R](val run: js.Function2[P, Ref[R], ReactNode]) {

  /** Create an reactjs component directly from this SFC. */
  def toEl(props: P) =
    ReactJS.createElement(run.asInstanceOf[ReactJsFunctionComponent], props.asInstanceOf[P|Unit])
}

object SFCWithRef {

  /** Create a SFC2 from 2 parameters, the 2nd being a react ref created via
   * `createRef`.
   */
  def apply[P <: js.Object, R](f: (P, Ref[R]) => ReactElement) = new SFCWithRef[P, R](f)
}

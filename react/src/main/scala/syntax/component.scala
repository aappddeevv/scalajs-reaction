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

trait ComponentSyntax {

  /** Memoize SFC1[P]. You should not need to do this as
   * SFCs should be declared with vals.
   */
  implicit class MemoSFC[P <: js.Object](private val sfc: SFC1[P]) {
    def memo = react.memo(sfc)
  }

  implicit def sfc0ToEl(sfc: SFC0): ReactElement =
    ReactJS.createElement(sfc.run, null)

  /** Convert a SFC and its argument, expressed as a tuple, to an element. */
  implicit def sfc1TupleOpsSyntax[P <: js.Object](f: (SFC1[P], P)): ReactElement =
    ReactJS.createElement(f._1.run, f._2.asInstanceOf[js.Any])

  /** Given a function component and an arg, convert to ReactElement. */
  implicit def func2Element[P <: js.Object](f: (ScalaJSFunctionComponent1, P)): ReactElement =
    ReactJS.createElement(f._1, f._2)

  /** Given a function component and an arg with children, convert to ReactElement. */
  implicit def funcChild2Element[P <: js.Object](f: (ScalaJSFunctionComponent1, P, ReactNode)): ReactElement =
    ReactJS.createElement(f._1, f._2, f._3)

  /** Evil! Auto type conversion... */
  implicit def thunkToSFC(f: () => ReactNode): SFC0 = new SFC0(f)
}

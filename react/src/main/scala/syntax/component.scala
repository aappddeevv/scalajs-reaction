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

trait ComponentSyntax {

  /** Given a function component and an arg, expressed as tuple, convert to ReactElement. */
  implicit def func2Element[P <: js.Object](f: (ScalaJSFunctionComponent1, P)): ReactNode =
    ReactJS.createElement(f._1, f._2)

  /** Given a function component and an arg with children, expressed as a tuple, convert to ReactElement. */
  implicit def funcChild2Element[P <: js.Object](f: (ScalaJSFunctionComponent1, P, ReactNode)): ReactNode =
    ReactJS.createElement(f._1, f._2, f._3)

  /** Slightly evil. Auto conversion of no-props function component to `ReactNode`. */
  implicit def sfc02Node(f: js.Function0[ReactNode]): ReactNode =
    ReactJS.createElement(f, null)

  /** Evil! Auto type conversion from a no-arg function. */
  implicit def thunkToSFC(f: () => ReactNode): ReactNode =
    ReactJS.createElement(js.Any.fromFunction0(f), null)

  /** Convert a plain js function to an element using some syntax. */
  implicit class RichJSFunction0(private val f: js.Function0[ReactNode]) {
    def element: ReactNode = ReactJS.createElement(f, null)
    def toEl: ReactNode = ReactJS.createElement(f, null)
    def displayName(name: String): Unit = f.asInstanceOf[js.Dynamic].displayName = name
  }

  /** Convert a plain js function with 1 props argument to an element using some syntax. */
  implicit class RichScalaJSFunction1(private val f: ScalaJSFunctionComponent1) {
    def unsafeElementWith(props: js.Dynamic): ReactNode = ReactJS.createElement(f, props.asInstanceOf[js.Any])
    def unsafeElementWith(name: String, props: js.Dynamic): ReactNode = {
      f.asInstanceOf[js.Dynamic].displayName = name
      ReactJS.createElement(f, props.asInstanceOf[js.Any])
    }
    def elementWith[P <: js.Object](props: P): ReactNode = ReactJS.createElement(f, props.asInstanceOf[js.Any])
    def elementWith[P <: js.Object](name: String, props: P): ReactNode = {
      f.asInstanceOf[js.Dynamic].displayName = name
      ReactJS.createElement(f, props.asInstanceOf[js.Any])
    }
    def displayName(name: String): Unit = f.asInstanceOf[js.Dynamic].displayName = name
  }

  /** Convert a plain js function with 1 props argument to an element using some syntax. */
//   implicit class RichJSFunction1[P <: js.Object](private val f: js.Function1[P, ReactNode]) {
//     def unsafeElementWith(props: js.Dynamic): ReactNode = ReactJS.createElement(f, props.asInstanceOf[js.Any])
//     def unsafeElementWith(name: String, props: js.Dynamic): ReactNode = {
//         f.asInstanceOf[js.Dynamic].displayName = name
//         ReactJS.createElement(f, props.asInstanceOf[js.Any])
//     }
//     def elementWith(props: P): ReactNode = ReactJS.createElement(f, props.asInstanceOf[js.Any])
//     def elementWith(name: String, props: P): ReactNode = {
//         f.asInstanceOf[js.Dynamic].displayName = name
//         ReactJS.createElement(f, props.asInstanceOf[js.Any])
//     }
//     def toEl(props: P): ReactNode = ReactJS.createElement(f, props.asInstanceOf[js.Any])
//     def displayName(name: String): Unit = f.asInstanceOf[js.Dynamic].displayName = name
//   }

  /** Convert standard scala function with 1 props arguments to an element using some syntax. */
  implicit class RichScalaFunction[P <: js.Object](private val f: P => ReactNode) {
    def unsafeElementWith(props: js.Dynamic): ReactNode =
      ReactJS.createElement(js.Any.fromFunction1(f), props.asInstanceOf[js.Any])
    def unsafeElementWith(name: String, props: js.Dynamic): ReactNode = {
      val jsf = js.Any.fromFunction1(f)
      jsf.asInstanceOf[js.Dynamic].displayName = name
      ReactJS.createElement(jsf, props.asInstanceOf[js.Any])
    }
    def elementWith(props: P): ReactNode = ReactJS.createElement(js.Any.fromFunction1(f), props.asInstanceOf[js.Any])
    def elementWith(name: String, props: P): ReactNode = {
      val jsf = js.Any.fromFunction1(f)
      jsf.asInstanceOf[js.Dynamic].displayName = name
      ReactJS.createElement(jsf, props.asInstanceOf[js.Any])
    }
    def toEl(props: P): ReactNode = ReactJS.createElement(js.Any.fromFunction1(f), props.asInstanceOf[js.Any])
  }
}

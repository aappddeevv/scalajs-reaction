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
package syntax

object component:
  import scala.scalajs.js

  extension (el: ReactElement)
    /** Simple conversion since many times you need a js.Array but its invariant. */
    def asNode = el.asInstanceOf[ReactNode]
  end extension

  /** Convert a plain js no-arg function to an element using some syntax. */
  extension (f: ScalaJSFunctionComponent)
    def element: ReactNode = ReactJS.createElement(f, null)

    def toEl: ReactNode = ReactJS.createElement(f, null)

    def displayName(name: String): Unit = f.asInstanceOf[js.Dynamic].updateDynamic("displayName")(name)
  end extension

  /** Convert a plain js function with 1 props argument to an element using some syntax. */
  extension [P <: js.Object](f: js.Function1[P, ReactNode])
    def unsafeElementWith(props: js.Dynamic): ReactNode = ReactJS.createElement(f, props.asInstanceOf[js.Any])

    def unsafeElementWith(name: String, props: js.Dynamic): ReactNode = 
        f.asInstanceOf[js.Dynamic].displayName = name
        ReactJS.createElement(f, props.asInstanceOf[js.Any])

    def elementWith(props: P): ReactNode = ReactJS.createElement(f, props.asInstanceOf[js.Any])

    def elementWith(name: String, props: P): ReactNode =
        f.asInstanceOf[js.Dynamic].displayName = name
        ReactJS.createElement(f, props.asInstanceOf[js.Any])

    def toEl(props: P): ReactNode = ReactJS.createElement(f, props.asInstanceOf[js.Any])

    def displayName(name: String): Unit = f.asInstanceOf[js.Dynamic].updateDynamic("displayName")(name)

end component
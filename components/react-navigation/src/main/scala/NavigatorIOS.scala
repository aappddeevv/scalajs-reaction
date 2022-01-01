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

package react_navigation

import scala.scalajs.js

import js.annotation._

import react._

@js.native
@JSImport("react-navigation", JSImport.Namespace)
object navigation extends NavigationJS

object NavigatorIOS {
  @js.native
  @JSImport("react-navigation", "NavigatorIOS")
  object JS extends ReactJSComponent

  def apply[T](props: Props) =
    createElement0(JS, props)

  trait Props extends js.Object {
    var initialRoute: js.UndefOr[Route] = js.undefined
  }
}

trait Route extends js.Object {
  val component: ReactType
  val title: String
  var passProps: js.UndefOr[js.Object] = js.undefined
}

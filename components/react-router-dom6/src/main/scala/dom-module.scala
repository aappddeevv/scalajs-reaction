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

package react_router6
package dom

import scala.scalajs.js

import js.annotation._
import js.|
import org.scalajs.dom

import react._
import vdom._

@js.native
trait Hooks extends js.Object {
  def usePrompt(message: String, when: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def useSearchParams(): dom.experimental.URLSearchParams = js.native
}

@js.native
@JSImport("react-router-dom", JSImport.Namespace)
object module extends Hooks

object BrowserRouter {
  @js.native
  @JSImport("react-router-dom", "BrowserRouter")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) = createElement(JS, props, children: _*)
  def apply(children: ReactNode*) = createElement(JS, null, children: _*)

  trait Props extends js.Object {
    var timeout: js.UndefOr[Int] = js.undefined
    var window: js.UndefOr[dom.Window] = js.undefined
  }
}

object HashRouter {
  @js.native
  @JSImport("react-router-dom", "HashRouter")
  object JS extends ReactJSComponent

  def apply(child: ReactNode) =
    createElementN(JS, null)(child)

  def apply(props: Props)(child: ReactNode) =
    createElementN(JS, props)(child)

  trait Props extends js.Object {
    var timeout: js.UndefOr[Int] = js.undefined
    var window: js.UndefOr[dom.Window] = js.undefined
  }
}

object Link {
  @js.native
  @JSImport("react-router-dom", "Link")
  object JS extends ReactJSComponent

  def apply[P <: Props](props: P)(children: ReactNode*) = createElement(JS, props, children: _*)
  def apply[P <: Props](props: P) = createElement0(JS, props)

  trait Props extends js.Object {
    var as: js.UndefOr[ReactType] = js.undefined
    var onClick: js.UndefOr[js.Function1[ReactMouseEvent[dom.html.Element], Unit]] = js.undefined
    var replace: js.UndefOr[Boolean] = js.undefined
    def state[S]: js.UndefOr[S] = js.undefined

    /** As in "a" link target. */
    var target: js.UndefOr[String] = js.undefined
    var to: js.UndefOr[LocationInit[_] | String] = js.undefined
  }
}

object NavLink {
  @js.native
  @JSImport("react-router-dom", "NavLink")
  object JS extends ReactJSComponent

  def apply(props: Props = null) = createElement0(JS, props)

  trait Props extends js.Object {
    var `aria-current`: js.UndefOr[String] = js.undefined
    var activeClassName: js.UndefOr[String] = js.undefined
    var activeStyle: js.UndefOr[js.Object] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var to: js.UndefOr[LocationInit[_] | String] = js.undefined
  }
}

object Prompt {
  @js.native
  @JSImport("react-router-dom", "Prompt")
  object JS extends ReactJSComponent

  def apply(props: Props = null) = createElement0(JS, props)

  trait Props extends js.Object {
    var when: js.UndefOr[Boolean] = js.undefined
    var message: js.UndefOr[js.Function1[Location[_] | String, String | Boolean]] = js.undefined
  }
}

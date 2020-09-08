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
import org.scalajs.{dom => sdom}

import react._
import vdom._

@js.native
trait Hooks extends js.Object {
  def usePrompt(message: String, when: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def useSearchParams(init: js.UndefOr[URLSearchParamsInit] = js.undefined): sdom.experimental.URLSearchParams = js.native
}

@js.native
trait Utils extends js.Object {
  def createSearchParams(init: URLSearchParamsInit): sdom.experimental.URLSearchParams = js.native
}

@js.native
@JSImport("react-router-dom", JSImport.Namespace)
object module extends Hooks with Utils

object BrowserRouter {
  @js.native
  @JSImport("react-router-dom", "BrowserRouter")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) = createElement(JS, props, children: _*)
  def apply(children: ReactNode*) = createElement(JS, null, children: _*)

  trait Props extends js.Object {
    var window: js.UndefOr[sdom.Window] = js.undefined
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
    var window: js.UndefOr[sdom.Window] = js.undefined
  }
}

object Link {
  @js.native
  @JSImport("react-router-dom", "Link")
  object JS extends ReactJSComponent

  def apply[P <: Props](props: P)(children: ReactNode*) = createElement(JS, props, children: _*)
  def apply[P <: Props](props: P) = createElement0(JS, props)

  // should drop href according react-router-dom docs because its
  // set by the component
  trait Props extends AnchorHTMLAttributes[sdom.html.Anchor] {
    var replace: js.UndefOr[Boolean] = js.undefined
    var state: js.UndefOr[js.Object|Null] = js.undefined
    var to: js.UndefOr[To] = js.undefined
  }
}

object NavLink {
  @js.native
  @JSImport("react-router-dom", "NavLink")
  object JS extends ReactJSComponent

  def apply(props: Props = null) = createElement0(JS, props)

  trait Props extends Link.Props {
    //var `aria-current`: js.UndefOr[String] = js.undefined
    var activeClassName: js.UndefOr[String] = js.undefined
    var activeStyle: js.UndefOr[js.Object] = js.undefined
    //var className: js.UndefOr[String] = js.undefined
    //var to: js.UndefOr[LocationInit[_] | String] = js.undefined
    var caseSensitive: js.UndefOr[Boolean] = js.undefined
    var end: js.UndefOr[Boolean] = js.undefined
  }
}

object Prompt {
  @js.native
  @JSImport("react-router-dom", "Prompt")
  object JS extends ReactJSComponent

  def apply(props: Props) = createElement0(JS, props)
  
  def apply(m: String, w: Boolean) = createElement0(JS, new Props { message = m; when = w })

  trait Props extends js.Object {
    var when: js.UndefOr[Boolean] = js.undefined
    var message: js.UndefOr[String ] = js.undefined
  }
}

object StaticRouter {
  @js.native
  @JSImport("react-router-dom", "StaticRouter")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) = createElement(JS, props, children:_*)
  
  trait Props extends js.Object {
    var location: js.UndefOr[LocationInit[_]] = js.undefined
  }
}

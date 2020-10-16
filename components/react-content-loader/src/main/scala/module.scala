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

package react_content_loader

import scala.scalajs.js
import js.|
import js.annotation._

import react._
import react.implicits._
import org.scalajs.dom
import react.vdom._
import svgtags._

//
@js.native
@JSImport("react-content-loader", JSImport.Namespace)
object module extends js.Object {}

object ContentLoader {
  @js.native
  @JSImport("react-content-loader", "ContentLoader")
  object JS extends ReactJSComponent

  trait Props extends AllSVGProps[dom.svg.Element] {
    //var key: js.UndefOr[String] = js.undefined
    var animate: js.UndefOr[Boolean] = js.undefined
    // var backgroundColor: js.UndefOr[String] = js.undefined
    // var backgroundOpacity: js.UndefOr[Int] = js.undefined
    var baseUrl: js.UndefOr[String] = js.undefined
    var foregroundColor: js.UndefOr[String] = js.undefined
    var foregroundOpacity: js.UndefOr[Int] = js.undefined
    var gradientRatio: js.UndefOr[Int] = js.undefined
    var interval: js.UndefOr[Int] = js.undefined
    var rtl: js.UndefOr[Boolean] = js.undefined
    //var speed: js.UndefOr[Float] = js.undefined
    var title: js.UndefOr[String] = js.undefined
    var uniqueKey: js.UndefOr[String] = js.undefined
    //var viewBox: js.UndefOr[String] = js.undefined
  }

  /** Should only be SVG children. */
  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children: _*)

  object Facebook {
    @js.native
    @JSImport("react-content-loader", "Facebook")
    object JS extends ReactJSComponent
    def apply(props: Props)(children: ReactNode*) =
      createElement(JS, props, children: _*)
  }
  object Instagram {
    @js.native
    @JSImport("react-content-loader", "Instagram")
    object JS extends ReactJSComponent
    def apply(props: Props)(children: ReactNode*) =
      createElement(JS, props, children: _*)
  }
  object Code {
    @js.native
    @JSImport("react-content-loader", "Code")
    object JS extends ReactJSComponent
    def apply(props: Props)(children: ReactNode*) =
      createElement(JS, props, children: _*)
  }
  object List {
    @js.native
    @JSImport("react-content-loader", "List")
    object JS extends ReactJSComponent
    def apply(props: Props)(children: ReactNode*) =
      createElement(JS, props, children: _*)
  }
  object BulletList {
    @js.native
    @JSImport("react-content-loader", "BulletList")
    object JS extends ReactJSComponent
    def apply(props: Props)(children: ReactNode*) =
      createElement(JS, props, children: _*)
  }
}

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

package fabric
package components

import scala.scalajs.js

import js.annotation._
import js.|

import org.scalajs.dom

import react._

import vdom._

import fabric.styling._

object Icon {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Icon", "Icon")
  object JS extends ReactJsComponent

  trait IIcon extends js.Object {}

  def apply(props: Props = null) =
    createElement0(JS, props)

  trait StyleProps extends Theme {
    var className: js.UndefOr[String]     = js.undefined
    var iconClassName: js.UndefOr[String] = js.undefined
    var isImage: js.UndefOr[Boolean]      = js.undefined
    var styles: js.UndefOr[Styles]        = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
  }

  trait Props extends ComponentRef[IIcon] with HTMLAttributes[dom.html.Element] with Theme {
    var ariaLabel: js.UndefOr[String]       = js.undefined
    var iconName: js.UndefOr[String]        = js.undefined
    var imageProps: js.UndefOr[Image.Props] = js.undefined
    // i think think this needs to be a constructor
    var imageErrorAs: js.UndefOr[js.Any]                               = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
  }

  object Image {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Icon", "ImageIcon")
    object JS extends ReactJsComponent

    trait Props extends HTMLAttributes[dom.html.Element] {
      var iconName: js.UndefOr[String] = js.undefined
    }
    def apply(props: Props = null) =
      createElement0(JS, props)
  }

  object Font {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Icon", "FontIcon")
    object JS extends ReactJsComponent

    trait Props extends HTMLAttributes[dom.html.Element] {
      var imageProps: Image.Props
    }
    def apply(props: Props = null) =
      createElement0(JS, props)

  }

}

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

object ChoiceGroup {

  @js.native
  @JSImport("office-ui-fabric-react/lib/ChoiceGroup", "ChoiceGroup")
  object JS extends ReactJSComponent

  def apply(props: Props = null) = createElement0(JS, props)

  @js.native
  trait IChoiceGroup extends js.Object {
    def focus(): Unit = js.native
    val checkedOption: js.UndefOr[Option] = js.native
  }

  // |dom.html.Input
  trait Props extends HTMLAttributes[dom.html.Element] with ReactJSProps with ComponentRef[IChoiceGroup] {
    var options: js.UndefOr[js.Array[Option]] = js.undefined
    var defaultSelectedKey: js.UndefOr[String | Int] = js.undefined
    var selectedKey: js.UndefOr[String | Int] = js.undefined
    @JSName("onChange")
    var onChangeEvent: js.UndefOr[OnChangeEvent] =
      js.undefined
    var label: js.UndefOr[String] = js.undefined
    var ariaLabelledBy: js.UndefOr[String] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var required: js.UndefOr[Boolean] = js.undefined
  }

  type OnChangeEvent = js.Function2[ReactFormEvent[dom.html.Element], js.UndefOr[Option], Unit]

  def OnChangeEvent(
    f: (ReactFormEvent[dom.html.Element], js.UndefOr[Option]) => Unit
  ): js.UndefOr[OnChangeEvent] =
    js.defined((e, o) => f(e, o))

  trait Styles extends IStyleSetTag {

    /** Not current root, temporarily use applicationRole. */
    var root: js.UndefOr[IStyle] = js.undefined
    var label: js.UndefOr[IStyle] = js.undefined
    var applicationRole: js.UndefOr[IStyle] = js.undefined
    var flexContainer: js.UndefOr[IStyle] = js.undefined
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var theme: js.UndefOr[ITheme] = js.undefined
    var optionsContainIconOrImage: js.UndefOr[Boolean] = js.undefined
  }

  // |dom.html.Input
  trait Option extends HTMLAttributes[dom.html.Element] {
    val key: String
    val text: String
    var onRenderField: js.UndefOr[IRenderFunction[Option]] = js.undefined
    var onRenderLabel: js.UndefOr[IRenderFunction[Option]] = js.undefined
    var iconProps: js.UndefOr[Icon.Props] = js.undefined
    var imageSrc: js.UndefOr[String] = js.undefined
    var imageAlt: js.UndefOr[String] = js.undefined
    var selectedImageSrc: js.UndefOr[String] = js.undefined
    var imageSize: js.UndefOr[ImageSize] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var checked: js.UndefOr[Boolean] = js.undefined
    //var id: js.UndefOr[String] = js.undefined
    var labelId: js.UndefOr[String] = js.undefined
    var ariaLabel: js.UndefOr[String] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[Option.StyleProps, Option.Styles]] = js.undefined
  }

  object Option {
    trait StyleProps extends js.Object {
      var theme: js.UndefOr[ITheme] = js.undefined
      var hasIcon: js.UndefOr[Boolean] = js.undefined
      var hasImage: js.UndefOr[Boolean] = js.undefined
      var checked: js.UndefOr[Boolean] = js.undefined
      var disabled: js.UndefOr[Boolean] = js.undefined
      var imageIsLarge: js.UndefOr[Boolean] = js.undefined
      var imageSize: js.UndefOr[ImageSize] = js.undefined
      var focused: js.UndefOr[Boolean] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var root: js.UndefOr[IStyle] = js.undefined
//   var choiceFieldWrapper?: IStyle;
//   var input?: IStyle;
//   var field?: IStyle;
//   var innerField?: IStyle;
//   var imageWrapper?: IStyle;
//   var selectedImageWrapper?: IStyle;
//   var iconWrapper?: IStyle;
//   var labelWrapper?: IStyle;
    }
  }

  trait ImageSize extends js.Object {
    val height: Int
    val width: Int
  }
}

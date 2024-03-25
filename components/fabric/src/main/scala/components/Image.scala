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
import org.scalajs.dom
import react._
import vdom._
import fabric.styling._

object Image {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Image", "Image")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children*)

  def only(props: Props) = createElement0(JS, props)

  @js.native
  trait IImage extends js.Object {}

  trait StyleProps extends Theme {
    var className: js.UndefOr[String] = js.undefined
    var maximizeFrame: js.UndefOr[Boolean] = js.undefined
    var isLoaded: js.UndefOr[Boolean] = js.undefined
    var shouldFadeIn: js.UndefOr[Boolean] = js.undefined
    var shouldStartVisible: js.UndefOr[Boolean] = js.undefined
    var isLandscape: js.UndefOr[Boolean] = js.undefined
    var isCenter: js.UndefOr[Boolean] = js.undefined
    var isContain: js.UndefOr[Boolean] = js.undefined
    var isCover: js.UndefOr[Boolean] = js.undefined
    var isCenterCover: js.UndefOr[Boolean] = js.undefined
    var isNone: js.UndefOr[Boolean] = js.undefined
    var isError: js.UndefOr[Boolean] = js.undefined
    var isNotImageFit: js.UndefOr[Boolean] = js.undefined
    var width: js.UndefOr[Double | String] = js.undefined
    var height: js.UndefOr[Double | String] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var image: js.UndefOr[IStyle] = js.undefined
  }

  @js.native
  abstract sealed trait Fit extends js.Any
  object Fit {
    val center = 0.asInstanceOf[Fit]
    val contain = 1.asInstanceOf[Fit]
    val cover = 2.asInstanceOf[Fit]
    val none = 3.asInstanceOf[Fit]
    val centerCover = 4.asInstanceOf[Fit]
  }

  @js.native
  abstract sealed trait CoverStyle extends js.Any
  object CoverStyle {
    val landscape = 0.asInstanceOf[CoverStyle]
    val portrait = 1.asInstanceOf[CoverStyle]
  }

  @js.native
  abstract sealed trait LoadState extends js.Any
  object LoadState {
    val notLoaded = 0.asInstanceOf[LoadState]
    val loaded = 1.asInstanceOf[LoadState]
    val error = 2.asInstanceOf[LoadState]
    val erroLoaded = 3.asInstanceOf[LoadState]
  }

  trait Props extends ComponentRef[IImage] with ImgHTMLAttributes[dom.html.Image] with Theme {
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    //var className: js.UndefOr[String] = js.undefined
    var shouldFadeIn: js.UndefOr[Boolean] = js.undefined
    var shouldStartVisible: js.UndefOr[Boolean] = js.undefined
    var imageFit: js.UndefOr[Fit] = js.undefined
    var errorSrc: js.UndefOr[String] = js.undefined
    var maximizeFrame: js.UndefOr[Boolean] = js.undefined
    var onLoadingStateChange: js.UndefOr[js.Function1[LoadState, Unit]] = js.undefined
    var coverStyle: js.UndefOr[CoverStyle] = js.undefined
  }

}

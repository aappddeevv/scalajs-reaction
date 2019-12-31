// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import react._
import vdom._
import fabric.styling._

object Image {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Image", "Image")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  @js.native
  trait IImage extends js.Object {
  }

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
    var width: js.UndefOr[Double|String] = js.undefined
    var height: js.UndefOr[Double|String] = js.undefined
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
  object LoadaState {
    val notLoaded = 0.asInstanceOf[CoverStyle]
    val loaded = 1.asInstanceOf[CoverStyle]
    val error = 2.asInstanceOf[CoverStyle]
    val erroLoaded = 3.asInstanceOf[CoverStyle]
  }

  trait Props
      extends ComponentRef[IImage]
      with ImgHTMLAttributes[dom.html.Image]
      with Theme {
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


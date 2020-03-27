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

object Persona {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Persona", "Persona")
  object JS extends ReactJSComponent

  def apply(props: Props = null) = createElement0(JS, props)

  @js.native
  trait IPersona extends js.Object {}

  trait StyleProps extends Theme {
    var className: js.UndefOr[String]          = js.undefined
    var coinSize: js.UndefOr[Int]              = js.undefined
    var size: js.UndefOr[Size]                 = js.undefined
    var presence: js.UndefOr[Presence]         = js.undefined
    var showSecondaryText: js.UndefOr[Boolean] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle]          = js.undefined
    var details: js.UndefOr[IStyle]       = js.undefined
    var primaryText: js.UndefOr[IStyle]   = js.undefined
    var secondaryText: js.UndefOr[IStyle] = js.undefined
    var tertiaryText: js.UndefOr[IStyle]  = js.undefined
    var optionalText: js.UndefOr[IStyle]  = js.undefined
    var textContent: js.UndefOr[IStyle]   = js.undefined
  }

  @js.native
  abstract sealed trait Size extends js.Any
  object Size {
    val tiny            = 0.asInstanceOf[Size]
    val extraExtraSmall = 1.asInstanceOf[Size]
    val extraSmall      = 2.asInstanceOf[Size]
    val small           = 3.asInstanceOf[Size]
    val regular         = 4.asInstanceOf[Size]
    val large           = 5.asInstanceOf[Size]
    val extraLarge      = 6.asInstanceOf[Size]
    val size28          = 7.asInstanceOf[Size]
    val size16          = 8.asInstanceOf[Size]
    val size10          = 9.asInstanceOf[Size]
    val size24          = 10.asInstanceOf[Size]
    val size32          = 11.asInstanceOf[Size]
    val size40          = 12.asInstanceOf[Size]
    val size48          = 13.asInstanceOf[Size]
    val size72          = 14.asInstanceOf[Size]
    val size100         = 15.asInstanceOf[Size]
  }

  @js.native
  abstract sealed trait size extends js.Any
  object size {
    val size10  = "10px".asInstanceOf[size]
    val size16  = "16px".asInstanceOf[size]
    val size24  = "24px".asInstanceOf[size]
    val size28  = "28px".asInstanceOf[size]
    val size32  = "32px".asInstanceOf[size]
    val size40  = "40px".asInstanceOf[size]
    val size48  = "48px".asInstanceOf[size]
    val size72  = "72px".asInstanceOf[size]
    val size100 = "100px".asInstanceOf[size]
  }

  @js.native
  abstract sealed trait presenceSize extends js.Any
  object presenceSize {
    val size6  = "6px".asInstanceOf[presenceSize]
    val size8  = "8px".asInstanceOf[presenceSize]
    val size12 = "12px".asInstanceOf[presenceSize]
    val size20 = "20px".asInstanceOf[presenceSize]
    val size28 = "28px".asInstanceOf[presenceSize]
    val border = "2px".asInstanceOf[presenceSize]
  }

  @js.native
  sealed trait Presence extends js.Any
  object Presence {
    val none    = 0.asInstanceOf[InitialsColor]
    val offline = 1.asInstanceOf[InitialsColor]
    val away    = 2.asInstanceOf[InitialsColor]
    val dnd     = 3.asInstanceOf[InitialsColor]
    val blocked = 4.asInstanceOf[InitialsColor]
    val busy    = 5.asInstanceOf[InitialsColor]
  }

  @js.native
  sealed trait InitialsColor extends js.Any
  object InitialsColor {
    val lightBlue   = 0.asInstanceOf[InitialsColor]
    val blue        = 1.asInstanceOf[InitialsColor]
    val darkBlue    = 2.asInstanceOf[InitialsColor]
    val teal        = 3.asInstanceOf[InitialsColor]
    val lightGreen  = 4.asInstanceOf[InitialsColor]
    val green       = 5.asInstanceOf[InitialsColor]
    val darkGreen   = 6.asInstanceOf[InitialsColor]
    val lightPink   = 7.asInstanceOf[InitialsColor]
    val pink        = 8.asInstanceOf[InitialsColor]
    val magenta     = 9.asInstanceOf[InitialsColor]
    val purple      = 10.asInstanceOf[InitialsColor]
    val black       = 11.asInstanceOf[InitialsColor]
    val orange      = 12.asInstanceOf[InitialsColor]
    val red         = 13.asInstanceOf[InitialsColor]
    val darkRed     = 14.asInstanceOf[InitialsColor]
    val transparent = 15.asInstanceOf[InitialsColor]
    val violet      = 16.asInstanceOf[InitialsColor]
  }

  trait SharedProps extends ComponentRef[IPersona] with HTMLAttributes[dom.html.Div] with Theme {
    var text: js.UndefOr[String]                                                   = js.undefined
    var size: js.UndefOr[Size]                                                     = js.undefined
    var onRenderCoin: js.UndefOr[IRenderFunction[SharedProps]]                     = js.undefined
    var imageShouldFadeIn: js.UndefOr[Boolean]                                     = js.undefined
    var imageShouldStartVisible: js.UndefOr[Boolean]                               = js.undefined
    var imageUrl: js.UndefOr[String]                                               = js.undefined
    var imageAlt: js.UndefOr[String]                                               = js.undefined
    var imageInitials: js.UndefOr[String]                                          = js.undefined
    var allowPhoneInitials: js.UndefOr[String]                                     = js.undefined
    var onRenderInitials: js.UndefOr[IRenderFunction[SharedProps]]                 = js.undefined
    var onPhotoLoadingStateChange: js.UndefOr[js.Function1[Image.LoadState, Unit]] = js.undefined
    var initialsColor: js.UndefOr[InitialsColor | String]                          = js.undefined
    var presence: js.UndefOr[Presence]                                             = js.undefined
    var secondaryText: js.UndefOr[String]                                          = js.undefined
    var tertiaryText: js.UndefOr[String]                                           = js.undefined
    var optionalText: js.UndefOr[String]                                           = js.undefined
    var hidePersonaDetails: js.UndefOr[Boolean]                                    = js.undefined
    var showSecondaryText: js.UndefOr[Boolean]                                     = js.undefined
    var showUnknownPersonaCoin: js.UndefOr[Boolean]                                = js.undefined
    var showInitialsUntilImageLoads: js.UndefOr[Boolean]                           = js.undefined
    var coinSize: js.UndefOr[Int]                                                  = js.undefined
    var coinProps: js.UndefOr[Coin.Props]                                          = js.undefined
  }

  trait Props extends SharedProps with ComponentRef[IPersona] with Theme {
    //var className: js.UndefOr[String] = js.undefined
    //var coinSize: js.UndefOr[Int] = js.undefined
    //var size: js.UndefOr[Size] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var onRenderPrimaryText: js.UndefOr[IRenderFunction[Props]]        = js.undefined
    var onRenderSecondaryText: js.UndefOr[IRenderFunction[Props]]      = js.undefined
    var onRenderTertiaryText: js.UndefOr[IRenderFunction[Props]]       = js.undefined
    var onRenderOptionalText: js.UndefOr[IRenderFunction[Props]]       = js.undefined
  }

  trait PresenceProps extends SharedProps {
    var styles: js.UndefOr[IStyleFunctionOrObject[PresenceStyleProps, PresenceStyles]] = js.undefined
  }

  trait PresenceStyleProps extends Theme {
    var className: js.UndefOr[String]  = js.undefined
    var presence: js.UndefOr[Presence] = js.undefined
    var size: js.UndefOr[Size]         = js.undefined
  }

  trait PresenceStyles extends IStyleSetTag {
    var presence: IStyle
    var presenceIcon: IStyle
  }

  object Coin {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Persona", "PersonaCoin")
    object JS extends ReactJSComponent

    def apply(props: Props = null) = createElement0(JS, props)

    trait Props extends SharedProps {
      var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
      //var className: js.UndefOr[String] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var coin: js.UndefOr[IStyle]                      = js.undefined
      var imageArea: js.UndefOr[IStyle]                 = js.undefined
      var image: js.UndefOr[IStyle]                     = js.undefined
      var initials: js.UndefOr[IStyle]                  = js.undefined
      var size10WithoutPresenceIcon: js.UndefOr[IStyle] = js.undefined
    }

    trait StyleProps extends Theme {
      var className: js.UndefOr[String]               = js.undefined
      var size: js.UndefOr[Size]                      = js.undefined
      var coinSize: js.UndefOr[Int]                   = js.undefined
      var showUnknownPersonaCoin: js.UndefOr[Boolean] = js.undefined
    }
  }

}

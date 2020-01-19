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

import react._

object Text {

  @js.native
  @JSImport("office-ui-fabric-react", "lib/Text")
  object JS extends ReactJsComponent

  def apply(props: Props)(text: js.UndefOr[String] = js.undefined) =
    createElement(JS, props)(text.asInstanceOf[ReactNode])

  // from ReactJsProps
  trait Props extends js.Object {
    var variant: js.UndefOr[Variant] = js.undefined
    var nowrap: js.UndefOr[Boolean]  = js.undefined
    var block: js.UndefOr[Boolean]   = js.undefined
  }

  @js.native
  abstract trait Variant extends js.Any
  object Variant {
    val large       = "large".asInstanceOf[Variant]
    val medium      = "medium".asInstanceOf[Variant]
    val mediumPlus  = "mediumPlus".asInstanceOf[Variant]
    val mega        = "mega".asInstanceOf[Variant]
    val small       = "small".asInstanceOf[Variant]
    val smallPlus   = "smallPlus".asInstanceOf[Variant]
    val superLarge  = "superLarge".asInstanceOf[Variant]
    val tiny        = "tiny".asInstanceOf[Variant]
    val xLarge      = "xLarge".asInstanceOf[Variant]
    val xLargePlus  = "xLargePlus".asInstanceOf[Variant]
    val xSmall      = "xSmall".asInstanceOf[Variant]
    val xxLarge     = "xxLarge".asInstanceOf[Variant]
    val xxLargePlus = "xxLargePlus".asInstanceOf[Variant]
  }

}

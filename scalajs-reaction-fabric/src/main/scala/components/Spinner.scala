// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import vdom._
import fabric.styling._

object Spinner {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("office-ui-fabric-react/lib/Spinner", "Spinner")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children: _*)

  @js.native
  trait ISpinner extends js.Object

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var circle: js.UndefOr[IStyle] = js.undefined
    var label: js.UndefOr[IStyle] = js.undefined
    var screenReaderText: js.UndefOr[IStyle] = js.undefined
  }

  trait StyleProps extends js.Object{
    var className: js.UndefOr[String]              = js.undefined
    var size: js.UndefOr[Size] = js.undefined
  }

  trait Props extends KeyAndRef {
    var componentRef: js.UndefOr[ISpinner => Unit] = js.undefined
    var size: js.UndefOr[Size]                      = js.undefined
    var label: js.UndefOr[String]                  = js.undefined
    var className: js.UndefOr[String]              = js.undefined
    var ariaLabel: js.UndefOr[String]              = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
  }

  @js.native
  abstract sealed trait Size extends js.Any
  object Size {
    var xSmall = 0.asInstanceOf[Size]
    var small  = 1.asInstanceOf[Size]
    var medium = 2.asInstanceOf[Size]
    var large  = 3.asInstanceOf[Size]
  }

}


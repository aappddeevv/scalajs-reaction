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

package react_teleporter

import react._
import react.implicits._
import scala.scalajs.js
import org.scalajs.dom
import js.annotation._
import js.|

@js.native
@JSImport("react-teleporter", JSImport.Namespace)
object module extends js.Object {
  /**
  * @tparam TC Props that you want the target to pass along to the source but defined in target.
  */
  def createTeleporter[TC <: js.Object](
      options: js.UndefOr[TeleporterOptions]=js.undefined): Teleporter[TC] = js.native
}

/** 
 * @tparam TC Props that will be passed along to the source but defined in target.
 */
@js.native
trait Teleporter[TC <: js.Object]  extends js.Object {
    def Source: TeleporterSource[TC] = js.native
    def Target: TeleporterTarget[TC] = js.native
    def useTargetRef(): Ref[dom.html.Element] = js.native
}

trait TargetProps extends js.Object {
  var as: js.UndefOr[ReactType] = js.undefined
}

trait SourceProps extends js.Object {
  var multiple: js.UndefOr[Boolean] = js.undefined
}

@js.native trait TeleporterSource[TC <: js.Object] extends ReactJSComponent

object TeleporterSource {

    implicit class RichTeleporterSource[TC <: js.Object](private val s: TeleporterSource[TC]) extends AnyVal {
      def apply(children: TC => ReactNode) = {
        val fchild: js.Function1[TC, ReactNode] = props => {
          children(props)
        }
        createElement(s, null, createElement(fchild, null))
      }
      def multiple(children: TC => ReactNode) = {
        val fchild: js.Function1[TC, ReactNode] = props => {
          children(props)
        }
        createElement(s, new SourceProps { multiple = true }, createElement(fchild, null))
      }
    }
}

@js.native trait TeleporterTarget[TC <: js.Object] extends ReactJSComponent 

object TeleporterTarget {
  implicit class RichTeleporterTarget[TC <: js.Object](private val t: TeleporterTarget[TC]) extends AnyVal {
      def apply(childProps: js.UndefOr[TC] = js.undefined, tprops: js.UndefOr[TargetProps] = js.undefined) =
          createElement(
            t, 
            js.Object.assign(js.Object(), childProps.asInstanceOf[js.Object], tprops.asInstanceOf[js.Object]), 
          )
    }
}

trait TeleporterOptions extends js.Object {
  var multiSources: js.UndefOr[Boolean] = js.undefined
}


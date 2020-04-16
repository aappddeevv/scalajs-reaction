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

package use_query_params

import react._
import scala.scalajs.js
import org.scalajs.dom
import js.|
import js.annotation._

//
@js.native
abstract trait UrlUpdateType extends js.Any
object UrlUpdateType {
  val replace = "replace".asInstanceOf[UrlUpdateType]
  val push = "push".asInstanceOf[UrlUpdateType]
  val replaceIn = "replaceIn".asInstanceOf[UrlUpdateType]
  val pushIn = "pushIn".asInstanceOf[UrlUpdateType]
}

trait Param extends js.Object {
// encode
// decode
}

@js.native
trait QueryParamContextValue extends js.Object {
  val history: js.Object = js.native
  val location: dom.Location = js.native
}

@js.native
trait module_Params extends js.Object {
  val NumberParam: Param = js.native
  val StringParam: Param = js.native
  val ObjectParam: Param = js.native
  val ArrayParam: Param = js.native
  val JsonParam: Param = js.native
  val DateParam: Param = js.native
  val BooleanParam: Param = js.native
  val NumericObjectParam: Param = js.native
  val DelimitedArrayParam: Param = js.native
  val DelimitedNumericArrayParam: Param = js.native
}

@js.native
private[use_query_params] trait module_hooks extends js.Object {

  /** You need to match the param to the scala type independently :-(. */
  def useQueryParam[T](name: String, encoder: Param): js.Tuple2[js.UndefOr[T], js.Function2[T, js.UndefOr[UrlUpdateType], Unit]] =
    js.native

  /** 2nd param in callback is UrlUpdateType e.g. "push" */
  def useQueryParams(name: String, encoders: QueryConfig): js.Tuple2[String, js.Function2[js.Dictionary[js.Any], UrlUpdateType, Unit]] = js.native
}

@js.native
@JSImport("use-query-params", JSImport.Namespace)
object module extends module_Params with module_hooks {
  val QueryParamContext: ReactContext[QueryParamContextValue] = js.native
}

object QueryParamProvider {
  @js.native
  @JSImport("use-query-params", "QueryParamProvider")
  object JS extends ReactJSComponent

  trait Props extends js.Object {
    var ReactRouterRoute: js.UndefOr[ReactJSComponent] = js.undefined
    var location: js.UndefOr[dom.Location] = js.undefined
    var reachHistory: js.UndefOr[js.Object] = js.undefined
    var history: js.UndefOr[js.Object] = js.undefined
  }

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  def apply(router: ReactJSComponent, children: ReactNode) =
    createElementN(JS, new Props { ReactRouterRoute = router })(children)
    
}

// object QueryParams {
//   @js.native
//   @JSImport("use-query-params", "QueryParams")
//   object JS extends ReactJSComponent
//
//   trait Props extends js.Object {
//     val config: js.UndefOr[QueryConfig] = js.undefined
//   }
//
//   def apply(props: Props)(children: js.Function1[???, ReactNode]) =
//     createElementN(JS, props)(children: _*)
//
// }

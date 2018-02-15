// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package examples

import scala.scalajs.js
import js.|
import js.annotation._
import js.JSConverters._
import js.Dynamic.{literal => lit}

import ttg.react._
import ttg.react.implicits._
import ttg.react.redux
import elements._

trait LabelAndChildProps extends js.Object {
  var label: js.UndefOr[String] = js.undefined
  var className: js.UndefOr[String] = js.undefined
}

@js.native
@JSImport("JSExamples/LabelAndChild", JSImport.Namespace)
object LabelAndChildNS extends js.Object {
  val LabelAndChild: ReactJsComponent = js.native
}

object JSAppImports {
  def LabelAndChild(props: LabelAndChildProps)(children: ReactNode*) =
    wrapJsForScala(LabelAndChildNS.LabelAndChild, props, children: _*)
}
@js.native
@JSImport("JSExamples/store", JSImport.Namespace)
object StoreNS extends js.Object {
  val store: redux.Store = js.native
}

@js.native
@JSImport("JSExamples/actions", JSImport.Namespace)
object ActionsNS extends js.Object {
  val ViewActions: js.Dynamic = js.native
  val AddressManagerActions: js.Dynamic = js.native
  val Actions: js.Dynamic = js.native
}
@js.native
@JSImport("react-markdown", JSImport.Default)
object ReactMarkdown extends ReactJsComponent

trait ReactMarkdownProps extends js.Object {
  var source: js.UndefOr[String] = js.undefined
  var className: js.UndefOr[String] = js.undefined
}

object ReactMarkdownC {
  def make(props: ReactMarkdownProps = noProps()) = wrapJsForScala(ReactMarkdown, props)
}

class PrettyJsonOptions(
    val noColor: js.UndefOr[Boolean] = js.undefined
) extends js.Object

@js.native
@JSImport("prettyjson", JSImport.Namespace)
object PrettyJson extends js.Object {
  def render(data: js.Object | js.Dynamic, options: js.UndefOr[PrettyJsonOptions] = js.undefined): String =
    js.native
}

@js.native
@JSImport("Examples/README.md", JSImport.Default)
object ReadmeText extends js.Object

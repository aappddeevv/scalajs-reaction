// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.annotation._
import js.|
import js._

import org.scalajs.dom

@js.native
@JSImport("source-map-support", JSImport.Namespace)
object SourceMapSupport extends js.Object {
  def install(): Unit = js.native
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

// https://github.com/chandu0101/sri/blob/a5fb8db2cc666299ecc863d6421994cce5d304e6/core/src/main/scala/sri/core/React.scala
@js.native
private[react] trait JSReact extends js.Object {

  /** Can take a wide variety of types for tpe: string | sfc | class (extending React.Component) */
  def createElement[P](el: js.Any | String, props: UndefOr[P], children: ReactNode*): ReactDOMElement = js.native
  def cloneElement(el: ReactElement, props: js.Dynamic): ReactDOMElement = js.native
  // symbol or number depending on browser/environment support for symbols
  val Fragment: js.Any = js.native
}

@js.native
@JSImport("react", JSImport.Namespace)
private[react] object JSReact extends JSReact

object React {
  def createElement(
      tag: String,
      props: js.UndefOr[js.Object],
      children: Seq[ReactNode]
  ): ReactDOMElement = JSReact.createElement(tag, props, children: _*)

  def createElement(
      tag: String,
      children: Seq[ReactNode]
  ): ReactDOMElement = JSReact.createElement(tag, js.undefined, children: _*)

  def createElement[P](
      c: ReactClass,
      props: P,
      children: Seq[ReactNode]
  ): ReactDOMElement = JSReact.createElement(c, props, children: _*)

  def createElement(
      c: ReactClass
  ): ReactDOMElement = JSReact.createElement(c, js.undefined)

  /**
    * Create a react fragment. Fragments are created as an "element" with a specific
    * tag (symbol or number) vs say, the string "div".
    */
  def createFragment(key: Option[String], children: ReactNode*): ReactDOMElement = {
    val props = js.Dictionary.empty[js.Any]
    key.foreach(props("key") = _)
    JSReact.createElement(JSReact.Fragment, props, children: _*)
  }
}

/**
  * We use create-react-class under the hood to create all classes
  */
@js.native
@JSImport("create-react-class", JSImport.Default)
object reactCreateClass extends js.Object {
  def apply(props: js.Object): ReactClass = js.native
}

@js.native
trait JSReactDOM extends js.Object {
  def render(node: ReactNode, target: dom.Element): Unit = js.native
  def createPortal(node: ReactNode, target: dom.Element): ReactElement = js.native
  def unmountComponentAtNode(el: dom.Element): Unit = js.native
  //def findDOMNode(??? .reactRef): dom.element = js.native
}

@js.native
@JSImport("react-dom", JSImport.Namespace)
object JSReactDOM extends JSReactDOM

object reactdom {

  /** Render into the DOM given an element id. */
  def renderToElementWithId(el: ReactNode, id: String) = {
    val target = Option(dom.document.getElementById(id))
    target.fold(throw new Exception(s"renderToElementWithId: No element with id $id found in the HTML."))(htmlel => JSReactDOM.render(el, htmlel))
  }
}
@js.native
@JSImport("prop-types", JSImport.Namespace)
object PropTypes extends ReactPropTypes {}

@js.native
trait Requireable[T <: js.Any] extends js.Object {
  def isRequired(obj: T, key: String, componentName: String, rest: js.Any*): js.Any = js.native
}

@js.native
trait ReactPropTypes extends js.Object {
  val `any`: Requireable[js.Any] = js.native
  val array: Requireable[js.Any] = js.native
  val bool: Requireable[js.Any] = js.native
  val func: Requireable[js.Any] = js.native
  val number: Requireable[js.Any] = js.native
  val `object`: Requireable[js.Any] = js.native
  val string: Requireable[js.Any] = js.native
  val node: Requireable[js.Any] = js.native
  val element: Requireable[js.Any] = js.native
  def instanceOf(expectedClass: js.Object): Requireable[js.Any] = js.native
  def oneOf(types: js.Array[js.Any]): Requireable[js.Any] = js.native
  def oneOfType(types: js.Array[Requireable[js.Any]]): Requireable[js.Any] = js.native
  def arrayOf(`type`: Requireable[js.Any]): Requireable[js.Any] = js.native
  def objectOf(`type`: Requireable[js.Any]): Requireable[js.Any] = js.native
  def shape(`type`: Requireable[js.Any]): Requireable[js.Any] = js.native
}

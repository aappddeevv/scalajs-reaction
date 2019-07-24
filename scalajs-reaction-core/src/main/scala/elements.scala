// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

// based on commit: reason-react master branch

import collection.mutable
import scala.scalajs.js
import js.|
import js.annotation._
import js.JSConverters._
import js.Dynamic.{literal => lit}
import org.scalajs.dom
import dom.html

object elements {

  /** Create a DOM element, which is always a string, lowercase. */
  def createDOMElement(
      n: String,
      props: js.Object | js.Dynamic,
      children: ReactElement*): ReactDOMElement = {
    React.createDOMElement(n, props.asInstanceOf[js.Object])(children: _*)
  }

  /** Clone a ReactElement and add new props. You should not use this if you can avoid it. */
  def cloneElement(element: ReactElement, props: js.Object): ReactElement =
    ReactJS.cloneElement(element, props.asInstanceOf[js.Dynamic])

  /** Create a React.fragment element. */
  object Fragment {
    def apply(key: Option[String] = None)(children: ReactNode*) =
      React.createFragment(key, children: _*)

    /** Preferred creation function. */
    def apply(children: ReactNode*) =
      React.createFragment(None, children: _*)
  }

  /** Strict element. Wraps your root component typically. */
  object StrictMode {
    /** Wrap some children with a Strict component. */
    def apply(children: ReactNode*): ReactNode =
      ReactJS.createElement(ReactJS.StrictMode, null, children:_*)
  }

  /** Catch a thrown js.Promise from the child. Show fallback until the promise is
   * resolved.
   */
  object Suspense {
    def apply(fallback: ReactNode/* | Null = null*/, children: ReactNode) =
      ReactJS.createElement(
        ReactJS.Suspense,
        lit("fallback" -> fallback.asInstanceOf[js.Any]),
        children
      )
  }

  /**
    * Given a js.Object (or subclass), find a "children" property and assume its
    * an array of ReactNodes. If not found, return an empty js array. This
    * function is used for interop where your scala "make" method takes children
    * as a separate parameter but your props (event JS friendly props) does not
    * contain the children property--it's there if there are children on the js
    * side. This is not needed in scalajs-react because the children are passed
    * and managed explicitly.
    *
    * @todo Seems like this is an expensive call. Can we do better?
    */
  @inline def extractChildren(item: js.UndefOr[js.Object]): js.Array[ReactNode] =
    if (item == null) js.Array() // need this since could be a "defined" null
    else
      item.toOption
        .flatMap(_.asInstanceOf[js.Dictionary[js.Array[ReactNode]]].get("children"))
        .getOrElse[js.Array[ReactNode]](js.Array())
}

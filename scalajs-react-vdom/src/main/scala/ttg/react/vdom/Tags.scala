// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

import scalajs.js
import js.|
import org.scalajs.dom
import js.JSConverters._
import implicits._

/**
  * Create a tag from a list of attributes. This is a "factory" for tags.
  */
class Tag(name: String, tagAttrs: List[Attrs] = Nil) {
  def apply(attrs: Attrs*)(children: ReactNode*): ReactDOMElement =
    React.createElement(name, Attrs.concat(tagAttrs ++ attrs).toJs)(children: _*)
}

/**
  * Create a tag that takes type non-native JS traits. This is a "factory" for
  * tags.
  */
class TagT[P <: js.Object](name: String, tagAttrs: P = noProps[P]()) { self =>

  /** Must have properties, maybe children. */
  def apply(attrs: P)(children: ReactNode*): ReactDOMElement =
    React.createElement(name, mergeJSObjects(tagAttrs.asDyn, attrs.asDyn).asJsObj)(children: _*)

  /** Only children. */
  def apply(children: ReactNode*): ReactDOMElement =
    React.createElement(name, tagAttrs)(children: _*)

  /** Merge attributes of type P or dynamic into this tag. By adding dynamic
    * literal, you are not type safe. Escape hatch!
    */
  def merge(objs: P | js.Dynamic*) = new TagT[P](name, react.merge(objs: _*))

}

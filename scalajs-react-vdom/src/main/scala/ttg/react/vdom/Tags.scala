// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

import scalajs.js
import org.scalajs.dom
import implicits._

/**
 * Create a tag from a list of attributes.
 */
class Tag(name: String, tagAttrs: List[Attrs] = Nil) {
  def apply(attrs: Attrs*)(children: ReactNode*): ReactDOMElement =
    React.createElement(name, Attrs.concat(tagAttrs ++ attrs).toJs)(children: _*)
}

/** 
 * Create a tag that takes type non-native JS traits.
 */
class TagT[P <: js.Object](name: String, tagAttrs: P = noProps[P]()) {
  /** Must have properties, maybe children. */
  def apply(attrs: P)(children: ReactNode*): ReactDOMElement =
    React.createElement(name, mergeJSObjects(tagAttrs.asDyn, attrs.asDyn).asJsObj)(children: _*)

  /** Only children. */
  def apply(children: ReactNode*): ReactDOMElement =
    React.createElement(name, tagAttrs)(children: _*)
}



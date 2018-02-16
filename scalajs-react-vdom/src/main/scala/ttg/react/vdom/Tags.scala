// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

import scalajs.js

import implicits._

class Tag(name: String, tagAttrs: List[Attrs] = Nil) {
  def apply(attrs: Attrs*)(children: ReactNode*): ReactDOMElement =
    React.createElement(name, Attrs.concat(tagAttrs ++ attrs).toJs)(children: _*)
}

// class TagP(name: String, tagAttrs: js.Object = noProps()) {
//   def apply(attrs: js.Object)(children: ReactNode*): ReactDOMElement =
//     React.createElement(name, mergeJSObjects(tagAttrs.asDyn, attrs.asDyn))(children: _*)
// }

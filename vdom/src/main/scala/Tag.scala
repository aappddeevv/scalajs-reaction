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

package react
package vdom

import scala.scalajs.js
import react.syntax.*
import jshelpers.syntax.*

/**
 * Create a tag from a list of attributes. This is a "factory" for tags as
 * react elements.
 * @deprecated Use `TagT`.
 */
class Tag(name: String, tagAttrs: List[Attrs] = Nil) {
  def apply(attrs: Attrs*)(children: ReactNode*): ReactDOMElement =
    react.createDOMElement(name, Attrs.concat(tagAttrs ++ attrs).toJs)(children: _*)
}

/**
 * Create a tag that takes type non-native JS traits. This is a "factory" for
 * the standard html tags, which are typically lowercase.
 */
class TagT[P <: js.Object](name: String, tagAttrs: P = noProps[P]()) { self =>

  /** Properties and and maybe children. */
  def apply(attrs: P)(children: ReactNode*): ReactDOMElement =
    react.createDOMElement(name, mergeJSObjects(tagAttrs.asDyn, attrs.asDyn).asJsObj)(children: _*)

  /** Create an element by explicitly indicating the props. */
  def withProps(attrs: P)(children: ReactNode*): ReactDOMElement =
    react.createDOMElement(name, mergeJSObjects(tagAttrs.asDyn, attrs.asDyn).asJsObj)(children: _*)

  /** Only children, no props. */
  def apply(children: ReactNode*): ReactDOMElement =
    react.createDOMElement(name, tagAttrs)(children: _*)

  /** Merge attributes of type P or dynamic into this tag. By adding dynamic
   * literal, you are not type safe. Escape hatch! This is more convenient then
   * calling `react.merge` or `react.mergeJSObjects` on the actual properties
   * themselves.
   */
  def merge(objs: P | js.Dynamic*) = new TagT[P](name, react.merge(objs: _*))

}

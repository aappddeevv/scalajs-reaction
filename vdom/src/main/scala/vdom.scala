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

// This entire package was copied from: https://github.com/eldis/scalajs-react and that Copyright applies.

package react

import scalajs.js

/**
 * Simple vdom. There are two styles, one with list of attributes like many
 * other scalajs react bindings.  The preferred approach is using typed traits
 * (subclasses of js.Objects) so that properties presence/absence can be
 * checked.
 */
package object vdom extends Events with HTMLTagsX {

  /** Create tag. */
  @inline def tag(name: String): Tag = new Tag(name)

  /** Create a tag that takes a typed js.Object. */
  @inline def tagt[P <: js.Object](name: String): TagT[P] = new TagT[P](name)
}

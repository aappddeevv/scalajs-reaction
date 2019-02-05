// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// This entire package was copied from: https://github.com/eldis/scalajs-react and that Copyright applies.

package ttg.react

import scalajs.js
import js.|
import org.scalajs.dom

/**
  * Simple vdom. There are two styles, one with list of attributes like many
  * other scalajs react bindings.  The preferred approach is using typed
  * js.Objects so that properties presence/absence can be checked.
  */
package object vdom extends vdom.Events {

  /** Create tag that takes a list of attributes. */
  @inline def tag(name: String): Tag = new Tag(name)

  /** Create a tag that takes a typed js.Object. */
  @inline def tagt[P <: js.Object](name: String): TagT[P] = new TagT[P](name)
}

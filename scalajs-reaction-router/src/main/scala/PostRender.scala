// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package router
package browser

import org.scalajs.dom

object PostRenderer {
  val scrollToTop: PathParts => Unit = _ => dom.window.scrollTo(0,0)
  val setTitle: String => PathParts => Unit = t => _ => dom.document.title = t
  val addPrefix: String => String => String = prefix => path => prefix + path
}

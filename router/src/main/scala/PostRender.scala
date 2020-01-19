
package router
package browser

import org.scalajs.dom

object PostRenderer {
  val scrollToTop: PathParts => Unit = _ => dom.window.scrollTo(0,0)
  val setTitle: String => PathParts => Unit = t => _ => dom.document.title = t
  val addPrefix: String => String => String = prefix => path => prefix + path
}

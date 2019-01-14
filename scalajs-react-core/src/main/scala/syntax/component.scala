// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._

/** Given a component, provide some syntax for easily converting it into an
 * element.  This is equivalent to using JSX syntax.
 */
final case class ComponentOps(c: Component) {
  /** Create an element with no key or ref. */
  @inline def toEl: ReactElement = elements.element(c)

  /** Create an element with an optional key and an optional ref. */
  @inline def toEl(key: Option[String] = None, ref: Option[Ref[js.Any]] = None) =
    elements.element(c, key, ref)
}

trait ComponentSyntax {
  implicit def componentOpsSyntax(c: Component) =
    ComponentOps(c)
}

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
  // /** Create an element with no key or ref. */
  // def toEl: ReactElement = elements.element(c)

  // /** Create an element with an optional key and an optional ref. */
  // def toEl(key: Option[String] = None, ref: Option[Ref[js.Any]] = None): ReactElement =
  //   elements.element(c, key, ref)

  // /** Create an element with an optional key and an optional ref. */
  // def toElKey(key: String): ReactElement =
  //   elements.element(c, Option(key), None)
}

trait ComponentSyntax {
  implicit def componentOpsSyntax(c: Component) = ComponentOps(c)

  /** Memoize SFC1[P]. */
  implicit class MemoSFC[P <: js.Object](val sfc: SFC1[P]) {
    def memo = React.memo(sfc)
  }

  implicit def sfc0ToEl(sfc: SFC0): ReactElement =
    ReactJS.createElement(sfc.run, null)

  /** Convert a SFC and its argument, expressed as a tuple, to an element. */
  implicit def sfc1TupleOpsSyntax[P <: js.Object](f: (SFC1[P],P)): ReactElement =
    ReactJS.createElement(f._1.run, f._2.asInstanceOf[js.Any])

  /** Evil! Auto type conversion... */
  implicit def thunkToSFC(f: () => ReactNode): SFC0 = new SFC0(f)
}

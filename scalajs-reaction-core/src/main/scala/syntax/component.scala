// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._

trait ComponentSyntax {

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

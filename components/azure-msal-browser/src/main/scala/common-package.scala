package azure
package msal

import scala.scalajs.js
import js.annotation._
import js.|

package object common {

  type LoggerCallback        = js.Function3[Int, String, Boolean, Unit]
  type StringDict            = js.Dictionary[String]

  @js.native
  @JSImport("@azure/msal-common/error/AuthError", "AuthErrorMessage")
  val AuthErrorMessage: AuthErrorMessageValue = js.native
}

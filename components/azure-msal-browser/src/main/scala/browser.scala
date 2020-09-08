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

package azure
package msal
package browser

import scala.scalajs.js
import js.Dynamic.literal
import js.annotation._
import js.|

import msal.common._

// msal-browser/error
@js.native
@JSImport("@azure/msal-browser", "BrowserAuthError")
class BrowserAuthError(code: String, message: js.UndefOr[String] = js.undefined)
    extends AuthError(code, message)

// msal-browser/error
@js.native
@JSImport("@azure/msal-browser", "BrowserAuthError")
object BrowserAuthError extends js.Object {
  // static creators...
}


// msal-browser/error
@js.native
@JSImport("@azure/msal-browser", "BrowserConfigurationAuthError")
class BrowserConfigurationAuthError(code: String, message: js.UndefOr[String] = js.undefined)
    extends AuthError(code, message)

// msal-browser/error
@js.native
@JSImport("@azure/msal-browser", "BrowserConfigurationAuthError")
object BrowserConfigurationAuthError extends js.Object {
  // static creators...
}

// msal-browser/request
trait RedirectRequest extends AuthorizationUrlRequest {
  var redirectStartPage: js.UndefOr[String] = js.undefined
}

// msal-browser/request
trait SilentRequest extends SilentFlowRequest {
  var redirectUri: js.UndefOr[String] = js.undefined
}

// msal-browser/app
@js.native
trait IPublicClientApplication extends js.Object {
  def acquireTokenPopup(request: PopupRequest): js.Promise[AuthenticationResult] = js.native
  def acquireTokenRedirect(request: RedirectRequest): js.Promise[Unit] = js.native
  def acquireTokenSilent(request: SilentRequest): js.Promise[AuthenticationResult] = js.native
  def getAccountByUsername(userName: String): AccountInfo|Null = js.native
  def getAllAccounts(): js.Array[AccountInfo] | Null = js.native
  def handleRedirectPromise(): js.Promise[AuthenticationResult|Null] = js.native
  def loginPopup(request: PopupRequest): js.Promise[AuthenticationResult] = js.native
  def loginRedirect(request: RedirectRequest): js.Promise[Unit] = js.native
  def logout(request: js.UndefOr[EndSessionRequest] = js.undefined): js.Promise[Unit] = js.native
  def ssoSilent(request: AuthorizationUrlRequest): js.Promise[AuthenticationResult] = js.native
}

trait BrowserAuthOptionsBase extends js.Object {
  var authority: js.UndefOr[String] = js.undefined
  var knownAuthorities: js.UndefOr[js.Array[String]] = js.undefined
  var cloudDiscoveryMetadata: js.UndefOr[String] = js.undefined
  var redirectUri: js.UndefOr[String] = js.undefined
  var postLogoutRedirectUri: js.UndefOr[String] = js.undefined
  var navigateToLoginRequestUir: js.UndefOr[Boolean] = js.undefined
}

trait BrowserAuthOptionsInit extends BrowserAuthOptionsBase {
  var clientId: js.UndefOr[String] = js.undefined
}

trait BrowserAuthOptions extends BrowserAuthOptionsBase {
  val clientId: String
}

trait BrowserSystemOptions extends SystemOptions {
  var loggerOptions: js.UndefOr[LoggerOptions]                 = js.undefined
  var networkClient: js.UndefOr[INetworkModule] = js.undefined
  var windowHashTimeout: js.UndefOr[Int]          = js.undefined
  var iframeHashTimeout: js.UndefOr[Int]          = js.undefined
  var loadFrameTimeout: js.UndefOr[Int]          = js.undefined
  // var tokenRenewalOffsetSeconds: js.UndefOr[Int] = js.undefined
  // var telemetry: js.UndefOr[TelemetryOptions]    = js.undefined
}

@js.native
sealed trait CacheLocation extends js.Any
object CacheLocation {
  val sessionStorage = "sessionStorage".asInstanceOf[CacheLocation]
  val localStorage   = "localStorage".asInstanceOf[CacheLocation]
}

trait CacheOptions extends js.Object {
  var cacheLocation: js.UndefOr[CacheLocation]    = js.undefined
  var storeAuthStateInCookie: js.UndefOr[Boolean] = js.undefined
}

trait ConfigurationBase extends js.Object {
  var cache: js.UndefOr[CacheOptions]         = js.undefined
  var system: js.UndefOr[BrowserSystemOptions]       = js.undefined
}

trait ConfigurationInit extends ConfigurationBase {
  var auth: js.UndefOr[BrowserAuthOptions] = js.undefined
}

object ConfigurationInit {
  private implicit class RichInit private[ConfigurationInit] (private val c: ConfigurationInit)
      extends AnyVal {
    def withRequired(auth: BrowserAuthOptions) =
      js.Object.assign(literal(), c, literal("auth" -> auth)).asInstanceOf[Configuration]
    def hasRequired = c.asInstanceOf[Configuration]
  }
}

trait Configuration extends ConfigurationBase {
  val auth: BrowserAuthOptions
}

@js.native
@JSImport("@azure/msal-browser/crypto", "CryptoOps")
class CryptoUtils() extends js.Object {
  def createNewGuid(): String             = js.native
  def base64Encode(input: String): String = js.native
  def base64Decode(inut: String): String  = js.native
  def generatePkceCodes(): js.Promise[js.Any] = js.native
}

@js.native
@JSImport("@azure/msal-browser/crypto", "GuidGenerator")
class GuidGenerator() extends js.Object {
  def generateGuid(): String             = js.native
  def isGuid(guid: String): Boolean = js.native
}

@js.native
@JSImport("@azure/msal-browser", "PublicClientApplication")
class PublicClientApplication(config: Configuration) extends IPublicClientApplication

// /** Really a class but we make a non-native js trait. */
// @js.native
// trait IdToken extends js.Object {
//   val issue: String
//   val objectId: String
//   val subject: String
//   val tenantId: String
//   val version: String
//   val preferredName: String
//   val name: String
//   val homeObjectId: String
//   val nonce: String
//   val expiration: String
//   val rawIdToken: String
//   val claims: StringDict
//   val sid: String
//   val cloudInstance: String
// }

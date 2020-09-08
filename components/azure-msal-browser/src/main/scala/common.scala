package azure
package msal
package common

import scala.scalajs.js
import js.annotation._
import js.|
import js.Dynamic.literal

// msal-common/account
@js.native
trait AccountInfo extends js.Object {
  val homeAccountId: String = js.native
  val environment: String = js.native
  val tenantId: String = js.native
  val username: String = js.native
}

// msal-common/request
trait BaseAuthRequest extends js.Object {
  // mentioned in the docs that this is required, can have an empty array, but ts says optional!
  var scopes: js.UndefOr[js.Array[String]] = js.undefined
  var authority: js.UndefOr[String] = js.undefined
  var correlationId: js.UndefOr[String] = js.undefined
}

// msal-common/utils
@js.native
sealed trait ResponseMode extends js.Any
object ResponseMode {
  val QUERY = "query".asInstanceOf[ResponseMode]
  val FRAGMENT = "fragment".asInstanceOf[ResponseMode]
  val FORM_POST = "form_post".asInstanceOf[ResponseMode]
}

// msal-common/request
trait AuthorizationUrlRequest extends BaseAuthRequest {
  var redirectUri: js.UndefOr[String] = js.undefined
  var extraScopesToConsent: js.UndefOr[js.Array[String]] = js.undefined
  var responseMode: js.UndefOr[ResponseMode] = js.undefined
  var codeChallenge: js.UndefOr[String] = js.undefined
  var codeChallengeMethod: js.UndefOr[String] = js.undefined
  var state: js.UndefOr[String] = js.undefined
  var prompt: js.UndefOr[String] = js.undefined
  var loginHint: js.UndefOr[String] = js.undefined
  var domainHint: js.UndefOr[String] = js.undefined
  var sid: js.UndefOr[String] = js.undefined
  var queryQueryParameters: js.UndefOr[StringDict] = js.undefined
  var claims: js.UndefOr[String] = js.undefined
  var nonce: js.UndefOr[String] = js.undefined
}

// msal-common/request
trait EndSessionRequest extends js.Object {
  // I think this is required
  var account: js.UndefOr[AccountInfo] = js.undefined
  var postlogoutRedirectUri: js.UndefOr[String] = js.undefined  
  var authority: js.UndefOr[String] = js.undefined
  var correlationId: js.UndefOr[String] = js.undefined
}

// msal-common/requset
trait SilentFlowRequest extends BaseAuthRequest {
  val account: AccountInfo
  var forceRefresh: js.UndefOr[Boolean] = js.undefined
}

// msal-common/response
@js.native
@JSImport("@azure/msal-common", "AuthenticationResult")
class AuthenticationResult extends js.Object {
  var uniqueId: String = js.native
  var tenantId: String = js.native
  var scopes: js.Array[String] = js.native
  var account: AccountInfo = js.native
  var idToken: String = js.native
  var idTokenClaims: StringDict = js.native
  var accessToken: String = js.native
  var fromCache: Boolean = js.native
  var expiresOn: js.Date = js.native
  var extExpiresOn: js.UndefOr[js.Date] = js.native
  var state: js.UndefOr[String] = js.native
  var familyId: js.UndefOr[String] = js.native
}

@js.native
trait CodeDesc extends js.Object {
  val code: String
  val desc: String
}

@js.native
trait AuthErrorMessageValue extends js.Object {
  val unexpectedError: CodeDesc =js.native
}

// msal-common/error
@js.native
@JSImport("@azure/msal-common", "ClientAuthError")
class ClientAuthError(code: String, message: js.UndefOr[String] = js.undefined)
    extends AuthError(code, message)

// msal-common/error
@js.native
@JSImport("@azure/msal-common", "ClientAuthError")
object ClientAuthError extends js.Object {
  // add static create functions...
}

// msal-common/error
@js.native
@JSImport("@azure/msal-common", "InteractionRequiredAuthError")
class InteractionRequiredAuthError(code: String, message: js.UndefOr[String] = js.undefined)
    extends ServerError(code, message)

// msal-common/error
@js.native
@JSImport("@azure/msal-common", "InteractionRequiredAuthError")
object InteractionRequiredAuthError extends js.Object {
  // static creators...
  // checkers
  def isInteractionRequiredError(errorCode: String, errorString: String, subError: js.UndefOr[String] = js.undefined): Boolean = js.native
}

// msal-common/error
@js.native
@JSImport("@azure/msal-common", "AuthError")
class AuthError(val errorCode: String, val errorMessage: js.UndefOr[String] = js.undefined)
    extends js.Error(errorMessage.getOrElse("<no message>"))

// msal-common/error
@js.native
@JSImport("@azure/msal-common", "AuthError")
object AuthError extends js.Object {
  def createUnexpectedError(desc: String): AuthError     = js.native
  def createNoWindowObjectError(desc: String): AuthError = js.native
}

// msal-common/error
@js.native
@JSImport("@azure/msal-common", "ClientConfigurationError")
class ClientConfigurationError(code: String, message: js.UndefOr[String] = js.undefined)
    extends ClientAuthError(code, message)

// msal-common/error
@js.native
@JSImport("@azure/msal-common", "ClientConfigurationError")
object ClientConfigurationError extends js.Object {
  // static creators...
}

// msal-common/error
@js.native
@JSImport("@azure/msal-common", "ServerError")
class ServerError(code: String, message: js.UndefOr[String] = js.undefined)
    extends AuthError(code, message)

// msal-common/error
@js.native
@JSImport("@azure/msal-common", "ServerError")
object ServerError extends js.Object {
  // static creators...
}

// msal-common/network
@js.native
trait NetworkResponse[T] extends js.Object {
  val headers: js.UndefOr[StringDict] = js.native
  val body: T = js.native
  val status: Int = js.native
}

// msal-common/network
trait INetworkRequestOptions extends js.Object {
  var headers: js.UndefOr[StringDict] = js.undefined
  var body: js.UndefOr[String] = js.undefined
}

// msal-common/network
// make these properties not methods
trait INetworkModule extends js.Object {
  def sendGetRequestAsync[T](url: String, options: js.UndefOr[INetworkRequestOptions] = js.undefined): js.Promise[NetworkResponse[T]]
  def sendPostRequestAsync[T](url: String, options: js.UndefOr[INetworkRequestOptions] = js.undefined): js.Promise[NetworkResponse[T]]
}

// msal-common/config
trait LoggerOptions extends js.Object {
  var loggerCallback: js.UndefOr[LoggerCallback] = js.undefined
  var logLevel: js.UndefOr[LogLevel]         = js.undefined
  var piiLoggingEnabled: js.UndefOr[Boolean] = js.undefined
}

// msal-common/config
trait SystemOptions extends js.Object {
  var tokenRenewalOffsetSeconds: js.UndefOr[Int] = js.undefined
}

// msal-common/logger
@js.native
sealed trait LogLevel extends js.Any
object LogLevel {
  val Error   = 0.asInstanceOf[LogLevel]
  val Warning = 1.asInstanceOf[LogLevel]
  val Info    = 2.asInstanceOf[LogLevel]
  val Verbose = 3.asInstanceOf[LogLevel]
}

// msal-common/logger
@js.native
@JSImport("@azure/msal-common", "Logger")
class Logger(cb: LoggerCallback, options: js.UndefOr[LoggerOptions] = js.undefined)
    extends js.Object {
  def isPiiLoggingEnabled(): Boolean = js.native
}


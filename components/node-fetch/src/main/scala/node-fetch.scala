package node_fetch

import scala.scalajs.js
import scala.scalajs.js.annotation.*
import scala.scalajs.js.typedarray.{ArrayBuffer, Uint8Array}

@js.native
@JSImport("node-fetch", JSImport.Default)
def fetch(
      info: RequestInfo,
      init: js.UndefOr[RequestInit] = js.undefined
  ): js.Promise[Response] = js.native

@js.native
@JSImport("node-fetch", "Request")
class Request(input: RequestInfo, init: js.UndefOr[RequestInit] = js.undefined) extends js.Object {

  // agent
  def method: HttpMethod = js.native

  @JSName("type")
  def `mediaType`: RequestType = js.native

  def url: String = js.native //should be USVString
  def headers: Headers = js.native
  def destination: RequestDestination = js.native
  def referrer: String = js.native
  //def referrerPolicy: ReferrerPolicy = js.native
  def mode: RequestMode = js.native
  def credentials: RequestCredentials = js.native
  def cache: RequestCache = js.native
  def redirect: RequestRedirect = js.native
  def integrity: String = js.native //should be DOMString
  def keepalive: Boolean = js.native
  //def signal: AbortSignal = js.native
}

trait RequestInit extends js.Object {
  var method: js.UndefOr[HttpMethod] = js.undefined
  var headers: js.UndefOr[HeadersInit] = js.undefined
  var body: js.UndefOr[BodyInit] = js.undefined
  var referrer: js.UndefOr[String] = js.undefined
  var referrerPolicy: js.UndefOr[ReferrerPolicy] = js.undefined
  var mode: js.UndefOr[RequestMode] = js.undefined
  var credentials: js.UndefOr[RequestCredentials] = js.undefined
  var cache: js.UndefOr[RequestCache] = js.undefined
  var redirect: js.UndefOr[RequestRedirect] = js.undefined
  var integrity: js.UndefOr[String] = js.undefined
  var keepalive: js.UndefOr[Boolean] = js.undefined
  //var signal: js.UndefOr[AbortSignal] = js.undefined
}

@js.native
@JSImport("node-fetch", "Response")
class Response(content: js.UndefOr[BodyInit]=js.undefined, init: js.UndefOr[ResponseInit] = js.undefined) extends Body {
  def `type`: ResponseType = js.native
  def url: String = js.native
  def ok: Boolean = js.native
  val status: Int = js.native //actually returns unsigned short
  def statusText: ByteString = js.native //actually returns ByteString
  val headers: Headers = js.native
  //val body: ReadableStream[Uint8Array] = js.native
  //override def clone(): Response = js.native
}

trait ResponseInit extends js.Object:
  var status: Int
  var statusText: ByteString
  var headers: HeadersInit

object ResponseInit:
  def apply(
      _status: Int = 200,
      _statusText: ByteString = "OK",
      _headers: HeadersInit = js.Dictionary[String]()
  ): ResponseInit =
    new ResponseInit:
      var status = _status
      var statusText = _statusText
      var headers = _headers

@js.native
@JSImport("node-fetch", "Body")
class Body() extends js.Object:
  def bodyUsed: Boolean = js.native
  def arrayBuffer(): js.Promise[ArrayBuffer] = js.native
  //def blob(): js.Promise[Blob] = js.native
  //def formData(): js.Promise[FormData] = js.native
  def json(): js.Promise[js.Any] = js.native
  def text(): js.Promise[String] = js.native
  def textConverted(): js.Promise[String] = js.native
  def size(): Int = js.native
  val timeout: Double = js.native

@js.native
@JSImport("node-fetch", "Headers")
class Headers(map: HeadersInit = js.Array[js.Array[String]]()) extends js.Iterable[js.Array[ByteString]] {
  @JSName(js.Symbol.iterator)
  def jsIterator(): js.Iterator[js.Array[ByteString]] = js.native

  def append(name: ByteString, value: ByteString): Unit = js.native
  def set(name: ByteString, value: ByteString): Unit = js.native
  def delete(name: ByteString): Unit = js.native
  def get(name: ByteString): js.UndefOr[ByteString] = js.native
  def getAll(name: ByteString): Sequence[ByteString] = js.native
  def has(name: ByteString): Boolean = js.native
}

opaque type ReferrerPolicy = String
object ReferrerPolicy:
  val empty: ReferrerPolicy = ""
  val `no-referrer`: ReferrerPolicy = "no-referrer"
  val `no-referrer-when-downgrade`: ReferrerPolicy = "no-referrer-when-downgrade"
  val `origin-only`: ReferrerPolicy = "origin-only"
  val `origin-when-cross-origin`: ReferrerPolicy = "origin-when-cross-origin"
  val `unsafe-url`: ReferrerPolicy = "unsafe-url"

@js.native
trait HttpMethod extends js.Any
object HttpMethod:
  val GET = "GET".asInstanceOf[HttpMethod]
  val POST = "POST".asInstanceOf[HttpMethod]
  val PUT = "PUT".asInstanceOf[HttpMethod]
  val PATCH = "PATCH".asInstanceOf[HttpMethod]
  val DELETE = "DELETE".asInstanceOf[HttpMethod]
  val QUERY = "QUERY".asInstanceOf[HttpMethod]
  val HEAD = "HEAD".asInstanceOf[HttpMethod]
  val OPTIONS = "OPTIONS".asInstanceOf[HttpMethod]

// RequestContext
@js.native
sealed trait RequestType extends js.Any
object RequestType:
  val empty = "".asInstanceOf[RequestType]
  val audio = "audio".asInstanceOf[RequestType]
  val font = "font".asInstanceOf[RequestType]
  val image = "image".asInstanceOf[RequestType]
  val script = "script".asInstanceOf[RequestType]
  val style = "style".asInstanceOf[RequestType]
  val track = "track".asInstanceOf[RequestType]
  val video = "video".asInstanceOf[RequestType]

@js.native
sealed trait RequestDestination extends js.Any
object RequestDestination:
  val empty = "".asInstanceOf[RequestDestination]
  val document = "document".asInstanceOf[RequestDestination]
  val sharedworker = "sharedworker".asInstanceOf[RequestDestination]
  val subresource = "subresource".asInstanceOf[RequestDestination]
  val unknown = "unknown".asInstanceOf[RequestDestination]
  val worker = "worker".asInstanceOf[RequestDestination]

@js.native
sealed trait RequestMode extends js.Any
object RequestMode:
  val navigate = "navigate".asInstanceOf[RequestMode]
  val `same-origin` = "same-origin".asInstanceOf[RequestMode]
  val `no-cors` = "no-cors".asInstanceOf[RequestMode]
  val cors = "cors".asInstanceOf[RequestMode]

@js.native
sealed trait RequestCredentials extends js.Any
object RequestCredentials:
  val omit = "omit".asInstanceOf[RequestCredentials]
  val `same-origin` = "same-origin".asInstanceOf[RequestCredentials]
  val include = "include".asInstanceOf[RequestCredentials]

@js.native
sealed trait RequestCache extends js.Any
object RequestCache:
  val default = "default".asInstanceOf[RequestCache]
  val `no-store` = "no-store".asInstanceOf[RequestCache]
  val reload = "reload".asInstanceOf[RequestCache]
  val `no-cache` = "no-cache".asInstanceOf[RequestCache]
  val `force-cache` = "force-cache".asInstanceOf[RequestCache]
  val `only-if-cached` = "only-if-cached".asInstanceOf[RequestCache]

@js.native
sealed trait RequestRedirect extends js.Any
object RequestRedirect:
  val follow = "follow".asInstanceOf[RequestRedirect]
  val error = "error".asInstanceOf[RequestRedirect]
  val manual = "manual".asInstanceOf[RequestRedirect]

opaque type ResponseType = String
object ResponseType:
  val basic: ResponseType = "basic"
  val cors: ResponseType = "cors"
  val default: ResponseType = "default"
  val error: ResponseType = "error"
  val opaque: ResponseType = "opaque"
  val opaqueredirect: ResponseType = "opaqueredirect"

@js.native
@JSImport("node-fetch", "FetchError")
class FetchError extends js.Error:
  val `type`: String = js.native
  val code: js.UndefOr[String] = js.native
  val errno: js.UndefOr[String] = js.native

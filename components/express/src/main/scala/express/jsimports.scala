// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package express

import scala.scalajs.js
import js.|
import js.annotation._

@js.native
trait HasApp extends js.Object {
  val app: App = js.native
}

@js.native
trait Request extends js.Object with HasApp {
  def params[T <: js.Object]: T = js.native
  def asJsonObject[T <: js.Object]: T = js.native
  val baseUrl: String = js.native
  val originalUrl: String = js.native
  val path: String = js.native
  val protocol: String = js.native
  val secure: Boolean = js.native
  def bodyJson[T <: js.Object]: T = js.native
  val cookies: js.Dictionary[js.Object] = js.native
  def query[T <: js.Object]: T = js.native
  val hostname: String = js.native
  val ip: String = js.native
  val fresh: Boolean = js.native
  val stale: Boolean = js.native
  val subdomains: js.Array[String] = js.native
  // based on which body parsers you have provided
  def body[T]: js.UndefOr[T] = js.native

  def is(t: String): Boolean = js.native
  def accepts(): js.Array[String] = js.native
  def accepts(t: String): String | Boolean = js.native
  def accepts(t: js.Array[String]): String | Boolean = js.native
  def get(field: String): String = js.native

}

@js.native
trait Response extends Renderer with HasLocals with HasApp {
  val headersSent: Boolean = js.native
  def status(v: Int): Response = js.native
  def json(v: js.Any): Unit = js.native
  def send(v: String|js.Object|js.Array[js.Any]): Unit = js.native
  def sendStatus(s: Int): Unit = js.native
  def set(k: String, v: String): Unit = js.native
  def get(f: String): String = js.native
  @JSName("append")
  def appendMany(f: String, values: js.Array[String]): Unit = js.native
  def append(f: String, v: String): Unit = js.native  
  def end(): Unit = js.native
  def attachment(a: String): Unit = js.native
  @JSName("attachment")
  def attachments(a: js.Array[String]): Unit = js.native
  def location(path: String): Unit = js.native
  def sendFile(path: String, options: js.Object, cb: js.Function2[Request, Response, js.Function1[_ <: js.Any, Unit]]): Unit = js.native
}

@js.native
trait HandlerBase extends js.Any {
  def apply(request: Request, response: Response, next: Next): js.Any = js.native
}

@js.native
trait Routable extends js.Object with HandlerBase {
  @JSName("use")  
  def useTotal(handler: TotalHandler|HandlerBase): Unit = js.native
  @JSName("use")
  def useOnPathTotal(p: PathParams, handler: TotalHandler|HandlerBase): Unit = js.native
  @JSName("use")  
  def useOnPathWithManyTotal(p: PathParams, handlers: js.Array[TotalHandler|HandlerBase]): Unit = js.native

  def use(handler: Handler|HandlerBase): Unit = js.native
  @JSName("use")
  def useOnPath(p: PathParams, handler: Handler|HandlerBase): Unit = js.native
  @JSName("use")  
  def useOnPathWithMany(p: PathParams, handlers: js.Array[Handler|HandlerBase]): Unit = js.native
}

@js.native
trait Renderer extends js.Object {
  def render(view: String, locals: js.Object | js.Dynamic, callabck: js.Function2[js.Any, String, Unit]): Unit = js.native
  def render(view: String, callback: js.Function2[js.Any, String, Unit]): Unit = js.native
}

@js.native
trait HasLocals extends js.Object {
  def locals[T <: js.Object]: T = js.native
}

@js.native
trait App extends Routable with Renderer with HasLocals {
  def on(event: String, callback: js.Any): Unit = js.native
  val mountpath: js.Array[String]|String = js.native
  def set(key: String, value: scala.Any): Unit = js.native
  def get[T](key: String): T = js.native
  def enable(key: String): Unit = js.native
  def enabled(key: String): Boolean = js.native
  def disable(key: String): Unit = js.native
  def disabled(key: String): Boolean = js.native
  def engine(name: String, callback: js.Any): Unit = js.native
}

trait JSONOptions extends js.Object {
  var inflate: js.UndefOr[Boolean] = js.undefined
  var limit: js.UndefOr[String|Int] = js.undefined
  var reviver: js.UndefOr[js.Function2[String, String, Unit]] = js.undefined
  var strict: js.UndefOr[Boolean] = js.undefined
  var `type`:  js.UndefOr[String|js.Function1[Request, Boolean]] = js.undefined
  var verify: js.UndefOr[js.Function4[Request, Response, js.Any, String, Unit]] = js.undefined
}

trait RouterOptions extends js.Object {
  var caseSensitive: js.UndefOr[Boolean] = js.undefined
  var mergeParam: js.UndefOr[Boolean] = js.undefined
  var strict: js.UndefOr[Boolean] = js.undefined
}

trait URLEncodedOptions extends js.Object {
  var extended: js.UndefOr[Boolean] = js.undefined
  var inflate: js.UndefOr[Boolean] = js.undefined
  var limit: js.UndefOr[String|Int] = js.undefined
  var parameterLimit: js.UndefOr[Int] = js.undefined
  var `type`:  js.UndefOr[String|js.Function1[Request, Boolean]] = js.undefined
  var verify: js.UndefOr[js.Function4[Request, Response, js.Any, String, Unit]] = js.undefined  
}

@js.native
abstract trait Dotfile extends js.Any
object Dotfile {
  val allow = "allow".asInstanceOf[Dotfile]
  val deny = "deny".asInstanceOf[Dotfile]
  val ignore = "ignore".asInstanceOf[Dotfile]
}

trait StaticOptions extends js.Object {
  var dotfiles: js.UndefOr[Dotfile] = js.undefined
  var etag: js.UndefOr[Boolean] = js.undefined
  var extensions: js.UndefOr[Boolean|js.Array[String]] = js.undefined
  var fallthrough: js.UndefOr[Boolean] = js.undefined
  var immutable: js.UndefOr[Boolean] = js.undefined
  var index: js.UndefOr[String|Boolean] = js.undefined
  var lastModifid: js.UndefOr[Boolean] = js.undefined
  var maxAge: js.UndefOr[Int] = js.undefined
  var redirect: js.UndefOr[Boolean] = js.undefined
  var setHeaders: js.UndefOr[js.Function3[Response, String, js.Dynamic, Unit]] = js.undefined
}

@js.native
@JSImport("express", JSImport.Default)
private object ExpressImport extends js.Object {
  def apply(): App = js.native
  def json(options: JSONOptions): HandlerBase = js.native
  def Router(options: RouterOptions): Routable = js.native
  def urlencoded(options: URLEncodedOptions): HandlerBase = js.native
  def static(options: StaticOptions): HandlerBase = js.native
}

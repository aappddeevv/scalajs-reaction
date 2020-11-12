// Copyright (c) 2019 The Trapelo Group LLC - All Rights Reserved
// Unauthorized copying of this file, via any medium is strictly prohibited.
// Proprietary and confidential.

package microsoft
package graph_client

import scala.scalajs.js
import js.annotation._
import js.|

@js.native
trait GraphRequest extends js.Object {
  def header(key: String, value: String): GraphRequest = js.native
  def headers(headers: KeyValuePairObjectStringNumber): GraphRequest = js.native
  def option(key: String, value: Any): GraphRequest = js.native
  def options(options: js.Dictionary[Any]): GraphRequest = js.native
  def middlewareOptions(options: js.Array[MiddlewareOptions]): GraphRequest = js.native
  def version(version: String): GraphRequest = js.native
  def responseType(t: ResponseType): GraphRequest = js.native
  def select(properties: String|js.Array[String]): GraphRequest = js.native
  def expand(properties: String|js.Array[String]): GraphRequest = js.native
  def orderBy(properties: String|js.Array[String]): GraphRequest = js.native
  def filter(properties: String): GraphRequest = js.native
  def search(properties: String): GraphRequest = js.native
  def top(n: Int): GraphRequest = js.native
  def skip(n: Int): GraphRequest = js.native
  def skipToken(properties: String): GraphRequest = js.native
  def count(isCount: Boolean): GraphRequest = js.native
  def query(q: String|KeyValuePairObjectStringNumber): GraphRequest = js.native
  def get[T](): js.Promise[T] = js.native
  def post[T](content: Any): js.Promise[T] = js.native
  def create[T](content: Any): js.Promise[T] = js.native
  def put[T](content: Any): js.Promise[T] = js.native
  def patch[T](content: Any): js.Promise[T] = js.native
  def update[T](content: Any): js.Promise[T] = js.native
  def delete[T](): js.Promise[T] = js.native
  def del[T](): js.Promise[T] = js.native
  def getStream[T](): js.Promise[T] = js.native
  def putStream[T](stream: Any): js.Promise[T] = js.native
}

@js.native
@JSImport("@microsoft/microsoft-graph-client", "Client")
class Client extends js.Object {
  def api(path: String): GraphRequest = js.native
}

@js.native
@JSImport("@microsoft/microsoft-graph-client", "Client")
object Client extends js.Object {
  def init(options: js.Dynamic): Client = js.native
  def initWithMiddleware(options: js.Dynamic): Client = js.native

}

@js.native
sealed trait ResponseType extends js.Any
object ResponseType {
  val ARRAYBUFFER = "arraybuffer".asInstanceOf[ResponseType]
  val BLOB = "blob".asInstanceOf[ResponseType]
  val DOCUMENT = "document".asInstanceOf[ResponseType]
  val JSON = "json".asInstanceOf[ResponseType]
  val RAW = "raw".asInstanceOf[ResponseType]
  val STREAM = "steram".asInstanceOf[ResponseType]
  val TEXT = "teuxt".asInstanceOf[ResponseType]
}

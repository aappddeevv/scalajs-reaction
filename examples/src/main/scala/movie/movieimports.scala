// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package examples
package movie

import scala.scalajs.js
import js.|
import js.annotation._

@js.native
trait Cache extends js.Object {

  def invalidate(): Unit = js.native

  def read[K, V, A](
      resourceType: js.Any,
      key: K,
      miss: js.Function1[A, js.Promise[V]],
      missArg: A): V = js.native

  def preload[K, V, A](
      resourceType: js.Any,
      key: K,
      miss: js.Function1[A, js.Promise[V]],
      missArg: A): Unit = js.native
}

/** Next version will have a read func vs is a read func. */
@js.native
trait Resource[K, V] extends js.Function2[Cache, K, V] {
  def preload(cache: Cache, key: K): Unit = js.native
}

@js.native
//@JSImport("Assets/simple-cache-provider.development", JSImport.Namespace)
@JSImport("simple-cache-provider", JSImport.Namespace)
object SimpleCacheProvider extends js.Object {

  def createCache(invalidator: js.Function0[js.Any | Unit]): Cache = js.native

  /** Your loadResource must take a key to index into the future result. */
  def createResource[K, V, H <: String](
      //def createResource[K, V, H <: (String|Number|Boolean)](
      loadResource: js.Function1[K, js.Thenable[V]],
      hash: js.UndefOr[js.Function1[K, H]]
  ): Resource[K, V] = js.native

  val SimpleCache: ReactContext[Cache] = js.native
}

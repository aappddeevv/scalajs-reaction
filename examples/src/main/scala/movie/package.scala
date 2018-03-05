// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package examples

import scala.scalajs.js
import concurrent._
import js.JSConverters._
import concurrent.ExecutionContext.Implicits.global

package object movie {

  //  export default function withCache(Component) {
  //   return props => (
  //     <SimpleCache.Consumer>
  //       {cache => <Component cache={cache} {...props} />}
  //     </SimpleCache.Consumer>
  //   );
  // }
 
  implicit class RichSimpleCacheProvider(c: SimpleCacheProvider.type) {
    def createResource[K, V](
      loadResource: K => Future[V]): Resource[K, V] =
      c.createResource((k: K) => loadResource(k).toJSPromise, js.undefined)

    def createResource[V](
      loadResource: => Future[V]): Resource[js.UndefOr[Unit], V] =
      c.createResource((k: js.UndefOr[Unit]) => loadResource.toJSPromise, js.undefined)
  }

}


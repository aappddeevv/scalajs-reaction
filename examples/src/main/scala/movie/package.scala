// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples

import scala.scalajs.js
import concurrent._
import js.JSConverters._
import concurrent.ExecutionContext.Implicits.global

package object movie {

  // def createResource[K, V](loadResource: K => Future[V]): Resource[K, V] =
  //   SimpleCacheProvider.createResource((k: K) => loadResource(k).toJSPromise, js.undefined)

  // def cleanQueryString(v: String): String = {
  //   v.trim()
  // }

  // val configKey = "__config__"
}

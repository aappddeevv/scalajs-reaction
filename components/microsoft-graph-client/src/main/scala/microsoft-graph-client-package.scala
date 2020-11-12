// Copyright (c) 2019 The Trapelo Group LLC - All Rights Reserved
// Unauthorized copying of this file, via any medium is strictly prohibited.
// Proprietary and confidential.

package microsoft

import scala.scalajs.js
import js.annotation._
import js.|

package object graph_client {
  type KeyValuePairObjectStringNumber = js.Dictionary[Int|Double|String]
  type MiddlewareOptions = js.Dictionary[Any]|js.Object|js.Dynamic
}

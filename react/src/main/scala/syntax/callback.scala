// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

import scala.scalajs.js
import js._

class CallbackOps[A,B](cb: Callback[A,B]) {
}

object CallbackOps {
}

trait CallbackSyntax {
  implicit def callbackSyntax[A,B](cb: Callback[A,B]) = new CallbackOps(cb)
}

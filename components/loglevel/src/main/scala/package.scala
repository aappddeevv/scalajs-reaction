// Copyright (c) 2019 The Trapelo Group LLC - All Rights Reserved
// Unauthorized copying of this file, via any medium is strictly prohibited.
// Proprietary and confidential.

import scala.scalajs.js

package object loglevel {
  type LoggingMethod = js.Any
  type MethodFactory = js.Function3[String, LogLevel, String, LoggingMethod]

  /** Root logger. */
  val root = module
}

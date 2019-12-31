// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package azure

import scala.scalajs.js
import js.|

package object msal {
  type AuthResponseCallback = js.Function2[AuthError|Null, js.UndefOr[AuthResponse], Unit]
  type TokenReceivedCallback = js.Function1[AuthResponse, Unit]
  type ErrorReceivedCallback = js.Function2[AuthError, String, Unit]
  type LoggerCallback = js.Function3[Int, String, Boolean, Unit]
  type StringDict = js.Dictionary[String]

  type TelemetryEmitter = js.Function1[js.Array[js.Object], Unit]
}

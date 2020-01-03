// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import scala.scalajs.js
import js.|

import apollo_observable._

package object apollo_link {

  import apollo_link._

  // apollo-link-error, returns Unit or Observable[FetchResult]
  type ErrorHandler[T <: js.Any, C <: js.Object, Ext <: js.Object] =
    js.Function1[ErrorResponse,Observable[FetchResult[T,C,Ext]] | Unit]
  // apollo-link
  type NextLink[TVars <: js.Object, T <: js.Any, C <: js.Object, Ext <: js.Object] =
    js.Function1[Operation[TVars], Observable[FetchResult[T, C, Ext]]]
  // apollo-link
  type RequestHandler[TVars <: js.Object, T <: js.Any, C <: js.Object, Ext <: js.Object] =
    js.Function2[Operation[TVars], NextLink[TVars,T,C,Ext], Observable[FetchResult[T, C, Ext]|Null]]
}

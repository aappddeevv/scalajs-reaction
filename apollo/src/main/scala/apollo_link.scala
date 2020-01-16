// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import scala.scalajs.js
import js.|

import apollo_observable._

package object apollo_link {

  import apollo_link._

  // apollo-link-error, returns Unit or Observable[FetchResult]
  type ErrorHandler[T <: js.Any] =
    js.Function1[ErrorResponse, Observable[FetchResult[T]] | Unit]
  // apollo-link
  type NextLink[TVars <: js.Object, T <: js.Any] =
    js.Function1[Operation[TVars], Observable[FetchResult[T]]]
  // apollo-link
  type RequestHandler[TVars <: js.Object, T <: js.Any] =
    js.Function2[Operation[TVars], NextLink[TVars, T], Observable[FetchResult[T] | Null]]
}

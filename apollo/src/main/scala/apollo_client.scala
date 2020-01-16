// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import scala.scalajs.js
import js.|

package object apollo_client {
  import apollo_client._
  import graphql._
  import apollo_cache._
  import apollo_link.FetchResult

  // apollo-client/core/types
  type MutationQueryReducer[T <: js.Any, R <: js.Object] = js.Function2[js.Object, MutationQueryReducerOptions[T], R]
  type RefetchQueryDescription = js.Array[String] | PureQueryOptions
  type MutationUpdaterFn[T <: js.Any] = js.Function2[DataProxy, FetchResult[T], Unit]
  type MutationQueryReducersMap[T <: js.Any, R <: js.Object] = js.Dictionary[MutationQueryReducer[T, R]]
}

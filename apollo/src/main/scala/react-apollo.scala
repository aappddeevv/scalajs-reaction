// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import scala.scalajs.js
import js.annotation._
import js.|

import react_apollo._
import apollo_boost._

/** Relies on @apollo/react-hooks, @apollo/react-hoc, @apollo/react-common, 
 * @apollo/react-ssr and @apollo/react-component in the real world. We just
  * export the hook related parts for scala.js.
 */
package object react_apollo {

  def useQuery[T <: js.Any, TVars <: js.Object](query: DocumentNode,
    options: js.UndefOr[QueryHookOptions[T, TVars]|js.Dynamic] = js.undefined): QueryResult[T, TVars] =
    react_apollo.module.useQuery(query, options)

  def useLazyQuery[T <: js.Any, TVars <: js.Object](query: DocumentNode,
    options: js.UndefOr[LazyQueryHookOptions[T, TVars]|js.Dynamic] = js.undefined) = 
    react_apollo.module.useLazyQuery[T, TVars](query, options)

  def useSubscription[T <: js.Any, TVars <: js.Object](subscription: DocumentNode,
    options: js.UndefOr[SubscriptionHookOptions[T, TVars]] = js.undefined) = 
    react_apollo.module.useSubscription[T, TVars](subscription, options)

  def useBaseQuery[T <: js.Any, TVars <: js.Object](query: DocumentNode,
    options: js.UndefOr[QueryHookOptions[T, TVars]] = js.undefined) =
    react_apollo.module.useBaseQuery[T, TVars](query, options)

  def useApolloClient() = react_apollo.module.useApolloClient()

  // @apollo/react-common
  /** Often used as a default type for TVars */
  type OperationVariables = js.Dictionary[js.Any]
}

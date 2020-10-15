/*
 * Copyright (c) 2018 The Trapelo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package apollo
package client3
package react

import scala.scalajs.js
import js.annotation._
import js.|

import client3.link._
import client3.core._
import client3.utilities._
import client3.errors._
import graphql._

trait ApolloClientOptionsMaker[T, TVars] {

  /** Make apollo_client.QueryOptions for the ApolloClient returned from `useQuery` which are slightly different than
   * `QueryHookOptions` used in the hook! This primarily exists so we can get a `js.Promise` to throw an exception
   * to the suspense mechanism.
   */
  def makeClientOptions(
    query: DocumentNode,
    variables: js.UndefOr[TVars] = js.undefined,
    unsafeVariables: js.UndefOr[js.Dynamic] = js.undefined,
    errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined,
    context: js.UndefOr[js.Object] = js.undefined,
    fetchResults: js.UndefOr[Boolean] = js.undefined,
    metadata: js.UndefOr[js.Object] = js.undefined,
    fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined
  ) = {
    val __obj = js.Dynamic
      .literal(
        "query" -> query,
        "errorPolicy" -> errorPolicy,
        "context" -> context,
        "fetchResult" -> fetchResults,
        "metadata" -> metadata,
        "fetchPolicy" -> fetchPolicy
      )
    if (variables.isDefined) __obj.updateDynamic("variables")(variables.asInstanceOf[js.Any])
    if (unsafeVariables.isDefined) __obj.updateDynamic("variables")(unsafeVariables)
    __obj.asInstanceOf[QueryOptions[TVars]]
  }
}

trait OptionsMaker[T, TVars] extends ApolloClientOptionsMaker[T, TVars] {

  /** Make QueryHookOptions */
  def makeOptions(
    variables: js.UndefOr[TVars] = js.undefined,
    unsafeVariables: js.UndefOr[js.Dynamic] = js.undefined,
    query: js.UndefOr[DocumentNode] = js.undefined,
    displayName: js.UndefOr[String] = js.undefined,
    onCompleted: js.UndefOr[js.Function1[T, Unit]] = js.undefined,
    onError: js.UndefOr[js.Function1[ApolloError, Unit]] = js.undefined,
    ssr: js.UndefOr[Boolean] = js.undefined,
    fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined,
    errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined,
    pollInterval: js.UndefOr[Int] = js.undefined,
    client: js.UndefOr[ApolloClient[_]] = js.undefined,
    notifyOnNetworkStatusChange: js.UndefOr[Boolean] = js.undefined,
    context: js.UndefOr[js.Object] = js.undefined,
    partialRefetch: js.UndefOr[Boolean] = js.undefined,
    returnPartialData: js.UndefOr[Boolean] = js.undefined,
    skip: js.UndefOr[Boolean] = js.undefined
  ) = {
    val __obj = js.Dynamic
      .literal(
        "query" -> query,
        "displayName" -> displayName,
        "onCompleted" -> onCompleted,
        "onError" -> onError,
        "ssr" -> ssr,
        "fetchPolicy" -> fetchPolicy,
        "errorPolicy" -> errorPolicy,
        "pollInterval" -> pollInterval,
        "client" -> client,
        "notifyOnNetworkStatusChange" -> notifyOnNetworkStatusChange,
        "context" -> context,
        "partialRefetch" -> partialRefetch,
        "returnPartialData" -> returnPartialData,
        "skip" -> skip,
      )
    if (variables.isDefined) __obj.updateDynamic("variables")(variables.asInstanceOf[js.Any])
    if (unsafeVariables.isDefined) __obj.updateDyanmic("variables")(unsafeVariables)
    __obj.asInstanceOf[QueryHookOptions[T, TVars]]
  }
}

/** Because there are some duplicative types in the signatures
 * which become burdensome, this helper class reduces type noise.
 * Instantiate the object then use values and methods in it without
 * need to always specify the types.
 */
case class UseQuery[T, TVars]() extends OptionsMaker[T,TVars] with ApolloClientOptionsMaker[T,TVars]{
  type TArg = T
  type TVarsArg = TVars
  def useQuery(
    query: DocumentNode,
    options: js.UndefOr[QueryHookOptions[T, TVars] | js.Dynamic] = js.undefined
  ): QueryResult[T, TVars] = apollo.client3.react.useQuery[T, TVars](query, options)
}

/** Instantiate to avoid all the type parameter noise. */
case class UseLazyQuery[T, TVars]() extends ApolloClientOptionsMaker[T, TVars] {
  type TArg = T
  type TVarsArg = TVars

  def useLazyQuery(
    query: DocumentNode,
    options: js.UndefOr[LazyQueryHookOptions[T, TVars] | js.Dynamic] = js.undefined
  ): (
    js.Function1[QueryLazyOptions[TVars], Unit],
    LazyQueryResult[T, TVars]
  ) = apollo.client3.react.useLazyQuery[T, TVars](query, options)

  /** Make LazyQueryHookOptions for the hook, not for the lazy fetcher. */
  def makeOptions(
    variables: js.UndefOr[TVars] = js.undefined,
    unsafeVariables: js.UndefOr[js.Dynamic] = js.undefined,
    query: js.UndefOr[DocumentNode] = js.undefined,
    displayName: js.UndefOr[String] = js.undefined,
    onCompleted: js.UndefOr[js.Function1[T, Unit]] = js.undefined,
    onError: js.UndefOr[js.Function1[ApolloError, Unit]] = js.undefined,
    ssr: js.UndefOr[Boolean] = js.undefined,
    fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined,
    errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined,
    pollInterval: js.UndefOr[Int] = js.undefined,
    client: js.UndefOr[ApolloClient[_]] = js.undefined,
    notifyOnNetworkStatusChange: js.UndefOr[Boolean] = js.undefined,
    context: js.UndefOr[js.Object] = js.undefined,
    partialRefetch: js.UndefOr[Boolean] = js.undefined,
    returnPartialData: js.UndefOr[Boolean] = js.undefined,
    skip: js.UndefOr[Boolean] = js.undefined
  ) = {
    val __obj = js.Dynamic
      .literal(
        "query" -> query,
        "displayName" -> displayName,
        "onCompleted" -> onCompleted,
        "onError" -> onError,
        "ssr" -> ssr,
        "fetchPolicy" -> fetchPolicy,
        "errorPolicy" -> errorPolicy,
        "pollInterval" -> pollInterval,
        "client" -> client,
        "notifyOnNetworkStatusChange" -> notifyOnNetworkStatusChange,
        "context" -> context,
        "partialRefetch" -> partialRefetch,
        "returnPartialData" -> returnPartialData,
        "skip" -> skip,
      )
    if (variables.isDefined) __obj.updateDynamic("variables")(variables.asInstanceOf[js.Any])
    if (unsafeVariables.isDefined) __obj.updateDyanmic("variables")(unsafeVariables)
    __obj.asInstanceOf[LazyQueryHookOptions[T, TVars]]
  }

  /** Make LazyQueryHookOptions */
  def makeLazyOptions(
    variables: js.UndefOr[TVars] = js.undefined,
    unsafeVariables: js.UndefOr[js.Dynamic] = js.undefined,
    context: js.UndefOr[js.Object] = js.undefined,
  ) = {
    val __obj = js.Dynamic
      .literal(
        "context" -> context,
      )
    if (variables.isDefined) __obj.updateDynamic("variables")(variables.asInstanceOf[js.Any])
    if (unsafeVariables.isDefined) __obj.updateDyanmic("variables")(unsafeVariables)
    __obj.asInstanceOf[QueryLazyOptions[TVars]]
  }
}

/** Because there are some duplicative types in the signatures
 * which become burdensome, this helper class reduces type noise.
 * Instantiate the object then use values and methods in it without
 * need to always specify the types.
 */
case class UseMutation[T, TVars]() {
  type TArg = T
  type TVarsArg = TVars

  def useMutation(
    mutation: DocumentNode,
    options: js.UndefOr[MutationHookOptions[T, TVars] | js.Dynamic] = js.undefined
  ): (
      js.Function1[MutationFunctionOptions[T, TVars], js.Promise[StandardFetchResult[T]]],
      MutationResult[T]
  ) = apollo.client3.react.useMutation[T, TVars](mutation, options)

  /** Make MutationHookOptions */
  def makeOptions(
    // refetchQueries ???
    awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined,
    client: js.UndefOr[ApolloClient[_]] = js.undefined,
    context: js.UndefOr[js.Object] = js.undefined,
    errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined,
    fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined,
    ignoreResults: js.UndefOr[Boolean] = js.undefined,
    mutation: js.UndefOr[DocumentNode] = js.undefined,
    notifyOnNetworkStatusChange: js.UndefOr[Boolean] = js.undefined,
    onCompleted: js.UndefOr[js.Function1[T, Unit]] = js.undefined,
    onError: js.UndefOr[js.Function1[ApolloError, Unit]] = js.undefined,
    update: js.UndefOr[MutationUpdaterFn[T]] = js.undefined,
    variables: js.UndefOr[TVars] = js.undefined,
    unsafeVariables: js.UndefOr[js.Dynamic] = js.undefined,
  ) = {
    val __obj = js.Dynamic
      .literal(
        "awaitRefetchQueries" -> awaitRefetchQueries,
        "client" -> client,
        "context" -> context,
        "errorPolicy" -> errorPolicy,
        "fetchPolicy" -> fetchPolicy,
        "igoreResults" -> ignoreResults,
        "mutation" -> mutation,
        "notifyOnNetworkStatusChange" -> notifyOnNetworkStatusChange,
        "onCompleted" -> onCompleted,
        "onError" -> onError,
        "update" -> update,
      )
    if (variables.isDefined) __obj.updateDynamic("variables")(variables.asInstanceOf[js.Any])
    if (unsafeVariables.isDefined) __obj.updateDyanmic("variables")(unsafeVariables)
    __obj.asInstanceOf[MutationHookOptions[T, TVars]]
  }

  /** Options needed for the "caller" function return from the mutation hook.
   * Other config info, such as error policy and the operation itself are take
   * from the hook.
   */
  def makeFunctionOptions(
    unsafeVariables: js.UndefOr[js.Dynamic] = js.undefined,
    variables: js.UndefOr[TVars] = js.undefined,
    optimisticResponseStrict: js.UndefOr[T] = js.undefined,
    optimisticResponse: js.UndefOr[js.Function1[TVars, T]] = js.undefined,
    refetchQueriesByName: js.UndefOr[js.Array[String]] = js.undefined,
    awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined,
    update: js.UndefOr[MutationUpdaterFn[T]] = js.undefined,
    context: js.UndefOr[js.Object] = js.undefined,
    fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined,
  ) = {
    val __obj = js.Dynamic
      .literal(
        "context" -> context,
        "fetchPolicy" -> fetchPolicy,
        "update" -> update,
        "refetchQueries" -> refetchQueriesByName,
        "awaitRefetchQueries" -> awaitRefetchQueries,
      )
    if (optimisticResponseStrict.isDefined)
      __obj.updateDynamic("optimisticResponse")(optimisticResponseStrict.asInstanceOf[js.Any])
    if (optimisticResponse.isDefined) __obj.updateDynamic("optimisticResponse")(optimisticResponse)
    if (variables.isDefined) __obj.updateDynamic("variables")(variables.asInstanceOf[js.Any])
    if (unsafeVariables.isDefined) __obj.updateDyanmic("variables")(unsafeVariables)
    __obj.asInstanceOf[MutationFunctionOptions[T, TVars]]
  }

  /** Make apollo_client.QueryOptions for the ApolloClient returned from `useMutation` which are slightly different than
   * `MutationHookOptions` used in the hook!
   * This primarily exists so we can get a `js.Promise` to throw an exception
   * to the suspense mechanism.
   */
  def makeClientOptions(
    mutation: DocumentNode,
    context: js.UndefOr[js.Object] = js.undefined,
    fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined,
    errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined,
    variables: js.UndefOr[TVars] = js.undefined,
    unsafeVariables: js.UndefOr[js.Dynamic] = js.undefined,
    update: js.UndefOr[MutationUpdaterFn[T]] = js.undefined,
    awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined,
    updateQueries: js.UndefOr[MutationQueryReducersMap[T]] = js.undefined,
    optimisticResponse: js.UndefOr[T | js.Function1[TVars, T]] = js.undefined,
  ) = {
    val __obj = js.Dynamic
      .literal(
        "mutation" -> mutation,
        "context" -> context,
        "fetchPolicy" -> fetchPolicy,
        "errorPolicy" -> errorPolicy,
        "update" -> update,
        "awaitRefetchQueries" -> awaitRefetchQueries,
        "updateQueries" -> updateQueries,
        "optimisticResponse" -> optimisticResponse.asInstanceOf[js.Any],
      )
    if (variables.isDefined) __obj.updateDynamic("variables")(variables.asInstanceOf[js.Any])
    if (unsafeVariables.isDefined) __obj.updateDyanmic("variables")(unsafeVariables)
    __obj.asInstanceOf[MutationOptions[T, TVars]]
  }
}

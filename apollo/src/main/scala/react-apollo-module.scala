// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react_apollo

import scala.scalajs.js
import js.|
import js.annotation._

import graphql._
import react._
import apollo_client._
import apollo_boost._

// @apollo/react-common
@js.native
trait QueryResult[T <: js.Any, TVars <: js.Object] extends js.Object {
  val client: apollo_client.ApolloClient = js.native
  val data: js.UndefOr[T] = js.native
  val error: js.UndefOr[ApolloError] = js.native
  val loading: Boolean = js.native
  val networkStatus: NetworkStatus = js.native  
  val called: Boolean = js.native
}

@js.native
@JSImport("react-apollo", JSImport.Namespace)
object module extends js.Object {
  // ... from @apollo/react-hooks
  def useQuery[T <: js.Any, TVars <: js.Object](query: DocumentNode,
    options: js.UndefOr[QueryHookOptions[T, TVars]|js.Dynamic] = js.undefined): QueryResult[T, TVars] = js.native

  def useLazyQuery[T <: js.Any, TVars <: js.Object](query: DocumentNode,
    options: js.UndefOr[LazyQueryHookOptions[T, TVars]|js.Dynamic] = js.undefined):
      js.Tuple2[QueryLazyOptions[TVars], QueryResult[T, TVars]] = js.native

  def useMutation[T <: js.Any, TVars <: js.Object](mutation: DocumentNode,
    options: js.UndefOr[MutationHookOptions[T, TVars]] = js.undefined):
      js.Tuple2[js.Function1[MutationFunctionOptions[T, TVars], js.Promise[ExecutionResult[T]]], MutationResult[T]] = js.native

  def useSubscription[T <: js.Any, TVars <: js.Object](subscription: DocumentNode,
    options: js.UndefOr[SubscriptionHookOptions[T, TVars]] = js.undefined):
      SubscriptionResult[T] with Variables[TVars] = js.native

  def useBaseQuery[T <: js.Any, TVars <: js.Object](query: DocumentNode,
    options: js.UndefOr[QueryHookOptions[T, TVars]] = js.undefined):
      js.Tuple2[js.Function1[js.UndefOr[QueryLazyOptions[TVars]], Unit], QueryResult[T, TVars]] = js.native

  def useApolloClient(): apollo_client.ApolloClient = js.native

  // @apollo/react-common
  def getApolloContext(): ReactContext[ApolloContextValue] = js.native
  // @apollo/react-common
  def resetApolloContext(): Unit = js.native
}

// @apollo/react-common
trait ApolloContextValue extends js.Object {
  var client: js.UndefOr[ApolloClient] = js.undefined
  var renderPromises: js.UndefOr[js.Object] = js.undefined
}

@js.native
trait ApolloError extends js.Object {
  val message: String = js.native  
  val operation: js.Object = js.native
  val response: js.Object = js.native
  val graphQLErrors: js.Object = js.native
  val networkError: ApolloNetworkError = js.native
}

@js.native
trait ApolloNetworkError extends js.Object {
  val name: String = js.native
  val response: js.Object = js.native
  val statusCode: Int = js.native
  val bodyText: String = js.native
}

/** From apollo-common */
@js.native
trait ApolloQueryResult[T <: js.Any] extends js.Object {
  val data: T = js.native
  val errors: js.UndefOr[js.Array[GraphQLError]] = js.native
  val loading: Boolean = js.native
  val networkStatus: NetworkStatus = js.native
  val stale: Boolean = js.native
}

trait BaseQueryOptions[TVars <: js.Object] extends js.Object {
  var ssr: js.UndefOr[Boolean] = js.undefined
  var variables: js.UndefOr[TVars|js.Dynamic] = js.undefined
  var fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined
  var errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined
  var pollInterval: js.UndefOr[Int] = js.undefined
  var client: js.UndefOr[ApolloClient] = js.undefined
  var context: js.UndefOr[js.Object] = js.undefined
  var partialRefetch: js.UndefOr[Boolean] = js.undefined
  var returnPartialData: js.UndefOr[Boolean] = js.undefined
}


// @apollo/react-common
trait Skip extends js.Object {
  var skip: js.UndefOr[Boolean] = js.undefined
}

// @apollo/react-common
trait QueryFunctionOptions[T <: js.Any, TVars <: js.Object] extends BaseQueryOptions[TVars] {
  var displayName: js.UndefOr[String] = js.undefined
  var onCompleted: js.UndefOr[js.Function1[T, Unit]] = js.undefined
  var onError: js.UndefOr[js.Function1[ApolloError, Unit]] = js.undefined
}

// @apollo/react-hooks
trait QueryHookOptions[T <: js.Any, TVars <: js.Object]
    extends QueryFunctionOptions[T, TVars]
    with Skip {
  // children?
  var query: js.UndefOr[DocumentNode] = js.undefined
}

// @apollo/react-hooks
object QueryHookOptions {
  def variables[T <: js.Any, TVars <: js.Object](v: TVars|js.Dynamic) = new QueryHookOptions[T,TVars] {
    variables = v
  }
}

// @apollo/react-hooks, return value from useLazyQuery
@js.native
trait QueryLazyOptions[TVars <: js.Object] extends js.Object {
  val variables: js.UndefOr[TVars] = js.undefined
  // there is a type call Context = Record<string, any>
  val context: js.UndefOr[js.Object] = js.undefined
}

// @apollo/react-hooks
trait LazyQueryHookOptions[T <: js.Any, TVars <: js.Object]
    extends QueryFunctionOptions[T, TVars] {
    var query: js.UndefOr[DocumentNode] = js.undefined
}

// @apollo/react-common
object ApolloProvider {
  @js.native
  @JSImport("react-apollo", "ApolloProvider")
  object JS extends ReactJsComponent

  trait Props extends ApolloContextValue

  def apply(props: Props)(children: ReactNode*) =
    react.createElement(JS, props)(children:_*)

  def apply(c: apollo_client.ApolloClient)(children: ReactNode*) =
    react.createElement(JS, new Props { client = c})(children:_*)
}

// @apollo/react-common
@js.native
trait ExecutionResult[T <: js.Any] extends js.Object {
  val data: js.UndefOr[T] = js.native
  val extensions: js.UndefOr[js.Dictionary[js.Any]] = js.native
  val errors: js.UndefOr[js.Array[GraphQLError]] = js.native
}

// @apollo/react-common
trait BaseMutationOptions[T <: js.Any, TVars <: js.Object] extends js.Object {
  var variables: js.UndefOr[TVars] = js.undefined
  //optimisticResponse
  @JSName("refetchQueries")
  var refetchQueriesByName: js.UndefOr[js.Array[String]] = js.undefined
  // this is not quite right, how to do varargs js funciton decl?
  @JSName("refetchQueries")
  var refetchQueriesBy: js.UndefOr[js.Function1[js.Array[js.Any], js.Array[String]]] = js.undefined  
  val awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined
  val errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined
  // update
  var client: js.UndefOr[ApolloClient] = js.undefined
  val notifyOnNetowrkStatusChange: js.UndefOr[Boolean] = js.undefined
  var context: js.UndefOr[js.Object] = js.undefined
  var onCompleted: js.UndefOr[js.Function1[T, Unit]] = js.undefined
  var onError: js.UndefOr[js.Function1[ApolloError, Unit]] = js.undefined
  var fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined
  var ignoreResults: js.UndefOr[Boolean] = js.undefined
}

// @apollo/react-common
trait MutationFunctionOptions[T <: js.Any, TVars <: js.Object] extends js.Object {
  var variables: js.UndefOr[TVars] = js.undefined
  @JSName("optimisticResponse")
  var optimisticResponseStrict: js.UndefOr[T] = js.undefined
  @JSName("optimisticResponse")
  var optimisticResponse: js.UndefOr[js.Function1[TVars, T]] = js.undefined
  @JSName("refetchQueries")
  var refetchQueriesByName: js.UndefOr[js.Array[String]] = js.undefined
  val awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined
  var context: js.UndefOr[js.Object] = js.undefined
  var fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined
}

// @apollo/react-hooks
trait MutationHookOptions[T <: js.Any, TVars <: js.Object] extends BaseMutationOptions[T, TVars] {
  var mutation: js.UndefOr[DocumentNode] = js.undefined
}

// @apollo/react-common
@js.native
trait MutationResult[T <: js.Any] extends js.Object {
  val data: js.UndefOr[T] = js.native
  val error: js.UndefOr[ApolloError] = js.native
  val loading: Boolean = js.native
  val called: Boolean = js.native
  val client: js.UndefOr[ApolloClient] = js.native
}

// @apollo/react-common
@js.native
trait SubscriptionResult[T <: js.Any] extends js.Object {
  val loading: Boolean = js.native
  val data: js.UndefOr[T] = js.native
  val error: js.UndefOr[ApolloError] = js.native
}

@js.native
trait Variables[TVars <: js.Object] extends js.Object {
  val variables: js.UndefOr[TVars] = js.undefined
}

// @apollo/react-common
@js.native
trait OnSubscriptionDataOptions[T <: js.Any] extends js.Object {
  val client: ApolloClient = js.native
  val subscriptionData: SubscriptionResult[T]
}

// @apollo/react-common
trait BaseSubscriptionOptions[T <: js.Any, TVars <: js.Object] {
  var variables: js.UndefOr[TVars] = js.undefined
  var fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined
  @JSName("shouldResubscribe")
  var shouldResubscribeStrict: js.UndefOr[Boolean] = js.undefined
  var shouldResubscribe: js.UndefOr[js.Function1[BaseSubscriptionOptions[T, TVars], Boolean]] = js.undefined
  var client: js.UndefOr[ApolloClient] = js.undefined
  var skip: js.UndefOr[Boolean] = js.undefined
  var onSubscriptionData: js.UndefOr[js.Function1[OnSubscriptionDataOptions[T], js.Any]] = js.undefined
  var onSubscriptionComplete: js.UndefOr[js.Function0[Unit]] = js.undefined
}

// @apollo/react-hooks
trait SubscriptionHookOptions[T <: js.Any, TVars <: js.Object] extends BaseSubscriptionOptions[T, TVars] {
  val subscription: js.UndefOr[DocumentNode] = js.undefined
  //var children: js.UndefOr[js.Function1[SubscriptionResult[T], ReactNode|Null]] = js.undefined
}

// @apollo/react-hooks this is actually a class for ssr use
@js.native
@JSImport("@apollo/react-hooks", "RenderPromises")
class RenderPromises() extends js.Object {
  // add "registration" functions ...
  def hasPromises(): Boolean = js.native
  def consumeAndAwaitPromises(): js.Promise[js.Array[js.Any]] = js.native
}

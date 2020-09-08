package apollo
package client3

import scala.scalajs.js
import js.|
import js.annotation._

import graphql._

package object react_module {
    import react_module._

    @js.native
    @JSImport("@apollo/client/react". "useQuery")
    def useQuery[T,TVars](
        query: DocumentNode, 
        options: js.UndefOr[QueryHookOptions[T,TVars]] = js.undefined
    ): QueryResult[T,TVars] = js.native

    @js.native
    @JSImport("@apollo/client/react". "useLazyQuery")
    def useLazyQuery[T,TVars](
        query: DocumentNode, 
        options: js.UndefOr[LazyQueryOptions[T,TVars]] = js.undefined
    ): QueryTuple[T,TVars] = js.native

    @js.native
    @JSImport("@apollo/client/react". "useMutation")
    def useMutation[T,TVars](
        mutation: DocumentNode, 
        options: js.UndefOr[MutationHookOptions[]] = js.undefined
    ): MutationTuple[T,TVars] = js.native

    @js.native
    @JSImport("@apollo/client/react". "useSubscription")
    def useSubscription[T,TVars](
        subscription: DocumentNode,
        options: js.UndefOr[SubscriptionHookOptions[T,TVars]] = js.undefined): js.Any = js.native

    @js.native
    @JSImport("@apollo/client/react". "useApolloClient")
    def useApolloClient[Shape](): ApolloClient[Shape] = js.native

    @js.native
    @JSImport("@apollo/client/react". "getApolloContext")
    def getApolloContext(): ReactContext[ApolloContext] = js.native

    @js.native
    @JSImport("@apollo/client/react". "resetApolloContext")
    def resetApolloContext(): Unit = js.native

}
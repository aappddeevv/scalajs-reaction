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

import scala.scalajs.js
import js.|
import js.annotation._
import react._

import _root_.react._
import graphql._
import client3.core._
import client3.link._

package object react extends react.context.ApolloContext_module with hooks.index_module with hooks.types.types_module {

  @js.native
  @JSImport("@apollo/client/react", "useQuery")
  def useQuery[T, TVars](
    query: DocumentNode,
    options: js.UndefOr[QueryHookOptions[T, TVars] | js.Dynamic] = js.undefined
  ): QueryResult[T, TVars] = js.native

  @js.native
  @JSImport("@apollo/client/react", "useLazyQuery")
  def useLazyQuery[T, TVars](
    query: DocumentNode,
    options: js.UndefOr[LazyQueryHookOptions[T, TVars] | js.Dynamic] = js.undefined
  ): QueryTuple[T, TVars] = js.native

  @js.native
  @JSImport("@apollo/client/react", "useMutation")
  def useMutation[T, TVars](
    mutation: DocumentNode,
    options: js.UndefOr[MutationHookOptions[T, TVars] | js.Dynamic] = js.undefined
  ): MutationTuple[T, TVars] = js.native

  @js.native
  @JSImport("@apollo/client/react", "useSubscription")
  def useSubscription[T, TVars](
    subscription: DocumentNode,
    options: js.UndefOr[SubscriptionHookOptions[T, TVars] | js.Dynamic] = js.undefined): js.Any = js.native

  @js.native
  @JSImport("@apollo/client/react", "useApolloClient")
  def useApolloClient[Shape](): ApolloClient[Shape] = js.native

  @js.native
  @JSImport("@apollo/client/react", "getApolloContext")
  def getApolloContext(): ReactContext[ApolloContextValue] = js.native

  @js.native
  @JSImport("@apollo/client/react", "resetApolloContext")
  def resetApolloContext(): Unit = js.native

}

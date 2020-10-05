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
package core

import scala.scalajs.js
import js.annotation._
import js.|

import client3.errors._
import rxjs._

@js.native
trait PartialApolloCurrentQueryResult extends js.Object {
  val error: js.UndefOr[ApolloError] = js.undefined
  val partial: js.UndefOr[ApolloError] = js.undefined
}

trait FetchMoreOptions[T, TVars] extends js.Object {
  var updateQuery: js.UndefOr[js.Function2[T, FetchMoreOptions.UpdateQueryOptions[T, TVars], T]] = js.undefined
}

object FetchMoreOptions {
  trait UpdateQueryOptions[T, TVars] extends js.Object {
    var fetchMoreResults: js.UndefOr[T] = js.undefined
    var variables: js.UndefOr[TVars] = js.undefined
  }
}

trait UpdateQueryOptions[TVars] extends js.Object {
  var variables: js.UndefOr[TVars] = js.undefined
}

@js.native
trait PartialObservableQuery[T, TVars] extends js.Object {
  def startPolling(pollInterval: Int): Unit = js.native
  def stopPolling(): Unit = js.native
  //def subscribeToMore[SubVars,SubData](options: SubscribeToMoreOptions[T, SubVars, SubData]): js.Function0[Unit] = js.undefined
  //def updateQuery
  def refetch(variables: js.UndefOr[TVars] = js.undefined): js.Promise[ApolloQueryResult[T]] = js.native
  def variables: js.UndefOr[TVars] = js.native
}

@js.native
@JSImport("@apollo/client/core/ObservableQuery", "ObservableQuery")
class ObservableQuery[T, TVars]() extends Observable[ApolloQueryResult[T]] with PartialObservableQuery[T, TVars] {
  def result(): js.Promise[ApolloQueryResult[T]] = js.native
  def getCurrentResult(): ApolloCurrentQueryResult[T] = js.native
  def isDifferentFromLastResult(newResult: ApolloQueryResult[T]): Boolean = js.native
  def getLastResult(): ApolloQueryResult[T] = js.native
  def getLastResulrt(): ApolloError = js.native
  def resetLastResults(): Unit = js.native
  def resetQueryStoreErrors(): Unit = js.native
  //def fetchMore(
  def setOptions(options: WatchQueryOptions[TVars]): js.Promise[ApolloQueryResult[T]] = js.native
  def updateLastResult(newResult: ApolloQueryResult[T]): ApolloQueryResult[T] = js.native
  //def reobserve

}

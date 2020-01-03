// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package apollo_cache

import scala.scalajs.js
import js.annotation._
import js.|

import graphql._

trait Query[TVars <: js.Object] extends js.Object {
  val query: DocumentNode
  var variables: js.UndefOr[TVars] = js.undefined
}

trait Fragment[TVars <: js.Object] extends js.Object {
  val id: String
  val fragment: graphql.DocumentNode
  var fragmentName: js.UndefOr[String] = js.undefined
  var variables: js.UndefOr[TVars] = js.undefined
}

trait WriteFragmentOptions[T <: js.Any, TVars <: js.Object] extends Fragment[TVars] {
  val data: T
}

trait WriteDataOptions[T <: js.Any] extends js.Object {
  val data: T
  var id: js.UndefOr[String] = js.undefined
}

trait WriteQueryOptions[T <: js.Any, TVars <: js.Object] extends Query[TVars] {
  val data: T
}

@js.native
trait DataProxy extends js.Object {
  def readQuery[QueryType <: js.Object, TVars <: js.Object](options: Query[TVars], optimistic: js.UndefOr[Boolean] = js.undefined): QueryType | Null
  def readFragment[TVars <: js.Object, FragmentType <: js.Object](options: Fragment[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined): FragmentType | Null
  def writeQuery[T <: js.Any, TVars <: js.Object](options: WriteQueryOptions[T, TVars]): Unit
  def writeFragment[T <: js.Any, TVars <: js.Object](options: WriteFragmentOptions[T, TVars]): Unit
  def writeData[T <: js.Any](options: WriteDataOptions[T]): Unit
}

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import scala.scalajs.js
import js.annotation._
import js.JSConverters._

package object apollo_boost {

  type DocumentNode = graphql.DocumentNode

  def gql_raw(query: js.Array[String], placeholders: js.Any*): DocumentNode =
    apollo_boost.module.gql(query, placeholders)

  def gql(query: String) = apollo_boost.module.gql(js.Array(query))

  implicit class GQLHelper(val sc: StringContext) extends AnyVal {

    /** If gql is not well-formed, this may thrown an error that is hard to determine its location. */
    def gql(args: Any*) = apollo_boost.module.gql(sc.parts.toJSArray, args.map(_.asInstanceOf[js.Any]):_*)

    /** Return either. Using this makes using the hook less ergonomic. */
    def safe_gql(args: Any*): Either[js.JavaScriptException, DocumentNode] =
      try Right(apollo_boost.module.gql(sc.parts.toJSArray, args.map(_.asInstanceOf[js.Any]):_*))
      catch { case e: js.JavaScriptException => Left(e)}
  }

}

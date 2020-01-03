// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package graphql

import scala.scalajs.js

@js.native
trait SourceLocation extends js.Object {
  val line: Int = js.native
  val column: Int = js.native
}

@js.native
trait Source extends js.Object {
  val body: String = js.native
  val name: String = js.native
  val locationOffset: SourceLocation = js.native
}

@js.native
trait Location extends js.Object {
  val start: String = js.native
  val end: String = js.native
  // startToken, endToken
  val source: Source = js.native
}

// AST element
@js.native
trait DocumentNode extends js.Object {
  val kind: String = js.native // always "Document"
  val location: Location = js.native
}

@js.native
trait GraphQLError extends js.Error {
  //locations
  //path
  //nodes
  //source
  //positions 
  //originalError
  //extensions
}

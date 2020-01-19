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

package mssql

import scala.scalajs.js

import js.annotation._

trait PoolConfig extends js.Object {
  var max: js.UndefOr[Int]               = js.undefined
  var min: js.UndefOr[Int]               = js.undefined
  var idleTimeoutMillis: js.UndefOr[Int] = js.undefined
  // maxWaitingClients
  // acquireTimeoutMillis
  // fifo
  // priorityRange
  // autostart
  // evictionRunIntervalMillis
  // numTestsPerRun
  // softIdleTimeoutMillis
}

trait Config extends js.Object {
  var user: js.UndefOr[String]           = js.undefined
  var password: js.UndefOr[String]       = js.undefined
  var server: js.UndefOr[String]         = js.undefined
  var port: js.UndefOr[Int]              = js.undefined
  var domain: js.UndefOr[String]         = js.undefined
  var database: js.UndefOr[String]       = js.undefined
  var connectionTimeout: js.UndefOr[Int] = js.undefined
  var requestTimeout: js.UndefOr[Int]    = js.undefined
  var stream: js.UndefOr[Boolean]        = js.undefined
  var parseJSON: js.UndefOr[Boolean]     = js.undefined
  var pool: js.UndefOr[PoolConfig]       = js.undefined
}

@js.native
@JSImport("mssql", "ConnectionPool")
class ConnectionPool(config: Config) extends js.Object {
  def request(): Request = js.native
  def cancel(): Unit     = js.native
}

@js.native
@JSImport("mssql", "Request")
class Request(pool: js.UndefOr[ConnectionPool] = js.undefined) extends js.Object {
  // def execute[T <: js.Object](pname: String): js.Promise[Result[T]] = js.native
  def query(q: String): js.Promise[Result] = js.native
}

@js.native
@JSImport("mssql", "Transaction")
class Transaction(pool: js.UndefOr[ConnectionPool] = js.undefined) extends js.Object {
  def begin(): js.Promise[Unit] = js.native
}

@js.native
trait Result extends js.Object {
  def recordset[T <: js.Object]: RecordSet[T]            = js.native
  def recordsets[T <: js.Object]: js.Array[RecordSet[T]] = js.native
  val rowsAffected: js.Array[Int]                        = js.native
  val output: js.Object                                  = js.native
}

@js.native
trait RecordSet[T <: js.Object] extends js.Array[T] {
  val columns: js.Dictionary[ColumnMetadata] = js.native
  //val toTable(): Table = js.native
}

@js.native
trait ColumnMetadata extends js.Object {
  val index: Int              = js.native
  val name: String            = js.native
  val length: Int             = js.native
  val `type`: js.Any          = js.native
  val udt: js.UndefOr[js.Any] = js.native
}

// @js.native
// @JSImport("mssql", JSImport.Namespace)
// object mssql {
//   //val Int: js.Any = js.native
// }

@js.native
trait Table extends js.Object {}

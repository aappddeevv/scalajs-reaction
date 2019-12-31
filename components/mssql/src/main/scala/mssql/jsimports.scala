// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package mssql

import scala.scalajs.js
import js.annotation._
import js.|


trait PoolConfig extends js.Object {
  var max: js.UndefOr[Int] = js.undefined
  var min: js.UndefOr[Int] = js.undefined
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
  var user: js.UndefOr[String] = js.undefined
  var password: js.UndefOr[String] = js.undefined
  var server: js.UndefOr[String] = js.undefined
  var port: js.UndefOr[Int] = js.undefined
  var domain: js.UndefOr[String] = js.undefined
  var database: js.UndefOr[String] = js.undefined
  var connectionTimeout: js.UndefOr[Int] = js.undefined  
  var requestTimeout: js.UndefOr[Int] = js.undefined
  var stream: js.UndefOr[Boolean] = js.undefined
  var parseJSON: js.UndefOr[Boolean] = js.undefined  
  var pool: js.UndefOr[PoolConfig] = js.undefined
}

@js.native
@JSImport("mssql", "ConnectionPool")
class ConnectionPool(config: Config) extends js.Object {
  def request(): Request = js.native
  def cancel(): Unit = js.native
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
  def recordset[T <: js.Object]: RecordSet[T] = js.native
  def recordsets[T <: js.Object]: js.Array[RecordSet[T]] = js.native
  val rowsAffected: js.Array[Int] = js.native
  val output: js.Object = js.native
}

@js.native
trait RecordSet[T <: js.Object] extends js.Array[T] {
  val columns: js.Dictionary[ColumnMetadata] = js.native
  //val toTable(): Table = js.native
}

@js.native
trait ColumnMetadata extends js.Object {
  val index: Int = js.native
  val name: String = js.native
  val length: Int = js.native
  val `type`: js.Any = js.native
  val udt: js.UndefOr[js.Any] = js.native
}

// @js.native
// @JSImport("mssql", JSImport.Namespace)
// object mssql {
//   //val Int: js.Any = js.native
// }

@js.native
trait Table extends js.Object {

}

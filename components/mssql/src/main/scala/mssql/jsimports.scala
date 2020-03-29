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

@js.native
trait MSSQLEventEmitter extends js.Object {
  def on(name: String, cb: js.Any): Unit = js.native
}

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
class ConnectionPool(config: Config) extends MSSQLEventEmitter {
  def request(): Request = js.native
  def cancel(): js.Promise[Unit]     = js.native
  def transaciton(): Transaction = js.native
}

@js.native
@JSImport("mssql", "Request")
class Request(pool: js.UndefOr[ConnectionPool] = js.undefined) extends MSSQLEventEmitter {
  def query(q: String): js.Promise[Result] = js.native
  def input(name: String, tpe: SQLType, value: scala.Any): Request = js.native
  @JSName("input")
  def inputAuto(name: String, value: scala.Any): Request = js.native
  def output(name: String, tpe: SQLType, defaultValue: js.UndefOr[scala.Any] = js.undefined): Request = js.native 
  def execute(name: String): js.Promise[ProcedureResult] = js.native
  def pipe(stream: js.Any): Request = js.native
  def batch(query: String): js.Promise[Result] = js.native
  def bulk(t: Table, options: js.UndefOr[BulkOptions] = js.undefined): js.Promise[BulkResult] = js.native
  def cancel(): Boolean = js.native
}

trait BulkOptions extends js.Object {
  var checkConstraints: js.UndefOr[Boolean] = js.undefined
  var fireTriggers: js.UndefOr[Boolean] = js.undefined
  var keepNulls: js.UndefOr[Boolean] = js.undefined
  var tableLock: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait ProcedureResult extends Result {
 val returnValue: js.Any = js.native
}

@js.native
trait BulkResult extends js.Object {
  val rowsAffected: Int = js.native
}


@js.native
@JSImport("mssql", "ISOLATION_LEVEL")
object ISOLATION_LEVEL extends js.Object {
  val READ_UNCOMMITTED: Int = js.native
  val READ_COMMITTED: Int = js.native
  val REPEATABLE_READ: Int = js.native
  val SERIALIZABLE: Int = js.native
  val SNAPSHOT: Int = js.native
}

@js.native
@JSImport("mssql", "Transaction")
class Transaction(pool: js.UndefOr[ConnectionPool] = js.undefined) extends MSSQLEventEmitter {
  def begin(isolationLevel: Int): js.Promise[Unit] = js.native
  def commitCB(err: js.UndefOr[js.Function1[scala.Any, Unit]] = js.undefined): Unit = js.native
  def commit(): js.Promise[Unit] = js.native
  def rollback(): js.Promise[Unit] = js.native
  def rollbackCB(err: js.UndefOr[js.Function1[scala.Any, Unit]] = js.undefined): Unit = js.native
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
  def toTable(name: js.UndefOr[String] = js.undefined): Table = js.native
}

@js.native
trait ColumnMetadata extends js.Object {
  val index: Int              = js.native
  val name: String            = js.native
  val length: Int             = js.native
  val `type`: js.Any          = js.native
  val udt: js.UndefOr[js.Any] = js.native
}

@js.native
@JSImport("mssql", "ConnectionError")
class ConnectionError(message: String, val code: js.UndefOr[scala.Any]= js.undefined) extends js.Error(message) {
  //val name: String = js.native
  //val code: String = js.native
 
}

@js.native
@JSImport("mssql", "TransactionError")
class TransactionError(message: String, val code: js.UndefOr[scala.Any]= js.undefined) extends js.Error(message) {
  //val name: String = js.native
  //val code: String = js.native
}

@js.native
@JSImport("mssql", "RequestError")
class RequestError(message: String, code: js.UndefOr[scala.Any]= js.undefined) extends js.Error(message) {
  //val name: String = js.native
  //val code: String = js.native
  val number: js.UndefOr[Int] = js.native
  val state: js.UndefOr[Int] = js.native
  //val class
  val lineNumber: js.UndefOr[Int]  =js.native
  val serverName: js.UndefOr[String] = js.native
  val procName: js.UndefOr[String] = js.native
}

@js.native
@JSImport("mssql", "PreparedStatementError")
class PreparedStatementError(message: String, val code: js.UndefOr[scala.Any]= js.undefined) extends js.Error(message) {
  //val name: String = js.native
  //val code: String = js.native
}

@js.native
trait Table extends js.Object {}

@js.native
trait SQLType extends js.Object 

@js.native
trait IMap extends js.Array[js.Any] {
  def register(jstype: scala.Any, sql: SQLType): Unit = js.native
}

@js.native
@JSImport("mssql", JSImport.Namespace)
object module extends js.Object {
  val map: IMap = js.native
  def VarChar(length: Int): SQLType = js.native
  @JSName("VarChar")
  val VarCharAny: SQLType = js.native
  def NVarChar(length: Int): SQLType = js.native
  @JSName("NVarChar")
  val NVarCharAny: SQLType = js.native
  val Text: SQLType = js.native
  val Int: SQLType = js.native
  val BigInt: SQLType = js.native
  val TinyInt: SQLType = js.native
  val SmallInt: SQLType = js.native
  val Bit: SQLType = js.native
  val Float: SQLType = js.native
  def Numeric(precision: Int, scale: Int): SQLType = js.native
  def Decimal(precision: Int, scale: Int): SQLType = js.native 
  val Real: SQLType = js.native
  val Date: SQLType = js.native
  val DateTime: SQLType = js.native
  def DateTime2(scale: Int): SQLType = js.native
  def DateTimeOffset(scale: Int): SQLType = js.native
  val SmallDateTime: SQLType = js.native
  def Time(scale: Int) : SQLType = js.native
  val UniqueIdentifier: SQLType = js.native
  val SmallMoney: SQLType = js.native
  val Money: SQLType = js.native
  def Binary(length: Int): SQLType = js.native
  def VarBinary(length: Int): SQLType = js.native
  val Image: SQLType = js.native
  val Xml: SQLType = js.native
  def Char(length: Int): SQLType = js.native
  @JSName("Char")
  val CharAny: SQLType = js.native
  def NChar(length: Int): SQLType = js.native
  @JSName("NChar")
  val NCharAny: SQLType = js.native
  val NText: SQLType = js.native
  val TVP: SQLType = js.native
  val UDT: SQLType = js.native
  val Geography: SQLType = js.native
  val Geometry: SQLType = js.native
  val Variant: SQLType = js.native
}

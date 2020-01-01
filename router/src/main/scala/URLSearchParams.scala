// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg 
package router
package browser

import scala.scalajs.js
import js.annotation._
import js.|

@js.native
@JSGlobal("URLSearchParams")
class URLSearchParams protected () extends js.Object {
  def this(init: String | URLSearchParams | js.Any = js.native) = this()
  def append(name: String, value: js.Any): Unit  = js.native
  def delete(name: String): Unit                 = js.native
  // key, value1, value2, ...
  def entries(): Iterator[js.Array[String]] = js.native
  // first value found
  def get(name: String): String                  = js.native
  def getAll(name: String): js.Array[String]                 = js.native
  def has(name: String): Boolean                 = js.native
  def keys(): Iterator[String]                   = js.native
  def set(name: String, value: js.Any): Unit     = js.native
  def values(pointer: String): Iterator[String]  = js.native
  def sort(): Unit = js.native
}


// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package native

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

// you should look at: https://github.com/sunnylqm/react-native-storage

@js.native
@JSImport("react-native", "AsyncStorage")
object AsyncStorage extends js.Object {
  def getItem(key: String): js.Promise[String] = js.native
  def setItem(key: String, value: String): js.Promise[Unit] = js.native
  def removeItem(key: String): js.Promise[Unit] = js.native
  def mergeItem(key: String, value: String): js.Promise[Unit] = js.native
  // clears everything!
  @JSName("clear")
  def unsafe_clear_everything(): js.Promise[Unit] = js.native
  def getAllKeys(): js.Promise[js.Array[String]] = js.native
  // ???
  def flushGetRequests(): js.Object = js.native
  def multiGet(keys: js.Array[String]): js.Promise[js.Array[js.Array[String]]] = js.native
  def multiSet(keyValuesPairs: js.Array[js.Array[String]]): js.Promise[Unit] = js.native
  def multiRemove(keys: js.Array[String]): js.Promise[Unit] = js.native
  def multiMerge(keyValuePairs: js.Array[js.Array[String]]): js.Promise[Unit] = js.native
}

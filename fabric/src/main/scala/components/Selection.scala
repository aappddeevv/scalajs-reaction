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

package fabric
package components

import scala.scalajs.js

import js.annotation._
import js.|

import react._

@js.native
trait ISelection[T <: js.Object] extends js.Object {
  val count: Int
  val mode: SelectionMode
  def canSelectItem(item: T, index: js.UndefOr[Int] = js.undefined): Boolean
  def getItems(): js.Array[T]
  def getSelection(): js.Array[T]
  def getSelectionIndices(): js.Array[Int]
  def getSelectedCount(): Int
  def isAllSelected(): Boolean
  def isModel(): Boolean
  def selectToKey(key: String, clearSelection: js.UndefOr[Boolean] = js.undefined): Unit
  def selectToIndex(index: Int, clearSelection: js.UndefOr[Boolean] = js.undefined): Unit
  def setItems(items: js.Array[T], shouldClear: Boolean): Unit
  def setKeySelected(key: String, isSelected: Boolean, shouldAnchor: Boolean): Unit
  def setIndexSelected(index: Int, isSelected: Boolean, shouldAnchor: Boolean): Unit
  def setChangeEvents(isEnabled: Boolean, suppressChange: js.UndefOr[Boolean] = js.undefined): Unit
  def setModal(isModal: Boolean): Unit
  def toggleAllSelected(): Unit
  def toggleKeySelected(key: String): Unit
  def toggleIndexSelected(index: Int): Unit
  def toggleRangeSelected(fromIndex: Int, count: Int): Unit
}

@js.native
abstract trait SelectionDirection extends js.Any
object SelectionDirection {
  val horizontal = 0.asInstanceOf[SelectionDirection]
  val vertical   = 1.asInstanceOf[SelectionDirection]
}

@js.native
abstract trait SelectionMode extends js.Any
object SelectionMode {
  val none     = 0.asInstanceOf[SelectionMode]
  val single   = 1.asInstanceOf[SelectionMode]
  val multiple = 2.asInstanceOf[SelectionMode]
}

trait ISelectionOptions[T <: js.Object] extends js.Object {
  var getKey: js.UndefOr[js.Function2[T, js.UndefOr[Int], String] | js.Function1[T, String]] =
    js.undefined
  @JSName("getKey")
  var getKeyFromItem: js.UndefOr[js.Function1[T, String | Int]]                         = js.undefined
  var selectionMode: js.UndefOr[SelectionMode]                                          = js.undefined
  var onSelectionChanged: js.UndefOr[js.Function0[Unit]]                                = js.undefined
  var canSelectItem: js.UndefOr[js.Function2[IObjectWithKey, js.UndefOr[Int], Boolean]] = js.undefined
}

@js.native
@JSImport("office-ui-fabric-react/lib/utilities/selection", "Selection")
class Selection[T <: js.Object](options: js.UndefOr[ISelectionOptions[T]] = js.undefined)
    extends js.Object
    with ISelection[T] {

  val count: Int                                                                                    = js.native
  def canSelectItem(item: T, index: js.UndefOr[Int] = js.undefined): Boolean                        = js.native
  val mode: SelectionMode                                                                           = js.native
  def getItems(): js.Array[T]                                                                       = js.native
  def getSelection(): js.Array[T]                                                                   = js.native
  def getSelectionIndices(): js.Array[Int]                                                          = js.native
  def getSelectedCount(): Int                                                                       = js.native
  def isModel(): Boolean                                                                            = js.native
  def isAllSelected(): Boolean                                                                      = js.native
  def selectToKey(key: String, clearSelection: js.UndefOr[Boolean] = js.undefined): Unit            = js.native
  def selectToIndex(index: Int, clearSelection: js.UndefOr[Boolean] = js.undefined): Unit           = js.native
  def setItems(items: js.Array[T], shouldClear: Boolean): Unit                                      = js.native
  def setKeySelected(key: String, isSelected: Boolean, shouldAnchor: Boolean): Unit                 = js.native
  def setIndexSelected(index: Int, isSelected: Boolean, shouldAnchor: Boolean): Unit                = js.native
  def setChangeEvents(isEnabled: Boolean, suppressChange: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def setModal(isModal: Boolean): Unit                                                              = js.native
  def toggleAllSelected(): Unit                                                                     = js.native
  def toggleKeySelected(key: String): Unit                                                          = js.native
  def toggleIndexSelected(index: Int): Unit                                                         = js.native
  def toggleRangeSelected(fromIndex: Int, count: Int): Unit                                         = js.native

}

object Selection {

  def apply[T <: js.Object](options: Options[T]) =
    new Selection[T](options.asInstanceOf[ISelectionOptions[T]])

  trait Options[T <: js.Object] extends js.Object {
    var getKey: js.UndefOr[js.Function2[T, js.UndefOr[Int], String] | js.Function1[T, String]] =
      js.undefined
    @JSName("getKey")
    var getKeyFromItem: js.UndefOr[js.Function1[T, String | Int]]                         = js.undefined
    var selectionMode: js.UndefOr[SelectionMode]                                          = js.undefined
    var onSelectionChanged: js.UndefOr[js.Function0[Unit]]                                = js.undefined
    var canSelectItem: js.UndefOr[js.Function2[IObjectWithKey, js.UndefOr[Int], Boolean]] = js.undefined
  }
}

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import vdom._
import fabric.styling._

@js.native
trait ISelection[T <: js.Object] extends js.Object {
  //val count: Int
  def getItems(): js.Array[T]
  def setItems(items: js.Array[T], shouldClear: Boolean): Unit
  def setKeySelected(key: String, isSelected: Boolean, shouldAnchor: Boolean): Unit
  def setChangeEvents(isEnabled: Boolean, suppressChange: js.UndefOr[Boolean] = js.undefined): Unit
  def toggleAllSelected(): Unit
  def setModal(isModal: Boolean): Unit
  def isModel(): Boolean
  def getSelection(): js.Array[T]
  def getSelectedCount(): Int
}

@js.native
abstract sealed trait SelectionMode extends js.Any
object SelectionMode {
  val none     = 0.asInstanceOf[SelectionMode]
  val single   = 1.asInstanceOf[SelectionMode]
  val multiple = 2.asInstanceOf[SelectionMode]
}

trait ISelectionOptions[T <: js.Object] extends js.Object {
  var getKey: js.UndefOr[js.Function2[T, js.UndefOr[Int], String] | js.Function1[T, String]] =
    js.undefined
  var selectionMode: js.UndefOr[SelectionMode]                     = js.undefined
  var onSelectionChanged: js.UndefOr[js.Function0[Unit]] = js.undefined
  var canSelectItem: js.UndefOr[js.Function2[IObjectWithKey, js.UndefOr[Int], Boolean]] = js.undefined
}

@js.native
@JSImport("office-ui-fabric-react/lib/utilities/selection", "Selection")
class Selection[T <: js.Object](options: js.UndefOr[ISelectionOptions[T]] = js.undefined)
    extends js.Object
    with ISelection[T] {
  val count: Int                                                                    = js.native
  def getItems(): js.Array[T]                                                       = js.native
  def setItems(items: js.Array[T], shouldClear: Boolean): Unit                      = js.native
  def setKeySelected(key: String, isSelected: Boolean, shouldAnchor: Boolean): Unit = js.native
  def setChangeEvents(
      isEnabled: Boolean,
      suppressChange: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def toggleAllSelected(): Unit                                 = js.native
  def setModal(isModal: Boolean): Unit                          = js.native
  def isModel(): Boolean                                        = js.native
  def getSelection(): js.Array[T]                               = js.native
  def getSelectedCount(): Int                                   = js.native
}

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package fabric

import scala.scalajs.js
import js.annotation._
import js.|

import ttg.react.vdom._
import org.scalajs.dom

/** Misc attributes to use individually, we should use traits instead. */
trait FabricAttrs {
  final lazy val text = attr("text")
  final lazy val disabled = attr("disabled")
  final lazy val onChanged = attr("onChanged")

  /** reactjs component => unit. */
  final lazy val componentRef = attr("componentRef")
}

object prefix_F {
  object F extends FabricAttrs
}

@js.native
@JSImport("@uifabric/icons", JSImport.Namespace)
object uifabric_icons extends js.Object {
  def initializeIcons(): Unit = js.native
}

// Import fabric components. This imports *everything* which is not what we usually want. */
@js.native
@JSImport("office-ui-fabric-react", JSImport.Namespace)
object FabricNS extends js.Object {
  val Fabric: ReactJsComponent = js.native
  val TextField: ReactJsComponent = js.native
  val Label: ReactJsComponent = js.native
  val PrimaryButton: ReactJsComponent = js.native
  val DefaultButton: ReactJsComponent = js.native
  val Pivot: ReactJsComponent = js.native
  val PivotItem: ReactJsComponent = js.native
  val DetailsList: ReactJsComponent = js.native
  val CommandBar: ReactJsComponent = js.native
  val Spinner: ReactJsComponent = js.native
  def Selection[T <: js.Object]: Selection[T] = js.native
}

/**
  * We could also define some native traits for the args...but that's more work...
  */
object components {
  import ttg.react.elements.wrapJsForScala

  def Fabric(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.Fabric, new Attrs(props).toJs, children: _*)
  def TextField(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.TextField, new Attrs(props).toJs, children: _*)

  def Label(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.Label, new Attrs(props).toJs, children: _*)
  def Label(props: LabelProps = noProps())(children: ReactNode*) = wrapJsForScala(FabricNS.Label, props, children: _*)
  def Label(props: LabelProps2)(children: ReactNode*) = wrapJsForScala(FabricNS.Label, props, children: _*)

  def PrimaryButton(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.PrimaryButton, new Attrs(props).toJs, children: _*)
  def DefaultButton(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.DefaultButton, new Attrs(props).toJs, children: _*)

  def Pivot(props: PivotProps = noProps())(children: ReactNode*) = wrapJsForScala(FabricNS.Pivot, props, children: _*)
  def PivotItem(props: PivotItemProps = noProps())(children: ReactNode*) = wrapJsForScala(FabricNS.PivotItem, props, children: _*)

  def DetailsList[T <: js.Object](props: IDetailsListProps[T] = noProps())(children: ReactNode*) =
    wrapJsForScala(FabricNS.DetailsList, props, children: _*)

  def CommandBar(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.CommandBar, new Attrs(props).toJs, children: _*)
  def CommandBar(props: ICommandBarProps = noProps())(children: ReactNode*) =
    wrapJsForScala(FabricNS.CommandBar, props, children: _*)
  //def CommandBar(props: js.UndefOr[js.Dynamic])(children: ReactNode*) =  wrapJsForScala(FabricNS.CommandBar, props.getOrElse(noProps), children: _*)

  def Spinner(props: ISpinnerProps = noProps())(children: ReactNode*) = wrapJsForScala(FabricNS.Spinner, props, children: _*)
}

@js.native
trait Focusable extends js.Object {
  def focus(): Unit = js.native
}

trait KeyAndRef extends js.Object {
  var key: js.UndefOr[String] = js.undefined
  var ref: js.UndefOr[RefCb] = js.undefined
}

// trait
trait LabelProps extends LabelHTMLAttributes[dom.html.Label] {
  var componentRef: js.UndefOr[js.Any] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var theme: js.UndefOr[js.Any] = js.undefined
}

// class, but only some vars are easy
class LabelProps2(
    var disabled: js.UndefOr[Boolean] = js.undefined
) extends LabelHTMLAttributes[dom.html.Label]

trait PivotItemProps extends HTMLAttributes[dom.html.Div] {
  var linkText: js.UndefOr[String] = js.undefined
  var itemKey: js.UndefOr[String] = js.undefined
  var itemCount: js.UndefOr[Int] = js.undefined
  var itemIcon: js.UndefOr[String] = js.undefined
  var ariaLabel: js.UndefOr[String] = js.undefined
  var onRenderItemLink: js.UndefOr[IRenderFunction[PivotItemProps]] = js.undefined
}

trait PivotProps extends js.Object {
  var initialSelectedKey: js.UndefOr[String] = js.undefined
  var initialSelectedIndex: js.UndefOr[Int] = js.undefined
  var selectedKey: js.UndefOr[String] = js.undefined
  var onLinkClick: js.UndefOr[((js.Object, js.UndefOr[ReactMouseEvent[dom.html.Element]]) => Unit) |(() => Unit)] = js.undefined
}

@js.native
trait ITextField extends ReactRef with Focusable {
  var value: js.UndefOr[String] = js.native
}

@js.native
trait IDetailsList extends js.Object {
  def forceUpdate(): Unit = js.native
}

trait IColumn extends js.Object {
  var key: String
  var name: String
  var fieldName: String
  var minWidth: Int
  var data: js.UndefOr[scala.Any] = js.undefined
}

object IColumn {
  def toCol(a: js.Dynamic): IColumn = a.asInstanceOf[IColumn]
}

trait IDetailsListProps[T <: js.Object] extends js.Object {
  val items: js.Array[T]
  var componentRef: js.UndefOr[IDetailsList => Unit] = js.undefined
  var setKey: js.UndefOr[String] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var columns: js.UndefOr[js.Array[IColumn] | js.Array[js.Object] | js.Array[js.Dynamic]] = js.undefined
  var listProps: js.UndefOr[js.Dynamic] = js.undefined
  var getKey: js.UndefOr[js.Function2[T, js.UndefOr[Int], String | Int] | js.Function1[T, String | Int]] = js.undefined
  var selection: js.UndefOr[ISelection[T]] = js.undefined
  var selectionPreservedOnEmptyClick: js.UndefOr[Boolean] = js.undefined
}

trait IContextualMenuProps extends KeyAndRef {
  var items: js.Array[IContextualMenuItem]
}

trait IIconProps extends js.Object {
  var iconName: js.UndefOr[String] = js.undefined
}

trait IContextualMenuItem extends js.Object {
  val key: String
  var name: js.UndefOr[String] = js.undefined

  /** 1 => divider, 0 => normal */
  var itemType: js.UndefOr[Int] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  type OC2 = js.Function2[ReactMouseEvent[dom.html.Element], js.UndefOr[IContextualMenuItem], Unit]
  type OC0 = js.Function0[Unit]
  var onClick: js.UndefOr[OC2|OC0] = js.undefined
  var subMenuProps: js.UndefOr[IContextualMenuProps] = js.undefined
  var iconProps: js.UndefOr[IIconProps | js.Dynamic] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var title: js.UndefOr[String] = js.undefined
  // cast your func to this to make it easier
  type RF2 = js.Function2[js.Object, js.Function2[js.Any, js.UndefOr[Boolean], Unit], ReactNode]
  // cast your func to this to make it easier
  type RF0 = js.Function0[ReactNode]  
  var onRender: js.UndefOr[RF2|RF0] = js.undefined
}

@js.native
trait ICommandBar extends Focusable {}

trait ICommandBarProps extends js.Object {
  var componentRef: js.UndefOr[ICommandBar => Unit] = js.undefined
  var isSearchBoxVisble: js.UndefOr[Boolean] = js.undefined
  var searchBoxPlaceholderText: js.UndefOr[String] = js.undefined
  val items: js.Array[IContextualMenuItem]
  var farItems: js.UndefOr[js.Array[IContextualMenuItem]] = js.undefined
}

@js.native
trait ISpinner extends js.Object {}

trait ISpinnerProps extends KeyAndRef {
  var componentRef: js.UndefOr[ISpinner => Unit] = js.undefined
  var size: js.UndefOr[Int] = js.undefined
  var label: js.UndefOr[String] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var ariaLabel: js.UndefOr[String] = js.undefined
}

object SpinnerSize {
  var xSmall = 0
  var small = 1
  var medium = 2
  var large = 3
}

@js.native
trait IObjectWithKey extends js.Object {
  var key: String = js.native
}

@js.native
trait ISelection[T <: js.Object] extends js.Object {
  val count: Int
  def getItems(): js.Array[T]
  def setItems(items: js.Array[T], shouldClear: Boolean): Unit
  def setKeySelected(key: String, isSelected: Boolean, shouldAnchor: Boolean): Unit
  def setChangeEvents(isEnabled: Boolean, suppressChange: js.UndefOr[Boolean] = js.undefined): Unit
  def toggleAllSelected(): Unit
  def setModal(isModal: Boolean): Unit
  def isModel(): Boolean
  def getSelection(): js.Array[T]
}

object SelectionMode {
  val none = 0
  val single = 1
  val multiple = 2
}

trait ISelectionOptions[T <: js.Object] extends js.Object {
  var getKey: js.UndefOr[js.Function2[T, js.UndefOr[Int], String | Int] | js.Function1[T, String | Int]] = js.undefined
  var selectionMode: js.UndefOr[Int] = js.undefined
  var onSelectionChanged: js.UndefOr[js.Function0[Unit]] = js.undefined
}

@js.native
@JSImport("office-ui-fabric-react/lib/utilities/selection", "Selection")
class Selection[T <: js.Object](options: js.UndefOr[ISelectionOptions[T]] = js.undefined) extends js.Object with ISelection[T] {
  val count: Int = js.native
  def getItems(): js.Array[T] = js.native
  def setItems(items: js.Array[T], shouldClear: Boolean): Unit = js.native
  def setKeySelected(key: String, isSelected: Boolean, shouldAnchor: Boolean): Unit = js.native
  def setChangeEvents(isEnabled: Boolean, suppressChange: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def toggleAllSelected(): Unit = js.native
  def setModal(isModal: Boolean): Unit = js.native
  def isModel(): Boolean = js.native
  def getSelection(): js.Array[T] = js.native
}

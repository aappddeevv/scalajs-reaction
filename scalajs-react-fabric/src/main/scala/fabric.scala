// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
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
  val Fabric: ReactClass = js.native
  val TextField: ReactClass = js.native
  val Label: ReactClass = js.native
  val PrimaryButton: ReactClass = js.native
  val DefaultButton: ReactClass = js.native
  val Pivot: ReactClass = js.native
  val PivotItem: ReactClass = js.native
  val DetailsList: ReactClass = js.native
  val CommandBar: ReactClass = js.native
  val Spinner: ReactClass = js.native
}

/**
  * We could also define some native traits for the args...but that's more work...
  */
object components {
  import ttg.react.elements.wrapJsForScala

  def Fabric(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.Fabric, new Attrs(props).toJs, children: _*)
  def TextField(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.TextField, new Attrs(props).toJs, children: _*)

  def Label(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.Label, new Attrs(props).toJs, children: _*)
  def Label(props: js.UndefOr[LabelProps])(children: ReactNode*) = wrapJsForScala(FabricNS.Label, props.getOrElse(noProps), children: _*)
  def Label(props: LabelProps2)(children: ReactNode*) = wrapJsForScala(FabricNS.Label, props, children: _*)

  def PrimaryButton(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.PrimaryButton, new Attrs(props).toJs, children: _*)
  def DefaultButton(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.DefaultButton, new Attrs(props).toJs, children: _*)
  def Pivot(props: js.UndefOr[PivotProps])(children: ReactNode*) = wrapJsForScala(FabricNS.Pivot, props.getOrElse(noProps), children: _*)
  def PivotItem(props: js.UndefOr[PivotItemProps])(children: ReactNode*) = wrapJsForScala(FabricNS.PivotItem, props.getOrElse(noProps), children: _*)
  def DetailsList(props: js.UndefOr[IDetailsListProps])(children: ReactNode*) = wrapJsForScala(FabricNS.DetailsList, props.getOrElse(noProps), children: _*)

  def CommandBar(props: Attr*)(children: ReactNode*) = wrapJsForScala(FabricNS.CommandBar, new Attrs(props).toJs, children: _*)
  def CommandBar(props: js.UndefOr[ICommandBarProps])(children: ReactNode*) = wrapJsForScala(FabricNS.CommandBar, props.getOrElse(noProps), children: _*)
  //def CommandBar(props: js.UndefOr[js.Dynamic])(children: ReactNode*) =  wrapJsForScala(FabricNS.CommandBar, props.getOrElse(noProps), children: _*)

  def Spinner(props: js.UndefOr[ISpinnerProps])(children: ReactNode*) = wrapJsForScala(FabricNS.Spinner, props.getOrElse(noProps), children: _*)
}
@js.native
trait Focusable extends js.Object {
  def focus(): Unit = js.native
}

trait KeyAndRef extends js.Object {
  val key: js.UndefOr[String] = js.undefined
  val ref: js.UndefOr[RefCb] = js.undefined
}

// trait
trait LabelProps extends LabelHTMLAttributes[dom.html.Label] {
  val componentRef: js.UndefOr[js.Any] = js.undefined
  val disabled: js.UndefOr[Boolean] = js.undefined
  val theme: js.UndefOr[js.Any] = js.undefined
}

// class, but only some vars are easy
class LabelProps2(
    val disabled: js.UndefOr[Boolean] = js.undefined
) extends LabelHTMLAttributes[dom.html.Label]

trait PivotItemProps extends HTMLAttributes[dom.html.Div] {
  val linkText: js.UndefOr[String] = js.undefined
  val itemKey: js.UndefOr[String] = js.undefined
  val itemCount: js.UndefOr[Int] = js.undefined
}

trait PivotProps extends js.Object {
  val initialSelectedKey: js.UndefOr[String] = js.undefined
  val initialSelectedIndex: js.UndefOr[Int] = js.undefined
  val selectedKey: js.UndefOr[String] = js.undefined
  val onLinkClick: js.UndefOr[((js.Object, js.UndefOr[ReactMouseEvent[dom.html.Element]]) => Unit) |(() => Unit)] = js.undefined
}

@js.native
trait ITextField extends ReactRef with Focusable {
  val value: js.UndefOr[String] = js.native
}

@js.native
trait IDetailsList extends js.Object {
  def forceUpdate(): Unit = js.native
}

trait IColumn extends js.Object {
  val key: String
  val name: String
  val fieldName: String
  val minWidth: Int
  val data: js.UndefOr[scala.Any] = js.undefined
}

object IColumn {
  def toCol(a: js.Dynamic): IColumn = a.asInstanceOf[IColumn]
}

trait IDetailsListProps extends js.Object {
  val componentRef: js.UndefOr[IDetailsList => Unit] = js.undefined
  val setKey: js.UndefOr[String] = js.undefined
  val className: js.UndefOr[String] = js.undefined
  val columns: js.UndefOr[js.Array[IColumn] | js.Array[js.Object] | js.Array[js.Dynamic]] = js.undefined
  val items: js.Array[js.Object]
  val listProps: js.UndefOr[js.Dynamic] = js.undefined
}

trait IContextualMenuProps extends KeyAndRef {
  val items: js.Array[IContextualMenuItem]
}

trait IIconProps extends js.Object {
  val iconName: js.UndefOr[String] = js.undefined
}

trait IContextualMenuItem extends js.Object {
  val key: String
  val name: js.UndefOr[String] = js.undefined

  /** 1 => divider, 0 => normal */
  val itemType: js.UndefOr[Int] = js.undefined
  val disabled: js.UndefOr[Boolean] = js.undefined
  val onClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], js.UndefOr[IContextualMenuItem], Unit] | js.Function0[Unit]] = js.undefined
  val subMenuProps: js.UndefOr[IContextualMenuProps] = js.undefined
  val iconProps: js.UndefOr[IIconProps | js.Dynamic] = js.undefined
}

@js.native
trait ICommandBar extends Focusable {}

trait ICommandBarProps extends js.Object {
  val componentRef: js.UndefOr[ICommandBar => Unit] = js.undefined
  val isSearchBoxVisble: js.UndefOr[Boolean] = js.undefined
  val searchBoxPlaceholderText: js.UndefOr[String] = js.undefined
  val items: js.Array[IContextualMenuItem]
  val farItems: js.Array[IContextualMenuItem]
}

@js.native
trait ISpinner extends js.Object {}

trait ISpinnerProps extends KeyAndRef {
  val componentRef: js.UndefOr[ISpinner => Unit] = js.undefined
  val size: js.UndefOr[Int] = js.undefined
  val label: js.UndefOr[String] = js.undefined
  val className: js.UndefOr[String] = js.undefined
  val ariaLabel: js.UndefOr[String] = js.undefined
}

object SpinnerSize {
  val xSmall = 0
  val small = 1
  val medium = 2
  val large = 3
}

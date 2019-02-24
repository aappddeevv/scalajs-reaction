// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|

import ttg.react.vdom._
import org.scalajs.dom
import fabric.styling
import styling._

@js.native
@JSImport("@uifabric/icons", JSImport.Namespace)
object uifabric_icons extends js.Object {
  def initializeIcons(): Unit = js.native
}

/** Provide a focs() method. */
@js.native
trait Focusable extends js.Object {
  def focus(): Unit = js.native
}

/** Focus method but returns a Boolean. */
@js.native
trait Focusable2 extends js.Object {
  def focus(): Boolean = js.native
}

trait IViewportProps extends js.Object {
  var skipViewportMeasures: js.UndefOr[Boolean] = js.undefined
}

/** Ref is kept js.Any since this "input" */
trait KeyAndRef extends js.Object {
  var key: js.UndefOr[String] = js.undefined
  var ref: js.UndefOr[Ref[js.Any]]  = js.undefined
}

/** Adds a componentRef member. */
trait ComponentRef[T] extends js.Object {
  var componentRef: js.UndefOr[js.Function1[T, Unit]] = js.undefined
}

/** Adds a disabled member. */
trait Disabled extends js.Object {
  var disabled: js.UndefOr[Boolean] = js.undefined
}

/** Add a theme member. */
trait Theme extends js.Object {
  var theme: js.UndefOr[ITheme] = js.undefined
}

/** Adds an iconProps member. */
trait WithIconProps extends js.Object {
  var iconProps: js.UndefOr[IIconProps | js.Dynamic] = js.undefined
}

trait ClassName extends js.Object {
  var className: js.UndefOr[String] = js.undefined
}

trait IColumn extends js.Object {
  import IColumn._
  val key: String
  val name: String
  val minWidth: Int  
  var fieldName: js.UndefOr[String] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var ariaLabel: js.UndefOr[String] = js.undefined
  var isRowHeader: js.UndefOr[Boolean] = js.undefined
  var maxwidth: js.UndefOr[Double] = js.undefined
  var columnActionMode: js.UndefOr[Int] = js.undefined
  var iconName: js.UndefOr[String] = js.undefined
  var isIconOnly: js.UndefOr[Boolean] = js.undefined
  var iconClassName: js.UndefOr[String] = js.undefined
  var isCollapsible: js.UndefOr[Boolean] = js.undefined
  var isSorted: js.UndefOr[Boolean]      = js.undefined
  var isSortedDescending: js.UndefOr[Boolean] = js.undefined
  var isMultiline: js.UndefOr[Boolean]      = js.undefined
  // adjust so we can delayr item's type vs js.Any 
  //def onRender[T <: js.Object]: js.UndefOr[js.Function3[T, Int, IColumn, js.Any]] = js.undefined
  var onRender: js.UndefOr[OnRender] = js.undefined
  // onRenderDivider
  var isFiltered: js.UndefOr[Boolean]      = js.undefined
  // onColumnClick
  // onColumnContextMenu
  var isGrouped: js.UndefOr[Boolean]      = js.undefined
  var data: js.UndefOr[scala.Any]        = js.undefined
  var headerClassName: js.UndefOr[String] = js.undefined
  var isPadded: js.UndefOr[Boolean]      = js.undefined
  var sortAscendingAriaLabel: js.UndefOr[String] = js.undefined
  var groupAriaLabel: js.UndefOr[String] = js.undefined
  var filterAriaLabel: js.UndefOr[String] = js.undefined
}

object IColumn {
  def toCol(a: js.Dynamic): IColumn = a.asInstanceOf[IColumn]
  type OnRender = js.Function3[js.Any, Int, IColumn, js.Any]
}

trait IColumnReorderOptions extends js.Object {
  var frozenColumnCountFromStart: js.UndefOr[Int] = js.undefined
  var frozencolumnCountFromEnd:js.UndefOr[Int] = js.undefined
  var onColumnDragStart: js.UndefOr[js.Function1[Boolean, Unit]] = js.undefined
  var handleColumnReorder: js.UndefOr[js.Function2[Int, Int, Unit]] = js.undefined
  var onColumnDrop: js.UndefOr[js.Function1[IColumnDragDropDetails, Unit]] = js.undefined
  // ColumnDragEndLocation
  var onDragEnd: js.UndefOr[js.Function1[Int, Unit]] = js.undefined
}

@js.native
sealed trait ColumnActionMode extends js.Any
object ColumnActionsMode {
  val disabled = 0.asInstanceOf[ColumnActionMode]
  val clickable = 1.asInstanceOf[ColumnActionMode]
  val hasDropdown = 2.asInstanceOf[ColumnActionMode]
}

@js.native
sealed trait ColumnDragEndLocation extends js.Any
object ColumnDragEndLocation {
  val outside = 0.asInstanceOf[ColumnDragEndLocation]
  val surface = 1.asInstanceOf[ColumnDragEndLocation]
  val header = 2.asInstanceOf[ColumnDragEndLocation]
}

trait IColumnDragDropDetails extends js.Object {
  val draggedIndex: Int
  val targetIndex: Int
}

@js.native
sealed trait CheckboxVisibility extends js.Any
object CheckboxVisibility {
  val onHover = 0.asInstanceOf[CheckboxVisibility]
  val always  = 1.asInstanceOf[CheckboxVisibility]
  val hidden  = 2.asInstanceOf[CheckboxVisibility]
}

@js.native
trait IDropHintDetails extends js.Object {
  var originX: js.UndefOr[Double] = js.undefined
  var startX: js.UndefOr[Double] = js.undefined
  var endX: js.UndefOr[Double] = js.undefined
  var dropHintElementRef: js.UndefOr[dom.html.Element] = js.undefined
}

@js.native
trait IColumnResizeDetails extends js.Object {
  var  columnIndex: Double = js.native
  var  originX: js.UndefOr[Double] = js.native
  var  columnMinWidth: Double = js.native
}

trait IContextualMenuProps extends KeyAndRef {
  val items: js.Array[IContextualMenuItem]
}

trait IContextualMenuItem extends WithIconProps {
  import IContextualMenuItem._
  val key: String
  var name: js.UndefOr[String] = js.undefined

  /** 1 => divider, 0 => normal */
  var itemType: js.UndefOr[Int]     = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  //var onClick: js.UndefOr[OC2 | OC0]                 = js.undefined
  var onClick: js.UndefOr[OC2|OC0]                 = js.undefined
  @JSName("onClick")
  var onEmptyClick: js.UndefOr[OC0] = js.undefined
  var subMenuProps: js.UndefOr[IContextualMenuProps] = js.undefined
  var className: js.UndefOr[String]                  = js.undefined
  var title: js.UndefOr[String]                      = js.undefined
  var onRender: js.UndefOr[RF2 | RF0] = js.undefined
}

object IContextualMenuItem {
  type OC2 = js.Function2[ReactMouseEvent[dom.html.Element], js.UndefOr[IContextualMenuItem], Unit]
  type OC0 = js.Function0[Unit]

  // cast your func to this to make it easier
  type RF2 = js.Function2[js.Object, js.Function2[js.Any, js.UndefOr[Boolean], Unit], ReactNode]
  // cast your func to this to make it easier
  type RF0 = js.Function0[ReactNode]
}

@js.native
trait IObjectWithKey extends js.Object {
  var key: String = js.native
}

@js.native
sealed trait IconType extends js.Any
object IconType {
  val default = 0.asInstanceOf[IconType]
  val image   = 1.asInstanceOf[IconType]
}

// export interface IIconStyles {
//   root?: IStyle;
//   rootHasPlaceHolder?: IStyle;
//   imageContainer?: IStyle;
// }

trait IIconProps extends HTMLAttributes[dom.html.Element] {
  var iconName: js.UndefOr[String] = js.undefined
  //var styles?: IIconStyles;
  var ariaLabel: js.UndefOr[String] = js.undefined
  var iconType: js.UndefOr[Int]     = js.undefined //?: IconType;
  //imageProps?: IImageProps;
}


@js.native
trait IBasePicker[T] extends Focusable {
  var items: js.UndefOr[js.Array[T]] = js.undefined
  var focusInput: js.Function0[Unit]
}

trait IInputProps extends InputHTMLAttributes[dom.html.Input] {
  //var `aria-label`: js.UndefOr[String] = js.undefined
}

@js.native
sealed trait ValidationState extends js.Any
object ValidationState {
  val valid   = 0.asInstanceOf[ValidationState]
  val warning = 1.asInstanceOf[ValidationState]
  val invalid = 2.asInstanceOf[ValidationState]
}

trait IBasePickerProps[T <: js.Object] extends ComponentRef[IBasePicker[T] | Null] {
  var defaultSelectedItems: js.UndefOr[js.Array[T]]         = js.undefined
  var onChange: js.UndefOr[js.Function1[js.Array[T], Unit]] = js.undefined
  //onFocus:   onFocus?: React.FocusEventHandler<HTMLInputElement | BaseAutoFill>;
  var onFocus: js.UndefOr[FocusEventHandler[dom.html.Input]] = js.undefined
  //onBlur:   onBlur?: React.FocusEventHandler<HTMLInputElement | BaseAutoFill>;
  var onBlur: js.UndefOr[FocusEventHandler[dom.html.Input]]        = js.undefined
  var getTextFromItem: js.UndefOr[js.Function2[T, String, String]] = js.undefined
  var className: js.UndefOr[String]                                = js.undefined
  var inputProps: js.UndefOr[IInputProps]                          = js.undefined

  /** ValdationState */
  var onValidateInput: js.UndefOr[js.Function1[String, ValidationState]] = js.undefined
  var disabled: js.UndefOr[Boolean]                          = js.undefined
  var itemLimit: js.UndefOr[Int]                             = js.undefined
  var selectedItems: js.UndefOr[js.Array[T]]                 = js.undefined
}

trait IBasePickerSuggestionsProps extends js.Object {
  var suggestionsHeaderText: js.UndefOr[String]      = js.undefined
  var mostRecentlyUsedHeaderText: js.UndefOr[String] = js.undefined
  var noResultsFoundText: js.UndefOr[String]         = js.undefined
  var suggestionsClassName: js.UndefOr[String]       = js.undefined
  var suggestionsItemClassName: js.UndefOr[String]   = js.undefined
  var searchForMoreText: js.UndefOr[String]          = js.undefined
  var forceResolveText: js.UndefOr[String]           = js.undefined
  var loadingText: js.UndefOr[String]                = js.undefined
  var searchingText: js.UndefOr[String]              = js.undefined
  var resultMaximumNumber: js.UndefOr[Int]           = js.undefined
  var showRemoveButtons: js.UndefOr[Boolean]         = js.undefined
}

trait ISelectableDroppableTextProps[T <: dom.html.Element]
    extends HTMLAttributes[T]
    with ComponentRef[T] {
  var label: js.UndefOr[String] = js.undefined
  // these are in HTMLAttributes I think
  // ariaLabel?: string;
  // id?: string;
  // className?: string;

  var defaultSelectedKey: js.UndefOr[String | Int | js.Array[String] | js.Array[Int]] = js.undefined
  var selectedKey: js.UndefOr[String | Int | js.Array[String] | js.Array[Int]]        = js.undefined

  /** Any ??? */
  var options: js.UndefOr[js.Any] = js.undefined
  // onChanged?: (option: ISelectableOption, index?: number) => void;
  // onRenderContainer?: IRenderFunction<ISelectableDroppableTextProps<T>>;
  var onRenderList: js.UndefOr[IRenderFunction[ISelectableDroppableTextProps[T]]] = js.undefined
  // onRenderItem?: IRenderFunction<ISelectableOption>;
  // onRenderOption?: IRenderFunction<ISelectableOption>;
  var disabled: js.UndefOr[Boolean] = js.undefined
  var required: js.UndefOr[Boolean] = js.undefined

  //calloutProps?: ICalloutProps;
  var panelProps: js.UndefOr[Panel.Props] = js.undefined
  var errorMessage: js.UndefOr[String]    = js.undefined
}

trait ISelectableOption extends js.Object {
  var key: String | Int
  var text: String
  var itemType: js.UndefOr[SelectableOptionMenuItemType]     = js.undefined
  var index: js.UndefOr[Int]        = js.undefined
  var ariaLabel: js.UndefOr[String] = js.undefined
  var selected: js.UndefOr[Boolean] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
}

@js.native
sealed trait SelectableOptionMenuItemType extends js.Any
object SelectableOptionMenuItemType {
  val Normal  = 0.asInstanceOf[SelectableOptionMenuItemType]
  val Divider = 1.asInstanceOf[SelectableOptionMenuItemType]
  val Header  = 2.asInstanceOf[SelectableOptionMenuItemType]
}

trait IDropdownOption extends ISelectableOption {
  var data: js.UndefOr[js.Any]        = js.undefined
  var isSelected: js.UndefOr[Boolean] = js.undefined
}

trait IKeytipProps extends js.Object {
  val content: String
  var disabled: js.UndefOr[Boolean] = js.undefined
  var visible: js.UndefOr[Boolean] = js.undefined
  val keySeqences: js.Array[String]
}


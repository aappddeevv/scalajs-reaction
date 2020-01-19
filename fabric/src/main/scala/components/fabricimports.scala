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

import js.Dynamic.literal
import js.annotation._
import js.|

import org.scalajs.dom

import react._
import react.vdom._

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
  var key: js.UndefOr[String]      = js.undefined
  var ref: js.UndefOr[Ref[js.Any]] = js.undefined
}

/** Adds a componentRef member. */
trait ComponentRef[T] extends js.Object {

  /** Callback style, the easiest to handle. */
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

// move to DetailsColumn component
trait IDetailsColumnStyleProps extends js.Object {
  var headerClassName: js.UndefOr[String]         = js.undefined
  var isActionable: js.UndefOr[Boolean]           = js.undefined
  var isEmpty: js.UndefOr[Boolean]                = js.undefined
  var isIconVisible: js.UndefOr[Boolean]          = js.undefined
  var isPadded: js.UndefOr[Boolean]               = js.undefined
  var isIconOnly: js.UndefOr[Boolean]             = js.undefined
  var iconClassName: js.UndefOr[Boolean]          = js.undefined
  var transitionDurationDrag: js.UndefOr[Boolean] = js.undefined
  var transitionDurationDrop: js.UndefOr[Boolean] = js.undefined
}

// move to DetailsColumn component
trait IDetailsColumnStyles extends IStyleSetTag {
  var root: js.UndefOr[IStyle]                    = js.undefined
  var gripperBarVerticalStyle: js.UndefOr[IStyle] = js.undefined
  var cellTooltip: js.UndefOr[IStyle]             = js.undefined
  var cellTitle: js.UndefOr[IStyle]               = js.undefined
  var cellName: js.UndefOr[IStyle]                = js.undefined
  var iconClassName: js.UndefOr[IStyle]           = js.undefined
  var nearIcon: js.UndefOr[IStyle]                = js.undefined
  var accessibleLabel: js.UndefOr[IStyle]         = js.undefined
  var sortIcon: js.UndefOr[IStyle]                = js.undefined
  var filterChevron: js.UndefOr[IStyle]           = js.undefined
  var borderAfterDropping: js.UndefOr[IStyle]     = js.undefined
  var noBorderAfterDropping: js.UndefOr[IStyle]   = js.undefined
  var borderWhileDragging: js.UndefOr[IStyle]     = js.undefined
  var noBorderWhileDragging: js.UndefOr[IStyle]   = js.undefined
}

trait IColumnBase extends js.Object {
  import IColumn._
  var ariaLabel: js.UndefOr[String]                  = js.undefined
  var className: js.UndefOr[String]                  = js.undefined
  var data: js.UndefOr[scala.Any]                    = js.undefined
  var columnActionMode: js.UndefOr[ColumnActionMode] = js.undefined
  var fieldName: js.UndefOr[String]                  = js.undefined
  var filterAriaLabel: js.UndefOr[String]            = js.undefined
  var groupAriaLabel: js.UndefOr[String]             = js.undefined
  var headerClassName: js.UndefOr[String]            = js.undefined
  var isRowHeader: js.UndefOr[Boolean]               = js.undefined
  var iconName: js.UndefOr[String]                   = js.undefined
  var isIconOnly: js.UndefOr[Boolean]                = js.undefined
  var iconClassName: js.UndefOr[String]              = js.undefined
  var isCollapsible: js.UndefOr[Boolean]             = js.undefined
  var isGrouped: js.UndefOr[Boolean]                 = js.undefined
  var isSorted: js.UndefOr[Boolean]                  = js.undefined
  var isSortedDescending: js.UndefOr[Boolean]        = js.undefined
  var isMultiline: js.UndefOr[Boolean]               = js.undefined
  var isResizable: js.UndefOr[Boolean]               = js.undefined
  var isPadded: js.UndefOr[Boolean]                  = js.undefined
  var isFiltered: js.UndefOr[Boolean]                = js.undefined
  // adjust so we can delayr item's type vs js.Any
  //def onRender[T <: js.Object]: js.UndefOr[js.Function3[T, Int, IColumn, js.Any]] = js.undefined
  var maxwidth: js.UndefOr[Double]                                                               = js.undefined
  var onRender: js.UndefOr[OnRender]                                                             = js.undefined
  var sortAscendingAriaLabel: js.UndefOr[String]                                                 = js.undefined
  var styles: js.UndefOr[IStyleFunctionOrObject[IDetailsColumnStyleProps, IDetailsColumnStyles]] = js.undefined
  // onRenderDivider
  // onColumnClick
  // onColumnContextMenu
}

trait IColumnInit extends IColumnBase {
  var key: js.UndefOr[String]   = js.undefined
  var name: js.UndefOr[String]  = js.undefined
  var minWidth: js.UndefOr[Int] = js.undefined
}

trait IColumn extends IColumnBase {
  val key: String
  val name: String
  val minWidth: Int
}

object IColumn {
  def toCol(a: js.Dynamic): IColumn = a.asInstanceOf[IColumn]
  type OnRender = js.Function3[js.Any, Int, IColumn, js.Any]
}

object IColumnInit {
  implicit class RichIColumn private[IColumnInit] (private val c: IColumnInit) extends AnyVal {
    def required(key: String, name: String, minWidth: Int) =
      react.merge[IColumn](
        c,
        literal(
          "key"      -> key,
          "name"     -> name,
          "minWidth" -> minWidth
        )
      )
    // we could probably put a check into this but
    // that would be a runtime check not static!
    // so we might as well skip it...
    def hasRequired = c.asInstanceOf[IColumn]
  }
}

trait IColumnReorderOptions extends js.Object {
  var frozenColumnCountFromStart: js.UndefOr[Int]                          = js.undefined
  var frozencolumnCountFromEnd: js.UndefOr[Int]                            = js.undefined
  var onColumnDragStart: js.UndefOr[js.Function1[Boolean, Unit]]           = js.undefined
  var handleColumnReorder: js.UndefOr[js.Function2[Int, Int, Unit]]        = js.undefined
  var onColumnDrop: js.UndefOr[js.Function1[IColumnDragDropDetails, Unit]] = js.undefined
  // ColumnDragEndLocation
  var onDragEnd: js.UndefOr[js.Function1[Int, Unit]] = js.undefined
}

@js.native
sealed trait ColumnActionMode extends js.Any
object ColumnActionsMode {
  val disabled    = 0.asInstanceOf[ColumnActionMode]
  val clickable   = 1.asInstanceOf[ColumnActionMode]
  val hasDropdown = 2.asInstanceOf[ColumnActionMode]
}

@js.native
sealed trait ColumnDragEndLocation extends js.Any
object ColumnDragEndLocation {
  val outside = 0.asInstanceOf[ColumnDragEndLocation]
  val surface = 1.asInstanceOf[ColumnDragEndLocation]
  val header  = 2.asInstanceOf[ColumnDragEndLocation]
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
  var originX: js.UndefOr[Double]                      = js.undefined
  var startX: js.UndefOr[Double]                       = js.undefined
  var endX: js.UndefOr[Double]                         = js.undefined
  var dropHintElementRef: js.UndefOr[dom.html.Element] = js.undefined
}

@js.native
trait IColumnResizeDetails extends js.Object {
  var columnIndex: Double         = js.native
  var originX: js.UndefOr[Double] = js.native
  var columnMinWidth: Double      = js.native
}

trait IContextualMenuProps extends KeyAndRef {
  val items: js.Array[IContextualMenuItem]
}

trait IContextualMenuItemBase extends WithIconProps {
  import IContextualMenuItem._
  var name: js.UndefOr[String] = js.undefined

  /** 1 => divider, 0 => normal */
  var itemType: js.UndefOr[Int]     = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  //var onClick: js.UndefOr[OC2 | OC0]                 = js.undefined
  var onClick: js.UndefOr[OC2 | OC0] = js.undefined
  @JSName("onClick")
  var onEventItemClick: js.UndefOr[OC2] = js.undefined
  @JSName("onClick")
  var onEmptyClick: js.UndefOr[OC0]                  = js.undefined
  var subMenuProps: js.UndefOr[IContextualMenuProps] = js.undefined
  var className: js.UndefOr[String]                  = js.undefined
  var title: js.UndefOr[String]                      = js.undefined
  var onRender: js.UndefOr[RF2 | RF0]                = js.undefined
}

trait IContextualMenuItemInit extends IContextualMenuItemBase {
  var key: js.UndefOr[String] = js.undefined
}

object IContextualMenuItemInit {
  implicit class RichObject(item: IContextualMenuItemInit) {
    def required(name: String) = react.merge[IContextualMenuItem](item, literal("key" -> name))
    def hasRequired            = item.asInstanceOf[IContextualMenuItem]
  }
}

trait IContextualMenuItem extends IContextualMenuItemBase {
  val key: String
}

object IContextualMenuItem {

  /** Callback with the react synthetic event. OC = onClick. */
  type OC2 = js.Function2[ReactMouseEvent[dom.html.Element], js.UndefOr[IContextualMenuItem], Unit]

  /** Parameterless callback. OC = onClick. */
  type OC0 = js.Function0[Unit]

  // cast your func to this to make it easier
  type RF2 = js.Function2[js.Object, js.Function2[js.Any, js.UndefOr[Boolean], Unit], ReactNode]
  // cast your func to this to make it easier
  type RF0 = js.Function0[ReactNode]
}

// from @uifabric/utilities
@js.native
trait IObjectWithKey extends js.Object {
  var key: String | Number = js.native
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
  var ariaLabel: js.UndefOr[String]       = js.undefined
  var iconType: js.UndefOr[Int]           = js.undefined //?: IconType;
  var imageProps: js.UndefOr[Image.Props] = js.undefined
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
  var disabled: js.UndefOr[Boolean]                                      = js.undefined
  var itemLimit: js.UndefOr[Int]                                         = js.undefined
  var selectedItems: js.UndefOr[js.Array[T]]                             = js.undefined
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

trait ISelectableDroppableTextProps[T <: dom.html.Element] extends HTMLAttributes[T] with ComponentRef[T] {
  var label: js.UndefOr[String] = js.undefined
  // these are in HTMLAttributes I think
  // ariaLabel?: string;
  // id?: string;
  // className?: string;

  var defaultSelectedKey: js.UndefOr[String | Int | js.Array[String] | js.Array[Int]] = js.undefined
  // does this make it controlled?
  var selectedKey: js.UndefOr[String | Int | js.Array[String] | js.Array[Int] | Null] = js.undefined

  /** Any ??? needs to have key & text, use structural type? ISelectableOption?? */
  var options: js.UndefOr[ISelectableOption | js.Any] = js.undefined
  // onChanged?: (option: ISelectableOption, index?: number) => void;
  // onRenderContainer?: IRenderFunction<ISelectableDroppableTextProps<T>>;
  var onRenderList: js.UndefOr[IRenderFunction[ISelectableDroppableTextProps[T]]] = js.undefined
  // onRenderItem?: IRenderFunction<ISelectableOption>;
  // onRenderOption?: IRenderFunction<ISelectableOption>;
  var disabled: js.UndefOr[Boolean] = js.undefined
  var required: js.UndefOr[Boolean] = js.undefined

  var calloutProps: js.UndefOr[ICalloutProps] = js.undefined
  var panelProps: js.UndefOr[Panel.Props]     = js.undefined
  var errorMessage: js.UndefOr[String]        = js.undefined
  var placeholder: js.UndefOr[String]         = js.undefined
}

@js.native
trait ICallout extends js.Object {}

trait ICalloutProps extends HTMLAttributes[dom.html.Div] with ComponentRef[ICallout] with Theme {

  var target: js.UndefOr[dom.html.Element | String | Null] = js.undefined
  var directionalHint: js.UndefOr[DirectionalHint]         = js.undefined
  //var directionalHintForRTL
  var gapSpace: js.UndefOr[Double]                      = js.undefined
  var beakWidth: js.UndefOr[Double]                     = js.undefined
  var calloutMaxWidth: js.UndefOr[Double]               = js.undefined
  var bounds: js.UndefOr[IRectangle]                    = js.undefined
  var isBeakVisible: js.UndefOr[Boolean]                = js.undefined
  var preventDismissOnScroll: js.UndefOr[Boolean]       = js.undefined
  var preventDismissOnResize: js.UndefOr[Boolean]       = js.undefined
  var preventDismissOnLostFocus: js.UndefOr[Boolean]    = js.undefined
  var coverTarget: js.UndefOr[Boolean]                  = js.undefined
  var alignTargetEdge: js.UndefOr[Boolean]              = js.undefined
  var onLayerMounted: js.UndefOr[js.Function0[Unit]]    = js.undefined
  var layerProps: js.UndefOr[LayerProps]                = js.undefined
  var onDismiss: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
  var directionalHintFixed: js.UndefOr[Boolean]         = js.undefined
  var finalHeight: js.UndefOr[Double]                   = js.undefined
  var hideOverflow: js.UndefOr[Boolean]                 = js.undefined
  var setInitialFocus: js.UndefOr[Boolean]              = js.undefined
  var calloutMaxHeight: js.UndefOr[Double]              = js.undefined
  //var onScroll: js.UndefOr[js.Function0[Unit]] = js.undefined
  //var hidden: js.UndefOr[Boolean] = js.undefined
}

@js.native
sealed trait DirectionalHint extends js.Any
object DirectionalHint {
  var topLeftEdge     = 0.asInstanceOf[DirectionalHint]
  var topCenter       = 1.asInstanceOf[DirectionalHint]
  var topRightEdge    = 2.asInstanceOf[DirectionalHint]
  var topAutoEdge     = 3.asInstanceOf[DirectionalHint]
  var bottomLeftEdge  = 4.asInstanceOf[DirectionalHint]
  var bottomCenter    = 5.asInstanceOf[DirectionalHint]
  var bottomRightEdge = 6.asInstanceOf[DirectionalHint]
  var bottomAutoEdge  = 7.asInstanceOf[DirectionalHint]
  var leftTopEdge     = 8.asInstanceOf[DirectionalHint]
  var leftCenter      = 9.asInstanceOf[DirectionalHint]
  var leftBottomEdge  = 10.asInstanceOf[DirectionalHint]
  var rightTopEdge    = 11.asInstanceOf[DirectionalHint]
  var rightCenter     = 12.asInstanceOf[DirectionalHint]
  var rightBottomEdge = 13.asInstanceOf[DirectionalHint]
}

trait IRectangle extends js.Object {}

// move to Layer component
trait LayerProps extends js.Object {}

trait ISelectableOption extends js.Object {
  var key: String | Int
  var text: String
  var itemType: js.UndefOr[SelectableOptionMenuItemType] = js.undefined
  var index: js.UndefOr[Int]                             = js.undefined
  var ariaLabel: js.UndefOr[String]                      = js.undefined
  var selected: js.UndefOr[Boolean]                      = js.undefined
  var disabled: js.UndefOr[Boolean]                      = js.undefined
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
  var visible: js.UndefOr[Boolean]  = js.undefined
  val keySeqences: js.Array[String]
}

@js.native
sealed trait ResponsiveMode extends js.Any
object ResponsiveMode {
  val small    = 0.asInstanceOf[ResponsiveMode]
  val medium   = 1.asInstanceOf[ResponsiveMode]
  val large    = 2.asInstanceOf[ResponsiveMode]
  val xLarge   = 3.asInstanceOf[ResponsiveMode]
  val xxLarge  = 4.asInstanceOf[ResponsiveMode]
  val xxxLarge = 5.asInstanceOf[ResponsiveMode]
}

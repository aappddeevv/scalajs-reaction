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

//

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
  var ref: js.UndefOr[Ref[js.Any]] = js.undefined
}

/** Adds a componentRef member. */
trait ComponentRef[T] extends js.Object {

  /** Callback style, the easiest to handle. */
  var componentRef: js.UndefOr[js.Function1[T, Unit]] = js.undefined
  //var componentRef: js.UndefOr[Ref[T]] = js.undefined
}

/** Adds a disabled member. */
trait Disabled extends js.Object {
  var disabled: js.UndefOr[Boolean] = js.undefined
}

/** Add a theme member. This should be HasTheme. */
trait Theme extends js.Object {
  var theme: js.UndefOr[ITheme] = js.undefined
}

/** Use this not `Theme`. This should be HasTheme */
trait Themable extends js.Object {
  var theme: js.UndefOr[ITheme] = js.undefined
}

/** Use this not Themable. :-) */
trait HasTheme extends js.Object {
    var theme: js.UndefOr[ITheme] = js.undefined
}

/** Adds an iconProps member. */
trait WithIconProps extends js.Object {
  var iconProps: js.UndefOr[Icon.Props | js.Dynamic] = js.undefined
}

trait ClassName extends js.Object {
  var className: js.UndefOr[String] = js.undefined
}

/** Base properties less those that are required. */
trait IColumnBase extends ClassName {
  var ariaLabel: js.UndefOr[String] = js.undefined
  //className
  var columnActionMode: js.UndefOr[ColumnActionMode] = js.undefined
  var data: js.UndefOr[scala.Any] = js.undefined
  var fieldName: js.UndefOr[String] = js.undefined
  var filterAriaLabel: js.UndefOr[String] = js.undefined
  var groupAriaLabel: js.UndefOr[String] = js.undefined
  var headerClassName: js.UndefOr[String] = js.undefined
  var isRowHeader: js.UndefOr[Boolean] = js.undefined
  var iconName: js.UndefOr[String] = js.undefined
  var isIconOnly: js.UndefOr[Boolean] = js.undefined
  var iconClassName: js.UndefOr[String] = js.undefined
  var isCollapsible: js.UndefOr[Boolean] = js.undefined
  var isGrouped: js.UndefOr[Boolean] = js.undefined
  var isSorted: js.UndefOr[Boolean] = js.undefined
  var isSortedDescending: js.UndefOr[Boolean] = js.undefined
  var isMultiline: js.UndefOr[Boolean] = js.undefined
  var isResizable: js.UndefOr[Boolean] = js.undefined
  var isPadded: js.UndefOr[Boolean] = js.undefined
  var isFiltered: js.UndefOr[Boolean] = js.undefined
  var maxWidth: js.UndefOr[Double] = js.undefined
  var onColumnClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], js.Any, Unit]] = js.undefined
  var onColumnContextMenu: js.UndefOr[js.Function2[js.Any, js.Any, Unit]] = js.undefined
  var onColumnResize: js.UndefOr[js.Function1[Int, Unit]] = js.undefined
  var onRender: js.UndefOr[js.Function3[js.Any, Int, js.Any, ReactNode]] = js.undefined
  var onRenderDivider: js.UndefOr[IRenderFunction[Details.ColumnProps]] = js.undefined
  var sortAscendingAriaLabel: js.UndefOr[String] = js.undefined
  var styles: js.UndefOr[IStyleFunctionOrObject[Details.ColumnStyleProps, Details.ColumnStyles]] = js.undefined
}

/** IColumn with required properties declared as optional. */
trait IColumnInit extends IColumnBase {
  var key: js.UndefOr[String] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var minWidth: js.UndefOr[Int] = js.undefined
}

object IColumnInit {
  implicit class RichIColumn private[IColumnInit] (private val c: IColumnInit) extends AnyVal {
    def required(key: String, name: String, minWidth: Int) =
      react.merge[IColumn](
        c,
        literal(
          "key" -> key,
          "name" -> name,
          "minWidth" -> minWidth
        )
      )

    /** Indicate that this object has all required columns. */
    def hasRequired = c.asInstanceOf[IColumn]
  }
}

/** IColumn with required properties. */
@jsenrich trait IColumn extends IColumnBase {

  /** In ts, this is String|Int */
  val key: String
  val name: String
  val minWidth: Int
}

object IColumn {

  /** Smart constructor for `IColumn.onColumnClick` */
  def OnColumnClick(
      f: (ReactMouseEvent[dom.html.Element], IColumn) => Unit
    ): js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], js.Any, Unit]] =
    js.defined((e, c) => f(e.asInstanceOf[ReactMouseEvent[dom.html.Element]], c.asInstanceOf[IColumn]))

  /** Smart constructor for `IColumn.onColumnContextMenu` */
  def OnColumnContextMenu(
      f: (IColumn, ReactMouseEvent[dom.html.Element]) => Unit
    ): js.UndefOr[js.Function2[js.Any, js.Any, Unit]] =
    js.defined((c, e) => f(c.asInstanceOf[IColumn], e.asInstanceOf[ReactMouseEvent[dom.html.Element]]))

  /** Smart constructor for `IColumn.onColumnResize`. */
  def OnColumnResize(f: Int => Unit): js.UndefOr[js.Function1[Int, Unit]] =
    js.defined(f(_))

  /** Smart constructor for `IColumn.onRender`. */
  def OnRender[T <: js.Any](
      f: (T, Int, IColumn) => ReactNode
    ): js.UndefOr[js.Function3[js.Any, Int, js.Any, ReactNode]] =
    js.defined((t, i, c) => f(t.asInstanceOf[T], i, c.asInstanceOf[IColumn]))

  /** Unsafe conversion. */
  def toCol(a: js.Dynamic): IColumn = a.asInstanceOf[IColumn]
}

trait IColumnReorderOptions extends js.Object {
  var frozenColumnCountFromStart: js.UndefOr[Int] = js.undefined
  var frozencolumnCountFromEnd: js.UndefOr[Int] = js.undefined
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
  val always = 1.asInstanceOf[CheckboxVisibility]
  val hidden = 2.asInstanceOf[CheckboxVisibility]
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
  var columnIndex: Double = js.native
  var originX: js.UndefOr[Double] = js.native
  var columnMinWidth: Double = js.native
}

trait IContextualMenuProps extends KeyAndRef {
  val items: js.Array[IContextualMenuItem]
}

@js.native
abstract trait ContextualMenuItemType extends js.Any
object ContextualMenuItemType {
    val Normal = 0.asInstanceOf[ContextualMenuItemType]
    val Divider = 1.asInstanceOf[ContextualMenuItemType]
    val Header = 2.asInstanceOf[ContextualMenuItemType]
    val Section = 3.asInstanceOf[ContextualMenuItemType]
}

trait IContextualMenuItemBase extends WithIconProps {
  import IContextualMenuItem._
  
  // key
  var text: js.UndefOr[String] = js.undefined
  var secondaryText: js.UndefOr[String] = js.undefined
  var itemType: js.UndefOr[ContextualMenuItemType] = js.undefined
  // iconProps
  var onRenderIcon: js.UndefOr[IRenderFunction[_ <: IContextualMenuItemBase]] = js.undefined
  var submenuIconProps: js.UndefOr[Icon.Props|js.Dynamic] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var primaryDisabled: js.UndefOr[Boolean] = js.undefined
  var canCheck: js.UndefOr[Boolean] = js.undefined
  var checked: js.UndefOr[Boolean] = js.undefined
  var split: js.UndefOr[Boolean] = js.undefined
  var data: js.UndefOr[scala.Any] = js.undefined
  var onClick: js.UndefOr[OC] = js.undefined
  var href: js.UndefOr[String] = js.undefined
  var target: js.UndefOr[String] = js.undefined
  var rel: js.UndefOr[String] = js.undefined
  var subMenuProps: js.UndefOr[IContextualMenuProps] = js.undefined
  var itemProps: js.UndefOr[IContextualMenuProps] = js.undefined
  //getSplitButtonVerticalDividerClassNames
  var sectionProps: js.UndefOr[js.Object/*IContextualMenuSection*/] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var ariaLabel: js.UndefOr[String] = js.undefined
  var title: js.UndefOr[String] = js.undefined
  var onRender: js.UndefOr[RF] = js.undefined
  var onMouseDown: js.UndefOr[OMD] = js.undefined
  var role: js.UndefOr[String] = js.undefined
  var customOnRenderLength: js.UndefOr[Int] = js.undefined
  var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
}

trait IContextualMenuItemInit extends IContextualMenuItemBase {
  var key: js.UndefOr[String] = js.undefined
}

object IContextualMenuItemInit {
  implicit class RichObject(item: IContextualMenuItemInit) {
    def required(name: String) = react.merge[IContextualMenuItem](item, literal("key" -> name))
    def hasRequired = item.asInstanceOf[IContextualMenuItem]
  }
}

trait IContextualMenuItem extends IContextualMenuItemBase {
  val key: String
}

object IContextualMenuItem {
  /** Callback with the react synthetic event. OC = onClick. Should be Mouse or Keyboard event! */
  type OC = js.Function2[ReactMouseEvent[dom.html.Element], IContextualMenuItem, js.UndefOr[Boolean]]

  def OnClick(f: (ReactMouseEvent[dom.html.Element], IContextualMenuItem) => js.UndefOr[Boolean]) =
    js.Any.fromFunction2(f).asInstanceOf[OC]

  def OnClick(f: () => Unit) = js.Any.fromFunction0(f).asInstanceOf[OC]
    
  type RF = js.Function2[js.Object, js.Function2[js.Any, js.UndefOr[Boolean], Unit], ReactNode]
  
  type OMD = js.Function2[IContextualMenuItem, ReactMouseEvent[dom.html.Element], Unit]
  
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
  val image = 1.asInstanceOf[IconType]
}

// @deprecated("Use Icon.Props")
// trait IIconProps extends HTMLAttributes[dom.html.Element] {
//   var iconName: js.UndefOr[String] = js.undefined
//   //var styles?: IIconStyles;
//   var ariaLabel: js.UndefOr[String] = js.undefined
//   var iconType: js.UndefOr[Int] = js.undefined //?: IconType;
//   var imageProps: js.UndefOr[Image.Props] = js.undefined
// }

@js.native
trait IBasePicker[T] extends Focusable {
  var items: js.UndefOr[js.Array[T]] = js.native
  var focusInput: js.Function0[Unit] = js.native
}

trait IInputProps extends InputHTMLAttributes[dom.html.Input] {
  //var `aria-label`: js.UndefOr[String] = js.undefined
}

@js.native
sealed trait ValidationState extends js.Any
object ValidationState {
  val valid = 0.asInstanceOf[ValidationState]
  val warning = 1.asInstanceOf[ValidationState]
  val invalid = 2.asInstanceOf[ValidationState]
}

trait IBasePickerProps[T <: js.Object] extends ComponentRef[IBasePicker[T] | Null] {
  var defaultSelectedItems: js.UndefOr[js.Array[T]] = js.undefined
  var onChange: js.UndefOr[js.Function1[js.Array[T], Unit]] = js.undefined
  //onFocus:   onFocus?: React.FocusEventHandler<HTMLInputElement | BaseAutoFill>;
  var onFocus: js.UndefOr[FocusEventHandler[dom.html.Input]] = js.undefined
  //onBlur:   onBlur?: React.FocusEventHandler<HTMLInputElement | BaseAutoFill>;
  var onBlur: js.UndefOr[FocusEventHandler[dom.html.Input]] = js.undefined
  var getTextFromItem: js.UndefOr[js.Function2[T, String, String]] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var inputProps: js.UndefOr[IInputProps] = js.undefined

  /** ValdationState */
  var onValidateInput: js.UndefOr[js.Function1[String, ValidationState]] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var itemLimit: js.UndefOr[Int] = js.undefined
  var selectedItems: js.UndefOr[js.Array[T]] = js.undefined
}

trait IBasePickerSuggestionsProps extends js.Object {
  var suggestionsHeaderText: js.UndefOr[String] = js.undefined
  var mostRecentlyUsedHeaderText: js.UndefOr[String] = js.undefined
  var noResultsFoundText: js.UndefOr[String] = js.undefined
  var suggestionsClassName: js.UndefOr[String] = js.undefined
  var suggestionsItemClassName: js.UndefOr[String] = js.undefined
  var searchForMoreText: js.UndefOr[String] = js.undefined
  var forceResolveText: js.UndefOr[String] = js.undefined
  var loadingText: js.UndefOr[String] = js.undefined
  var searchingText: js.UndefOr[String] = js.undefined
  var resultMaximumNumber: js.UndefOr[Int] = js.undefined
  var showRemoveButtons: js.UndefOr[Boolean] = js.undefined
}

trait ISelectableDroppableTextProps[I <: ISelectableOption, T <: dom.html.Element]
    extends HTMLAttributes[T]
    with ComponentRef[T] {
  var label: js.UndefOr[String] = js.undefined
  // these are in HTMLAttributes I think
  // ariaLabel?: string;
  // id?: string;
  // className?: string;

  var defaultSelectedKey: js.UndefOr[String | Int | js.Array[String] | js.Array[Int] | Null] = js.undefined
  var selectedKey: js.UndefOr[String | Int | js.Array[String] | js.Array[Int] | Null] = js.undefined

  var multiSelect: js.UndefOr[Boolean] = js.undefined

  /** Any ??? needs to have key & text, use structural type? ISelectableOption?? */
  val options: js.Array[I] | js.Array[_ <: js.Dynamic]
  //@JSName("options")
  //var unsafeOptions: js.UndefOr[js.Array[js.Dynamic]] = js.undefined
  // onChanged?: (option: ISelectableOption, index?: number) => void;
  // onRenderContainer?: IRenderFunction<ISelectableDroppableTextProps<T>>;
  var onRenderList: js.UndefOr[IRenderFunction[ISelectableDroppableTextProps[I, T]]] = js.undefined
  var onRenderItem: js.UndefOr[IRenderFunction[ISelectableOption]] = js.undefined
  var onRenderOption: js.UndefOr[IRenderFunction[ISelectableOption]] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var required: js.UndefOr[Boolean] = js.undefined

  var calloutProps: js.UndefOr[CalloutProps] = js.undefined
  var panelProps: js.UndefOr[Panel.Props] = js.undefined
  var errorMessage: js.UndefOr[String] = js.undefined
  var placeholder: js.UndefOr[String] = js.undefined
  var selectedKeys: js.UndefOr[js.Array[String] | js.Array[Int] | Null] = js.undefined
}

@js.native
trait ICallout extends js.Object {}

trait CalloutProps extends HTMLAttributes[dom.html.Div] with ComponentRef[ICallout] with Theme {

  var target: js.UndefOr[dom.html.Element | String | Null] = js.undefined
  var directionalHint: js.UndefOr[DirectionalHint] = js.undefined
  //var directionalHintForRTL
  var gapSpace: js.UndefOr[Double] = js.undefined
  var beakWidth: js.UndefOr[Double] = js.undefined
  var calloutMaxWidth: js.UndefOr[Double] = js.undefined
  var bounds: js.UndefOr[IRectangle] = js.undefined
  var isBeakVisible: js.UndefOr[Boolean] = js.undefined
  var preventDismissOnScroll: js.UndefOr[Boolean] = js.undefined
  var preventDismissOnResize: js.UndefOr[Boolean] = js.undefined
  var preventDismissOnLostFocus: js.UndefOr[Boolean] = js.undefined
  var coverTarget: js.UndefOr[Boolean] = js.undefined
  var alignTargetEdge: js.UndefOr[Boolean] = js.undefined
  var onLayerMounted: js.UndefOr[js.Function0[Unit]] = js.undefined
  var layerProps: js.UndefOr[LayerProps] = js.undefined
  var onDismiss: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
  var directionalHintFixed: js.UndefOr[Boolean] = js.undefined
  var finalHeight: js.UndefOr[Double] = js.undefined
  var hideOverflow: js.UndefOr[Boolean] = js.undefined
  var setInitialFocus: js.UndefOr[Boolean] = js.undefined
  var calloutMaxHeight: js.UndefOr[Double] = js.undefined
  //var onScroll: js.UndefOr[js.Function0[Unit]] = js.undefined
  //var hidden: js.UndefOr[Boolean] = js.undefined
}

@js.native
sealed trait DirectionalHint extends js.Any
object DirectionalHint {
  var topLeftEdge = 0.asInstanceOf[DirectionalHint]
  var topCenter = 1.asInstanceOf[DirectionalHint]
  var topRightEdge = 2.asInstanceOf[DirectionalHint]
  var topAutoEdge = 3.asInstanceOf[DirectionalHint]
  var bottomLeftEdge = 4.asInstanceOf[DirectionalHint]
  var bottomCenter = 5.asInstanceOf[DirectionalHint]
  var bottomRightEdge = 6.asInstanceOf[DirectionalHint]
  var bottomAutoEdge = 7.asInstanceOf[DirectionalHint]
  var leftTopEdge = 8.asInstanceOf[DirectionalHint]
  var leftCenter = 9.asInstanceOf[DirectionalHint]
  var leftBottomEdge = 10.asInstanceOf[DirectionalHint]
  var rightTopEdge = 11.asInstanceOf[DirectionalHint]
  var rightCenter = 12.asInstanceOf[DirectionalHint]
  var rightBottomEdge = 13.asInstanceOf[DirectionalHint]
}

trait IRectangle extends js.Object {}

// move to Layer component
trait LayerProps extends js.Object {}

/** Trait with key/title in it. */
trait FabricOption extends js.Object {}

trait ISelectableOptionBase extends js.Object {
  var id: js.UndefOr[String] = js.undefined
  var data: js.UndefOr[js.Any] = js.undefined
//  val key: String | Int
//  val text: String
  var hidden: js.UndefOr[Boolean] = js.undefined
  var itemType: js.UndefOr[SelectableOptionMenuItemType] = js.undefined
  var index: js.UndefOr[Int] = js.undefined
  var ariaLabel: js.UndefOr[String] = js.undefined
  var selected: js.UndefOr[Boolean] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
}

trait ISelectableOptionInit extends ISelectableOptionBase {
  var key: js.UndefOr[String | Int] = js.undefined
  @JSName("key") var keyString: js.UndefOr[String] = js.undefined
  @JSName("key") var keyInt: js.UndefOr[Int] = js.undefined
  var text: js.UndefOr[String] = js.undefined
}

object ISelectableOptionInit {
  private implicit class Rich(private val i: ISelectableOptionInit) extends AnyVal {
    def required(key: String | Int, text: String) =
      js.Object
        .assign(
          js.Dynamic
            .literal("key" -> key.asInstanceOf[js.Any], "text" -> text.asInstanceOf[js.Any]),
          i
        )
        .asInstanceOf[ISelectableOption]
    def hasRequired = i.asInstanceOf[ISelectableOption]
  }
}

trait ISelectableOption extends ISelectableOptionBase {
  val key: String | Int
  val text: String
}

@js.native
sealed trait SelectableOptionMenuItemType extends js.Any
object SelectableOptionMenuItemType {
  val Normal = 0.asInstanceOf[SelectableOptionMenuItemType]
  val Divider = 1.asInstanceOf[SelectableOptionMenuItemType]
  val Header = 2.asInstanceOf[SelectableOptionMenuItemType]
}

trait IDropdownOptionBase extends ISelectableOptionBase

trait IDropdownOptionInit extends ISelectableOptionInit

object IDropdownOptionInit {
  private implicit class Rich(private val i: IDropdownOptionInit) extends AnyVal {
    def required(key: String | Int, text: String) =
      js.Object
        .assign(
          js.Dynamic
            .literal("key" -> key.asInstanceOf[js.Any], "text" -> text.asInstanceOf[js.Any]),
          i
        )
        .asInstanceOf[IDropdownOption]
    def hasRequired = i.asInstanceOf[IDropdownOption]
    def combine(that: IDropdownOptionInit | IDropdownOption) =
      js.Object
        .assign(js.Dynamic.literal(), i, that.asInstanceOf[IDropdownOptionInit])
        .asInstanceOf[IDropdownOptionInit]
  }
}

trait IDropdownOption extends ISelectableOption

trait IKeytipProps extends js.Object {
  val content: String
  var disabled: js.UndefOr[Boolean] = js.undefined
  var visible: js.UndefOr[Boolean] = js.undefined
  val keySeqences: js.Array[String]
}

@js.native
sealed trait ResponsiveMode extends js.Any
object ResponsiveMode {
  val small = 0.asInstanceOf[ResponsiveMode]
  val medium = 1.asInstanceOf[ResponsiveMode]
  val large = 2.asInstanceOf[ResponsiveMode]
  val xLarge = 3.asInstanceOf[ResponsiveMode]
  val xxLarge = 4.asInstanceOf[ResponsiveMode]
  val xxxLarge = 5.asInstanceOf[ResponsiveMode]
}

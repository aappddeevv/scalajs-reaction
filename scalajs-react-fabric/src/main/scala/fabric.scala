// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package fabric

import scala.scalajs.js
import js.annotation._
import js.|
//import scala.annotation.unchecked.{uncheckedVariance => uv}

import ttg.react.vdom._
import org.scalajs.dom
import styling._

@js.native
@JSImport("@uifabric/icons", JSImport.Namespace)
object uifabric_icons extends js.Object {
  def initializeIcons(): Unit = js.native
}

// Import fabric components. This imports *everything* which is not what we usually want. */
@js.native
@JSImport("office-ui-fabric-react", JSImport.Namespace)
object FabricNS extends js.Object {
  val Fabric: ReactJsComponent                = js.native
  val TextField: ReactJsComponent             = js.native
  val BaseButton: ReactJsComponent            = js.native
  val Button: ReactJsComponent                = js.native
  val CommandBar: ReactJsComponent            = js.native
  val DetailsList: ReactJsComponent           = js.native
  val DefaultButton: ReactJsComponent         = js.native
  val Dropdown: ReactJsComponent              = js.native
  val IconButton: ReactJsComponent            = js.native
  val Label: ReactJsComponent                 = js.native
  val Link: ReactJsComponent                  = js.native
  val List: ReactJsComponent                  = js.native
  val MarqueeSelection: ReactJsComponent      = js.native
  val MessageBar: ReactJsComponent = js.native
  val Nav: ReactJsComponent      = js.native
  val OverflowSet: ReactJsComponent      = js.native
  val Panel: ReactJsComponent                 = js.native
  val Pivot: ReactJsComponent                 = js.native
  val PivotItem: ReactJsComponent             = js.native
  val PrimaryButton: ReactJsComponent         = js.native
  val ScrollablePane: ReactJsComponent = js.native
  val SearchBox: ReactJsComponent = js.native
  val ShimmeredDetailsList: ReactJsComponent = js.native
  val Spinner: ReactJsComponent               = js.native
  def Selection[T <: js.Object]: Selection[T] = js.native
  val Sticky: ReactJsComponent                = js.native
  val FocusZone: ReactJsComponent             = js.native
  val TagPicker: ReactJsComponent             = js.native
}

/**
  * We could also define some native traits for the args...but that's more work...
  */
object components {
  import ttg.react.elements.wrapJsForScala

  def CommandBar(props: Attr*)(children: ReactNode*) =
    wrapJsForScala(FabricNS.CommandBar, new Attrs(props).toJs, children: _*)

  def CommandBar(props: ICommandBarProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.CommandBar, props, children: _*)

  def DefaultButton(props: IButtonProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.DefaultButton, props, children: _*)

  def DetailsList[T <: js.Object](props: IDetailsListProps[T] = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.DetailsList, props, children: _*)

  def Dropdown[T <: js.Object](props: IDropdownProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Dropdown, props, children: _*)

  def Fabric(props: IFabricProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Fabric, props, children: _*)

  def FocusZone[T <: js.Object](props: IFocusZoneProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.FocusZone, props, children: _*)

  def IconButton[T <: js.Object](props: IButtonProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.IconButton, props, children: _*)

  def Label(props: ILabelProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Label, props, children: _*)

  def Link(props: ILinkProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Link, props, children: _*)

  def List[T <: js.Object](props: IListProps[T] = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.List, props, children: _*)

  def MarqueeSelection[T <: js.Object](props: IMarqueeSelectionProps[T] = null)(
      children: ReactNode*) =
    wrapJsForScala(FabricNS.MarqueeSelection, props, children: _*)

  def MessageBar(props: IMessageBarProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.MessageBar, props, children:_*)

  def Nav(props: INavProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Nav, props, children: _*)

  def Nav(props: INavProps) = wrapJsForScala(FabricNS.Nav, props)

  def OverflowSet(props: IOverflowSetProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.OverflowSet, props, children: _*)

  def Panel(props: IPanelProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Panel, props, children: _*)

  def Pivot(props: IPivotProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Pivot, props, children: _*)

  def PivotItem(props: IPivotItemProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.PivotItem, props, children: _*)

  def PrimaryButton(props: IButtonProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.PrimaryButton, props, children: _*)

  def SearchBox(props: ISearchBoxProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.SearchBox, props, children: _*)

  def ShimmeredDetailsList[T <: js.Object](props: IShimmeredDetailsListProps[T] = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.ShimmeredDetailsList, props, children: _*)

  def Spinner(props: ISpinnerProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Spinner, props, children: _*)

  def ScrollablePane(props: IScrollablePaneProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.ScrollablePane, props, children: _*)

  def Sticky(props: IStickyProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Sticky, props, children: _*)

  def TagPicker(props: ITagPickerProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.TagPicker, props, children: _*)

  def TextField(props: ITextFieldProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.TextField, props, children: _*)

}

//
// common parts
//
/** Provide a focs() method. */
@js.native
trait Focusable extends js.Object {
  def focus(): Unit = js.native
}

trait IWithViewportProps extends js.Object {
  var skipViewportMeasures: js.UndefOr[Boolean] = js.undefined
}

trait KeyAndRef extends js.Object {
  var key: js.UndefOr[String] = js.undefined
  var ref: js.UndefOr[RefCb]  = js.undefined
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

//
// For components
//

trait ILabelProps
    extends LabelHTMLAttributes[dom.html.Label]
    with ComponentRef[js.Any]
    with Disabled
    with Theme {
  var required: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait IPivot extends js.Object {
  def focus(): Unit = js.native
}

trait IPivotItemProps extends HTMLAttributes[dom.html.Div] {
  var headerText: js.UndefOr[String]                                   = js.undefined
  var itemKey: js.UndefOr[String]                                    = js.undefined
  var headerButtonProps: js.UndefOr[js.Object] = js.undefined
  var itemCount: js.UndefOr[Int]                                     = js.undefined
  var itemIcon: js.UndefOr[String]                                   = js.undefined
  var ariaLabel: js.UndefOr[String]                                  = js.undefined
  var onRenderItemLink: js.UndefOr[IRenderFunction[IPivotItemProps]] = js.undefined
  var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
}

trait IPivotStyleProps extends Theme {
  var className: js.UndefOr[String] = js.undefined
}

trait IPivotStyles extends IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
  var link: js.UndefOr[IStyle] = js.undefined
  var linkContent: js.UndefOr[IStyle] = js.undefined
  var text: js.UndefOr[IStyle] = js.undefined
  var count: js.UndefOr[IStyle] = js.undefined
  var icon: js.UndefOr[IStyle] = js.undefined  
}

trait IPivotProps extends Attributes with Theme with ComponentRef[IPivot] {
  var styles: js.UndefOr[IStyleFunctionOrObject[IPivotStyleProps, IPivotStyles]] = js.undefined
  var className: js.UndefOr[String] = js.undefined

  var initialSelectedKey: js.UndefOr[String] = js.undefined
  var initialSelectedIndex: js.UndefOr[Int]  = js.undefined
  var selectedKey: js.UndefOr[String]        = js.undefined
  var onLinkClick: js.UndefOr[
    ((js.Object, js.UndefOr[ReactMouseEvent[dom.html.Element]]) => Unit) |(() => Unit)] =
    js.undefined
  var linkSize: js.UndefOr[PivotLinkSize] = js.undefined
  var headersOnly: js.UndefOr[Boolean] = js.undefined
  var getTableId: js.UndefOr[js.Function2[String, Int, String]] = js.undefined
}

@js.native
sealed trait PivotLinkSize extends js.Any
object PivotLinkSize {
  var normal = 0.asInstanceOf[PivotLinkSize]
  val largeg = 1.asInstanceOf[PivotLinkSize]
}

@js.native
trait ITextField extends Focusable with ReactRef {
  var value: js.UndefOr[String]                       = js.native
  var select: js.Function0[Unit]                      = js.native
  var setSelectionStart: js.Function1[Int, Unit]      = js.native
  var setSelectionEnd: js.Function1[Int, Unit]        = js.native
  var setSelectionRange: js.Function2[Int, Int, Unit] = js.native
  var selectionStart: Int                             = js.native
  var selectionEnd: Int                               = js.native
}

trait ITextFieldStyleProps extends js.Object {
}

trait ITextFieldStyles extends IStyleSetTag {
}

//export interface ITextFieldProps extends React.AllHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
// withDisabled

// should also included attributes from dom.html.TextArea
trait ITextFieldProps
    extends WithIconProps
    with Theme
    with ComponentRef[ITextField]
    with AllHTMLAttributes[dom.html.Input] {
  var multiline: js.UndefOr[Boolean]        = js.undefined
  var resizable: js.UndefOr[Boolean]        = js.undefined
  var autoAdjustHeight: js.UndefOr[Boolean] = js.undefined
  var underlined: js.UndefOr[Boolean]       = js.undefined
  var borderless: js.UndefOr[Boolean]       = js.undefined
  //var label: js.UndefOr[String] = js.undefined
  var onRenderLabel: js.UndefOr[IRenderFunction[ITextFieldProps]] = js.undefined
  var description: js.UndefOr[String]                             = js.undefined
  var addonString: js.UndefOr[String]                             = js.undefined
  var onRenderAddon: js.UndefOr[IRenderFunction[ITextFieldProps]] = js.undefined
  var prefix: js.UndefOr[String] = js.undefined
  //var iconProps?: IIconProps;
  // defined in HTMLAttributes
  //var defaultValue: js.UndefOr[String] = js.undefined
  //var value: js.UndefOr[String] = js.undefined
  var errorMessage: js.UndefOr[String]                       = js.undefined
  // should this be js.UndefOr[String] ?
  @JSName("onChange")
  var onChangeInput: js.UndefOr[js.Function2[SyntheticFormEvent[dom.html.Input], String, Unit]]      = js.undefined
  @JSName("onChange")
  var onChangeTextArea: js.UndefOr[js.Function2[SyntheticFormEvent[ dom.html.TextArea], String, Unit]]      = js.undefined
  // onChanged is deprecated
  var onBeforeChange: js.UndefOr[js.Function1[String, Unit]] = js.undefined
  /** error message, value */
  var onNotifyValidationResult: js.UndefOr[js.Function2[String, String, Unit]] = js.undefined
  var onGetErrorMessage: js.UndefOr[String => js.Promise[String]] = js.undefined
  //: string) => string | PromiseLike<string> | undefined;
  var deferredValidationTime: js.UndefOr[Long] = js.undefined
  //var className: js.UndefOr[String] = js.undefined
  var inputClassName: js.UndefOr[String]      = js.undefined
  var ariaLabel: js.UndefOr[String]           = js.undefined
  var validateOnFocusIn: js.UndefOr[Boolean]  = js.undefined
  var validateOnFocusOut: js.UndefOr[Boolean] = js.undefined
  var validateOnLoad: js.UndefOr[Boolean]     = js.undefined
  var componentId: js.UndefOr[String]         = js.undefined
  var styles: js.UndefOr[IStyleFunctionOrObject[ITextFieldStyleProps, ITextFieldStyles]] =
    js.undefined
}

@js.native
trait IList extends js.Object {
  def scrollToIndex(index: Int, measureItem: js.UndefOr[js.Function1[Int, Int]]): Unit = js.native
}

@js.native
trait IDetailsList extends IList {
  def forceUpdate(): Unit = js.native
  def focusIndex(index: Int, forceIntoFirstElement: js.UndefOr[Boolean],
    measureItem: js.UndefOr[js.Function1[Int, Int]], scrollToMode: js.UndefOr[Int]): Unit = js.native
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

object ColumnActionsMode {
  val disabled = 0
  val clickable = 1
  val hasDropdown = 2
}

object ColumnDragEndLocation {
  val outside = 0
  val surface = 1
  val header = 2
}

trait IColumnDragDropDetails extends js.Object {
  val draggedIndex: Int
  val targetIndex: Int
}

object CheckboxVisibility {
  val onHover = 0
  val always  = 1
  val hidden  = 2
}

@js.native
sealed trait DetailsListLayoutMode extends js.Any

object DetailsListLayoutMode {
  val fixedColumns = 0.asInstanceOf[DetailsListLayoutMode]
  val justified    = 1.asInstanceOf[DetailsListLayoutMode]
}

@js.native
sealed trait ConstrainMode extends js.Any

object ConstrainMode {
  val unconstrained         = 0.asInstanceOf[ConstrainMode]
  val horizontalConstrained = 1.asInstanceOf[ConstrainMode]
}

trait IDetailsRowProps extends js.Object {
  var componentRef: js.UndefOr[js.Function0[Unit]] = js.undefined
  var item: js.Any
  var itemIndex: Int
  var selectionMode: js.UndefOr[Int]               = js.undefined
  var selection: js.UndefOr[ISelection[js.Object]] = js.undefined
  var compact: js.UndefOr[Boolean]                 = js.undefined
  var checkboxCellClassName: js.UndefOr[String]    = js.undefined
  var className: js.UndefOr[String]                = js.undefined
  var columns: js.Array[IColumn]
}

@js.native
trait IDetailsHeader extends Focusable {}

trait IDetailsHeaderProps extends ComponentRef[IDetailsHeader] {
  var columns: js.Array[IColumn]
  var layoutMode: js.UndefOr[Int]        = js.undefined
  var groupNestingDepth: js.UndefOr[Int] = js.undefined
}

trait IDetailsListProps[T <: js.Object] extends ComponentRef[IDetailsList] with Attributes {
  import IDetailsListProps._
  //val items: js.Array[T]
  var items: js.UndefOr[js.Array[T]] = js.undefined
  var setKey: js.UndefOr[String]                      = js.undefined
  var className: js.UndefOr[String]                   = js.undefined
  var checkboxCellClassName: js.UndefOr[String]       = js.undefined
  var enterModalSelectionOnTouch: js.UndefOr[Boolean] = js.undefined
  var columns: js.UndefOr[js.Array[IColumn] | js.Array[js.Object] | js.Array[js.Dynamic]] =
    js.undefined
  var constrainMode: js.UndefOr[ConstrainMode] = js.undefined
  var listProps
    : js.UndefOr[js.Dynamic] = js.undefined // should be IListProps but then items is required
  var getKey: js.UndefOr[js.Function2[T, js.UndefOr[Int], String] | js.Function1[T, String]] =
    js.undefined
  var initialFocusedIndex: js.UndefOr[Int]                                       = js.undefined
  var selection: js.UndefOr[ISelection[T]]                                       = js.undefined
  var selectionMode: js.UndefOr[Int] = js.undefined
  var selectionPreservedOnEmptyClick: js.UndefOr[Boolean]                        = js.undefined
  var layoutMode: js.UndefOr[DetailsListLayoutMode]                                                = js.undefined
  var isHeaderVisible: js.UndefOr[Boolean]                                       = js.undefined
  var compact: js.UndefOr[Boolean]                                               = js.undefined
  var usePageCache: js.UndefOr[Boolean]                                          = js.undefined
  var onRenderRow: js.UndefOr[IRenderFunction[IDetailsRowProps]]                 = js.undefined
  var onRenderItemColumn: js.UndefOr[js.Function3[js.Any, Int, IColumn, js.Any]] = js.undefined
  var onRenderMissingItem: js.UndefOr[js.Function1[Int, js.Any]]                 = js.undefined
  var onRenderDetailsHeader: js.UndefOr[IRenderFunction[IDetailsHeaderProps]]    = js.undefined
  var onActiveItemChanged: js.UndefOr[OAIC[T]] = js.undefined
  var onColumnHeaderClick: js.UndefOr[OCHC[T]] = js.undefined
  var renderedWindowsAhead: js.UndefOr[Int]                                = js.undefined
  var renderedWindowsBehind: js.UndefOr[Int]                               = js.undefined
  var onShouldVirtualize: js.UndefOr[js.Function1[IListProps[T], Boolean]] = js.undefined
  //var styles: js.UndefOr[styling.IStyleFunctionOrObject[IDetailsListStyleProps, IDetailsListStyles]] = js.undefined
}

trait IDetailsListStyleProps extends js.Object {
  var isHorizontalConstrained: js.UndefOr[Boolean] = js.undefined
  var compact: js.UndefOr[Boolean] = js.undefined
  var isFixed: js.UndefOr[Boolean] = js.undefined
}

trait IDetailsListStyles extends styling.IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
  var focusZone: js.UndefOr[IStyle] = js.undefined
}

object IDetailsListProps {
  type OAIC[T <: js.Object] = js.Function3[
    js.UndefOr[T],
    js.UndefOr[Int],
    js.UndefOr[SyntheticFocusEvent[dom.html.Element]],
    Unit]
  
    type OCHC[T <: js.Object] =
    js.Function2[js.UndefOr[SyntheticMouseEvent[dom.html.Element]], js.UndefOr[IColumn], Unit]
}

trait IListProps[T <: js.Object] extends IWithViewportProps with ComponentRef[IDetailsList] {
  import IListProps._

  var getKey
    : js.UndefOr[js.Function2[T, js.UndefOr[Int], String | Int] | js.Function1[T, String | Int]] =
    js.undefined
  val items: js.Array[T]
  var setKey: js.UndefOr[String]           = js.undefined
  var className: js.UndefOr[String]        = js.undefined
  var initialFocusedIndex: js.UndefOr[Int] = js.undefined
  // do the groups thing
  var selection: js.UndefOr[ISelection[T]]                = js.undefined
  var selectionMode: js.UndefOr[Int]                      = js.undefined
  var selectionPreservedOnEmptyClick: js.UndefOr[Boolean] = js.undefined
  var layoutMode: js.UndefOr[Int]                         = js.undefined
  // checkbox visibility....
  var isHeaderVisible: js.UndefOr[Boolean] = js.undefined
  var columns: js.UndefOr[js.Array[IColumn] | js.Array[js.Object] | js.Array[js.Dynamic]] =
    js.undefined
  var constrainMode: js.UndefOr[Int] = js.undefined

  var onItemInvoked: js.UndefOr[OII[T]] = js.undefined

  var onRenderRow: js.UndefOr[IRenderFunction[IDetailsRowProps]]              = js.undefined
  var onRenderMissingItem: js.UndefOr[js.Function1[Int, js.Any]]              = js.undefined
  var onRenderDetailsHeader: js.UndefOr[IRenderFunction[IDetailsHeaderProps]] = js.undefined

  var onActiveItemChanged: js.UndefOr[OAIC[T]] = js.undefined
  var onColumnHeaderClick: js.UndefOr[OCHC[T]] = js.undefined

  var maximumPixelsForDrag: js.UndefOr[Int]           = js.undefined
  var compact: js.UndefOr[Boolean]                    = js.undefined
  var checkboxCellClassName: js.UndefOr[String]       = js.undefined
  var enterModelSelectionOnTouch: js.UndefOr[Boolean] = js.undefined

  var usePageCache: js.UndefOr[Boolean] = js.undefined
  var renderCount: js.UndefOr[Int]      = js.undefined
}

object IListProps {

  type OII[T <: js.Object] = js.Function3[
    T,
    Int,
    SyntheticFocusEvent[dom.html.Element],
    Unit]

  type OAIC[T <: js.Object] = js.Function3[
    T,
    Int,
    SyntheticFocusEvent[dom.html.Element],
    Unit]

  type OCHC[T <: js.Object] =
    js.Function2[SyntheticMouseEvent[dom.html.Element], IColumn, Unit]
}

trait IContextualMenuProps extends KeyAndRef {
  var items: js.Array[IContextualMenuItem]
}

trait IContextualMenuItem extends WithIconProps {
  import IContextualMenuItem._
  val key: String
  var name: js.UndefOr[String] = js.undefined

  /** 1 => divider, 0 => normal */
  var itemType: js.UndefOr[Int]     = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  //var onClick: js.UndefOr[OC2 | OC0]                 = js.undefined
  var onClick: js.UndefOr[OC2 | OC0]                 = js.undefined
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
trait ICommandBar extends Focusable {}

trait ICommandBarProps extends HTMLAttributes[dom.html.Div] {
  var componentRef: js.UndefOr[ICommandBar => Unit] = js.undefined
  var isSearchBoxVisble: js.UndefOr[Boolean]        = js.undefined
  var searchBoxPlaceholderText: js.UndefOr[String]  = js.undefined
  val items: js.Array[IContextualMenuItem]
  var farItems: js.UndefOr[js.Array[IContextualMenuItem]] = js.undefined
}

trait ICommandBarItemProps extends IContextualMenuItem {
  var iconOnly: js.UndefOr[Boolean] = js.undefined
  //tooltipHostPropsp
  // buttonStyles
  var cacheKey: js.UndefOr[Boolean] = js.undefined
  var renderedInOverflow: js.UndefOr[Boolean] = js.undefined
  //commandBarButtonAs is a IComponentAs<ICommandBarItemProps> is this something new?
}

trait ICommandBarStyleProps extends js.Object {
  var theme: js.UndefOr[ITheme] = js.undefined
  var className: js.UndefOr[String] = js.undefined
}

trait ICommandBarStyles extends IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
  var primarySet: js.UndefOr[IStyle] = js.undefined
  var secondarySet: js.UndefOr[IStyle] = js.undefined
}

@js.native
trait ISpinner extends js.Object

trait ISpinnerStyles extends IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
  var circle: js.UndefOr[IStyle] = js.undefined
  var label: js.UndefOr[IStyle] = js.undefined
  var screenReaderText: js.UndefOr[IStyle] = js.undefined  
}

trait ISpinnerStyleProps extends js.Object{
  var className: js.UndefOr[String]              = js.undefined
  var size: js.UndefOr[SpinnerSize] = js.undefined
}

trait ISpinnerProps extends KeyAndRef {
  var componentRef: js.UndefOr[ISpinner => Unit] = js.undefined
  var size: js.UndefOr[SpinnerSize]                      = js.undefined
  var label: js.UndefOr[String]                  = js.undefined
  var className: js.UndefOr[String]              = js.undefined
  var ariaLabel: js.UndefOr[String]              = js.undefined
  var styles: js.UndefOr[IStyleFunctionOrObject[ISpinnerStyleProps, ISpinnerStyles]] = js.undefined
}

@js.native
trait SpinnerSize extends js.Any
object SpinnerSize {
  var xSmall = 0.asInstanceOf[SpinnerSize]
  var small  = 1.asInstanceOf[SpinnerSize]
  var medium = 2.asInstanceOf[SpinnerSize]
  var large  = 3.asInstanceOf[SpinnerSize]
}

@js.native
trait IObjectWithKey extends js.Object {
  var key: String = js.native
}

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

object SelectionMode {
  val none     = 0
  val single   = 1
  val multiple = 2
}

trait ISelectionOptions[T <: js.Object] extends js.Object {
  var getKey: js.UndefOr[js.Function2[T, js.UndefOr[Int], String] | js.Function1[T, String]] =
    js.undefined
  var selectionMode: js.UndefOr[Int]                     = js.undefined
  var onSelectionChanged: js.UndefOr[js.Function0[Unit]] = js.undefined
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

object IconType {
  val default = 0
  val image   = 1
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

trait IFabricProps extends HTMLAttributes[dom.html.Div] with Theme {
  var componentRef: js.UndefOr[js.Function0[Unit]] = js.undefined
}

@js.native
trait IScrollablePane extends js.Object {
  def forceLayoutUpdate(): Unit = js.native
  def getScrollPosition(): Double = js.native
}

trait IScrollablePaneProps
    extends HTMLAttributes[dom.html.Element]
    with ComponentRef[IScrollablePane] {
  val initialScrollPosition: js.UndefOr[Double] = js.undefined
  var scrollbarVisibility: js.UndefOr[Boolean] = js.undefined
}

trait IScrollablePaneStyleProps extends js.Object {
  val className: js.UndefOr[String] = js.undefined
}

trait IScrollablePaneStyles extends styling.IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
  var stickyAbove: js.UndefOr[IStyle] = js.undefined
  var stickyBelow: js.UndefOr[IStyle] = js.undefined
  var stickyBelowItems: js.UndefOr[IStyle] = js.undefined
  var contentContainer: js.UndefOr[IStyle] = js.undefined
}

object ScrollbarVisibility {
  var auto = "auto"
  var always = "always"
}

trait IStickyProps extends ComponentRef[IStickyProps] {
  var stickyCassName: js.UndefOr[String] = js.undefined
  var stickyPosition: js.UndefOr[Int]    = js.undefined
}

object StickyPositionType {
  val Both   = 0
  val Header = 1
  val Footer = 2
}

@js.native
trait IButton extends Focusable {
  def dismissMenu(): Unit
}

trait IButtonProps
    extends ComponentRef[IButton]
    with WithIconProps
    with AllHTMLAttributes[dom.html.Button]
    with Attributes {
  // having variance problems
  //dom.html.Anchor|FabricNS.BaseButton|FabricNS.Button] {
  //var href: js.UndefOr[String] = js.undefined
  var primary: js.UndefOr[Boolean] = js.undefined
  //var disabled: js.UndefOr[Boolean] = js.undefined
  var primaryDisabled: js.UndefOr[Boolean] = js.undefined
  //var checked: js.UndefOr[Boolean] = js.undefined
  var ariaLabel: js.UndefOr[String]         = js.undefined
  var ariaDescription: js.UndefOr[String]   = js.undefined
  var ariaHidden: js.UndefOr[Boolean]       = js.undefined
  var text: js.UndefOr[String]              = js.undefined
  var split: js.UndefOr[Boolean]            = js.undefined
  var menuIconProps: js.UndefOr[IIconProps] = js.undefined
  var onMenuClick: js.UndefOr[
    js.Function2[SyntheticMouseEvent[dom.html.Element], js.UndefOr[IButtonProps], Unit]] =
    js.undefined
  var onRenderIcon: js.UndefOr[IRenderFunction[IButtonProps]]            = js.undefined
  var onRenderText: js.UndefOr[IRenderFunction[IButtonProps]]            = js.undefined
  var onRenderDescription: js.UndefOr[IRenderFunction[IButtonProps]]     = js.undefined
  var onRenderAriaDescription: js.UndefOr[IRenderFunction[IButtonProps]] = js.undefined
  var onRenderChildren: js.UndefOr[IRenderFunction[IButtonProps]]        = js.undefined
  var onRenderMenuIcon: js.UndefOr[IRenderFunction[IButtonProps]]        = js.undefined
  var onRenderMenu: js.UndefOr[IRenderFunction[IContextualMenuProps]]    = js.undefined
  var description: js.UndefOr[String]                                    = js.undefined
  var toggled: js.UndefOr[Boolean]                                       = js.undefined
  // note scala.Any not js.Any
  //var data: js.UndefOr[scala.Any] = js.undefined
}

trait ILinkStyleProps extends js.Object {
  var className: js.UndefOr[String] = js.undefined
  var isButton: js.UndefOr[Boolean] = js.undefined
  var isDisabled: js.UndefOr[Boolean] = js.undefined
}

trait ILinkStyles extends IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
}

@js.native
trait ILink extends Focusable

//export interface ILinkProps extends React.AllHTMLAttributes<HTMLAnchorElement | HTMLButtonElement | HTMLElement | Link>

trait ILinkProps extends AllHTMLAttributes[dom.html.Anchor] with ComponentRef[ILink] {
  //var styles: js.UndefOr[IStyleFunctionOrObject[ILinkStyleProps, ILinkStyles]] = js.undefined
  var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
}

@js.native
trait IMarqueeSelection extends js.Object {}

trait IMarqueeSelectionProps[T <: js.Object] extends ComponentRef[IMarqueeSelection] with Attributes {
  var selection: js.UndefOr[ISelection[T]]                              = js.undefined
  var rootProps: js.UndefOr[HTMLAttributes[dom.html.Div]]               = js.undefined
  var onShouldStartSelection: js.UndefOr[js.Function1[js.Any, Boolean]] = js.undefined
  var isEnabled: js.UndefOr[Boolean]                                    = js.undefined
  var isDraggingConstrainedToRoot: js.UndefOr[Boolean]                  = js.undefined

}
@js.native
trait IFocusZone extends js.Object {
  def focus(forceIntoFirstElement: js.UndefOr[Boolean]): Boolean        = js.native
  def focusElement(childElement: js.UndefOr[dom.html.Element]): Boolean = js.native
}

trait IFocusZoneProps extends ComponentRef[IFocusZone] with HTMLAttributes[dom.html.Div] {
  //var className: js.UndefOr[String] = js.undefined
  var direction: js.UndefOr[Int]               = js.undefined
  var defaultActiveElement: js.UndefOr[String] = js.undefined
  var disabled: js.UndefOr[Boolean]            = js.undefined

  /** e.g. div */
  var elementType: js.UndefOr[String]           = js.undefined
  var isCircularNavigation: js.UndefOr[Boolean] = js.undefined
  var isInnerZoneKeyStroke
    : js.UndefOr[js.Function1[SyntheticKeyboardEvent[dom.html.Element], Boolean]] = js.undefined
  var ariaLabelledBy: js.UndefOr[String]                                          = js.undefined
  var ariaDescribedBy: js.UndefOr[String]                                         = js.undefined
  var onActiveElementChanged: js.UndefOr[
    js.Function0[Unit] | js.Function2[
      js.UndefOr[dom.html.Element],
      js.UndefOr[SyntheticFocusEvent[dom.html.Element]],
      Unit]
  ]                                                       = js.undefined
  var rootProps: js.UndefOr[HTMLAttributes[dom.html.Div]] = js.undefined
  var onBeforeFocus: js.UndefOr[js.Function1[dom.html.Element, Boolean] | js.Function0[Boolean]] =
    js.undefined
  var allowFocusRoot: js.UndefOr[Boolean] = js.undefined
  var allowTabKey: js.UndefOr[Boolean]    = js.undefined

  /** FocusZoneTabbableElements */
  var handleTabKey: js.UndefOr[Int] = js.undefined
  var shouldInputLoseFocusOnArrowKey: js.UndefOr[js.Function1[dom.html.Input, Boolean]] =
    js.undefined
  var checkForNoWrap: js.UndefOr[Boolean] = js.undefined
}

object FocusZoneDirection {
  val vertical      = 0
  val horizontal    = 1
  val biderectional = 2
}

object FocusZoneTabbableElements {
  val none      = 0
  val all       = 1
  val inputOnly = 2
}

@js.native
trait IBasePicker[T] extends Focusable {
  var items: js.UndefOr[js.Array[T]] = js.undefined
  var focusInput: js.Function0[Unit]
}

trait IInputProps extends InputHTMLAttributes[dom.html.Input] {
  var `aria-label`: js.UndefOr[String] = js.undefined
}

object ValidationState {
  val valid   = 0
  val warning = 1
  val invalid = 2
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
  var onValidateInput: js.UndefOr[js.Function1[String, Int]] = js.undefined
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

trait ITag extends js.Object {
  var key: String
  var name: String
}

trait ITagPickerProps extends IBasePickerProps[ITag] {}

@js.native
trait IDropdown extends js.Object {

  /** Should focus on open? */
  var focus: js.Function1[Boolean, Unit]
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
  var panelProps: js.UndefOr[IPanelProps] = js.undefined
  var errorMessage: js.UndefOr[String]    = js.undefined
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

trait IDropdownProps extends ISelectableDroppableTextProps[dom.html.Div] {
  var placeholder: js.UndefOr[String] = js.undefined
  //var onChanged: js.UndefOr[js.Function1[IDropdownOption, Unit] | js.Function2[IDropdownOption, Int, Unit]] = js.undefined
  var onChanged: js.UndefOr[js.Function2[IDropdownOption, js.UndefOr[Int], Unit]] = js.undefined
  var onDismiss: js.UndefOr[js.Function0[Unit]]                                   = js.undefined
  var onRenderPlaceholder: js.UndefOr[IRenderFunction[IDropdownProps]]            = js.undefined
  var onRenderTitle
    : js.UndefOr[IRenderFunction[IDropdownOption] | IRenderFunction[js.Array[IDropdownOption]]] =
    js.undefined
  var onRenderCaretDown: js.UndefOr[IRenderFunction[IDropdownOption]] = js.undefined
  var dropdownWidth: js.UndefOr[Int]                                  = js.undefined

  /** Responsive mode */
  var responsiveMode: js.UndefOr[ResponsiveMode]                                   = js.undefined
  var multiselect: js.UndefOr[Boolean]                                  = js.undefined
  var defaultSelectedKeys: js.UndefOr[js.Array[String] | js.Array[Int]] = js.undefined
  var selectedKeys: js.UndefOr[js.Array[String] | js.Array[Int]]        = js.undefined
  var multiSelectDelimiter: js.UndefOr[String]                          = js.undefined
  var isDisabled: js.UndefOr[Boolean]                                   = js.undefined
}

trait ISelectableOption extends js.Object {
  var key: String | Int
  var text: String
  var itemType: js.UndefOr[Int]     = js.undefined
  var index: js.UndefOr[Int]        = js.undefined
  var ariaLabel: js.UndefOr[String] = js.undefined
  var selected: js.UndefOr[Boolean] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
}

object SelectableOptionMenuItemType {
  val Normal  = 0
  val Divider = 1
  val Header  = 2
}

trait IDropdownOption extends ISelectableOption {
  var data: js.UndefOr[js.Any]        = js.undefined
  var isSelected: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait INav extends js.Object {
  var selectedKey: js.UndefOr[String] = js.undefined
}

trait INavStyleProps extends js.Object {
  var theme: js.UndefOr[ITheme] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var isOnTop: js.UndefOr[Boolean] = js.undefined
  var isLink: js.UndefOr[Boolean] = js.undefined
  var isGroup: js.UndefOr[Boolean] = js.undefined
  var isExpanded: js.UndefOr[Boolean] = js.undefined
  var isSelected: js.UndefOr[Boolean] = js.undefined
  var isButtonEntry: js.UndefOr[Boolean] = js.undefined
  var navHeight: js.UndefOr[Double] = js.undefined
  var leftPadding: js.UndefOr[Double] = js.undefined
  var leftPaddingExpanded: js.UndefOr[Double] = js.undefined
  var rightPadding: js.UndefOr[Double] = js.undefined
  var position: js.UndefOr[Int] = js.undefined
  var groups: js.UndefOr[js.Array[INavLinkGroup]|Null] = js.undefined
}

trait INavStyles extends IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
  var linkText: js.UndefOr[IStyle] = js.undefined
  var link: js.UndefOr[IStyle] = js.undefined
  var compositeLink: js.UndefOr[IStyle] = js.undefined
  var chevronButton: js.UndefOr[IStyle] = js.undefined
  var chevronIcon: js.UndefOr[IStyle] = js.undefined
  var navItems: js.UndefOr[IStyle] = js.undefined
  var navItem: js.UndefOr[IStyle] = js.undefined
  var group: js.UndefOr[IStyle] = js.undefined
  var groupContent: js.UndefOr[IStyle] = js.undefined
}

trait INavProps extends ComponentRef[INav] {
  var styles: js.UndefOr[IStyleFunctionOrObject[INavStyleProps, INavStyles]] = js.undefined
  var theme: js.UndefOr[ITheme] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var groups: js.UndefOr[js.Array[INavLinkGroup]|Null] = js.undefined
  var onLinkClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], INavLink, Unit]] = js.undefined
  var onLinkExpandClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], INavLink, Unit]] = js.undefined  
  var isOnTop: js.UndefOr[Boolean] = js.undefined
  var initialSelectedKey: js.UndefOr[String] = js.undefined
  var selectedKey: js.UndefOr[String] = js.undefined
  var ariaLabel: js.UndefOr[String] = js.undefined
  var expandButtonAriaLabel: js.UndefOr[String] = js.undefined
}

trait INavLinkGroup extends js.Object {
  var name: js.UndefOr[String] = js.undefined
  var links: js.UndefOr[js.Array[INavLink]] = js.undefined
  var automationId: js.UndefOr[String] = js.undefined
  var collapseByDefault: js.UndefOr[Boolean] = js.undefined
  var onHeaderClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element] ,Boolean, Unit]] = js.undefined
}

trait INavLink extends WithIconProps {
  var name: js.UndefOr[String] = js.undefined
  var url: js.UndefOr[String] = js.undefined
  var key: js.UndefOr[String] = js.undefined
  var links: js.UndefOr[js.Array[INavLink]] = js.undefined
  var onClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], INavLink, Unit]] = js.undefined
  var automationId: js.UndefOr[String] = js.undefined
  var isExpanded: js.UndefOr[Boolean] = js.undefined
  var ariaLabel: js.UndefOr[String] = js.undefined
  var title: js.UndefOr[String] = js.undefined
  var target: js.UndefOr[String] = js.undefined
  var forceAnchor: js.UndefOr[Boolean] = js.undefined
}

trait IKeytipProps extends js.Object {
  val content: String
  var disabled: js.UndefOr[Boolean] = js.undefined
  var visible: js.UndefOr[Boolean] = js.undefined
  val keySeqences: js.Array[String]
}

@js.native
trait IOveflowSet extends js.Object {
  var focus: js.Function1[Boolean, Unit]
  var focusElement: js.Function1[dom.html.Element, Boolean]
}

trait IOverflowSetStyles extends IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
  var item: js.UndefOr[IStyle] = js.undefined
  var overflowButton: js.UndefOr[IStyle] = js.undefined
}

/** Subclass to add your own properties. */
trait IOverflowSetItemProps extends js.Object {
  val key: String
  var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
}

trait IOverflowSetProps extends js.Object {
  var className: js.UndefOr[String] = js.undefined
  var items:  js.UndefOr[Seq[IOverflowSetItemProps]] = js.undefined
  var vertical: js.UndefOr[Boolean] = js.undefined
  var overflowItems: js.UndefOr[Seq[IOverflowSetItemProps]] = js.undefined
  var doNotContainWithinFocusZone: js.UndefOr[Boolean] = js.undefined
  var role: js.UndefOr[String] = js.undefined
  /** Always make this a function. */
  var styles: js.UndefOr[IStyleFunctionOrObject[IOverflowSetProps,IOverflowSetStyles]] = js.undefined
}

@js.native
trait ISearchBox extends Focusable {
  def hasFocus(): Boolean = js.native
}

trait ISearchBoxStyles extends IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
  var iconContainer: js.UndefOr[IStyle] = js.undefined
  var icon: js.UndefOr[IStyle] = js.undefined
  var field: js.UndefOr[IStyle] = js.undefined
  var clearButton: js.UndefOr[IStyle] = js.undefined  
}

trait ISearchBoxStyleProps extends Attributes {
  //var theme: js.UndefOr[ITheme] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var hasFocus: js.UndefOr[Boolean] = js.undefined
  var underlined: js.UndefOr[Boolean] = js.undefined
  var hasInput: js.UndefOr[Boolean] = js.undefined
  var disableAnimation: js.UndefOr[Boolean] = js.undefined  
}

trait ISearchBoxProps extends InputHTMLAttributes[dom.html.Input] with Attributes {
  var ariaLabel: js.UndefOr[String] = js.undefined
  //var className: js.UndefOr[String] = js.undefined
  var clearButtonProps: js.UndefOr[IButtonProps] = js.undefined
  var disableAnimation: js.UndefOr[Boolean] = js.undefined
  //var placeholder: js.UndefOr[String] = js.undefined
  var underlined: js.UndefOr[Boolean] = js.undefined
  //var value: js.UndefOr[String]                       = js.undefined

  var styles: js.UndefOr[IStyleFunction[ISearchBoxStyleProps, ISearchBoxStyles]] = js.undefined

  /** new value, as you type */
  //var onChange: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
  /** Event returned. */
  var onClear: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
  /** Event returned. */
  var onEscape: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
  /** For onChange & onSearch, return the string value or "" if no value.
   * You can use implicit `.toTruthyUndefOr.toOption` to set "" => None.
   */
  var onSearch: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
}

@js.native
trait IPanel extends js.Object {
  def open(): Unit = js.native
  def dismiss(ev: ReactKeyboardEvent[dom.html.Element]): Unit = js.native
}

trait IPanelStyleProps extends js.Object {
  var className: js.UndefOr[String] = js.undefined
  var isOpen: js.UndefOr[Boolean] = js.undefined
  var isAnimating: js.UndefOr[Boolean] = js.undefined
  var isOnRightSide: js.UndefOr[Boolean] = js.undefined
  var isHiddenOnDismiss: js.UndefOr[Boolean] = js.undefined
  var isFooterAtBottom: js.UndefOr[Boolean] = js.undefined
  var isFooterSticky: js.UndefOr[Boolean] = js.undefined
  var hasCloseButton: js.UndefOr[Boolean] = js.undefined
  var `type`: js.UndefOr[PanelType] = js.undefined
  var headerClassName: js.UndefOr[String] = js.undefined
}

trait IPanelStyles extends IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
  var overlay: js.UndefOr[IStyle] = js.undefined
  var hiddenPanel: js.UndefOr[IStyle] = js.undefined
  var main: js.UndefOr[IStyle] = js.undefined
  var commands: js.UndefOr[IStyle] = js.undefined
  var contentInner: js.UndefOr[IStyle] = js.undefined
  var scrollableContent: js.UndefOr[IStyle] = js.undefined
  var navigation: js.UndefOr[IStyle] = js.undefined
  var closeButton: js.UndefOr[IStyle] = js.undefined
  var header: js.UndefOr[IStyle] = js.undefined
  var headerText: js.UndefOr[IStyle] = js.undefined
  var content: js.UndefOr[IStyle] = js.undefined
  var footer: js.UndefOr[IStyle] = js.undefined
  var footerInner: js.UndefOr[IStyle] = js.undefined
}

@js.native
sealed trait PanelType extends js.Any

object PanelType {
  val smallFluid = 0.asInstanceOf[PanelType]
  val smallFixedFar = 1.asInstanceOf[PanelType]
  val smallFixedNear = 2.asInstanceOf[PanelType]
  val medium = 3.asInstanceOf[PanelType]
  val large = 4.asInstanceOf[PanelType]
  val largeFixed = 5.asInstanceOf[PanelType]
  val extraLarge = 6.asInstanceOf[PanelType]
  val custom = 7.asInstanceOf[PanelType]
}

trait IPanelProps extends ComponentRef[IPanel] {
  var isOpen: js.UndefOr[Boolean] = js.undefined
  var hasCloseButton: js.UndefOr[Boolean] = js.undefined
  var isLightDismiss: js.UndefOr[Boolean] = js.undefined
  var isHiddenOnDismiss: js.UndefOr[Boolean] = js.undefined
  var isBlocking: js.UndefOr[Boolean] = js.undefined
  var isFooterAtBottom: js.UndefOr[Boolean] = js.undefined
  var headerText: js.UndefOr[String] = js.undefined
  var onDismss: js.UndefOr[js.Function1[ReactEvent[dom.html.Element], Unit]] = js.undefined
  var onDismissed: js.UndefOr[js.Function0[Unit]] = js.undefined
  var styles: js.UndefOr[IStyleFunctionOrObject[IPanelStyleProps, IPanelStyles]] = js.undefined
  var theme: js.UndefOr[ITheme] = js.undefined
  var `type`: js.UndefOr[PanelType] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var customWidth: js.UndefOr[String] = js.undefined
  var closeButtonAriaLabel: js.UndefOr[String] = js.undefined
  var headerClassName: js.UndefOr[String] = js.undefined
  var elementToFocusOnDismiss: js.UndefOr[dom.html.Element] = js.undefined
  var layerProps: js.UndefOr[ILayerProps] = js.undefined
  var onLightDismissClick: js.UndefOr[js.Function0[Unit]] = js.undefined
  var onOuterClick: js.UndefOr[js.Function0[Unit]] = js.undefined

  var onRenderNavigation: js.UndefOr[IRenderFunction[IPanelProps]] = js.undefined
  /** This is not quite right. See API docs. */
  var onRenderHeader: js.UndefOr[IRenderFunction[IPanelProps]] = js.undefined
  var onRenderBody: js.UndefOr[IRenderFunction[IPanelProps]] = js.undefined
  var onRenderFooter: js.UndefOr[IRenderFunction[IPanelProps]] = js.undefined
  var onRenderFooterContent: js.UndefOr[IRenderFunction[IPanelProps]] = js.undefined
}

trait IMessageBar extends js.Object {
}

@js.native
sealed trait MessageBarType extends js.Any

object MessageBarType {
  val info = 0.asInstanceOf[MessageBarType]
  val error = 1.asInstanceOf[MessageBarType]
  val blocked = 2.asInstanceOf[MessageBarType]
  val severeWarning = 3.asInstanceOf[MessageBarType]
  val success = 4.asInstanceOf[MessageBarType]
  val warning = 5.asInstanceOf[MessageBarType]
}

trait IMessageBarStyleProps extends js.Object {
  var className : js.UndefOr[IStyle] = js.undefined
  var messageBarType: js.UndefOr[MessageBarType] = js.undefined
  var onDismiss: js.UndefOr[Boolean] = js.undefined
  var truncated: js.UndefOr[Boolean] = js.undefined
  var isMultiline: js.UndefOr[Boolean] = js.undefined
  var expandSingleLine: js.UndefOr[Boolean] = js.undefined
  var actions: js.UndefOr[Boolean] = js.undefined
}

trait IMessageBarStyles extends IStyleSetTag {
  var root: js.UndefOr[IStyle] = js.undefined
  var content: js.UndefOr[IStyle] = js.undefined
  var iconContainer: js.UndefOr[IStyle] = js.undefined
  var icon: js.UndefOr[IStyle] = js.undefined
  var text: js.UndefOr[IStyle] = js.undefined
  var innerText: js.UndefOr[IStyle] = js.undefined
  var dismissal: js.UndefOr[IStyle] = js.undefined
  var expand: js.UndefOr[IStyle] = js.undefined
  var dismissSingleLine: js.UndefOr[IStyle] = js.undefined
  var expandSingleLine: js.UndefOr[IStyle] = js.undefined
  var actions: js.UndefOr[IStyle] = js.undefined
}

trait IMessageBarProps extends HTMLAttributes[dom.html.Element]
    with ComponentRef[IMessageBar] with Theme with Attributes {
  var messageBarType: js.UndefOr[MessageBarType] = js.undefined
  var actions: js.UndefOr[ReactElement] = js.undefined
  var arialLabel: js.UndefOr[String] = js.undefined
  var onDimiss: js.UndefOr[js.Function1[js.Any, js.Any]] = js.undefined
  var isMultiline: js.UndefOr[Boolean] = js.undefined
  var dismissButtonAriaLabel: js.UndefOr[String] = js.undefined
  var truncated: js.UndefOr[Boolean] = js.undefined
  var overflowButtonAriaLabel: js.UndefOr[String] = js.undefined
  var styles: js.UndefOr[IStyleFunctionOrObject[js.Object, IStyleSetTag]] = js.undefined
}

trait ILayerStyles extends IStyleSetTag with Theme {
  var root: js.UndefOr[IStyle] = js.undefined
  var content: js.UndefOr[IStyle] = js.undefined
}

trait ILayerStyleProps extends js.Object {
  var className: js.UndefOr[String] = js.undefined
  var isNotHost: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait ILayer extends js.Object

trait ILayerProps extends HTMLAttributes[dom.html.Div] with ComponentRef[ILayer] {
  var styles: js.UndefOr[IStyleFunctionOrObject[ILayerStyleProps, ILayerStyles]] = js.undefined
}

trait IShimmeredDetailsListStyleProps extends js.Object {
  var enableShimmer: js.UndefOr[Boolean] = js.undefined
}

trait IShimmeredDetailsListStyles extends  {
  var root: js.UndefOr[IStyle] = js.undefined
}

trait IShimmeredDetailsListProps[T <: js.Object] extends IDetailsListProps[T] {
  var enableShimmer: js.UndefOr[Boolean] = js.undefined  
  var shimmerLines: js.UndefOr[Int] = js.undefined
  var onRenderCustomPlaceholder: js.UndefOr[js.Function0[ReactNode]] = js.undefined
}

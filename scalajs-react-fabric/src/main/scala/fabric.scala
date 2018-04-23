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
  val Label: ReactJsComponent                 = js.native
  val Link: ReactJsComponent                  = js.native
  val List: ReactJsComponent                  = js.native
  val MarqueeSelection: ReactJsComponent      = js.native
  val Pivot: ReactJsComponent                 = js.native
  val PivotItem: ReactJsComponent             = js.native
  val PrimaryButton: ReactJsComponent         = js.native
  val Spinner: ReactJsComponent               = js.native
  def Selection[T <: js.Object]: Selection[T] = js.native
  val ScrollablePane: ReactJsComponent        = js.native
  val Sticky: ReactJsComponent                = js.native
  val FocusZone: ReactJsComponent = js.native
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

  def Fabric(props: IFabricProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Fabric, props, children: _*)

  def FocusZone[T <: js.Object](props: IFocusZoneProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.FocusZone, props, children: _*)

  def Label(props: ILabelProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Label, props, children: _*)

  def Link(props: ILinkProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Link, props, children: _*)

  def List[T <: js.Object](props: IListProps[T] = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.List, props, children: _*)

  def MarqueeSelection[T <: js.Object](props: IMarqueeSelectionProps[T] = null)(
      children: ReactNode*) =
    wrapJsForScala(FabricNS.MarqueeSelection, props, children: _*)

  def PrimaryButton(props: IButtonProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.PrimaryButton, props, children: _*)

  def Pivot(props: IPivotProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Pivot, props, children: _*)

  def PivotItem(props: IPivotItemProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.PivotItem, props, children: _*)

  def Spinner(props: ISpinnerProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Spinner, props, children: _*)

  def ScrollablePane(props: IScrollablePane = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.ScrollablePane, props, children: _*)

  def Sticky(props: IStickyProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.Sticky, props, children: _*)

  def TextField(props: ITextFieldProps = null)(children: ReactNode*) =
    wrapJsForScala(FabricNS.TextField, props, children: _*)

}

//
// common parts
//
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

trait ComponentRef[T] extends js.Object {
  var componentRef: js.UndefOr[js.Function1[T, Unit]] = js.undefined
}

trait Disabled extends js.Object {
  var disabled: js.UndefOr[Boolean] = js.undefined
}

trait Theme extends js.Object {
  var theme: js.UndefOr[js.Any] = js.undefined
}

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

trait IPivotItemProps extends HTMLAttributes[dom.html.Div] {
  var linkText: js.UndefOr[String]                                   = js.undefined
  var itemKey: js.UndefOr[String]                                    = js.undefined
  var itemCount: js.UndefOr[Int]                                     = js.undefined
  var itemIcon: js.UndefOr[String]                                   = js.undefined
  var ariaLabel: js.UndefOr[String]                                  = js.undefined
  var onRenderItemLink: js.UndefOr[IRenderFunction[IPivotItemProps]] = js.undefined
}

trait IPivotProps extends js.Object {
  var initialSelectedKey: js.UndefOr[String] = js.undefined
  var initialSelectedIndex: js.UndefOr[Int]  = js.undefined
  var selectedKey: js.UndefOr[String]        = js.undefined
  var onLinkClick: js.UndefOr[
    ((js.Object, js.UndefOr[ReactMouseEvent[dom.html.Element]]) => Unit) |(() => Unit)] =
    js.undefined
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

//export interface ITextFieldProps extends React.AllHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
// withDisabled

// should also included attributes from dom.html.TextArea
trait ITextFieldProps
    extends WithIconProps
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
  //var iconProps?: IIconProps;
  // defined in HTMLAttributes
  //var defaultValue: js.UndefOr[String] = js.undefined
  //var value: js.UndefOr[String] = js.undefined
  var errorMessage: js.UndefOr[String]                       = js.undefined
  var onChanged: js.UndefOr[js.Function1[String, Unit]]      = js.undefined
  var onBeforeChange: js.UndefOr[js.Function1[String, Unit]] = js.undefined
  //onNotifyValidationResult?: (errorMessage: string, value: string | undefined) => void;
  //onGetErrorMessage?: (value: string) => string | PromiseLike<string> | undefined;
  var deferredValidationTime: js.UndefOr[Long] = js.undefined
  //var className: js.UndefOr[String] = js.undefined
  var inputClassName: js.UndefOr[String]      = js.undefined
  var ariaLabel: js.UndefOr[String]           = js.undefined
  var validateOnFocusIn: js.UndefOr[Boolean]  = js.undefined
  var validateOnFocusOut: js.UndefOr[Boolean] = js.undefined
  var validateOnLoad: js.UndefOr[Boolean]     = js.undefined
  var componentId: js.UndefOr[String]         = js.undefined
}

@js.native
trait IList extends js.Object {
  def scrollToIndex(index: Int, measureItem: js.UndefOr[js.Function1[Int, Int]]): Unit = js.native
}

@js.native
trait IDetailsList extends IList {
  def forceUpdate(): Unit = js.native
}

trait IColumn extends js.Object {
  val key: String
  val name: String
  val fieldName: String
  val minWidth: Int
  var data: js.UndefOr[scala.Any]        = js.undefined
  var isCollapsible: js.UndefOr[Boolean] = js.undefined
  var isSorted: js.UndefOr[Boolean]      = js.undefined
}

object IColumn {
  def toCol(a: js.Dynamic): IColumn = a.asInstanceOf[IColumn]
}

object CheckboxVisibility {
  val onHover = 0
  val always  = 1
  val hidden  = 2
}

object DetailsListLayoutMode {
  val fixedColumns = 0
  val justified    = 1
}

object ConstrainMode {
  val unconstrained         = 0
  val horizontalConstrained = 1
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

trait IDetailsListProps[T <: js.Object] extends ComponentRef[IDetailsList] {
  val items: js.Array[T]
  var setKey: js.UndefOr[String]                      = js.undefined
  var className: js.UndefOr[String]                   = js.undefined
  var checkboxCellClassName: js.UndefOr[String]       = js.undefined
  var enterModalSelectionOnTouch: js.UndefOr[Boolean] = js.undefined
  var columns: js.UndefOr[js.Array[IColumn] | js.Array[js.Object] | js.Array[js.Dynamic]] =
    js.undefined
  var constrainMode: js.UndefOr[Int] = js.undefined
  var listProps
    : js.UndefOr[js.Dynamic] = js.undefined // should be IListProps but then items is required
  var getKey: js.UndefOr[js.Function2[T, js.UndefOr[Int], String] | js.Function1[T, String]] =
    js.undefined
  var initialFocusedIndex: js.UndefOr[Int]                                       = js.undefined
  var selection: js.UndefOr[ISelection[T]]                                       = js.undefined
  var selectionPreservedOnEmptyClick: js.UndefOr[Boolean]                        = js.undefined
  var layoutMode: js.UndefOr[Int]                                                = js.undefined
  var isHeaderVisible: js.UndefOr[Boolean]                                       = js.undefined
  var compact: js.UndefOr[Boolean]                                               = js.undefined
  var usePageCache: js.UndefOr[Boolean]                                          = js.undefined
  var onRenderRow: js.UndefOr[IRenderFunction[IDetailsRowProps]]                 = js.undefined
  var onRenderItemColumn: js.UndefOr[js.Function3[js.Any, Int, IColumn, js.Any]] = js.undefined
  var onRenderMissingItem: js.UndefOr[js.Function1[Int, js.Any]]                 = js.undefined
  var onRenderDetailsHeader: js.UndefOr[IRenderFunction[IDetailsHeaderProps]]    = js.undefined
  type OAIC = js.Function3[
    js.UndefOr[T],
    js.UndefOr[Int],
    js.UndefOr[SyntheticFocusEvent[dom.html.Element]],
    Unit]
  var onActiveItemChanged: js.UndefOr[OAIC] = js.undefined
  type OCHC =
    js.Function2[js.UndefOr[SyntheticMouseEvent[dom.html.Element]], js.UndefOr[IColumn], Unit]
  var onColumnHeaderClick: js.UndefOr[OCHC] = js.undefined

  var renderedWindowsAhead: js.UndefOr[Int]                                = js.undefined
  var renderedWindowsBehind: js.UndefOr[Int]                               = js.undefined
  var onShouldVirtualize: js.UndefOr[js.Function1[IListProps[T], Boolean]] = js.undefined
}

trait IListProps[T <: js.Object] extends IWithViewportProps with ComponentRef[IDetailsList] {
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

  type OII = js.Function3[
    js.UndefOr[T],
    js.UndefOr[Int],
    js.UndefOr[SyntheticFocusEvent[dom.html.Element]],
    Unit]
  var onItemInvoked: js.UndefOr[OII] = js.undefined

  var onRenderRow: js.UndefOr[IRenderFunction[IDetailsRowProps]]              = js.undefined
  var onRenderMissingItem: js.UndefOr[js.Function1[Int, js.Any]]              = js.undefined
  var onRenderDetailsHeader: js.UndefOr[IRenderFunction[IDetailsHeaderProps]] = js.undefined

  type OAIC = js.Function3[
    js.UndefOr[T],
    js.UndefOr[Int],
    js.UndefOr[SyntheticFocusEvent[dom.html.Element]],
    Unit]
  var onActiveItemChanged: js.UndefOr[OAIC] = js.undefined
  type OCHC =
    js.Function2[js.UndefOr[SyntheticMouseEvent[dom.html.Element]], js.UndefOr[IColumn], Unit]
  var onColumnHeaderClick: js.UndefOr[OCHC] = js.undefined

  var maximumPixelsForDrag: js.UndefOr[Int]           = js.undefined
  var compact: js.UndefOr[Boolean]                    = js.undefined
  var checkboxCellClassName: js.UndefOr[String]       = js.undefined
  var enterModelSelectionOnTouch: js.UndefOr[Boolean] = js.undefined

  var usePageCache: js.UndefOr[Boolean] = js.undefined
  var renderCount: js.UndefOr[Int]      = js.undefined
}

trait IContextualMenuProps extends KeyAndRef {
  var items: js.Array[IContextualMenuItem]
}

trait IContextualMenuItem extends WithIconProps {
  val key: String
  var name: js.UndefOr[String] = js.undefined

  /** 1 => divider, 0 => normal */
  var itemType: js.UndefOr[Int]     = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  type OC2 = js.Function2[ReactMouseEvent[dom.html.Element], js.UndefOr[IContextualMenuItem], Unit]
  type OC0 = js.Function0[Unit]
  var onClick: js.UndefOr[OC2 | OC0]                 = js.undefined
  var subMenuProps: js.UndefOr[IContextualMenuProps] = js.undefined
  var className: js.UndefOr[String]                  = js.undefined
  var title: js.UndefOr[String]                      = js.undefined
  // cast your func to this to make it easier
  type RF2 = js.Function2[js.Object, js.Function2[js.Any, js.UndefOr[Boolean], Unit], ReactNode]
  // cast your func to this to make it easier
  type RF0 = js.Function0[ReactNode]
  var onRender: js.UndefOr[RF2 | RF0] = js.undefined
}

@js.native
trait ICommandBar extends Focusable {}

trait ICommandBarProps extends js.Object {
  var componentRef: js.UndefOr[ICommandBar => Unit] = js.undefined
  var isSearchBoxVisble: js.UndefOr[Boolean]        = js.undefined
  var searchBoxPlaceholderText: js.UndefOr[String]  = js.undefined
  val items: js.Array[IContextualMenuItem]
  var farItems: js.UndefOr[js.Array[IContextualMenuItem]] = js.undefined
}

@js.native
trait ISpinner extends js.Object {}

trait ISpinnerProps extends KeyAndRef {
  var componentRef: js.UndefOr[ISpinner => Unit] = js.undefined
  var size: js.UndefOr[Int]                      = js.undefined
  var label: js.UndefOr[String]                  = js.undefined
  var className: js.UndefOr[String]              = js.undefined
  var ariaLabel: js.UndefOr[String]              = js.undefined
}

object SpinnerSize {
  var xSmall = 0
  var small  = 1
  var medium = 2
  var large  = 3
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
}

trait IScrollablePaneProps
    extends HTMLAttributes[dom.html.Element]
    with ComponentRef[IScrollablePane] {}

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
    with AllHTMLAttributes[dom.html.Button] {
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

@js.native
trait ILink extends Focusable

//export interface ILinkProps extends React.AllHTMLAttributes<HTMLAnchorElement | HTMLButtonElement | HTMLElement | Link>

trait ILinkProps extends AllHTMLAttributes[dom.html.Anchor] with ComponentRef[ILink]
@js.native
trait IMarqueeSelection extends js.Object {}

trait IMarqueeSelectionProps[T <: js.Object] extends ComponentRef[IMarqueeSelection] {
  var selection: js.UndefOr[ISelection[T]]                              = js.undefined
  var rootProps: js.UndefOr[HTMLAttributes[dom.html.Div]]               = js.undefined
  var onShouldStartSelection: js.UndefOr[js.Function1[js.Any, Boolean]] = js.undefined
  var isEnabled: js.UndefOr[Boolean]                                    = js.undefined
  var isDraggingConstrainedToRoot: js.UndefOr[Boolean]                  = js.undefined

}


@js.native 
trait IFocusZone extends js.Object {
  def focus(forceIntoFirstElement: js.UndefOr[Boolean]): Boolean = js.native
  def focusElement(childElement: js.UndefOr[dom.html.Element]): Boolean = js.native
}

trait IFocusZoneProps extends ComponentRef[IFocusZone] with HTMLAttributes[dom.html.Div] {
  //var className: js.UndefOr[String] = js.undefined
  var direction: js.UndefOr[Int] = js.undefined
  var defaultActiveElement: js.UndefOr[String] = js.undefined
  var disabled:  js.UndefOr[Boolean]  = js.undefined
  /** e.g. div */
  var elementType: js.UndefOr[String] = js.undefined
  var isCircularNavigation: js.UndefOr[Boolean]  = js.undefined
  var isInnerZoneKeyStroke: js.UndefOr[js.Function1[SyntheticKeyboardEvent[dom.html.Element], Boolean]] = js.undefined
  var ariaLabelledBy: js.UndefOr[String] = js.undefined
  var ariaDescribedBy: js.UndefOr[String] = js.undefined
  var onActiveElementChanged: js.UndefOr[
    js.Function0[Unit] |
      js.Function2[js.UndefOr[dom.html.Element], js.UndefOr[SyntheticFocusEvent[dom.html.Element]], Unit]
  ] = js.undefined
  var rootProps: js.UndefOr[HTMLAttributes[dom.html.Div]] = js.undefined
  var onBeforeFocus: js.UndefOr[js.Function1[dom.html.Element, Boolean]|js.Function0[Boolean]] = js.undefined
  var allowFocusRoot: js.UndefOr[Boolean]  = js.undefined
  var allowTabKey: js.UndefOr[Boolean] = js.undefined
  /** FocusZoneTabbableElements */
  var handleTabKey: js.UndefOr[Int] = js.undefined
  var shouldInputLoseFocusOnArrowKey: js.UndefOr[js.Function1[dom.html.Input, Boolean]] = js.undefined
  var checkForNoWrap:js.UndefOr[Boolean] = js.undefined
}

object FocusZoneDirection {
  val vertical = 0
  val horizontal = 1
  val biderectional = 2
}

object FocusZoneTabbableElements {
  val none = 0
  val all = 1
  val inputOnly = 2
}

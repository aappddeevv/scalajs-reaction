// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import ttg.react._
import vdom._
import fabric.styling._

object Details {

  trait ICellStyleProps extends js.Object {
    var  cellLeftPadding: js.UndefOr[Double] = js.undefined
    var  cellRightPadding: js.UndefOr[Double] = js.undefined
    var  cellExtraRightPadding: js.UndefOr[Double] = js.undefined
  }

  object Row {
    @js.native
    @JSImport("office-ui-fabric-react/lib/DetailsList", "DetailsRow")
    object JS extends ReactJsComponent

    def apply(props: Props = null)(children: ReactNode*) =
      React.createElement(JS, props)(children:_*)

    @js.native
    trait IDetailsRow extends js.Object

    trait BaseProps extends Item.Props {
      var theme: js.UndefOr[ITheme] = js.undefined
      var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
      var item: js.UndefOr[js.Any] = js.undefined
      var itemIndex: js.UndefOr[Int] = js.undefined
      var compact: js.UndefOr[Boolean] = js.undefined
      // on*
      // onRender*
      var className: js.UndefOr[String] = js.undefined
      var shimmer: js.UndefOr[Boolean] = js.undefined
      var useReducedRowRenderer: js.UndefOr[Boolean] = js.undefined
      var checkboxCellClassName: js.UndefOr[String]    = js.undefined
    }

    trait Props extends BaseProps {
      //var selectionMode: SelectionMode
      //var selection: ISelection[js.Object]
      //var columns: js.Array[IColumn]
    }

    trait StyleProps extends js.Object {
      var isSelected: js.UndefOr[Boolean] = js.undefined
      var anySelected: js.UndefOr[Boolean] = js.undefined
      var canSelect: js.UndefOr[Boolean] = js.undefined
      var droppingClassName: js.UndefOr[String] = js.undefined
      var isCheckVisible: js.UndefOr[Boolean] = js.undefined
      var isRowHeader: js.UndefOr[Boolean] = js.undefined
      var checkboxCellClassName: js.UndefOr[String] = js.undefined
      var className: js.UndefOr[String] = js.undefined
      var compact: js.UndefOr[Boolean] = js.undefined
      var cellStyleProps: js.UndefOr[ICellStyleProps] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var  root: js.UndefOr[IStyle] = js.undefined
      var  cell: js.UndefOr[IStyle] = js.undefined
      var  cellUnpadded: js.UndefOr[IStyle] = js.undefined
      var  cellPadded: js.UndefOr[IStyle] = js.undefined
      var  checkCell: js.UndefOr[IStyle] = js.undefined
      var  isRowHeader: js.UndefOr[IStyle] = js.undefined
      var  isMultiline: js.UndefOr[IStyle] = js.undefined
      var  fields: js.UndefOr[IStyle] = js.undefined
      var  cellMeasurer: js.UndefOr[IStyle] = js.undefined
      var  checkCover: js.UndefOr[IStyle] = js.undefined
      var  shimmer: js.UndefOr[IStyle] = js.undefined
      var  shimmerIconPlaceholder: js.UndefOr[IStyle] = js.undefined
      var  shimmerLeftBorder: js.UndefOr[IStyle] = js.undefined
      var  shimmerBottomBorder: js.UndefOr[IStyle] = js.undefined
      var  check: js.UndefOr[IStyle] = js.undefined
    }
  }

  object Header {

    @js.native
    @JSImport("office-ui-fabric-react/lib/DetailsList", "DetailsHeader")
    object JS extends ReactJsComponent

    def apply(props: Props = null)(children: ReactNode*) =
      React.createElement(JS, props)(children:_*)

    @js.native
    trait IDetailsHeader extends Focusable2

    trait BaseProps
        extends Theme
        with ClassName
        with ClassAttributes[IDetailsHeader] // is this right? maybe ReactElement?
        with ComponentRef[IDetailsHeader] {
      var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
      var layoutMode: js.UndefOr[List.LayoutMode] = js.undefined
      var onColumnIsSizingChanged: js.UndefOr[js.Function2[IColumn, Boolean, Unit]] = js.undefined
      var onColumnResized: js.UndefOr[js.Function3[IColumn, Double, Int, Unit]] = js.undefined
      var onColumnAutoResized: js.UndefOr[js.Function2[IColumn, Int, Unit]] = js.undefined
      var onColumnClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], IColumn, Unit]] = js.undefined
      // onColumnContextMenu
      // onRenderColumnHeaderTooltip
      // collapseAllVisibility
      var isAllCollapsed: js.UndefOr[Boolean] = js.undefined
      // onToggleCollapseAll
      var ariaLabel: js.UndefOr[String] = js.undefined
      // arialLabelForSelectAllCheckbox
      var selectAllVisibility: js.UndefOr[SelectAllVisibility] = js.undefined
      var columnReorderOptions: js.UndefOr[IColumnReorderOptions] = js.undefined
      var minimumPixelsForDrag: js.UndefOr[Int] = js.undefined
    }

    // trait Props extends ComponentRef[IDetailsHeader] {
    //   var columns: js.Array[IColumn]
    //   var layoutMode: js.UndefOr[Int]        = js.undefined
    //   var groupNestingDepth: js.UndefOr[Int] = js.undefined
    // }
    trait Props extends BaseProps {
      var selectionMode: SelectionMode
      var selection: ISelection[js.Object]
      var columns: js.Array[IColumn]
    }

    @js.native
    sealed trait SelectAllVisibility extends js.Any
    object SelectAllVisibility {
      val none = 0.asInstanceOf[SelectAllVisibility]
      val hidden = 1.asInstanceOf[SelectAllVisibility]
      val visible = 2.asInstanceOf[SelectAllVisibility]
    }

    @js.native
    trait State extends js.Object {
      // columnReorderProps
      // columnResizeDetails
      // isAllSelected
      // isSizing
      // groupNestingDepth
      // isAllCollapsed
    }

    trait StyleProps extends Theme with ClassName {
      var isSelectedAllHidden: js.UndefOr[Boolean] = js.undefined
      var isAllSelected: js.UndefOr[Boolean] = js.undefined
      var isResizingColumn: js.UndefOr[Boolean] = js.undefined
      var isAllCollapsed: js.UndefOr[Boolean] = js.undefined
      var isSizing: js.UndefOr[Boolean] = js.undefined
      var isCheckboxHidden: js.UndefOr[Boolean] = js.undefined
      var cellStyleProps: js.UndefOr[ICellStyleProps] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var  root: js.UndefOr[IStyle] = js.undefined
      var  check: js.UndefOr[IStyle] = js.undefined
      var  cellWrapperPadded: js.UndefOr[IStyle] = js.undefined
      var  cellIsCheck: js.UndefOr[IStyle] = js.undefined
      var  cellIsActionable: js.UndefOr[IStyle] = js.undefined
      var  cellIsEmpty: js.UndefOr[IStyle] = js.undefined
      var  cellSizer: js.UndefOr[IStyle] = js.undefined
      var  cellSizerStart: js.UndefOr[IStyle] = js.undefined
      var  cellSizerEnd: js.UndefOr[IStyle] = js.undefined
      var  cellIsResizing: js.UndefOr[IStyle] = js.undefined
      var  cellIsGroupExpander: js.UndefOr[IStyle] = js.undefined
      var  collapseButton: js.UndefOr[IStyle] = js.undefined
      var  checkTooltip: js.UndefOr[IStyle] = js.undefined
      var  sizingOverlay: js.UndefOr[IStyle] = js.undefined
      var  dropHintCircleStyle: js.UndefOr[IStyle] = js.undefined
      var  dropHintCaretStyle: js.UndefOr[IStyle] = js.undefined
      var  dropHintLineStyle: js.UndefOr[IStyle] = js.undefined
      var  dropHintStyle: js.UndefOr[IStyle] = js.undefined
      var  accessibleLabel: js.UndefOr[IStyle] = js.undefined
    }
  }

  object Item {
    trait Props extends IViewportProps {
      var columns: js.UndefOr[js.Array[IColumn]] = js.undefined
      var groupNestingDepth: js.UndefOr[Double] = js.undefined
      var indentWidth: js.UndefOr[Double] = js.undefined
      var selection: js.UndefOr[ISelection[js.Object]] = js.undefined
      var selectionMode: js.UndefOr[SelectionMode] = js.undefined
      var checkboxVisibility: js.UndefOr[CheckboxVisibility] = js.undefined
      var cellStyleProps: js.UndefOr[ICellStyleProps] = js.undefined
    }
  }


  object List {
    @js.native
    @JSImport("office-ui-fabric-react/lib/DetailsList", "DetailsList")
    object JS extends ReactJsComponent

    def apply[T <: js.Object](props: Props[T] = null)(children: ReactNode*) =
      React.createElement(JS, props)(children:_*)

    @js.native
    trait IDetailsList extends components.List.IList {
      def focusIndex(
        index: Int,
        forceIntoFirstElement: js.UndefOr[Boolean],
        measureItem: js.UndefOr[js.Function1[Int, Int]],
        scrollToMode: js.UndefOr[components.List.ScrollToMode] = js.undefined): Unit = js.native
    }

    @js.native
    sealed trait LayoutMode extends js.Any
    object LayoutMode {
      val fixedColumns = 0.asInstanceOf[LayoutMode]
      val justified    = 1.asInstanceOf[LayoutMode]
    }

    @js.native
    abstract sealed trait ConstrainMode extends js.Any
    object ConstrainMode {
      val unconstrained         = 0.asInstanceOf[ConstrainMode]
      val horizontalConstrained = 1.asInstanceOf[ConstrainMode]
    }

    trait Props[T <: js.Object]
        extends ComponentRef[IDetailsList]
        with Attributes
        with IViewportProps
        with Theme {
      //val items: js.Array[T]
      var items: js.UndefOr[js.Array[T]] = js.undefined
      var setKey: js.UndefOr[String]                      = js.undefined
      var className: js.UndefOr[String]                   = js.undefined
      //var groups: js.UndefOr[js.Array[IGroup]] = js.undefined
      var indentWidth: js.UndefOr[Double] = js.undefined
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
      var selectionMode: js.UndefOr[SelectionMode] = js.undefined
      var selectionPreservedOnEmptyClick: js.UndefOr[Boolean]                        = js.undefined
      var layoutMode: js.UndefOr[LayoutMode]                                                = js.undefined
      var isHeaderVisible: js.UndefOr[Boolean]                                       = js.undefined
      var compact: js.UndefOr[Boolean]                                               = js.undefined
      var usePageCache: js.UndefOr[Boolean]                                          = js.undefined
      var onRenderRow: js.UndefOr[IRenderFunction[Details.Row.Props]]                 = js.undefined
      var onRenderItemColumn: js.UndefOr[js.Function3[js.Any, Int, IColumn, js.Any]] = js.undefined
      var onRenderMissingItem: js.UndefOr[js.Function1[Int, js.Any]]                 = js.undefined
      var onRenderDetailsHeader: js.UndefOr[IRenderFunction[Details.Header.Props]]    = js.undefined
      /** You can still be "active" but not deselected. */
      var onActiveItemChanged: js.UndefOr[components.List.OAIC[T]] = js.undefined
      @JSName("onActiveItemChanged")
      var onActiveItemChanged2: js.UndefOr[js.Function2[T,Int,Unit]] = js.undefined
      @JSName("onActiveItemChanged")      
      var onActiveItemChanged1: js.UndefOr[js.Function1[T,Unit]] = js.undefined
      var onColumnHeaderClick: js.UndefOr[components.List.OCHC[T]] = js.undefined
      var onItemInvoked: js.UndefOr[components.List.OII[T]] = js.undefined
      @JSName("onItemInvoked")
      var onItemInvoked2: js.UndefOr[js.Function2[T,Int,Unit]] = js.undefined
      @JSName("onItemInvoked")      
      var onItemInvoked1: js.UndefOr[js.Function1[T,Unit]] = js.undefined
      var renderedWindowsAhead: js.UndefOr[Int]                                = js.undefined
      var renderedWindowsBehind: js.UndefOr[Int]                               = js.undefined
      var onShouldVirtualize: js.UndefOr[js.Function1[components.List.Props[T], Boolean]] = js.undefined
      //var styles: js.UndefOr[styling.IStyleFunctionOrObject[IDetailsListStyleProps, IDetailsListStyles]] = js.undefined
    }

    trait StyleProps extends js.Object {
      var isHorizontalConstrained: js.UndefOr[Boolean] = js.undefined
      var compact: js.UndefOr[Boolean] = js.undefined
      var isFixed: js.UndefOr[Boolean] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var root: js.UndefOr[IStyle] = js.undefined
      var focusZone: js.UndefOr[IStyle] = js.undefined
    }
  }

  object Shimmered {
    @js.native
    @JSImport("office-ui-fabric-react/lib/ShimmeredDetailsList", "ShimmeredDetailsList")
    object JS extends ReactJsComponent

    def apply[T <: js.Object](props: Props[T] = null)(children: ReactNode*) =
      React.createElement(JS, props)(children: _*)

    trait StyleProps extends ClassName {
      var enableShimmer: js.UndefOr[Boolean] = js.undefined
    }

    trait Styles extends  IStyleSetTag {
      var root: js.UndefOr[IStyle] = js.undefined
    }

    trait Props[T <: js.Object] extends List.Props[T] {
      var enableShimmer: js.UndefOr[Boolean] = js.undefined
      var shimmerLines: js.UndefOr[Int] = js.undefined
      var onRenderCustomPlaceholder: js.UndefOr[js.Function0[ReactNode]] = js.undefined
    }
  }

}


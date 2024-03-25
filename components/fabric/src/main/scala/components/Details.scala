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
import org.scalajs.dom
import react._
import vdom._
import fabric.styling._

object Details {

  type OnColumnResize = js.Function3[IColumn, Double, Int, Unit]

  def OnColumnResize(f: (IColumn, Double, Int) => Unit): js.UndefOr[OnColumnResize] =
    js.defined(f(_, _, _))

  type OnActiveItemChanged[T <: js.Object] = js.Function3[T, Int, ReactFocusEvent[dom.html.Element], Unit]

  def OnActiveItemChanged[T <: js.Object](f: (T, Int, ReactFocusEvent[dom.html.Element]) => Unit) =
    js.Any.fromFunction3(f).asInstanceOf[OnActiveItemChanged[T]]

  type OnColumnHeaderClick = js.Function2[ReactMouseEvent[dom.html.Element], IColumn, Unit]
  type OnItemInvoked[T <: js.Object] = js.Function3[T, Int, ReactEvent[?], Unit]

  @js.native
  sealed trait LayoutMode extends js.Any
  object LayoutMode {
    val fixedColumns = 0.asInstanceOf[LayoutMode]
    val justified = 1.asInstanceOf[LayoutMode]
  }

  @js.native
  abstract sealed trait ConstrainMode extends js.Any
  object ConstrainMode {
    val unconstrained = 0.asInstanceOf[ConstrainMode]
    val horizontalConstrained = 1.asInstanceOf[ConstrainMode]
  }

  trait CellStyleProps extends js.Object {
    var cellLeftPadding: js.UndefOr[Double] = js.undefined
    var cellRightPadding: js.UndefOr[Double] = js.undefined
    var cellExtraRightPadding: js.UndefOr[Double] = js.undefined
  }

  trait ColumnProps extends Theme {
    var cellStyleProps: js.UndefOr[js.Object] = js.undefined
    var column: js.UndefOr[IColumn] = js.undefined
    var columnIndex: js.UndefOr[Int] = js.undefined
    var isDraggable: js.UndefOr[Boolean] = js.undefined
    var isDropped: js.UndefOr[Boolean] = js.undefined
    var onColumnClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], IColumn, Unit]] = js.undefined
    var onColumnContextMenu: js.UndefOr[js.Function2[IColumn, ReactMouseEvent[dom.html.Element], Unit]] = js.undefined
    var onRenderColumnHeaderTooltip: js.UndefOr[IRenderFunction[ColumnRenderTooltipProps]] = js.undefined
    var parentId: js.UndefOr[String] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[ColumnStyleProps, ColumnStyles]] = js.undefined
  }

  trait ColumnRenderTooltipProps extends Tooltip.Host.Props {
    var column: js.UndefOr[IColumn] = js.undefined
  }

  trait ColumnStyleProps extends js.Object {
    var headerClassName: js.UndefOr[String] = js.undefined
    var isActionable: js.UndefOr[Boolean] = js.undefined
    var isEmpty: js.UndefOr[Boolean] = js.undefined
    var isIconVisible: js.UndefOr[Boolean] = js.undefined
    var isPadded: js.UndefOr[Boolean] = js.undefined
    var isIconOnly: js.UndefOr[Boolean] = js.undefined
    var iconClassName: js.UndefOr[Boolean] = js.undefined
    var transitionDurationDrag: js.UndefOr[Boolean] = js.undefined
    var transitionDurationDrop: js.UndefOr[Boolean] = js.undefined
  }

  trait ColumnStyles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var gripperBarVerticalStyle: js.UndefOr[IStyle] = js.undefined
    var cellTooltip: js.UndefOr[IStyle] = js.undefined
    var cellTitle: js.UndefOr[IStyle] = js.undefined
    var cellName: js.UndefOr[IStyle] = js.undefined
    var iconClassName: js.UndefOr[IStyle] = js.undefined
    var nearIcon: js.UndefOr[IStyle] = js.undefined
    var accessibleLabel: js.UndefOr[IStyle] = js.undefined
    var sortIcon: js.UndefOr[IStyle] = js.undefined
    var filterChevron: js.UndefOr[IStyle] = js.undefined
    var borderAfterDropping: js.UndefOr[IStyle] = js.undefined
    var noBorderAfterDropping: js.UndefOr[IStyle] = js.undefined
    var borderWhileDragging: js.UndefOr[IStyle] = js.undefined
    var noBorderWhileDragging: js.UndefOr[IStyle] = js.undefined
  }

  object Row {
    @js.native
    @JSImport("office-ui-fabric-react/lib/DetailsList", "DetailsRow")
    object JS extends ReactJSComponent

    def apply(props: Props)(children: ReactNode*) =
      createElementN(JS, props)(children*)

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
      var checkboxCellClassName: js.UndefOr[String] = js.undefined
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
      var cellStyleProps: js.UndefOr[CellStyleProps] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var root: js.UndefOr[IStyle] = js.undefined
      var cell: js.UndefOr[IStyle] = js.undefined
      var cellUnpadded: js.UndefOr[IStyle] = js.undefined
      var cellPadded: js.UndefOr[IStyle] = js.undefined
      var checkCell: js.UndefOr[IStyle] = js.undefined
      var isRowHeader: js.UndefOr[IStyle] = js.undefined
      var isMultiline: js.UndefOr[IStyle] = js.undefined
      var fields: js.UndefOr[IStyle] = js.undefined
      var cellMeasurer: js.UndefOr[IStyle] = js.undefined
      var checkCover: js.UndefOr[IStyle] = js.undefined
      var shimmer: js.UndefOr[IStyle] = js.undefined
      var shimmerIconPlaceholder: js.UndefOr[IStyle] = js.undefined
      var shimmerLeftBorder: js.UndefOr[IStyle] = js.undefined
      var shimmerBottomBorder: js.UndefOr[IStyle] = js.undefined
      var check: js.UndefOr[IStyle] = js.undefined
    }
  }

  object Header {

    @js.native
    @JSImport("office-ui-fabric-react/lib/DetailsList", "DetailsHeader")
    object JS extends ReactJSComponent

//     def apply(props: Props = null)(children: ReactNode*) =
//       createElementN(JS, props)(children: _*)

    def apply(props: Props) = createElement(JS, props)

    @js.native
    trait IDetailsHeader extends Focusable2

    trait BaseProps
        extends Theme
        with ClassName
        with ClassAttributes[IDetailsHeader] // is this right? maybe ReactElement?
        with ComponentRef[IDetailsHeader] {
      var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
      var layoutMode: js.UndefOr[LayoutMode] = js.undefined
      var onColumnIsSizingChanged: js.UndefOr[js.Function2[IColumn, Boolean, Unit]] = js.undefined
      var onColumnResized: js.UndefOr[js.Function3[IColumn, Double, Int, Unit]] = js.undefined
      var onColumnAutoResized: js.UndefOr[js.Function2[IColumn, Int, Unit]] = js.undefined
      var onColumnClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], IColumn, Unit]] = js.undefined
      // onColumnContextMenu
      var onRenderColumnHeaderTooltip: js.UndefOr[IRenderFunction[ColumnRenderTooltipProps]] = js.undefined
      // collapseAllVisibility
      var isAllCollapsed: js.UndefOr[Boolean] = js.undefined
      // onToggleCollapseAll
      var ariaLabel: js.UndefOr[String] = js.undefined
      // arialLabelForSelectAllCheckbox
      var selectAllVisibility: js.UndefOr[SelectAllVisibility] = js.undefined
      var columnReorderOptions: js.UndefOr[IColumnReorderOptions] = js.undefined
      var minimumPixelsForDrag: js.UndefOr[Int] = js.undefined
    }

    /** Use for reation without the vals. */
    trait PropsInit extends BaseProps {}

    object PropsInit {
      implicit class RichPropsInit(private val props: PropsInit) extends AnyVal {
        def required = props.asInstanceOf[Props]
      }
    }

    // trait Props extends ComponentRef[IDetailsHeader] {
    //   var columns: js.Array[IColumn]
    //   var layoutMode: js.UndefOr[Int]        = js.undefined
    //   var groupNestingDepth: js.UndefOr[Int] = js.undefined
    // }
    trait Props extends BaseProps {
      val selectionMode: SelectionMode
      val selection: ISelection[js.Object]
      val columns: js.Array[IColumn]
    }

    @js.native
    sealed trait SelectAllVisibility extends js.Any
    object SelectAllVisibility {
      val none = 0.asInstanceOf[SelectAllVisibility]
      val hidden = 1.asInstanceOf[SelectAllVisibility]
      val visible = 2.asInstanceOf[SelectAllVisibility]
    }

    // @js.native
    // trait State extends js.Object {
    //   // columnReorderProps
    //   // columnResizeDetails
    //   // isAllSelected
    //   // isSizing
    //   // groupNestingDepth
    //   // isAllCollapsed
    // }

    trait StyleProps extends Theme with ClassName {
      var isSelectedAllHidden: js.UndefOr[Boolean] = js.undefined
      var isAllSelected: js.UndefOr[Boolean] = js.undefined
      var isResizingColumn: js.UndefOr[Boolean] = js.undefined
      var isAllCollapsed: js.UndefOr[Boolean] = js.undefined
      var isSizing: js.UndefOr[Boolean] = js.undefined
      var isCheckboxHidden: js.UndefOr[Boolean] = js.undefined
      var cellStyleProps: js.UndefOr[CellStyleProps] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var root: js.UndefOr[IStyle] = js.undefined
      var check: js.UndefOr[IStyle] = js.undefined
      var cellWrapperPadded: js.UndefOr[IStyle] = js.undefined
      var cellIsCheck: js.UndefOr[IStyle] = js.undefined
      var cellIsActionable: js.UndefOr[IStyle] = js.undefined
      var cellIsEmpty: js.UndefOr[IStyle] = js.undefined
      var cellSizer: js.UndefOr[IStyle] = js.undefined
      var cellSizerStart: js.UndefOr[IStyle] = js.undefined
      var cellSizerEnd: js.UndefOr[IStyle] = js.undefined
      var cellIsResizing: js.UndefOr[IStyle] = js.undefined
      var cellIsGroupExpander: js.UndefOr[IStyle] = js.undefined
      var collapseButton: js.UndefOr[IStyle] = js.undefined
      var checkTooltip: js.UndefOr[IStyle] = js.undefined
      var sizingOverlay: js.UndefOr[IStyle] = js.undefined
      var dropHintCircleStyle: js.UndefOr[IStyle] = js.undefined
      var dropHintCaretStyle: js.UndefOr[IStyle] = js.undefined
      var dropHintLineStyle: js.UndefOr[IStyle] = js.undefined
      var dropHintStyle: js.UndefOr[IStyle] = js.undefined
      var accessibleLabel: js.UndefOr[IStyle] = js.undefined
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
      var cellStyleProps: js.UndefOr[CellStyleProps] = js.undefined
    }
  }

  object List {
    @js.native
    @JSImport("office-ui-fabric-react/lib/DetailsList", "DetailsList")
    object JS extends ReactJSComponent

    def apply[T <: js.Object](props: Props[T]) =
      createElement0(JS, props)

    @js.native
    trait IDetailsList extends components.List.IList {
      def focusIndex(
        index: Int,
        forceIntoFirstElement: js.UndefOr[Boolean] = js.undefined,
        measureItem: js.UndefOr[js.Function1[Int, Int]] = js.undefined,
        scrollToMode: js.UndefOr[components.List.ScrollToMode] = js.undefined
      ): Unit = js.native
    }

    trait PropsBase[T <: js.Object]
        extends ComponentRef[IDetailsList]
        with ClassName
        with Attributes
        with IViewportProps
        with Theme {
      var constrainMode: js.UndefOr[ConstrainMode] = js.undefined

      //val items: js.Array[T]
      //var items: js.UndefOr[js.Array[T]] = js.undefined
      //var className: js.UndefOr[String] = js.undefined
      //var groups: js.UndefOr[js.Array[IGroup]] = js.undefined
      var checkboxCellClassName: js.UndefOr[String] = js.undefined
      var checkboxVisibility: js.UndefOr[CheckboxVisibility] = js.undefined
      var compact: js.UndefOr[Boolean] = js.undefined
      var columns: js.UndefOr[js.Array[IColumn]] = js.undefined
      var enterModalSelectionOnTouch: js.UndefOr[Boolean] = js.undefined
      var getKey: js.UndefOr[js.Function2[js.UndefOr[T], Int, String | Int]] = js.undefined
      @JSName("getKey")
      var getKeyString: js.UndefOr[js.Function2[js.UndefOr[T], Int, String]] = js.undefined
      @JSName("getKey")
      var getKeyInt: js.UndefOr[js.Function2[js.UndefOr[T], Int, Int]] = js.undefined
      var indentWidth: js.UndefOr[Double] = js.undefined
      var initialFocusedIndex: js.UndefOr[Int] = js.undefined
      var isHeaderVisible: js.UndefOr[Boolean] = js.undefined
      var isPlaceholderData: js.UndefOr[Boolean] = js.undefined
      var layoutMode: js.UndefOr[LayoutMode] = js.undefined
      var listProps: js.UndefOr[List.Props[T]] = js.undefined // should be IListProps but then items is required
      var onRenderRow: js.UndefOr[IRenderFunction[Details.Row.Props]] = js.undefined
      var onRenderItemColumn: js.UndefOr[js.Function3[js.Any, Int, IColumn, js.Any]] = js.undefined
      var onRenderMissingItem: js.UndefOr[js.Function1[Int, js.Any]] = js.undefined
      var onRenderDetailsHeader: js.UndefOr[IRenderFunction[Details.Header.Props]] = js.undefined

      /** You can still be "active" but not deselected. */
      var onActiveItemChanged: js.UndefOr[OnActiveItemChanged[T]] = js.undefined
      var onColumnHeaderClick: js.UndefOr[OnColumnHeaderClick] = js.undefined
      var onColumnResize: js.UndefOr[OnColumnResize] = js.undefined
      var onItemInvoked: js.UndefOr[OnItemInvoked[T]] = js.undefined
      var renderedWindowsAhead: js.UndefOr[Int] = js.undefined
      var renderedWindowsBehind: js.UndefOr[Int] = js.undefined
      var onShouldVirtualize: js.UndefOr[js.Function1[components.List.Props[T], Boolean]] = js.undefined
      var selection: js.UndefOr[ISelection[T]] = js.undefined
      var selectionMode: js.UndefOr[SelectionMode] = js.undefined
      var selectionPreservedOnEmptyClick: js.UndefOr[Boolean] = js.undefined
      var setKey: js.UndefOr[String] = js.undefined
      var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
      var usePageCache: js.UndefOr[Boolean] = js.undefined
    }

    /** If you created your column array untyped, convert it. */
    def unsafeToCols(items: js.Array[js.Dynamic]) = items.asInstanceOf[js.Array[IColumn]]

    trait PropsInit[T <: js.Object] extends PropsBase[T] {
      var items: js.UndefOr[js.Array[T]] = js.undefined
    }

    object PropsInit {
      private implicit class RichPropsInit[T <: js.Object](private val item: PropsInit[T]) extends AnyVal {
        def required(items: js.Array[T]) =
          js.Object
            .assign(
              new js.Object,
              item,
              js.Dynamic.literal("items" -> items)
            )
            .asInstanceOf[Props[T]]
        def hasRequired = item.asInstanceOf[Props[T]]
      }
    }

    trait Props[T <: js.Object] extends PropsBase[T] {
      val items: js.Array[T]
    }

    trait StyleProps extends js.Object {
      var className: js.UndefOr[String] = js.undefined
      var theme: js.UndefOr[ITheme] = js.undefined
      var isHorizontalConstrained: js.UndefOr[Boolean] = js.undefined
      var compact: js.UndefOr[Boolean] = js.undefined
      var isFixed: js.UndefOr[Boolean] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var root: js.UndefOr[IStyle] = js.undefined
      var focusZone: js.UndefOr[IStyle] = js.undefined
      var headerWrapper: js.UndefOr[IStyle] = js.undefined
      var contentWrapper: js.UndefOr[IStyle] = js.undefined
    }
  }

  object Shimmered {
    @js.native
    @JSImport("office-ui-fabric-react/lib/ShimmeredDetailsList", "ShimmeredDetailsList")
    object JS extends ReactJSComponent

    def apply[T <: js.Object](props: Props[T]) =
      createElement0(JS, props)

    trait StyleProps extends List.StyleProps

    trait Styles extends List.Styles

    trait PropsBase[T <: js.Object] extends List.PropsBase[T] {
      var ariaForShimmer: js.UndefOr[String] = js.undefined
      var detailsListStyles: js.UndefOr[IStyleFunctionOrObject[List.StyleProps, List.Styles]] =
        js.undefined
      var enableShimmer: js.UndefOr[Boolean] = js.undefined
      var onRenderCustomPlaceholder: js.UndefOr[js.Function0[ReactNode]] = js.undefined
      var removeFadingOverlay: js.UndefOr[Boolean] = js.undefined
      var shimmerOverlayStyles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] =
        js.undefined
      var shimmerLines: js.UndefOr[Int] = js.undefined
    }
    trait PropsInit[T <: js.Object] extends PropsBase[T] {
      var items: js.UndefOr[js.Array[T]] = js.undefined
    }

    object PropsInit {
      private implicit class RichPropsInit[T <: js.Object](private val item: PropsInit[T]) extends AnyVal {
        def required(items: js.Array[T]) =
          js.Object
            .assign(
              new js.Object,
              item,
              js.Dynamic.literal("items" -> items)
            )
            .asInstanceOf[Props[T]]
        def hasRequired = item.asInstanceOf[Props[T]]
      }
    }

    trait Props[T <: js.Object] extends PropsBase[T] {
      val items: js.Array[T]
    }

  }

}

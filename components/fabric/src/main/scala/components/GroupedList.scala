package fabric
package components

import scala.scalajs.js
import js.annotation._
import org.scalajs.dom
import react._
import vdom._
import fabric.styling._

sealed trait CollapseAllVisibility extends js.Any
object CollapseAllVisibility {
  val hidden = 0.asInstanceOf[CollapseAllVisibility]
  val visible = 1.asInstanceOf[CollapseAllVisibility]
}

object GroupedList {

  @js.native
  trait IGroupedList extends List.IList {
    //def forceUpdate(): Unit = js.native
    def toggleCollapseAll(allCollapsed: Boolean): Unit = js.native
  }

  trait IGroup extends js.Object {
    val key: String
    val name: String
    val startIndex: Int
    val count: Int
    var children: js.UndefOr[IGroup] = js.undefined
    var level: js.UndefOr[Int] = js.undefined
    var isCollapsed: js.UndefOr[Boolean] = js.undefined
    var isShowingAll: js.UndefOr[Boolean] = js.undefined
    var isDropEnabled: js.UndefOr[Boolean] = js.undefined
    var data: js.UndefOr[js.Any] = js.undefined
    var ariaLabel: js.UndefOr[String] = js.undefined
    var hasMoreData: js.UndefOr[Boolean] = js.undefined
  }

  trait RenderProps extends js.Object {
    var isAllGroupsCollapsed: js.UndefOr[Boolean] = js.undefined
//   getGroupItemLimit?: (group: IGroup) => number;
    var onToggleCollapseAll: js.UndefOr[js.Function1[Boolean, Unit]] = js.undefined
    var headerProps: js.UndefOr[Header.Props] = js.undefined
    var showAllProps: js.UndefOr[ShowAllProps] = js.undefined
    var footerProps: js.UndefOr[Footer.Props] = js.undefined
    var onRenderHeader: js.UndefOr[IRenderFunction[Header.Props]] = js.undefined
    var onRenderShowAll: js.UndefOr[IRenderFunction[ShowAllProps]] = js.undefined
    var onRenderFooter: js.UndefOr[IRenderFunction[Footer.Props]] = js.undefined
//   collapseAllVisibility?: CollapseAllVisibility;
    var showEmptyGroups: js.UndefOr[Boolean] = js.undefined
    var role: js.UndefOr[String] = js.undefined
  }

  @js.native
  @JSImport("office-ui-fabric-react/lib/GroupedList", "GroupedList")
  object JS extends ReactJSComponent

  def apply(props: Props) = createElement(JS, props)

  trait PropsInit extends MaybeHasStrKey with ComponentRef[IGroupedList] with Themable with ClassName {
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var compact: js.UndefOr[Boolean] = js.undefined
    //var dragDropEvents?: IDragDropEvents;
    //var dragDropHelper?: IDragDropHelper;
    //var eventsToRegister?: { eventName: string; callback: (context: IDragDropContext, event?: any) => void }[];
    var groupProps: js.UndefOr[RenderProps] = js.undefined
    var groups: js.UndefOr[js.Array[IGroup]] = js.undefined

    var focusZoneProps: js.UndefOr[FocusZone.Props] = js.undefined
    var listProps: js.UndefOr[List.Props[? <: js.Object]] = js.undefined
    var rootListProps: js.UndefOr[List.Props[? <: js.Object]] = js.undefined
    var onRenderCell: js.UndefOr[js.Function3[Int, js.Any, Int, ReactNode]] =
      js.undefined //(nestingDepth?: number, item?: any, index?: number) => React.ReactNode;
    var role: js.UndefOr[String] = js.undefined
    var selection: js.UndefOr[ISelection[? <: js.Object]] = js.undefined
    var selectionMode: js.UndefOr[SelectionMode] = js.undefined
    var viewport: js.UndefOr[fabric.utilities.IViewport] = js.undefined
    var onGroupExpandStateChanged: js.UndefOr[js.Function1[Boolean, Unit]] = js.undefined
    var usePageCache: js.UndefOr[Boolean] = js.undefined
    var onShouldVirtualize: js.UndefOr[js.Function1[List.Props[? <: js.Object], Boolean]] = js.undefined
    var getGroupHeight: js.UndefOr[js.Function2[IGroup, Int, Int]] = js.undefined
  }

  trait Props extends PropsInit {
    val items: js.Array[Any]
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var theme: js.UndefOr[ITheme] = js.undefined
    var isCollapsed: js.UndefOr[Boolean] = js.undefined
    var compact: js.UndefOr[Boolean] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var group: js.UndefOr[IStyle] = js.undefined
    var groupIsDropping: js.UndefOr[IStyle] = js.undefined
  }

  trait DividerProps extends Themable with ClassName with ComponentRef[js.Any] {
    //componentRef?: IRefObject<{}>;
    var compact: js.UndefOr[Boolean] = js.undefined
    var isGroupLoading: js.UndefOr[js.Function1[IGroup, Boolean]] = js.undefined
    var loadingText: js.UndefOr[String] = js.undefined
    var group: js.UndefOr[IGroup] = js.undefined
    var groupIndex: js.UndefOr[Int] = js.undefined
    var groupLevel: js.UndefOr[Int] = js.undefined
    var ariaColSpan: js.UndefOr[Int] = js.undefined
    var indentWidth: js.UndefOr[Int] = js.undefined
    var selected: js.UndefOr[Boolean] = js.undefined
    var viewport: js.UndefOr[fabric.utilities.IViewport] = js.undefined
    var selectionMode: js.UndefOr[SelectionMode] = js.undefined
    var footerText: js.UndefOr[String] = js.undefined
    var showAllLinkText: js.UndefOr[String] = js.undefined
    var onToggleSummarize: js.UndefOr[js.Function1[IGroup, Unit]] = js.undefined
    var onGroupHeaderClick: js.UndefOr[js.Function1[IGroup, Unit]] = js.undefined
    //var onGroupHeaderKeyUp: (ev: React.KeyboardEvent<HTMLElement>, group: IGroup) => void
    var onToggleCollapse: js.UndefOr[js.Function1[IGroup, Unit]] = js.undefined
    var onToggleSelectGroup: js.UndefOr[js.Function1[IGroup, Unit]] = js.undefined
    var isCollapsedGroupSelectVisible: js.UndefOr[Boolean] = js.undefined
    var onRenderTitle: js.UndefOr[IRenderFunction[Header.Props]] = js.undefined
    //var expandButtonProps: React.HTMLAttributes[dom.html.Button] = js.undefined
    var groups: js.UndefOr[js.Array[IGroup]] = js.undefined
    //className?: string;
    //theme?: ITheme;
  }

  trait ShowAllProps extends DividerProps {
    var styles: js.UndefOr[IStyleFunctionOrObject[ShowAllStyleProps, ShowAllStyles]] = js.undefined
    //var showAllLinkText: js.UndefOr[String] = js.undefined
  }

  trait ShowAllStyleProps extends Themable {}

  trait ShowAllStyles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
  }

  object Header {
    trait Props extends DividerProps {
      var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
      var groupedListId: js.UndefOr[String] = js.undefined
      //expandButtonProps?: React.HTMLAttributes<HTMLButtonElement>;
      //expandButtonIcon?: string;
      //selectAllButtonProps?: React.HTMLAttributes<HTMLButtonElement>;
      var ariaSetSize: js.UndefOr[Int] = js.undefined
      var ariaPosInSet: js.UndefOr[Int] = js.undefined
      var onRenderGroupHeaderCheckbox: js.UndefOr[IRenderFunction[CheckboxProps]] = js.undefined
      var useFastIcons: js.UndefOr[Boolean] = js.undefined
    }

    trait StyleProps extends Themable with ClassName {
      var selected: js.UndefOr[Boolean] = js.undefined
      var isCollapsed: js.UndefOr[Boolean] = js.undefined
      var compact: js.UndefOr[Boolean] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var root: js.UndefOr[IStyle] = js.undefined
      var groupHeaderContainer: js.UndefOr[IStyle] = js.undefined
      var headerCount: js.UndefOr[IStyle] = js.undefined
      var check: js.UndefOr[IStyle] = js.undefined
      var dropIcon: js.UndefOr[IStyle] = js.undefined
      var expand: js.UndefOr[IStyle] = js.undefined
      var expandIsCollapsed: js.UndefOr[IStyle] = js.undefined
      var title: js.UndefOr[IStyle] = js.undefined
    }

    trait CheckboxProps extends Themable {
      var checked: js.UndefOr[Boolean] = js.undefined
    }
  }

  object Footer {
    trait Props extends DividerProps {
      var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    }

    trait StyleProps extends ClassName with Themable {
      var selected: js.UndefOr[Boolean] = js.undefined
      var isCollapsed: js.UndefOr[Boolean] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var root: js.UndefOr[IStyle] = js.undefined
    }

  }

}

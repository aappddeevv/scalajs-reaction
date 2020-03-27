package fabric
package experiments

import scala.scalajs.js
import js.annotation._

import react._

import fabric.components._
import fabric.styling._

object Pagination {

  @js.native
  @JSImport("@uifabric/experiments/lib/Pagination", "Pagination")
  object JS extends ReactJSComponent

  def apply(props: Props = null) = createElement0(JS, props)

  trait PropsBase extends ReactJSProps with ComponentRef[js.Object] {
    var itemsPerPage: js.UndefOr[Int] = js.undefined
    var totalItemCount: js.UndefOr[Int] = js.undefined
    var selectedPageIndex: js.UndefOr[Int] = js.undefined
    var firstPageIconProps: js.UndefOr[Icon.Props] = js.undefined
    var previousPageIconProps: js.UndefOr[Icon.Props] = js.undefined
    var nextPageIconProps: js.UndefOr[Icon.Props] = js.undefined
    var lastPageIconProps: js.UndefOr[Icon.Props] = js.undefined
    var firstPageAriaLabel: js.UndefOr[String] = js.undefined
    var lastPageAriaLabel: js.UndefOr[String] = js.undefined
    var pageAriaLabel: js.UndefOr[String] = js.undefined
    var selectedAriaLabel: js.UndefOr[String] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var comboBoxAriaLabel: js.UndefOr[String] = js.undefined
    var format: js.UndefOr[PaginationFormat] = js.undefined
    var numberOfPageButton: js.UndefOr[Int] = js.undefined
    var strings: js.UndefOr[PaginationString] = js.undefined
    var onPageChange: js.UndefOr[js.Function1[Int, Unit]] = js.undefined
    var onRenderVisibleItemLabel: js.UndefOr[IRenderFunction[Props]] = js.undefined
  }

  trait PropsInit extends PropsBase {
    var pageCount: js.UndefOr[Int] = js.undefined
  }

  trait Props extends PropsBase {
    val pageCount: Int
    val theme: ITheme
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var comboBox: js.UndefOr[IStyle] = js.undefined
    var pageNumber: js.UndefOr[IStyle] = js.undefined
    var previousNextPage: js.UndefOr[IStyle] = js.undefined
    var previousNextPageDisabled: js.UndefOr[IStyle] = js.undefined
    var visibleItemLabel: js.UndefOr[IStyle] = js.undefined
  }

  trait StyleProps extends js.Object {
    val theme: ITheme
    var format: js.UndefOr[PaginationFormat] = js.undefined
  }

  @js.native
  abstract trait PaginationFormat extends js.Any
  object PaginationFormat {
    val comboBox = "comboBox".asInstanceOf[PaginationFormat]
    val buttons = "buttons".asInstanceOf[PaginationFormat]
  }

  trait PaginationString extends js.Object {
    var divider: js.UndefOr[String] = js.undefined
    var of: js.UndefOr[String] = js.undefined
  }
}

object PageNumber {

  @js.native
  @JSImport("@uifabric/experiments/lib/Pagination/PageNumber", "PageNumber")
  object JS extends ReactJSComponent

  def apply(props: Props = null) = createElement0(JS, props)

  trait Props extends js.Object {
    var onClick: js.UndefOr[js.Function1[Int, Unit]] = js.undefined
    val page: Int
    val selected: Boolean
    var ariaLabel: js.UndefOr[String] = js.undefined
    var className: js.UndefOr[String] = js.undefined
  }
}

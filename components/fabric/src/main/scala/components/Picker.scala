package fabric
package components

import scala.scalajs.js
import js.|
import js.annotation._

import react._
import react.implicits._
import styling._
import vdom._
import org.scalajs.dom

object Picker {

  @js.native
  abstract trait ValidationState extends js.Any
  object ValidationState {
    val valid = "valid".asInstanceOf[ValidationState]
    val warning = "warning".asInstanceOf[ValidationState]
    val invalid = "invalid".asInstanceOf[ValidationState]
  }

  @js.native
  trait IBasePicker[T <: js.Object] extends js.Object {
    val items: js.UndefOr[js.Array[T]] = js.native
    def focus(): Unit = js.native
    def focusInput(): Unit = js.native
    def completeSuggestions(forceComplete: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  }

  /** Resolve to nothing. */
  def ResolveNothing[T <: js.Object]: js.Function2[String, js.Array[T], js.Array[T] | js.Thenable[js.Array[T]]] =
    (_, _) => js.Array[T]()

  trait BaseProps[T <: js.Object] extends MaybeHasStrKey with ComponentRef[IBasePicker[T]] {
    // Item.Props[T] may not have the right variance as it will alwasy be a subclass
    // of Item.Props[T].
    var onRenderItem: js.UndefOr[js.Function1[Item.Props[T], ReactNode]] = js.undefined
    //var onRenderSuggestionsItem
    val onResolveSuggestions: js.Function2[String, js.Array[T], js.Array[T] | js.Thenable[js.Array[T]]]
    //var resolveDelay
    //var onEmptyInputFocus
    //var onEmptyResolveSuggestions
    var defaultSelectedItems: js.UndefOr[js.Array[T]] = js.undefined
    var onChange: js.UndefOr[js.Function1[js.Array[T], Unit]] = js.undefined
    //var onFocus
    //var onBlur
    //var getTextFromItem
    //var onGetMoreResults
    var className: js.UndefOr[String] = js.undefined
    //var pickerSuggestionProps
    //var pickerCalloutProps
    //var inputProps
    //var onRemoveSuggestion
    //var onValidateInput
    var searchingText: js.UndefOr[String] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var itemLimit: js.UndefOr[Int] = js.undefined
    //var createGenericItem
    var removeButtonAriaLabel: js.UndefOr[String] = js.undefined
    //var onItemSelected
    var selectedItems: js.UndefOr[js.Array[T]] = js.undefined
    var onInputChange: js.UndefOr[js.Function1[String, String]] = js.undefined
    //var onDismiss
    //enableSelectedSuggestionAlert
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var isFocused: js.UndefOr[Boolean] = js.undefined
    var inputClassName: js.UndefOr[String] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var text: js.UndefOr[IStyle] = js.undefined
    var itemsWrapper: js.UndefOr[IStyle] = js.undefined
    var input: js.UndefOr[IStyle] = js.undefined
    var screenReaderText: js.UndefOr[IStyle] = js.undefined
  }

  // base item
  object Item {

    @js.native
    trait IItem extends js.Object {}

    trait Props[T <: js.Object] extends MaybeHasStrKey with AllHTMLAttributes[dom.html.Element] {
      val item: T
      val index: Int
      //var selected: js.UndefOr[Boolean] = js.undefined
      var onRemoveItem: js.UndefOr[js.Function0[Unit]] = js.undefined
      //var onItemChange - internal use only
      var removeButtonAriaLabel: js.UndefOr[String] = js.undefined
    }

    trait Styles extends IStyleSetTag {}

    trait StyleProps extends js.Object {}

  }

  object Tag {
    @js.native
    @JSImport("office-ui-fabric-react/lib", "TagPicker")
    object JS extends ReactJSComponent

    val ResolveNothing: js.Function2[String, js.Array[ITag], js.Array[ITag] | js.Thenable[js.Array[ITag]]] =
      (_, _) => js.Array[ITag]()

    def apply(props: Props) = createElement(JS, props)

    trait Props extends BaseProps[ITag] {}

    trait ITag extends js.Object {
      val name: String
      val key: String | Int
    }

    object Item {
      @js.native
      @JSImport("office-ui-fabric-react/lib", "TagItem")
      object JS extends ReactJSComponent
      
      def apply(props: Props)(children: ReactNode*) = createElement(JS, props, children:_*)
    
      trait Props extends Picker.Item.Props[ITag] {
        var enableTagFocusInDisabledPicker: js.UndefOr[String] = js.undefined
        //var title: js.UndefOr[String] = js.undefined
        //var className: js.UndefOr[String] = js.undefined
        var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
      }

      trait StyleProps extends Picker.Item.StyleProps {
        var className: js.UndefOr[String] = js.undefined
        var selected: js.UndefOr[Boolean] = js.undefined
        var disabled: js.UndefOr[Boolean] = js.undefined
      }

      trait Styles extends Picker.Item.Styles {
        var root: js.UndefOr[IStyle] = js.undefined
        var text: js.UndefOr[IStyle] = js.undefined
        var close: js.UndefOr[IStyle] = js.undefined
      }

      object Suggestion {
        trait Props extends AllHTMLAttributes[dom.html.Element] {
          //var className: js.UndefOr[String] = js.undefined
          var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
        }
        trait StyleProps extends js.Object {
          var className: js.UndefOr[String] = js.undefined
        }

        trait Styles extends IStyleSetTag {
          var suggestionTextOverflow: js.UndefOr[IStyle] = js.undefined
        }
      }

    }
  }

}

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

package react
package vdom

// needed because I could not figure out the proper variance construct
import scala.annotation.unchecked.{ uncheckedVariance => uv }

import scala.scalajs.js

import js.Dynamic.{ literal => lit }
import js.annotation.JSName
import js.|

import org.scalajs.dom

/** Used to set the inner HTML dangerously. */
trait SetInnerHTML extends js.Object {
  val __html: String
  //var __html: js.UndefOr[String] = js.undefined
}

/** Add a key. */
trait Attributes extends js.Object {
  var key: js.UndefOr[KeyType] = js.undefined
}

/** Add a ref callback. ReactJSProps does not carry a type. */
trait ClassAttributes[E] extends Attributes {
  var ref: js.UndefOr[Ref[E]] = js.undefined
}

/** A props trait that takes all HTML props. Use wisely. */
trait HTMLProps[+T <: dom.EventTarget] extends AllHTMLAttributes[T @uv] with ClassAttributes[T @uv] {}

trait ElementAttributesOnly extends HTMLAttributes[dom.html.Element] with ClassAttributes[dom.html.Element]

/**
 * The HTML tags (elements/components) for standard DOM elements. Each tag is
 * represented in lowercase while the props object is the uppercase tag name
 * followed by Props. So the div tag has DivProps for the props type.
 */
trait HTMLTagsX {

  trait AProps extends AnchorHTMLAttributes[dom.html.Anchor] with ClassAttributes[dom.html.Anchor]
  final lazy val a = tagt[AProps]("a")

  // should be dom.html.Abbr
  type AbbrProps = ElementAttributesOnly
  final lazy val abbr = tagt[AbbrProps]("abbr")

  type AddressProps = ElementAttributesOnly
  final lazy val address = tagt[AddressProps]("addres")

  trait AreaProps extends AreaHTMLAttributes[dom.html.Area] with ClassAttributes[dom.html.Area]
  final lazy val area = tagt[AreaProps]("area")

  // should be dom.html.Article
  type ArticleProps = ElementAttributesOnly
  final lazy val article = tagt[ArticleProps]("article")

  // should be dom.html.Aside
  type AsideProps = ElementAttributesOnly
  final lazy val aside = tagt[AsideProps]("aside")

  trait AudioProps extends AudioHTMLAttributes[dom.html.Audio] with ClassAttributes[dom.html.Audio]
  final lazy val audio = tagt[AudioProps]("audio")

  // should map to dom.html.B
  type BProps = ElementAttributesOnly
  final lazy val b = tagt[BProps]("b")

  trait BaseProps extends BaseHTMLAttributes[dom.html.Base] with ClassAttributes[dom.html.Base]
  final lazy val base = tagt[BaseProps]("base")

  //val BdiProps = DetailedHTMLProps[dom.html.BDI, HTMLAttributes[dom.htl.BDI]]
  //final lazy val        bdi = tagt[BdiProps]("bdi")
  //final lazy val        bdo: DetailedHTMLFactory<HTMLAttributes<HTMLElement>, HTMLElement>;
  //final lazy val        big: DetailedHTMLFactory<HTMLAttributes<HTMLElement>, HTMLElement>;

  //  scala jsdom should have block => blockquote
  type BlockQuoteProps = ElementAttributesOnly
  final lazy val blockquote = tagt[BlockQuoteProps]("blockquote")

  trait BodyProps extends HTMLAttributes[dom.html.Body] with ClassAttributes[dom.html.Body]
  final lazy val body = tagt[BodyProps]("body")

  // should be dom.html.Br
  type BrProps = ElementAttributesOnly
  final lazy val br = tagt[BrProps]("br")

  trait ButtonProps extends ButtonHTMLAttributes[dom.html.Button] with ClassAttributes[dom.html.Button]
  final lazy val button = tagt[ButtonProps]("button")

  trait CanvasProps extends CanvasHTMLAttributes[dom.html.Canvas] with ClassAttributes[dom.html.Canvas]
  final lazy val canvas = tagt[CanvasProps]("canvas")

  // should be dom.html.Caption
  type CaptionProps = ElementAttributesOnly
  final lazy val caption = tagt[CaptionProps]("caption")

  // should be dom.html.Cite
  type CiteProps = ElementAttributesOnly
  final lazy val cite = tagt[CiteProps]("cite")

  // should be dom.html.Code
  type CodeProps = ElementAttributesOnly
  final lazy val code = tagt[CodeProps]("code")

  // should be dom.html.Col
  trait ColProps extends ColHTMLAttributes[dom.html.Element] with ClassAttributes[dom.html.Element]
  final lazy val col = tagt[ColProps]("col")

  // should be  dom.hmtl.Colgroup
  trait ColgroupProps extends ColgroupHTMLAttributes[dom.html.Element] with ClassAttributes[dom.html.Element]
  final lazy val colgroup = tagt[ColgroupProps]("colgroup")

  // shoud be dom.html.Data
  type DataProps = ElementAttributesOnly
  final lazy val data = tagt[DataProps]("data")

  trait DataListProps extends HTMLAttributes[dom.html.DataList] with ClassAttributes[dom.html.DataList]
  final lazy val datalist = tagt[DataListProps]("datalist")
/*
  trait DDProps extends HTMLAttributes[dom.html.DD] with ClassAttributes[dom.html.DD]
  final lazy val dd = tagt[DDProps]("dd")
*/
  // should be dom.html.Del, and DelHTMLAttributes
  type DelProps = ElementAttributesOnly
  final lazy val del = tagt[DelProps]("del")

  // should be dom.html.Details and DetailsHTMLAttributes
  type DetailsProps = ElementAttributesOnly
  final lazy val details = tagt[DetailsProps]("details")

  // shoud be dom.html.Dfn
  type DfnProps = ElementAttributesOnly
  final lazy val dfn = tagt[DfnProps]("dfn")

  // should be dom.html.Dialog
  type DialogProps = ElementAttributesOnly
  final lazy val dialog = tagt[DialogProps]("dialog")

  trait DivProps extends HTMLAttributes[dom.html.Div] with ClassAttributes[dom.html.Div]
  final lazy val div = tagt[DivProps]("div")

  /** A common scenario to wrap a div just to add the classname. */
  def divWithClassname(
    cn: js.UndefOr[String],
    children: ReactNode*
  ) = div(new DivProps { className = cn })(children: _*)

  // should be dom.html.Dl
  type DlProps = ElementAttributesOnly
  final lazy val dl = tagt[DlProps]("dl")

  // should be dom.html.Dt
  type DtProps = ElementAttributesOnly
  final lazy val dt = tagt[DtProps]("dt")

  // should be dom.html.El
  type EmProps = ElementAttributesOnly
  final lazy val em = tagt[EmProps]("em")

  trait EmbedProps extends EmbedHTMLAttributes[dom.html.Embed] with ClassAttributes[dom.html.Embed]
  final lazy val embed = tagt[EmbedProps]("embed")

  trait FieldsetProps extends FieldsetHTMLAttributes[dom.html.FieldSet] with ClassAttributes[dom.html.FieldSet]
  final lazy val fieldset = tagt[FieldsetProps]("fieldset")

  type FigcaptionProps = ElementAttributesOnly
  final lazy val figcaption = tagt[FigcaptionProps]("figcaption")

  type FigureProps = ElementAttributesOnly
  final lazy val figure = tagt[FigureProps]("figure")

  type FooterProps = ElementAttributesOnly
  final lazy val footer = tagt[FooterProps]("footer")

  trait FormProps extends FormHTMLAttributes[dom.html.Form] with ClassAttributes[dom.html.Form]
  final lazy val form = tagt[FormProps]("form")

  trait HProps extends HTMLAttributes[dom.html.Heading]
  final lazy val h1 = tagt[HProps]("h1")
  final lazy val h2 = tagt[HProps]("h2")
  final lazy val h3 = tagt[HProps]("h2")
  final lazy val h4 = tagt[HProps]("h2")
  final lazy val h5 = tagt[HProps]("h2")
  final lazy val h6 = tagt[HProps]("h2")

  trait HeadProps extends HTMLAttributes[dom.html.Head] with ClassAttributes[dom.html.Head]
  final lazy val head = tagt[HeadProps]("head")

  type HeaderProps = ElementAttributesOnly
  final lazy val header = tagt[HeaderProps]("header")

  type HgroupProps = ElementAttributesOnly
  final lazy val hgroup = tagt[HgroupProps]("hgroup")

  trait HrProps extends HTMLAttributes[dom.html.HR] with ClassAttributes[dom.html.HR]
  final lazy val hr = tagt[HrProps]("hr")

  trait HtmlProps extends HtmlHTMLAttributes[dom.html.Html] with ClassAttributes[dom.html.Html]
  final lazy val html = tagt[HtmlProps]("html")

  type IProps = ElementAttributesOnly
  final lazy val i = tagt[IProps]("i")

  trait IframeProps extends IframeHTMLAttributes[dom.html.IFrame] with ClassAttributes[dom.html.IFrame]
  final lazy val iframe = tagt[IframeProps]("iframe")

  trait ImgProps extends ImgHTMLAttributes[dom.html.Image] with ClassAttributes[dom.html.Image]
  final lazy val img = tagt[ImgProps]("img")

  trait InputProps extends InputHTMLAttributes[dom.html.Input] with ClassAttributes[dom.html.Input]

  /** Can use either input(props)(children) or input.checkbox(props)(children). */
  object input extends TagT[InputProps]("input") {
    def withType(`type`: String) =
      new TagT[InputProps]("input", lit("type" -> `type`).asInstanceOf[InputProps])

    /** Not supported? */
    lazy val button = this withType "button"

    /** Not supported? */
    lazy val checkbox      = this withType "checkbox"
    lazy val color         = this withType "color"
    lazy val date          = this withType "date"
    lazy val datetime      = this withType "datetime"
    lazy val datetimeLocal = this withType "datetime-local"
    lazy val email         = this withType "email"
    lazy val file          = this withType "file"

    /** Not supported? */
    lazy val hidden = this withType "hidden"

    /** Not supported? */
    lazy val image    = this withType "image"
    lazy val month    = this withType "month"
    lazy val number   = this withType "number"
    lazy val password = this withType "password"

    /** Not supported? */
    lazy val radio = this withType "radio"
    lazy val range = this withType "range"

    /** Not supported? */
    lazy val reset  = this withType "reset"
    lazy val search = this withType "search"

    /** Not supported? */
    lazy val submit = this withType "submit"
    lazy val tel    = this withType "tel"
    lazy val text   = this withType "text"
    lazy val time   = this withType "time"
    lazy val url    = this withType "url"
    lazy val week   = this withType "week"
  }

  trait InsProps extends InsHTMLAttributes[dom.html.Mod] with ClassAttributes[dom.html.Mod]
  final lazy val ins = tagt[InsProps]("ins")

  type KbdProps = ElementAttributesOnly
  final lazy val kbd = tagt[KbdProps]("kbd")

  type KeygenProps = ElementAttributesOnly
  final lazy val keygen = tagt[KeygenProps]("keygen")

  trait LabelProps extends LabelHTMLAttributes[dom.html.Label] with ClassAttributes[dom.html.Label]
  final lazy val label = tagt[LabelProps]("label")

  trait LegendProps extends HTMLAttributes[dom.html.Legend] with ClassAttributes[dom.html.Legend]
  final lazy val legend = tagt[LegendProps]("legend")

  trait LiProps extends LiHTMLAttributes[dom.html.LI] with ClassAttributes[dom.html.LI]
  final lazy val li = tagt[LiProps]("li")

  trait LinkProps extends LinkHTMLAttributes[dom.html.Link] with ClassAttributes[dom.html.Link]
  final lazy val link = tagt[LinkProps]("link")

  type MainProps = ElementAttributesOnly
  final lazy val main = tagt[MainProps]("main")

  trait MapProps extends MapHTMLAttributes[dom.html.Map] with ClassAttributes[dom.html.Map]
  final lazy val map = tagt[MapProps]("map")

  type MarkProps = ElementAttributesOnly
  final lazy val mark = tagt[MarkProps]("mark")

  trait MenuProps extends MenuHTMLAttributes[dom.html.Menu] with ClassAttributes[dom.html.Menu]
  final lazy val menu = tagt[MenuProps]("menu")

  trait MenuitemProps extends HTMLAttributes[dom.html.Element] with ClassAttributes[dom.html.Element]
  final lazy val menuitem = tagt[MenuitemProps]("menuitem")

  trait MetaProps extends MetaHTMLAttributes[dom.html.Meta] with ClassAttributes[dom.html.Meta]
  final lazy val meta = tagt[MetaProps]("meta")

  /** not on IE or safari. */
  type MeterProps = ElementAttributesOnly
  final lazy val meter = tagt[MeterProps]("meter")

  type NavProps = ElementAttributesOnly
  final lazy val nav = tagt[NavProps]("nav")

  type NoscriptProps = ElementAttributesOnly
  final lazy val noscript = tagt[NoscriptProps]("noscript")

  trait ObjectProps extends ObjectHTMLAttributes[dom.html.Object] with ClassAttributes[dom.html.Object]
  final lazy val `object` = tagt[ObjectProps]("object")

  trait OlProps extends OlHTMLAttributes[dom.html.Element] with ClassAttributes[dom.html.Element]
  final lazy val ol = tagt[OlProps]("ol")

  trait OptgroupProps extends OptgroupHTMLAttributes[dom.html.OptGroup] with ClassAttributes[dom.html.OptGroup]
  final lazy val optgroup = tagt[OptgroupProps]("optgroup")

  trait OptionProps extends OptionHTMLAttributes[dom.html.Option] with ClassAttributes[dom.html.Option]
  final lazy val option = tagt[OptionProps]("option")

  // should be dom.html.Output
  type OutputProps = ElementAttributesOnly
  final lazy val output = tagt[OutputProps]("output")

  trait PProps extends HTMLAttributes[dom.html.Paragraph] with ClassAttributes[dom.html.Paragraph]
  final lazy val p = tagt[PProps]("p")

  trait ParamProps extends ParamHTMLAttributes[dom.html.Param] with ClassAttributes[dom.html.Param]
  final lazy val param = tagt[ParamProps]("param")

  type PictureProps = ElementAttributesOnly
  final lazy val picture = tagt[PictureProps]("picture")

  type PreProps = ElementAttributesOnly
  final lazy val pre = tagt[PreProps]("pre")

  trait ProgressProps extends ProgressHTMLAttributes[dom.html.Progress] with ClassAttributes[dom.html.Progress]
  final lazy val progress = tagt[ProgressProps]("progress")

  trait QProps extends QuoteHTMLAttributes[dom.html.Quote] with ClassAttributes[dom.html.Quote]
  final lazy val q = tagt[QProps]("q")
  /*
  type RpProps = DetailedHTMLProps[dom.html.Element, HTMLAttributes[dom.html.Element]]
  final lazy val        rp = tagt[RpProps]("rp")

  type RtProps = DetailedHTMLProps[dom.html.Element, HTMLAttributes[dom.html.Element]]
  final lazy val        rt = tagt[RtProps]("rt")

  type RubyProps = DetailedHTMLProps[dom.html.Element, HTMLAttributes[dom.html.Element]]
  final lazy val        ruby = tagt[RubyProps]("ruby")
   */
  type SProps = ElementAttributesOnly
  final lazy val s = tagt[SProps]("s")

  type SampProps = ElementAttributesOnly
  final lazy val samp = tagt[SampProps]("samp")

  trait ScriptProps extends ScriptHTMLAttributes[dom.html.Script] with ClassAttributes[dom.html.Script]
  final lazy val scripf = tagt[ScriptProps]("script")

  type SectionProps = ElementAttributesOnly
  final lazy val section = tagt[SectionProps]("section")

  trait SelectProps extends SelectHTMLAttributes[dom.html.Select] with ClassAttributes[dom.html.Select]
  final lazy val select = tagt[SelectProps]("select")

  type SmallProps = ElementAttributesOnly
  final lazy val small = tagt[SmallProps]("small")

  trait SourceProps extends SourceHTMLAttributes[dom.html.Source] with ClassAttributes[dom.html.Source]
  final lazy val source = tagt[SourceProps]("source")

  trait SpanProps extends HTMLAttributes[dom.html.Span] with ClassAttributes[dom.html.Span]
  final lazy val span = tagt[SpanProps]("span")

  def spanWithClassname(
    cn: js.UndefOr[String],
    children: ReactNode*
  ) = span(new SpanProps { className = cn })(children: _*)

  type StrongProps = ElementAttributesOnly
  final lazy val strong = tagt[StrongProps]("strong")

  trait StyleProps extends StyleHTMLAttributes[dom.html.Style] with ClassAttributes[dom.html.Style]
  final lazy val style = tagt[StyleProps]("style")

  type SubProps = ElementAttributesOnly
  final lazy val sub = tagt[SubProps]("sub")

  type SummaryProps = ElementAttributesOnly
  final lazy val summary = tagt[SummaryProps]("summary")

  type SupProps = ElementAttributesOnly
  final lazy val sup = tagt[SubProps]("sup")

  trait TableProps extends TableHTMLAttributes[dom.html.Table] with ClassAttributes[dom.html.Table]
  final lazy val table = tagt[TableProps]("table")

  trait TbodyProps extends HTMLAttributes[dom.html.TableSection] with ClassAttributes[dom.html.TableSection]
  final lazy val tbody = tagt[TbodyProps]("tbody")

  trait TdProps extends HTMLAttributes[dom.html.TableCell] with ClassAttributes[dom.html.TableCell]
  final lazy val td = tagt[TdProps]("td")

  trait TextareaProps extends TextAreaHTMLAttributes[dom.html.TextArea] with ClassAttributes[dom.html.TextArea]
  final lazy val textarea = tagt[TextareaProps]("textarea")

  trait TfootProps extends HTMLAttributes[dom.html.TableSection] with ClassAttributes[dom.html.TableSection]
  final lazy val tfoot = tagt[TfootProps]("tfoot")

  trait ThProps extends ThHTMLAttributes[dom.html.TableCell] with ClassAttributes[dom.html.TableCell]
  final lazy val th = tagt[ThProps]("th")

  trait TheadProps extends HTMLAttributes[dom.html.TableSection] with ClassAttributes[dom.html.TableSection]
  final lazy val thead = tagt[TheadProps]("thead")

  // should be dom.html.Time
  trait TimeProps extends TimeHTMLAttributes[dom.html.Element] with ClassAttributes[dom.html.Element]
  final lazy val time = tagt[TimeProps]("time")

  trait TitleProps extends HTMLAttributes[dom.html.Title] with ClassAttributes[dom.html.Title]
  final lazy val title = tagt[TitleProps]("title")

  trait TrProps extends HTMLAttributes[dom.html.TableRow] with ClassAttributes[dom.html.TableRow]
  final lazy val tr = tagt[TrProps]("tr")

  trait TrackProps extends TrackHTMLAttributes[dom.html.Track] with ClassAttributes[dom.html.Track]
  final lazy val track = tagt[TrackProps]("track")

  type UProps = ElementAttributesOnly
  final lazy val u = tagt[UProps]("u")

  trait UlProps extends HTMLAttributes[dom.html.UList] with ClassAttributes[dom.html.UList]
  final lazy val ul = tagt[UlProps]("ul")

  //final lazy val        `var`: DetailedHTMLFactory<HTMLAttributes<HTMLElement>, HTMLElement>;
  trait VideoProps extends VideoHTMLAttributes[dom.html.Video] with ClassAttributes[dom.html.Video]
  final lazy val video = tagt[VideoProps]("video")

  type WbrProps = ElementAttributesOnly
  final lazy val wbr = tagt[WbrProps]("wbr")

  type WebviewProps = WebViewHTMLAttributes[dom.html.Element]
  final lazy val webview = tagt[WebviewProps]("webview")

}

/**
 * Mostly handlers for DOM nodes.
 */
trait DOMAttributes[+T <: dom.EventTarget] extends js.Object {

  /** reactjs specific */
  var children: js.UndefOr[ReactNode] = js.undefined

  /** reactjs specific */
  var dangerouslySetInnerHTML: js.UndefOr[SetInnerHTML] = js.undefined

  // clipboard events
  var onCopy: js.UndefOr[ClipboardEventHandler[T @uv]]         = js.undefined
  var onCopyCapture: js.UndefOr[ClipboardEventHandler[T @uv]]  = js.undefined
  var onCut: js.UndefOr[ClipboardEventHandler[T @uv]]          = js.undefined
  var onCutCapture: js.UndefOr[ClipboardEventHandler[T @uv]]   = js.undefined
  var onPaste: js.UndefOr[ClipboardEventHandler[T @uv]]        = js.undefined
  var onPasteCapture: js.UndefOr[ClipboardEventHandler[T @uv]] = js.undefined

  // compositoin events
  var onCompositionEnd: js.UndefOr[CompositionEventHandler[T @uv]]           = js.undefined
  var onCompositionEndCapture: js.UndefOr[CompositionEventHandler[T @uv]]    = js.undefined
  var onCompositionStart: js.UndefOr[CompositionEventHandler[T @uv]]         = js.undefined
  var onCompositionStartCapture: js.UndefOr[CompositionEventHandler[T @uv]]  = js.undefined
  var onCompositionUpdate: js.UndefOr[CompositionEventHandler[T @uv]]        = js.undefined
  var onCompositionUpdateCapture: js.UndefOr[CompositionEventHandler[T @uv]] = js.undefined

  // keyboard events val onKeyDown: js.UndefOr[js.Function0[]] = js.undefined
  var onKeyDown: js.UndefOr[KeyboardEventHandler[T @uv]]         = js.undefined
  var onKeyPress: js.UndefOr[KeyboardEventHandler[T @uv]]        = js.undefined
  var onKeyPressCapture: js.UndefOr[KeyboardEventHandler[T @uv]] = js.undefined
  var onKeyUp: js.UndefOr[KeyboardEventHandler[T @uv]]           = js.undefined
  var onKeyUpCapture: js.UndefOr[KeyboardEventHandler[T @uv]]    = js.undefined

  // image events
  var onLoad: js.UndefOr[ReactEventHandler[T @uv]]         = js.undefined
  var onLoadCapture: js.UndefOr[ReactEventHandler[T @uv]]  = js.undefined
  var onError: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onErrorCapture: js.UndefOr[ReactEventHandler[T @uv]] = js.undefined

  // mouse events
  var onClick: js.UndefOr[MouseEventHandler[T @uv]]              = js.undefined
  var onClickCapture: js.UndefOr[MouseEventHandler[T @uv]]       = js.undefined
  var onContextMenu: js.UndefOr[MouseEventHandler[T @uv]]        = js.undefined
  var onContextMenuCapture: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onDoubleClick: js.UndefOr[MouseEventHandler[T @uv]]        = js.undefined
  var onDoubleClickCapture: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onDrag: js.UndefOr[DragEventHandler[T @uv]]                = js.undefined
  var onDragCapture: js.UndefOr[DragEventHandler[T @uv]]         = js.undefined
  var onDragEnd: js.UndefOr[DragEventHandler[T @uv]]             = js.undefined
  var onDragEndCapture: js.UndefOr[DragEventHandler[T @uv]]      = js.undefined
  var onDragEnter: js.UndefOr[DragEventHandler[T @uv]]           = js.undefined
  var onDragEnterCapture: js.UndefOr[DragEventHandler[T @uv]]    = js.undefined
  var onDragExit: js.UndefOr[DragEventHandler[T @uv]]            = js.undefined
  var onDragExitCapture: js.UndefOr[DragEventHandler[T @uv]]     = js.undefined
  var onDragLeave: js.UndefOr[DragEventHandler[T @uv]]           = js.undefined
  var onDragLeaveCapture: js.UndefOr[DragEventHandler[T @uv]]    = js.undefined
  var onDragOver: js.UndefOr[DragEventHandler[T @uv]]            = js.undefined
  var onDragOverCapture: js.UndefOr[DragEventHandler[T @uv]]     = js.undefined
  var onDragStart: js.UndefOr[DragEventHandler[T @uv]]           = js.undefined
  var onDragStartCapture: js.UndefOr[DragEventHandler[T @uv]]    = js.undefined
  var onDrop: js.UndefOr[DragEventHandler[T @uv]]                = js.undefined
  var onDropCapture: js.UndefOr[DragEventHandler[T @uv]]         = js.undefined
  var onMouseDown: js.UndefOr[MouseEventHandler[T @uv]]          = js.undefined
  var onMouseDownCapture: js.UndefOr[MouseEventHandler[T @uv]]   = js.undefined
  var onMouseEnter: js.UndefOr[MouseEventHandler[T @uv]]         = js.undefined
  var onMouseLeave: js.UndefOr[MouseEventHandler[T @uv]]         = js.undefined
  var onMouseMove: js.UndefOr[MouseEventHandler[T @uv]]          = js.undefined
  var onMouseMoveCapture: js.UndefOr[MouseEventHandler[T @uv]]   = js.undefined
  var onMouseOut: js.UndefOr[MouseEventHandler[T @uv]]           = js.undefined
  var onMouseOutCapture: js.UndefOr[MouseEventHandler[T @uv]]    = js.undefined
  var onMouseOver: js.UndefOr[MouseEventHandler[T @uv]]          = js.undefined
  var onMouseOverCapture: js.UndefOr[MouseEventHandler[T @uv]]   = js.undefined
  var onMouseUp: js.UndefOr[MouseEventHandler[T @uv]]            = js.undefined
  var onMouseUpCapture: js.UndefOr[MouseEventHandler[T @uv]]     = js.undefined

  // this won't work because once it's a val we can't put in type bounds
  //def onKeyDown[U >: T <: dom.EventTarget]: js.UndefOr[KeyboardEventHandler[U]] = js.undefined

  // Focus Events
  var onFocus: js.UndefOr[FocusEventHandler[T @uv]]        = js.undefined
  var onFocusCapture: js.UndefOr[FocusEventHandler[T @uv]] = js.undefined
  var onBlur: js.UndefOr[FocusEventHandler[T @uv]]         = js.undefined
  var onBlurCapture: js.UndefOr[FocusEventHandler[T @uv]]  = js.undefined

  // Form Events
  //var        onChange: js.UndefOr[FormEventHandler[T @uv]] = js.undefined
  var onChangeCapture: js.UndefOr[FormEventHandler[T @uv]]  = js.undefined
  var onInput: js.UndefOr[FormEventHandler[T @uv]]          = js.undefined
  var onInputCapture: js.UndefOr[FormEventHandler[T @uv]]   = js.undefined
  var onReset: js.UndefOr[FormEventHandler[T @uv]]          = js.undefined
  var onResetCapture: js.UndefOr[FormEventHandler[T @uv]]   = js.undefined
  var onSubmit: js.UndefOr[FormEventHandler[T @uv]]         = js.undefined
  var onSubmitCapture: js.UndefOr[FormEventHandler[T @uv]]  = js.undefined
  var onInvalid: js.UndefOr[FormEventHandler[T @uv]]        = js.undefined
  var onInvalidCapture: js.UndefOr[FormEventHandler[T @uv]] = js.undefined

  // selection events
  var onSelect: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onSelectCapture: js.UndefOr[ReactEventHandler[T @uv]] = js.undefined

  // wheel events
  var onWheel: js.UndefOr[WheelEventHandler[T @uv]]        = js.undefined
  var onWheelCapture: js.UndefOr[WheelEventHandler[T @uv]] = js.undefined

  // touch events
  var onTouchCancel: js.UndefOr[TouchEventHandler[T @uv]]        = js.undefined
  var onTouchCancelCapture: js.UndefOr[TouchEventHandler[T @uv]] = js.undefined
  var onTouchEnd: js.UndefOr[TouchEventHandler[T @uv]]           = js.undefined
  var onTouchEndCapture: js.UndefOr[TouchEventHandler[T @uv]]    = js.undefined
  var onTouchMove: js.UndefOr[TouchEventHandler[T @uv]]          = js.undefined
  var onTouchMoveCapture: js.UndefOr[TouchEventHandler[T @uv]]   = js.undefined
  var onTouchStart: js.UndefOr[TouchEventHandler[T @uv]]         = js.undefined
  var onTouchStartCapture: js.UndefOr[TouchEventHandler[T @uv]]  = js.undefined

  // media events
  var onAbort: js.UndefOr[ReactEventHandler[T @uv]]                 = js.undefined
  var onAbortCapture: js.UndefOr[ReactEventHandler[T @uv]]          = js.undefined
  var onCanPlay: js.UndefOr[ReactEventHandler[T @uv]]               = js.undefined
  var onCanPlayCapture: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onCanPlayThrough: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onCanPlayThroughCapture: js.UndefOr[ReactEventHandler[T @uv]] = js.undefined
  var onDurationChange: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onDurationChangeCapture: js.UndefOr[ReactEventHandler[T @uv]] = js.undefined
  var onEmptied: js.UndefOr[ReactEventHandler[T @uv]]               = js.undefined
  var onEmptiedCapture: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onEncrypted: js.UndefOr[ReactEventHandler[T @uv]]             = js.undefined
  var onEncryptedCapture: js.UndefOr[ReactEventHandler[T @uv]]      = js.undefined
  var onEnded: js.UndefOr[ReactEventHandler[T @uv]]                 = js.undefined
  var onEndedCapture: js.UndefOr[ReactEventHandler[T @uv]]          = js.undefined
  var onLoadedData: js.UndefOr[ReactEventHandler[T @uv]]            = js.undefined
  var onLoadedDataCapture: js.UndefOr[ReactEventHandler[T @uv]]     = js.undefined
  var onLoadedMetadata: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onLoadedMetadataCapture: js.UndefOr[ReactEventHandler[T @uv]] = js.undefined
  var onLoadStart: js.UndefOr[ReactEventHandler[T @uv]]             = js.undefined
  var onLoadStartCapture: js.UndefOr[ReactEventHandler[T @uv]]      = js.undefined
  var onPause: js.UndefOr[ReactEventHandler[T @uv]]                 = js.undefined
  var onPauseCapture: js.UndefOr[ReactEventHandler[T @uv]]          = js.undefined
  var onPlay: js.UndefOr[ReactEventHandler[T @uv]]                  = js.undefined
  var onPlayCapture: js.UndefOr[ReactEventHandler[T @uv]]           = js.undefined
  var onPlaying: js.UndefOr[ReactEventHandler[T @uv]]               = js.undefined
  var onPlayingCapture: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onProgress: js.UndefOr[ReactEventHandler[T @uv]]              = js.undefined
  var onProgressCapture: js.UndefOr[ReactEventHandler[T @uv]]       = js.undefined
  var onRateChange: js.UndefOr[ReactEventHandler[T @uv]]            = js.undefined
  var onRateChangeCapture: js.UndefOr[ReactEventHandler[T @uv]]     = js.undefined
  var onSeeked: js.UndefOr[ReactEventHandler[T @uv]]                = js.undefined
  var onSeekedCapture: js.UndefOr[ReactEventHandler[T @uv]]         = js.undefined
  var onSeeking: js.UndefOr[ReactEventHandler[T @uv]]               = js.undefined
  var onSeekingCapture: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onStalled: js.UndefOr[ReactEventHandler[T @uv]]               = js.undefined
  var onStalledCapture: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onSuspend: js.UndefOr[ReactEventHandler[T @uv]]               = js.undefined
  var onSuspendCapture: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined
  var onTimeUpdate: js.UndefOr[ReactEventHandler[T @uv]]            = js.undefined
  var onTimeUpdateCapture: js.UndefOr[ReactEventHandler[T @uv]]     = js.undefined
  var onVolumeChange: js.UndefOr[ReactEventHandler[T @uv]]          = js.undefined
  var onVolumeChangeCapture: js.UndefOr[ReactEventHandler[T @uv]]   = js.undefined
  var onWaiting: js.UndefOr[ReactEventHandler[T @uv]]               = js.undefined
  var onWaitingCapture: js.UndefOr[ReactEventHandler[T @uv]]        = js.undefined

  // scroll events
  var onScroll: js.UndefOr[UIEventHandler[T @uv]]        = js.undefined
  var onScrollCapture: js.UndefOr[UIEventHandler[T @uv]] = js.undefined

}

trait HTMLAttributes[+T <: dom.EventTarget] extends DOMAttributes[T] {

  /** reactjs specific */
  var defaultChecked: js.UndefOr[Boolean] = js.undefined

  /** reactjs specific */
  var defaultValue: js.UndefOr[String | js.Array[String]] = js.undefined

  /** reactjs specific */
  var suppressContentEditableWarning: js.UndefOr[Boolean] = js.undefined

  var accessKey: js.UndefOr[String]        = js.undefined
  var className: js.UndefOr[String]        = js.undefined
  var contentEditable: js.UndefOr[Boolean] = js.undefined
  var dir: js.UndefOr[String]              = js.undefined

  var draggable: js.UndefOr[Boolean]  = js.undefined
  var hidden: js.UndefOr[Boolean]     = js.undefined
  var id: js.UndefOr[String]          = js.undefined
  var lang: js.UndefOr[String]        = js.undefined
  var slot: js.UndefOr[String]        = js.undefined
  var spellCheck: js.UndefOr[Boolean] = js.undefined

  /** The react style attribute, which should be an object.  If you use this in
   * the raw, you can use `StyleAttr` but its typed more open here to be any
   * object.
   */
  var style: js.UndefOr[js.Object | js.Dynamic] = js.undefined
  var tabIndex: js.UndefOr[Int]                 = js.undefined
  var title: js.UndefOr[String]                 = js.undefined

  var inputMode: js.UndefOr[String]  = js.undefined
  var is: js.UndefOr[String]         = js.undefined
  var radioGroup: js.UndefOr[String] = js.undefined

  // WAI-ARIA
  var role: js.UndefOr[String] = js.undefined

  // non-standard
  var autoCapitalize: js.UndefOr[String] = js.undefined
  var autoCorrect: js.UndefOr[String]    = js.undefined
  var autoSave: js.UndefOr[String]       = js.undefined
  var color: js.UndefOr[String]          = js.undefined
  var itemProp: js.UndefOr[String]       = js.undefined
  var itemScope: js.UndefOr[Boolean]     = js.undefined
  var itemType: js.UndefOr[String]       = js.undefined
  var itemID: js.UndefOr[String]         = js.undefined
  var itemRef: js.UndefOr[String]        = js.undefined
  // or Number?
  var results: js.UndefOr[Int]         = js.undefined
  var security: js.UndefOr[String]     = js.undefined
  var nselectable: js.UndefOr[Boolean] = js.undefined

  @JSName("will-change")
  var willChange: js.UndefOr[String] = js.undefined;

  /*
        'aria-activedescendant'?: string;
        'aria-atomic'?: boolean | 'false' | 'true';
        'aria-autocomplete'?: 'none' | 'inline' | 'list' | 'both';
   */
  var `aria-busy`: js.UndefOr[Boolean|String] = js.undefined
  var `aria-checked`: js.UndefOr[Boolean|String] = js.undefined //?: boolean | 'false' | 'mixed' | 'true';
  /*
        'aria-colcount'?: number;
        'aria-colindex'?: number;
        'aria-colspan'?: number;
        'aria-current'?: boolean | 'false' | 'true' | 'page' | 'step' | 'location' | 'date' | 'time';
        'aria-describedby'?: string;
   */
  var `aria-details`: js.UndefOr[String]            = js.undefined //'?: string;
  var `aria-disabled`: js.UndefOr[Boolean | String] = js.undefined //?: boolean | 'false' | 'true';
  /*
        'aria-dropeffect'?: 'none' | 'copy' | 'execute' | 'link' | 'move' | 'popup';
        'aria-errormessage'?: string;
        'aria-expanded'?: boolean | 'false' | 'true';
        'aria-flowto'?: string;
        'aria-grabbed'?: boolean | 'false' | 'true';
        'aria-haspopup'?: boolean | 'false' | 'true' | 'menu' | 'listbox' | 'tree' | 'grid' | 'dialog';
   */
  var `aria-hidden`: js.UndefOr[Boolean | String] = js.undefined //?: boolean | 'false' | 'true';
  var `aria-invalid`
    : js.UndefOr[Boolean | String] = js.undefined // : boolean | 'false' | 'true' | 'grammar' | 'spelling';

  var `aria-keyshortcuts`: js.UndefOr[String] = js.undefined
  var `aria-label`: js.UndefOr[String]        = js.undefined
  var `aria-labelledby`: js.UndefOr[String]   = js.undefined
  var `aria-level`: js.UndefOr[Int]           = js.undefined
  /*
        'aria-live'?: 'off' | 'assertive' | 'polite';
        'aria-modal'?: boolean | 'false' | 'true';
        'aria-multiline'?: boolean | 'false' | 'true';
        'aria-multiselectable'?: boolean | 'false' | 'true';
        'aria-orientation'?: 'horizontal' | 'vertical';
        'aria-owns'?: string;
   */
  var `aria-placeholder`: js.UndefOr[String] = js.undefined
  var `aria-posinset`: js.UndefOr[Int]       = js.undefined
  /*
        'aria-pressed'?: boolean | 'false' | 'mixed' | 'true';
        'aria-readonly'?: boolean | 'false' | 'true';
        'aria-relevant'?: 'additions' | 'additions text' | 'all' | 'removals' | 'text';
   */
  var `aria-required`: js.UndefOr[Boolean|String] = js.undefined
  var `aria-roledescription`: js.UndefOr[String] = js.undefined
  var `aria-rowcount`: js.UndefOr[Int] = js.undefined
  var `aria-rowindex`: js.UndefOr[Int] = js.undefined
  var `aria-rowspan`: js.UndefOr[Int] = js.undefined
  var `aria-selected`: js.UndefOr[Boolean|String] = js.undefined
  var `aria-setsize`: js.UndefOr[Boolean|String] = js.undefined
  var `aria-sort`: js.UndefOr[String] = js.undefined//'?: 'none' | 'ascending' | 'descending' | 'other';
  var `aria-valuemax`: js.UndefOr[Int]     = js.undefined
  var `aria-valuemin`: js.UndefOr[Int]     = js.undefined
  var `aria-valuenow`: js.UndefOr[Int]     = js.undefined
  var `aria-valuetext`: js.UndefOr[String] = js.undefined
}

trait AllHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  // Standard HTML Attributes
  var accept: js.UndefOr[String]                      = js.undefined
  var acceptCharset: js.UndefOr[String]               = js.undefined
  var action: js.UndefOr[String]                      = js.undefined
  var allowFullScreen: js.UndefOr[Boolean]            = js.undefined
  var allowTransparency: js.UndefOr[Boolean]          = js.undefined
  var alt: js.UndefOr[String]                         = js.undefined
  var as: js.UndefOr[String]                          = js.undefined
  var async: js.UndefOr[Boolean]                      = js.undefined
  var autoComplete: js.UndefOr[String]                = js.undefined
  var autoFocus: js.UndefOr[Boolean]                  = js.undefined
  var autoPlay: js.UndefOr[Boolean]                   = js.undefined
  var capture: js.UndefOr[Boolean]                    = js.undefined
  var cellPadding: js.UndefOr[String | Int]           = js.undefined
  var cellSpacing: js.UndefOr[String | Int]           = js.undefined
  var charSet: js.UndefOr[String]                     = js.undefined
  var challenge: js.UndefOr[String]                   = js.undefined
  var checked: js.UndefOr[Boolean]                    = js.undefined
  var cite: js.UndefOr[String]                        = js.undefined
  var classID: js.UndefOr[String]                     = js.undefined
  var cols: js.UndefOr[Int]                           = js.undefined
  var colSpan: js.UndefOr[Int]                        = js.undefined
  var content: js.UndefOr[String]                     = js.undefined
  var controls: js.UndefOr[Boolean]                   = js.undefined
  var coords: js.UndefOr[String]                      = js.undefined
  var crossOrigin: js.UndefOr[String]                 = js.undefined
  var data: js.UndefOr[String]                        = js.undefined
  var dateTime: js.UndefOr[String]                    = js.undefined
  var default: js.UndefOr[Boolean]                    = js.undefined
  var defer: js.UndefOr[Boolean]                      = js.undefined
  var disabled: js.UndefOr[Boolean]                   = js.undefined
  var download: js.UndefOr[js.Any]                    = js.undefined
  var encType: js.UndefOr[String]                     = js.undefined
  var form: js.UndefOr[String]                        = js.undefined
  var formAction: js.UndefOr[String]                  = js.undefined
  var formEncType: js.UndefOr[String]                 = js.undefined
  var formMethod: js.UndefOr[String]                  = js.undefined
  var formNoValidate: js.UndefOr[Boolean]             = js.undefined
  var formTarget: js.UndefOr[String]                  = js.undefined
  var frameBorder: js.UndefOr[String | Int]           = js.undefined
  var headers: js.UndefOr[String]                     = js.undefined
  var height: js.UndefOr[Int | String]                = js.undefined
  var high: js.UndefOr[Int]                           = js.undefined
  var href: js.UndefOr[String]                        = js.undefined
  var hrefLang: js.UndefOr[String]                    = js.undefined
  var htmlFor: js.UndefOr[String]                     = js.undefined
  var httpEquiv: js.UndefOr[String]                   = js.undefined
  var integrity: js.UndefOr[String]                   = js.undefined
  var keyParams: js.UndefOr[String]                   = js.undefined
  var keyType: js.UndefOr[String]                     = js.undefined
  var kind: js.UndefOr[String]                        = js.undefined
  var label: js.UndefOr[String]                       = js.undefined
  var list: js.UndefOr[String]                        = js.undefined
  var loop: js.UndefOr[Boolean]                       = js.undefined
  var low: js.UndefOr[Int]                            = js.undefined
  var manifest: js.UndefOr[String]                    = js.undefined
  var marginHeight: js.UndefOr[Int]                   = js.undefined
  var marginWidth: js.UndefOr[Int]                    = js.undefined
  var max: js.UndefOr[Int | String]                   = js.undefined
  var maxLength: js.UndefOr[Int]                      = js.undefined
  var media: js.UndefOr[String]                       = js.undefined
  var mediaGroup: js.UndefOr[String]                  = js.undefined
  var method: js.UndefOr[String]                      = js.undefined
  var min: js.UndefOr[Int | String]                   = js.undefined
  var minLength: js.UndefOr[Int]                      = js.undefined
  var multiple: js.UndefOr[Boolean]                   = js.undefined
  var muted: js.UndefOr[Boolean]                      = js.undefined
  var name: js.UndefOr[String]                        = js.undefined
  var nonce: js.UndefOr[String]                       = js.undefined
  var noValidate: js.UndefOr[Boolean]                 = js.undefined
  var open: js.UndefOr[Boolean]                       = js.undefined
  var optimum: js.UndefOr[Int]                        = js.undefined
  var pattern: js.UndefOr[String]                     = js.undefined
  var placeholder: js.UndefOr[String]                 = js.undefined
  var playsInline: js.UndefOr[Boolean]                = js.undefined
  var poster: js.UndefOr[String]                      = js.undefined
  var preload: js.UndefOr[String]                     = js.undefined
  var readOnly: js.UndefOr[Boolean]                   = js.undefined
  var rel: js.UndefOr[String]                         = js.undefined
  var required: js.UndefOr[Boolean]                   = js.undefined
  var reversed: js.UndefOr[Boolean]                   = js.undefined
  var rows: js.UndefOr[Int]                           = js.undefined
  var rowSpan: js.UndefOr[Int]                        = js.undefined
  var sandbox: js.UndefOr[String]                     = js.undefined
  var scope: js.UndefOr[String]                       = js.undefined
  var scoped: js.UndefOr[String]                      = js.undefined
  var scrolling: js.UndefOr[String]                   = js.undefined
  var seamless: js.UndefOr[Boolean]                   = js.undefined
  var selected: js.UndefOr[Boolean]                   = js.undefined
  var shape: js.UndefOr[String]                       = js.undefined
  var size: js.UndefOr[Int]                           = js.undefined
  var sizes: js.UndefOr[String]                       = js.undefined
  var span: js.UndefOr[Int]                           = js.undefined
  var src: js.UndefOr[String]                         = js.undefined
  var srcDoc: js.UndefOr[String]                      = js.undefined
  var srcLang: js.UndefOr[String]                     = js.undefined
  var srcSet: js.UndefOr[String]                      = js.undefined
  var start: js.UndefOr[Int]                          = js.undefined
  var step: js.UndefOr[Int | String]                  = js.undefined
  var summary: js.UndefOr[String]                     = js.undefined
  var target: js.UndefOr[String]                      = js.undefined
  var `type`: js.UndefOr[String]                      = js.undefined
  var useMap: js.UndefOr[String]                      = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
  var width: js.UndefOr[String | Int]                 = js.undefined
  var wmode: js.UndefOr[String]                       = js.undefined
  var wrap: js.UndefOr[String]                        = js.undefined
}

trait AnchorHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var download: js.UndefOr[js.Any] = js.undefined
  var href: js.UndefOr[String]     = js.undefined
  var hrefLang: js.UndefOr[String] = js.undefined
  var media: js.UndefOr[String]    = js.undefined
  var rel: js.UndefOr[String]      = js.undefined
  var target: js.UndefOr[String]   = js.undefined
  var `type`: js.UndefOr[String]   = js.undefined
  var as: js.UndefOr[String]       = js.undefined
}

trait AreaHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var alt: js.UndefOr[String]      = js.undefined
  var coords: js.UndefOr[String]   = js.undefined
  var download: js.UndefOr[js.Any] = js.undefined
  var href: js.UndefOr[String]     = js.undefined
  var hrefLang: js.UndefOr[String] = js.undefined
  var media: js.UndefOr[String]    = js.undefined
  var rel: js.UndefOr[String]      = js.undefined
  var shape: js.UndefOr[String]    = js.undefined
  var target: js.UndefOr[String]   = js.undefined
}

trait AudioHTMLAttributes[+T <: dom.EventTarget] extends MediaHTMLAttributes[T] {}

trait BaseHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var cite: js.UndefOr[String] = js.undefined
}

trait BlockQuoteHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var cite: js.UndefOr[String] = js.undefined
}

trait ButtonHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var autoFocus: js.UndefOr[Boolean]                     = js.undefined
  var disabled: js.UndefOr[Boolean]                      = js.undefined
  var form: js.UndefOr[String]                           = js.undefined
  var formAction: js.UndefOr[String]                     = js.undefined
  var formEncType: js.UndefOr[String]                    = js.undefined
  var formMethod: js.UndefOr[String]                     = js.undefined
  var formNoVaridate: js.UndefOr[Boolean]                = js.undefined
  var formTarget: js.UndefOr[String]                     = js.undefined
  var name: js.UndefOr[String]                           = js.undefined
  var `type`: js.UndefOr[String]                         = js.undefined
  var varue: js.UndefOr[String | js.Array[String] | Int] = js.undefined
}

trait CanvasHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var height: js.UndefOr[Int | String] = js.undefined
  var width: js.UndefOr[String | Int]  = js.undefined
}

trait ColHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var span: js.UndefOr[Int] = js.undefined
}

trait ColgroupHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var span: js.UndefOr[Int] = js.undefined
}

trait HtmlHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var mainfest: js.UndefOr[String] = js.undefined
}

trait DelHTMLAtributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var cite: js.UndefOr[String]     = js.undefined
  var dateTime: js.UndefOr[String] = js.undefined
}

trait EmbedHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var height: js.UndefOr[Int | String] = js.undefined
  var src: js.UndefOr[String]          = js.undefined
  var width: js.UndefOr[String | Int]  = js.undefined
  var `type`: js.UndefOr[String]       = js.undefined
}

trait FieldsetHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var disabled: js.UndefOr[Boolean] = js.undefined
  var form: js.UndefOr[String]      = js.undefined
  var name: js.UndefOr[String]      = js.undefined
}

trait FormHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var acceptCharset: js.UndefOr[String] = js.undefined
  var action: js.UndefOr[String]        = js.undefined
  var autoComplete: js.UndefOr[String]  = js.undefined
  var encType: js.UndefOr[String]       = js.undefined
  var method: js.UndefOr[String]        = js.undefined
  var name: js.UndefOr[String]          = js.undefined
  var noValidate: js.UndefOr[Boolean]   = js.undefined
  var target: js.UndefOr[String]        = js.undefined
}

trait IframeHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var allowFullScreen: js.UndefOr[Boolean]   = js.undefined
  var allowTransparency: js.UndefOr[Boolean] = js.undefined
  var frameBorder: js.UndefOr[String | Int]  = js.undefined
  var height: js.UndefOr[Int | String]       = js.undefined
  var marginHeight: js.UndefOr[Int]          = js.undefined
  var marginWidth: js.UndefOr[Int]           = js.undefined
  var name: js.UndefOr[String]               = js.undefined
  var sandbox: js.UndefOr[String]            = js.undefined
  var scrolling: js.UndefOr[String]          = js.undefined
  var seamless: js.UndefOr[Boolean]          = js.undefined
  var src: js.UndefOr[String]                = js.undefined
  var srcDoc: js.UndefOr[String]             = js.undefined
  var width: js.UndefOr[String | Int]        = js.undefined
}

trait ImgHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var alt: js.UndefOr[String]                   = js.undefined
  var height: js.UndefOr[String | Double | Int] = js.undefined
  var sizes: js.UndefOr[String]                 = js.undefined
  var src: js.UndefOr[String]                   = js.undefined
  var srcSet: js.UndefOr[String]                = js.undefined
  var useMap: js.UndefOr[String]                = js.undefined
  var width: js.UndefOr[String | Double | Int]  = js.undefined
}

trait InputHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var accept: js.UndefOr[String]                      = js.undefined
  var alt: js.UndefOr[String]                         = js.undefined
  var autoComplete: js.UndefOr[String]                = js.undefined
  var autoFocus: js.UndefOr[Boolean]                  = js.undefined
  var capture: js.UndefOr[Boolean]                    = js.undefined
  var checked: js.UndefOr[Boolean]                    = js.undefined
  var crossOrigin: js.UndefOr[String]                 = js.undefined
  var disabled: js.UndefOr[Boolean]                   = js.undefined
  var form: js.UndefOr[String]                        = js.undefined
  var formAction: js.UndefOr[String]                  = js.undefined
  var formEncType: js.UndefOr[String]                 = js.undefined
  var formNoValidate: js.UndefOr[Boolean]             = js.undefined
  var formTarget: js.UndefOr[String]                  = js.undefined
  var height: js.UndefOr[String | Int]                = js.undefined
  var list: js.UndefOr[String]                        = js.undefined
  var max: js.UndefOr[Int | String]                   = js.undefined
  var maxLength: js.UndefOr[Int]                      = js.undefined
  var min: js.UndefOr[Int | String]                   = js.undefined
  var minLength: js.UndefOr[Int]                      = js.undefined
  var multiple: js.UndefOr[Boolean]                   = js.undefined
  var name: js.UndefOr[String]                        = js.undefined
  var pattern: js.UndefOr[String]                     = js.undefined
  var placeholder: js.UndefOr[String]                 = js.undefined
  var readOnly: js.UndefOr[Boolean]                   = js.undefined
  var required: js.UndefOr[Boolean]                   = js.undefined
  var size: js.UndefOr[Int]                           = js.undefined
  var src: js.UndefOr[String]                         = js.undefined
  var step: js.UndefOr[String | Int]                  = js.undefined
  var `type`: js.UndefOr[String]                      = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
  var width: js.UndefOr[String | Int]                 = js.undefined
  var onChange: js.UndefOr[ChangeEventHandler[T @uv]] = js.undefined
}

trait InsHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var cite: js.UndefOr[String]     = js.undefined
  var dateTime: js.UndefOr[String] = js.undefined
}

trait LabelHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {

  /** @deprecated */
  var form: js.UndefOr[String]    = js.undefined
  var htmlFor: js.UndefOr[String] = js.undefined
}

trait LiHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
}

trait LinkHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var as: js.UndefOr[String]          = js.undefined
  var crossOrigin: js.UndefOr[String] = js.undefined
  var href: js.UndefOr[String]        = js.undefined
  var hrefLang: js.UndefOr[String]    = js.undefined
  var integrity: js.UndefOr[String]   = js.undefined
  var media: js.UndefOr[String]       = js.undefined
  var rel: js.UndefOr[String]         = js.undefined
  var sizes: js.UndefOr[String]       = js.undefined
  var `type`: js.UndefOr[String]      = js.undefined
}

trait MapHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var name: js.UndefOr[String] = js.undefined
}

trait MediaHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var autoPlay: js.UndefOr[Boolean]    = js.undefined
  var controls: js.UndefOr[Boolean]    = js.undefined
  var crossOrigin: js.UndefOr[String]  = js.undefined
  var loop: js.UndefOr[Boolean]        = js.undefined
  var mediaGroup: js.UndefOr[String]   = js.undefined
  var muted: js.UndefOr[Boolean]       = js.undefined
  var playsInline: js.UndefOr[Boolean] = js.undefined
  var preload: js.UndefOr[String]      = js.undefined
  var src: js.UndefOr[String]          = js.undefined
}

trait MenuHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var `type`: js.UndefOr[String] = js.undefined
}

trait MetaHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var charSet: js.UndefOr[String]   = js.undefined
  var content: js.UndefOr[String]   = js.undefined
  var httpEquiv: js.UndefOr[String] = js.undefined
  var name: js.UndefOr[String]      = js.undefined
}

trait MeterHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var form: js.UndefOr[String]                        = js.undefined
  var high: js.UndefOr[Int]                           = js.undefined
  var low: js.UndefOr[Int]                            = js.undefined
  var max: js.UndefOr[Int | String]                   = js.undefined
  var min: js.UndefOr[Int | String]                   = js.undefined
  var optimum: js.UndefOr[Int]                        = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
}

trait ObjectHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var classID: js.UndefOr[String]      = js.undefined
  var data: js.UndefOr[String]         = js.undefined
  var form: js.UndefOr[String]         = js.undefined
  var height: js.UndefOr[String | Int] = js.undefined
  var name: js.UndefOr[String]         = js.undefined
  var `type`: js.UndefOr[String]       = js.undefined
  var useMap: js.UndefOr[String]       = js.undefined
  var width: js.UndefOr[String | Int]  = js.undefined
  var wmode: js.UndefOr[String]        = js.undefined
}

trait OlHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var reversed: js.UndefOr[Boolean] = js.undefined
  var start: js.UndefOr[Int]        = js.undefined
}

trait OptgroupHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var disabled: js.UndefOr[Boolean] = js.undefined
  var label: js.UndefOr[String]     = js.undefined
}

trait OptionHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var disabled: js.UndefOr[Boolean]                   = js.undefined
  var label: js.UndefOr[String]                       = js.undefined
  var selected: js.UndefOr[Boolean]                   = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
}

trait OutputHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var form: js.UndefOr[String]    = js.undefined
  var htmlFor: js.UndefOr[String] = js.undefined
  var name: js.UndefOr[String]    = js.undefined
}

trait ParamHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var name: js.UndefOr[String]                        = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
}

trait ProgressHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var max: js.UndefOr[Int | String]                   = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
}

trait QuoteHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var cite: js.UndefOr[String] = js.undefined
}

trait ScriptHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var async: js.UndefOr[Boolean]      = js.undefined
  var charSet: js.UndefOr[String]     = js.undefined
  var crossOrigin: js.UndefOr[String] = js.undefined
  var defer: js.UndefOr[Boolean]      = js.undefined
  var integrity: js.UndefOr[String]   = js.undefined
  var nonce: js.UndefOr[String]       = js.undefined
  var src: js.UndefOr[String]         = js.undefined
  var `type`: js.UndefOr[String]      = js.undefined
}

trait SelectHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var autoFocus: js.UndefOr[Boolean]                  = js.undefined
  var disabled: js.UndefOr[Boolean]                   = js.undefined
  var form: js.UndefOr[String]                        = js.undefined
  var multiple: js.UndefOr[Boolean]                   = js.undefined
  var name: js.UndefOr[String]                        = js.undefined
  var required: js.UndefOr[Boolean]                   = js.undefined
  var size: js.UndefOr[Int]                           = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
  var onChange: js.UndefOr[ChangeEventHandler[T @uv]] = js.undefined
}

trait SourceHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var media: js.UndefOr[String]  = js.undefined
  var sizes: js.UndefOr[String]  = js.undefined
  var src: js.UndefOr[String]    = js.undefined
  var srcSet: js.UndefOr[String] = js.undefined
  var `type`: js.UndefOr[String] = js.undefined
}

trait StyleHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var media: js.UndefOr[String]  = js.undefined
  var nonce: js.UndefOr[String]  = js.undefined
  var scoped: js.UndefOr[String] = js.undefined
  var `type`: js.UndefOr[String] = js.undefined
}

trait TableHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var cellPadding: js.UndefOr[String | Int] = js.undefined
  var cellSpacing: js.UndefOr[String | Int] = js.undefined
  var summary: js.UndefOr[String]           = js.undefined
}

trait TextAreaHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var autoComplete: js.UndefOr[String]                = js.undefined
  var autoFocus: js.UndefOr[String]                   = js.undefined
  var cols: js.UndefOr[Int]                           = js.undefined
  var dirName: js.UndefOr[String]                     = js.undefined
  var disabled: js.UndefOr[Boolean]                   = js.undefined
  var form: js.UndefOr[String]                        = js.undefined
  var maxLength: js.UndefOr[Int]                      = js.undefined
  var minLength: js.UndefOr[Int]                      = js.undefined
  var name: js.UndefOr[String]                        = js.undefined
  var placeholder: js.UndefOr[String]                 = js.undefined
  var readOnly: js.UndefOr[Boolean]                   = js.undefined
  var required: js.UndefOr[Boolean]                   = js.undefined
  var rows: js.UndefOr[Int]                           = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
  var wrap: js.UndefOr[String]                        = js.undefined
  var onChange: js.UndefOr[ChangeEventHandler[T @uv]] = js.undefined
}

trait TdHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var colSpan: js.UndefOr[Int]    = js.undefined
  var headers: js.UndefOr[String] = js.undefined
  var rowSpan: js.UndefOr[Int]    = js.undefined
  var scope: js.UndefOr[String]   = js.undefined
}

trait ThHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var colSpan: js.UndefOr[Int]    = js.undefined
  var headers: js.UndefOr[String] = js.undefined
  var rowSpan: js.UndefOr[Int]    = js.undefined
  var scope: js.UndefOr[String]   = js.undefined
}

trait TimeHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var dateTime: js.UndefOr[String] = js.undefined
}

trait TrackHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var default: js.UndefOr[Boolean] = js.undefined
  var kind: js.UndefOr[String]     = js.undefined
  var label: js.UndefOr[String]    = js.undefined
  var src: js.UndefOr[String]      = js.undefined
  var srcLang: js.UndefOr[String]  = js.undefined
}

trait VideoHTMLAttributes[+T <: dom.EventTarget] extends MediaHTMLAttributes[T] {
  var height: js.UndefOr[Int | String] = js.undefined
  var poster: js.UndefOr[String]       = js.undefined
  var width: js.UndefOr[String | Int]  = js.undefined
}

/** Electron support. */
trait WebViewHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var allowFullScreen: js.UndefOr[Boolean]     = js.undefined
  var allowpopups: js.UndefOr[Boolean]         = js.undefined
  var autoFocus: js.UndefOr[Boolean]           = js.undefined
  var autosize: js.UndefOr[Boolean]            = js.undefined
  var blinkfeatures: js.UndefOr[String]        = js.undefined
  var disableblinkfeatures: js.UndefOr[String] = js.undefined
  var disableguestresize: js.UndefOr[Boolean]  = js.undefined
  var disablewebsecurity: js.UndefOr[Boolean]  = js.undefined
  var guestinstance: js.UndefOr[String]        = js.undefined
  var httpreferrer: js.UndefOr[String]         = js.undefined
  var nodeintegration: js.UndefOr[Boolean]     = js.undefined
  var partition: js.UndefOr[Boolean]           = js.undefined
  var plugins: js.UndefOr[Boolean]             = js.undefined
  var preload: js.UndefOr[String]              = js.undefined
  var src: js.UndefOr[String]                  = js.undefined
  var useragent: js.UndefOr[String]            = js.undefined
  var webpreferences: js.UndefOr[String]       = js.undefined
}

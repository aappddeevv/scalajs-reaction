// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

import scala.annotation.unchecked.{uncheckedVariance => uv}
import scalajs.js
import js.Dynamic.{literal => lit}
import js.|
import org.scalajs.dom
import dom.html

trait SetInnerHTML extends js.Object {
  val __html: js.UndefOr[String] = js.undefined
}

trait Attributes extends js.Object {
  var key: js.UndefOr[String] = js.undefined
}

trait ClassAttributes[E] extends Attributes {
  var ref: js.UndefOr[RefCbE[E]] = js.undefined
}

/** A props trait that takes all HTML props. */
trait HTMLProps[+T <: dom.EventTarget]
    extends AllHTMLAttributes[T @uv]
    with ClassAttributes[T @uv] {}

/** A props trait that takes all SVG props. */
trait SVGProps[+T <: dom.EventTarget] extends SVGAttributes[T @uv] with ClassAttributes[T @uv] {}
trait ElementAttributesOnly
    extends HTMLAttributes[dom.html.Element]
    with ClassAttributes[dom.html.Element]

object tags {

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

  trait ButtonProps
      extends ButtonHTMLAttributes[dom.html.Button]
      with ClassAttributes[dom.html.Button]
  final lazy val button = tagt[ButtonProps]("button")

  trait CanvasProps
      extends CanvasHTMLAttributes[dom.html.Canvas]
      with ClassAttributes[dom.html.Canvas]
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
  trait ColgroupProps
      extends ColgroupHTMLAttributes[dom.html.Element]
      with ClassAttributes[dom.html.Element]
  final lazy val colgroup = tagt[ColgroupProps]("colgroup")

  // shoud be dom.html.Data
  type DataProps = ElementAttributesOnly
  final lazy val data = tagt[DataProps]("data")

  trait DataListProps
      extends HTMLAttributes[dom.html.DataList]
      with ClassAttributes[dom.html.DataList]
  final lazy val datalist = tagt[DataListProps]("datalist")

  trait DDProps extends HTMLAttributes[dom.html.DD] with ClassAttributes[dom.html.DD]
  final lazy val dd = tagt[DDProps]("dd")

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

  // should be dom.html.Dl
  type DlProps = ElementAttributesOnly
  final lazy val dl = tagt[DivProps]("dl")

  // should be dom.html.Dt
  type DtProps = ElementAttributesOnly
  final lazy val dt = tagt[DtProps]("dt")

  // should be dom.html.El
  type EmProps = ElementAttributesOnly
  final lazy val em = tagt[EmProps]("em")

  trait EmbedProps extends EmbedHTMLAttributes[dom.html.Embed] with ClassAttributes[dom.html.Embed]
  final lazy val embed = tagt[EmbedProps]("embed")

  trait FieldsetProps
      extends FieldsetHTMLAttributes[dom.html.FieldSet]
      with ClassAttributes[dom.html.FieldSet]
  final lazy val fieldset = tagt[FieldsetProps]("fieldset")

  type FigcaptionProps = ElementAttributesOnly
  final lazy val figcaption = tagt[FigcaptionProps]("figcaption")

  type FigureProps = ElementAttributesOnly
  final lazy val figure = tagt[FigureProps]("figure")

  type FooterProps = ElementAttributesOnly
  final lazy val footer = tagt[FooterProps]("footer")

  trait FormProps extends FormHTMLAttributes[dom.html.Form] with ClassAttributes[dom.html.Form]
  final lazy val form = tagt[FormProps]("form")

  trait HeadingProps extends HTMLAttributes[dom.html.Heading]
  final lazy val h1 = tagt[HeadingProps]("h1")
  final lazy val h2 = tagt[HeadingProps]("h2")
  final lazy val h3 = tagt[HeadingProps]("h2")
  final lazy val h4 = tagt[HeadingProps]("h2")
  final lazy val h5 = tagt[HeadingProps]("h2")
  final lazy val h6 = tagt[HeadingProps]("h2")

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

  trait IframeProps
      extends IframeHTMLAttributes[dom.html.IFrame]
      with ClassAttributes[dom.html.IFrame]
  final lazy val iframe = tagt[IframeProps]("iframe")

  trait ImgProps extends ImgHTMLAttributes[dom.html.Image] with ClassAttributes[dom.html.Image]
  final lazy val img = tagt[ImgProps]("img")

  trait InputProps extends InputHTMLAttributes[dom.html.Input] with ClassAttributes[dom.html.Input]

  /** Can use either input(props)(children) or input.checkbox(props)(children). */
  object input extends TagT[InputProps]("input") {
    def withType(`type`: String) =
      new TagT[InputProps]("input", lit("type" -> `type`).asInstanceOf[InputProps])

    lazy val button        = this withType "button"
    lazy val checkbox      = this withType "checkbox"
    lazy val color         = this withType "color"
    lazy val date          = this withType "date"
    lazy val datetime      = this withType "datetime"
    lazy val datetimeLocal = this withType "datetime-local"
    lazy val email         = this withType "email"
    lazy val file          = this withType "file"
    lazy val hidden        = this withType "hidden"
    lazy val image         = this withType "image"
    lazy val month         = this withType "month"
    lazy val number        = this withType "number"
    lazy val password      = this withType "password"
    lazy val radio         = this withType "radio"
    lazy val range         = this withType "range"
    lazy val reset         = this withType "reset"
    lazy val search        = this withType "search"
    lazy val submit        = this withType "submit"
    lazy val tel           = this withType "tel"
    lazy val text          = this withType "text"
    lazy val time          = this withType "time"
    lazy val url           = this withType "url"
    lazy val week          = this withType "week"
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

  trait MenuitemProps
      extends HTMLAttributes[dom.html.Element]
      with ClassAttributes[dom.html.Element]
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

  trait ObjectProps
      extends ObjectHTMLAttributes[dom.html.Object]
      with ClassAttributes[dom.html.Object]
  final lazy val `object` = tagt[ObjectProps]("object")

  trait OlProps extends OlHTMLAttributes[dom.html.Element] with ClassAttributes[dom.html.Element]
  final lazy val ol = tagt[OlProps]("ol")

  trait OptgroupProps
      extends OptgroupHTMLAttributes[dom.html.OptGroup]
      with ClassAttributes[dom.html.OptGroup]
  final lazy val optgroup = tagt[OptgroupProps]("optgroup")

  trait OptionProps
      extends OptionHTMLAttributes[dom.html.Option]
      with ClassAttributes[dom.html.Option]
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

  trait ProgressProps
      extends ProgressHTMLAttributes[dom.html.Progress]
      with ClassAttributes[dom.html.Progress]
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

  trait ScriptProps
      extends ScriptHTMLAttributes[dom.html.Script]
      with ClassAttributes[dom.html.Script]
  final lazy val scripf = tagt[ScriptProps]("script")

  type SectionProps = ElementAttributesOnly
  final lazy val section = tagt[SectionProps]("section")

  trait SelectProps
      extends SelectHTMLAttributes[dom.html.Select]
      with ClassAttributes[dom.html.Select]
  final lazy val select = tagt[SelectProps]("select")

  type SmallProps = ElementAttributesOnly
  final lazy val small = tagt[SmallProps]("small")

  trait SourceProps
      extends SourceHTMLAttributes[dom.html.Source]
      with ClassAttributes[dom.html.Source]
  final lazy val source = tagt[SourceProps]("source")

  trait SpanProps extends HTMLAttributes[dom.html.Span] with ClassAttributes[dom.html.Span]
  final lazy val span = tagt[SpanProps]("span")

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

  trait TbodyProps
      extends HTMLAttributes[dom.html.TableSection]
      with ClassAttributes[dom.html.TableSection]
  final lazy val tbody = tagt[TbodyProps]("tbody")

  trait TdProps
      extends HTMLAttributes[dom.html.TableDataCell]
      with ClassAttributes[dom.html.TableDataCell]
  final lazy val td = tagt[TdProps]("td")

  trait TextareaProps
      extends TextAreaHTMLAttributes[dom.html.TextArea]
      with ClassAttributes[dom.html.TextArea]
  final lazy val textarea = tagt[TextareaProps]("textarea")

  trait TfootProps
      extends HTMLAttributes[dom.html.TableSection]
      with ClassAttributes[dom.html.TableSection]
  final lazy val tfoot = tagt[TfootProps]("tfoot")

  trait ThProps
      extends ThHTMLAttributes[dom.html.TableHeaderCell]
      with ClassAttributes[dom.html.TableHeaderCell]
  final lazy val th = tagt[ThProps]("th")

  trait TheadProps
      extends HTMLAttributes[dom.html.TableSection]
      with ClassAttributes[dom.html.TableSection]
  final lazy val thead = tagt[TheadProps]("thead")

  // should be dom.html.Time
  trait TimeProps
      extends TimeHTMLAttributes[dom.html.Element]
      with ClassAttributes[dom.html.Element]
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
  /*
final lazy val        webview: DetailedHTMLFactory<WebViewHTMLAttributes<HTMLElement>, HTMLElement>;
 */
}

/**
  * Mostly handlers for DOM nodes.
  */
trait DOMAttributes[+T <: dom.EventTarget] extends js.Object {

  /** react specific */
  var children: js.UndefOr[ReactNode] = js.undefined

  /** react specific */
  var dangerouslySetInnerHTML: js.UndefOr[SetInnerHTML] = js.undefined

  // keyboard events val onKeyDown: js.UndefOr[js.Function0[]] = js.undefined
  var onKeyDown: js.UndefOr[KeyboardEventHandler[T @uv]]         = js.undefined
  var onKeyPress: js.UndefOr[KeyboardEventHandler[T @uv]]        = js.undefined
  var onKeyPressCapture: js.UndefOr[KeyboardEventHandler[T @uv]] = js.undefined
  var onKeyUp: js.UndefOr[KeyboardEventHandler[T @uv]]           = js.undefined
  var onKeyUpCapture: js.UndefOr[KeyboardEventHandler[T @uv]]    = js.undefined

  // mouse events
  var onClick: js.UndefOr[MouseEventHandler[T @uv]]              = js.undefined
  var onClickCapture: js.UndefOr[MouseEventHandler[T @uv]]       = js.undefined
  var onDoubleClick: js.UndefOr[MouseEventHandler[T @uv]]        = js.undefined
  var onDoubleClickCapture: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined

  var onMouseDown: js.UndefOr[MouseEventHandler[T @uv]]  = js.undefined
  var onMouseEnter: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onMouseLeave: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onMouseMove: js.UndefOr[MouseEventHandler[T @uv]]  = js.undefined
  var onMouseOut: js.UndefOr[MouseEventHandler[T @uv]]   = js.undefined
  var onMouseOver: js.UndefOr[MouseEventHandler[T @uv]]  = js.undefined
  var onMouseUp: js.UndefOr[MouseEventHandler[T @uv]]    = js.undefined

  // this won't work because once it's a val we can't put in type bounds
  //def onKeyDown[U >: T <: dom.EventTarget]: js.UndefOr[KeyboardEventHandler[U]] = js.undefined

  // lots more to type...

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

}

trait HTMLAttributes[+T <: dom.EventTarget] extends DOMAttributes[T] {

  /** react specific */
  var defaultChecked: js.UndefOr[Boolean] = js.undefined

  /** react specific */
  var defaultValue: js.UndefOr[String | js.Array[String]] = js.undefined

  /** react specific */
  var suppressContentEditableWarning: js.UndefOr[Boolean] = js.undefined

  var accessKey: js.UndefOr[String]        = js.undefined
  var className: js.UndefOr[String]        = js.undefined
  var contentEditable: js.UndefOr[Boolean] = js.undefined
  var dir: js.UndefOr[String]              = js.undefined
  var draggable: js.UndefOr[Boolean]       = js.undefined
  var hidden: js.UndefOr[Boolean]          = js.undefined
  var id: js.UndefOr[String]               = js.undefined
  var lang: js.UndefOr[String]             = js.undefined
  var slot: js.UndefOr[String]             = js.undefined
  var spellCheck: js.UndefOr[Boolean]      = js.undefined
  // was js.Any, but use js.Object|js.Dynamic or other, can use StyleAttr here!
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
  var alt: js.UndefOr[String]          = js.undefined
  var height: js.UndefOr[String | Int] = js.undefined
  var sizes: js.UndefOr[String]        = js.undefined
  var src: js.UndefOr[String]          = js.undefined
  var srcSet: js.UndefOr[String]       = js.undefined
  var useMap: js.UndefOr[String]       = js.undefined
  var width: js.UndefOr[String | Int]  = js.undefined
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

trait SVGAttributes[+T <: dom.EventTarget] extends DOMAttributes[T] {
  /*
        className?: string;
        color?: string;
        height?: number | string;
        id?: string;
        lang?: string;
        max?: number | string;
        media?: string;
        method?: string;
        min?: number | string;
        name?: string;
        style?: CSSProperties;
        target?: string;
        type?: string;
        width?: number | string;

        // Other HTML properties supported by SVG elements in browsers
        role?: string;
        tabIndex?: number;

        // SVG Specific attributes
        accentHeight?: number | string;
        accumulate?: "none" | "sum";
        additive?: "replace" | "sum";
        alignmentBaseline?: "auto" | "baseline" | "before-edge" | "text-before-edge" | "middle" | "central" | "after-edge" |
        "text-after-edge" | "ideographic" | "alphabetic" | "hanging" | "mathematical" | "inherit";
        allowReorder?: "no" | "yes";
        alphabetic?: number | string;
        amplitude?: number | string;
        arabicForm?: "initial" | "medial" | "terminal" | "isolated";
        ascent?: number | string;
        attributeName?: string;
        attributeType?: string;
        autoReverse?: number | string;
        azimuth?: number | string;
        baseFrequency?: number | string;
        baselineShift?: number | string;
        baseProfile?: number | string;
        bbox?: number | string;
        begin?: number | string;
        bias?: number | string;
        by?: number | string;
        calcMode?: number | string;
        capHeight?: number | string;
        clip?: number | string;
        clipPath?: string;
        clipPathUnits?: number | string;
        clipRule?: number | string;
        colorInterpolation?: number | string;
        colorInterpolationFilters?: "auto" | "sRGB" | "linearRGB" | "inherit";
        colorProfile?: number | string;
        colorRendering?: number | string;
        contentScriptType?: number | string;
        contentStyleType?: number | string;
        cursor?: number | string;
        cx?: number | string;
        cy?: number | string;
        d?: string;
        decelerate?: number | string;
        descent?: number | string;
        diffuseConstant?: number | string;
        direction?: number | string;
        display?: number | string;
        divisor?: number | string;
        dominantBaseline?: number | string;
        dur?: number | string;
        dx?: number | string;
        dy?: number | string;
        edgeMode?: number | string;
        elevation?: number | string;
        enableBackground?: number | string;
        end?: number | string;
        exponent?: number | string;
        externalResourcesRequired?: number | string;
        fill?: string;
        fillOpacity?: number | string;
        fillRule?: "nonzero" | "evenodd" | "inherit";
        filter?: string;
        filterRes?: number | string;
        filterUnits?: number | string;
        floodColor?: number | string;
        floodOpacity?: number | string;
        focusable?: number | string;
        fontFamily?: string;
        fontSize?: number | string;
        fontSizeAdjust?: number | string;
        fontStretch?: number | string;
        fontStyle?: number | string;
        fontVariant?: number | string;
        fontWeight?: number | string;
        format?: number | string;
        from?: number | string;
        fx?: number | string;
        fy?: number | string;
        g1?: number | string;
        g2?: number | string;
        glyphName?: number | string;
        glyphOrientationHorizontal?: number | string;
        glyphOrientationVertical?: number | string;
        glyphRef?: number | string;
        gradientTransform?: string;
        gradientUnits?: string;
        hanging?: number | string;
        horizAdvX?: number | string;
        horizOriginX?: number | string;
        ideographic?: number | string;
        imageRendering?: number | string;
        in2?: number | string;
        in?: string;
        intercept?: number | string;
        k1?: number | string;
        k2?: number | string;
        k3?: number | string;
        k4?: number | string;
        k?: number | string;
        kernelMatrix?: number | string;
        kernelUnitLength?: number | string;
        kerning?: number | string;
        keyPoints?: number | string;
        keySplines?: number | string;
        keyTimes?: number | string;
        lengthAdjust?: number | string;
        letterSpacing?: number | string;
        lightingColor?: number | string;
        limitingConeAngle?: number | string;
        local?: number | string;
        markerEnd?: string;
        markerHeight?: number | string;
        markerMid?: string;
        markerStart?: string;
        markerUnits?: number | string;
        markerWidth?: number | string;
        mask?: string;
        maskContentUnits?: number | string;
        maskUnits?: number | string;
        mathematical?: number | string;
        mode?: number | string;
        numOctaves?: number | string;
        offset?: number | string;
        opacity?: number | string;
        operator?: number | string;
        order?: number | string;
        orient?: number | string;
        orientation?: number | string;
        origin?: number | string;
        overflow?: number | string;
        overlinePosition?: number | string;
        overlineThickness?: number | string;
        paintOrder?: number | string;
        panose1?: number | string;
        pathLength?: number | string;
        patternContentUnits?: string;
        patternTransform?: number | string;
        patternUnits?: string;
        pointerEvents?: number | string;
        points?: string;
        pointsAtX?: number | string;
        pointsAtY?: number | string;
        pointsAtZ?: number | string;
        preserveAlpha?: number | string;
        preserveAspectRatio?: string;
        primitiveUnits?: number | string;
        r?: number | string;
        radius?: number | string;
        refX?: number | string;
        refY?: number | string;
        renderingIntent?: number | string;
        repeatCount?: number | string;
        repeatDur?: number | string;
        requiredExtensions?: number | string;
        requiredFeatures?: number | string;
        restart?: number | string;
        result?: string;
        rotate?: number | string;
        rx?: number | string;
        ry?: number | string;
        scale?: number | string;
        seed?: number | string;
        shapeRendering?: number | string;
        slope?: number | string;
        spacing?: number | string;
        specularConstant?: number | string;
        specularExponent?: number | string;
        speed?: number | string;
        spreadMethod?: string;
        startOffset?: number | string;
        stdDeviation?: number | string;
        stemh?: number | string;
        stemv?: number | string;
        stitchTiles?: number | string;
        stopColor?: string;
        stopOpacity?: number | string;
        strikethroughPosition?: number | string;
        strikethroughThickness?: number | string;
        string?: number | string;
        stroke?: string;
        strokeDasharray?: string | number;
        strokeDashoffset?: string | number;
        strokeLinecap?: "butt" | "round" | "square" | "inherit";
        strokeLinejoin?: "miter" | "round" | "bevel" | "inherit";
        strokeMiterlimit?: number | string;
        strokeOpacity?: number | string;
        strokeWidth?: number | string;
        surfaceScale?: number | string;
        systemLanguage?: number | string;
        tableValues?: number | string;
        targetX?: number | string;
        targetY?: number | string;
        textAnchor?: string;
        textDecoration?: number | string;
        textLength?: number | string;
        textRendering?: number | string;
        to?: number | string;
        transform?: string;
        u1?: number | string;
        u2?: number | string;
        underlinePosition?: number | string;
        underlineThickness?: number | string;
        unicode?: number | string;
        unicodeBidi?: number | string;
        unicodeRange?: number | string;
        unitsPerEm?: number | string;
        vAlphabetic?: number | string;
        values?: string;
        vectorEffect?: number | string;
        version?: string;
        vertAdvY?: number | string;
        vertOriginX?: number | string;
        vertOriginY?: number | string;
        vHanging?: number | string;
        vIdeographic?: number | string;
        viewBox?: string;
        viewTarget?: number | string;
        visibility?: number | string;
        vMathematical?: number | string;
        widths?: number | string;
        wordSpacing?: number | string;
        writingMode?: number | string;
        x1?: number | string;
        x2?: number | string;
        x?: number | string;
        xChannelSelector?: string;
        xHeight?: number | string;
        xlinkActuate?: string;
        xlinkArcrole?: string;
        xlinkHref?: string;
        xlinkRole?: string;
        xlinkShow?: string;
        xlinkTitle?: string;
        xlinkType?: string;
        xmlBase?: string;
        xmlLang?: string;
        xmlns?: string;
        xmlnsXlink?: string;
        xmlSpace?: string;
        y1?: number | string;
        y2?: number | string;
        y?: number | string;
        yChannelSelector?: string;
        z?: number | string;
  zoomAndPan?: string;
 */
}

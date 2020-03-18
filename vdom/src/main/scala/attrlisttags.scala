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

package react.vdom

/**
 * Inherit from this trait to bring HTML tags into scope.
 */
trait HtmlTags {
  final lazy val html       = tag("html")
  final lazy val head       = tag("head")
  final lazy val base       = tag("base")
  final lazy val link       = tag("link")
  final lazy val meta       = tag("meta")
  final lazy val script     = tag("script")
  final lazy val body       = tag("body")
  final lazy val h1         = tag("h1")
  final lazy val h2         = tag("h2")
  final lazy val h3         = tag("h3")
  final lazy val h4         = tag("h4")
  final lazy val h5         = tag("h5")
  final lazy val h6         = tag("h6")
  final lazy val header     = tag("header")
  final lazy val footer     = tag("footer")
  final lazy val p          = tag("p")
  final lazy val hr         = tag("hr")
  final lazy val pre        = tag("pre")
  final lazy val blockquote = tag("blockquote")
  final lazy val ol         = tag("ol")
  final lazy val ul         = tag("ul")
  final lazy val li         = tag("li")
  final lazy val dl         = tag("dl")
  final lazy val dt         = tag("dt")
  final lazy val dd         = tag("dd")
  final lazy val figure     = tag("figure")
  final lazy val figcaption = tag("figcaption")
  final lazy val div        = tag("div")
  final lazy val a          = tag("a")
  final lazy val em         = tag("em")
  final lazy val strong     = tag("strong")
  final lazy val small      = tag("small")
  final lazy val s          = tag("s")
  final lazy val cite       = tag("cite")
  final lazy val code       = tag("code")
  final lazy val sub        = tag("sub")
  final lazy val sup        = tag("sup")
  final lazy val i          = tag("i")
  final lazy val b          = tag("b")
  final lazy val u          = tag("u")
  final lazy val span       = tag("span")
  final lazy val br         = tag("br")
  final lazy val wbr        = tag("wbr")
  final lazy val ins        = tag("ins")
  final lazy val del        = tag("del")
  final lazy val img        = tag("img")
  final lazy val iframe     = tag("iframe")
  final lazy val embed      = tag("embed")
  final lazy val `object`   = tag("object")
  final lazy val param      = tag("param")
  final lazy val video      = tag("video")
  final lazy val audio      = tag("audio")
  final lazy val source     = tag("source")
  final lazy val track      = tag("track")
  final lazy val canvas     = tag("canvas")
  final lazy val map        = tag("map")
  final lazy val area       = tag("area")
  final lazy val table      = tag("table")
  final lazy val caption    = tag("caption")
  final lazy val colgroup   = tag("colgroup")
  final lazy val col        = tag("col")
  final lazy val tbody      = tag("tbody")
  final lazy val thead      = tag("thead")
  final lazy val tfoot      = tag("tfoot")
  final lazy val tr         = tag("tr")
  final lazy val td         = tag("td")
  final lazy val th         = tag("th")
  final lazy val form       = tag("form")
  final lazy val fieldset   = tag("fieldset")
  final lazy val legend     = tag("legend")
  final lazy val label      = tag("label")
  final lazy val button     = tag("button")
  final lazy val select     = tag("select")
  final lazy val datalist   = tag("datalist")
  final lazy val optgroup   = tag("optgroup")
  final lazy val option     = tag("option")
  final lazy val textarea   = tag("textarea")

  object input extends Tag("input") {

    def withType(`type`: String): Tag =
      new Tag("input", Attr(AttrName("type"), AttrValue(`type`)) :: Nil)

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

  final lazy val titleTag = tag("title")
  final lazy val styleTag = tag("style")
  final lazy val noscript = tag("noscript")

  final lazy val section = tag("section")

  final lazy val nav      = tag("nav")
  final lazy val article  = tag("article")
  final lazy val aside    = tag("aside")
  final lazy val address  = tag("address")
  final lazy val main     = tag("main")
  final lazy val q        = tag("q")
  final lazy val dfn      = tag("dfn")
  final lazy val abbr     = tag("abbr")
  final lazy val data     = tag("data")
  final lazy val time     = tag("time")
  final lazy val `var`    = tag("var")
  final lazy val samp     = tag("samp")
  final lazy val kbd      = tag("kbd")
  final lazy val math     = tag("math")
  final lazy val mark     = tag("mark")
  final lazy val ruby     = tag("ruby")
  final lazy val rt       = tag("rt")
  final lazy val rp       = tag("rp")
  final lazy val bdi      = tag("bdi")
  final lazy val bdo      = tag("bdo")
  final lazy val keygen   = tag("keygen")
  final lazy val output   = tag("output")
  final lazy val progress = tag("progress")
  final lazy val meter    = tag("meter")
  final lazy val details  = tag("details")
  final lazy val summary  = tag("summary")
  final lazy val command  = tag("command")
  final lazy val menu     = tag("menu")

}

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

package react_helmet

import scala.scalajs.js

import js.annotation._
import js.|

import org.scalajs.dom

import react._

import vdom._

object Helmet {

  @js.native
  @JSImport("react-helmet", "Helmet")
  object JS extends ReactJsComponent

  def apply(props: Props, children: ReactNode*) =
    createElementN(JS, props)(children: _*)
  def apply(props: Props)         = createElement0(JS, props)
  def apply(children: ReactNode*) = createElementN(JS, null)(children: _*)
  def withTitle(wtitle: String) =
    apply(new Props {
      title = wtitle
    })

  @jsenrich trait Props extends js.Object {
    var async: js.UndefOr[Boolean]                   = js.undefined
    var base: js.UndefOr[js.Any]                     = js.undefined
    var defaultTitle: js.UndefOr[String]             = js.undefined
    var defer: js.UndefOr[Boolean]                   = js.undefined
    var encodeSpecialCharacters: js.UndefOr[Boolean] = js.undefined
    // htmlAtributes
    var onChangeClientState: js.UndefOr[js.Function3[js.Any, HelmetTags, HelmetTags, Unit]] = js.undefined
    var link: js.UndefOr[js.Array[js.Object]]                                               = js.undefined
    var meta: js.UndefOr[js.Array[js.Object | js.Dynamic]]                                  = js.undefined
    var noscript: js.UndefOr[js.Array[js.Any]]                                              = js.undefined
    var style: js.UndefOr[js.Array[js.Any]]                                                 = js.undefined
    var title: js.UndefOr[String]                                                           = js.undefined
    var titleAttributes: js.UndefOr[js.Object]                                              = js.undefined
    var titleTemplate: js.UndefOr[String]                                                   = js.undefined
  }
}

@js.native
trait HelmetTags extends js.Object {
  val baseTag: js.Array[js.Any]
  val linkTags: js.Array[dom.html.Link]
  val metaTags: js.Array[dom.html.Meta]
  val noscriptTags: js.Array[js.Any]
  val scripttags: js.Array[dom.html.Script]
  val styleTags: js.Array[dom.html.Style]
}

@js.native
@JSImport("react-helmet", JSImport.Namespace)
object module extends js.Object {
  //peek, rewind, renderState
  def peek(): HelmetData       = js.native
  def rewind(): HelmetData     = js.native
  def renderStatic: HelmetData = js.native
  val canUseDOM: Boolean       = js.native
}

@js.native
trait HelmetDatum extends js.Object {
  def toComponent(): ReactJsComponent = js.native
}

@js.native
trait HelmetHTMLBodyDatum extends js.Object {
  def toComponent(): HTMLAttributes[dom.html.Body] = js.native
}

@js.native
trait HelmetHTMLElementDatum extends js.Object {
  def toComponent(): HTMLAttributes[dom.html.Element] = js.native
}

@js.native
trait HelmetData extends js.Object {
  val base: HelmetDatum                      = js.native
  val bodyAttributes: HelmetHTMLBodyDatum    = js.native
  val htmlAttributes: HelmetHTMLElementDatum = js.native
  val link: HelmetDatum                      = js.native
  val meta: HelmetDatum                      = js.native
  val noscript: HelmetDatum                  = js.native
  val script: HelmetDatum                    = js.native
  val style: HelmetDatum                     = js.native
  val title: HelmetDatum                     = js.native
  val titleAttributes: HelmetDatum           = js.native
}

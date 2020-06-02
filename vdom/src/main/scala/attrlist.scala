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

import scalajs.js

/** The rhs of an Attribute name value pair. value could be a js undefined value. */
case class AttrValue(value: js.Any)

/**
 * This definition won't stop you from assigning the wrong type of value to an
 * attribute so be careful. You can use the specific symbol syntax to reduce
 * the danger slightly. The methods on this class are a builder pattern to
 * ultimately create a Attr object.
 */
case class AttrName(value: String) {
  def :=(value: AttrValue): Attr = Attr(this, value)
  def -->(value: js.Function0[_]): Attr =
    Attr(this, AttrValue(value: js.Function))
  def ==>[T](value: js.Function1[T, _]): Attr = Attr(this, AttrValue(value))

  def :=?[A](value: Option[A])(implicit toAttrValue: A => AttrValue): Attrs =
    value.fold(Attrs.zero)(a => Attrs(this := toAttrValue(a)))

  def -->?[F](value: Option[F])(implicit toJSFunction: F => js.Function0[_]): Attrs =
    value.fold(Attrs.zero)(f => Attrs(this --> toJSFunction(f)))

  def ==>?[T, FT](value: Option[FT])(implicit toJsFunction: FT => js.Function1[T, _]): Attrs =
    value.fold(Attrs.zero)(ft => Attrs(this ==> toJsFunction(ft)))
}

/**
 * Attribute name and value.
 */
case class Attr(
  val name: AttrName,
  val value: AttrValue
)

/** List of attributes. Call `.toJs` to obtain a `js.Object`. */
class Attrs(private val attrs: Seq[Attr]) {
  def toJs: js.Object =
    js.Dictionary(
        attrs
          .filter(a => !js.isUndefined(a.value.value))
          .map(a => a.name.value -> a.value.value): _*
      )
      .asInstanceOf[js.Object]
}

/**
 * Instead of using the methods on this object directly, import `attrlist` and use
 * `Style` or `Attribute` which has more semantic meeting.
 */
object Attrs {

  /** Make a list of attributes. To obtain a `js.Object` call `.toJs` on the
   * result.
   */
  def apply(attrs: Attr*): Attrs = new Attrs(attrs)

  /** Make a list out of another list. */
  def apply(attr0: Attrs, attrs: Attrs*): Attrs = concat(attr0 +: attrs)

  /** Attrs is a monoid. This is the zero value. */
  def zero: Attrs = Attrs()

  def append(a: Attrs, b: Attrs): Attrs =
    Attrs(a.attrs ++: b.attrs: _*)

  /** Lazy create a new list from existing lists. */
  def concat(as: Seq[Attrs]): Attrs =
    Attrs(as.flatMap(_.attrs): _*)

  implicit def attrToAttrs(attr: Attr): Attrs = Attrs(attr)
}

trait AttributeListLowerOrderPriorityImplicits {

  /** Convert an js value to a AttrValue. */
  @inline implicit def cvtJsAnyToAttrValue(v: js.Any): AttrValue = AttrValue(v)
}

trait AttributeListSyntax extends AttributeListLowerOrderPriorityImplicits {

  @inline def attr(name: String): AttrName = AttrName(name)

  @inline implicit def cvtStringsPairToAttr(p: (String, String)): Attr =
    AttrName(p._1) := AttrValue(p._2)

  @inline implicit def cvtStringsPairToAttrs(p: (String, String)): Attrs =
    Attrs(AttrName(p._1) := AttrValue(p._2))

  @inline implicit final class optionalMarkupOps(flag: Boolean) {
    def ?=(attr: Attr): Attrs =
      if (flag)
        attr
      else
        Attrs.zero
  }
  @inline implicit def cvtStringToAttrName(s: String): AttrName = AttrName(s)
  @inline implicit def cvtStringToAttrValue(s: String): AttrValue = AttrValue(s)
  @inline implicit def cvtBoolToAttrValue(v: Boolean): AttrValue = AttrValue(v)
  @inline implicit def cvtStyleAttroAttrValue(s: StyleAttr): AttrValue = AttrValue(s)
  @inline implicit def cvtIntToAttrValue(v: Int): AttrValue = AttrValue(v)
  @inline implicit def cvtDoubleToAttrValue(v: Double): AttrValue = AttrValue(v)
  @inline implicit def cvtFunctionToAttrValue(v: js.Function): AttrValue =
    AttrValue(v)
  @inline implicit def cvtAttrsToAttrValue(attrs: Attrs): AttrValue =
    AttrValue(attrs.toJs)
  @inline implicit def cvtDynamicToAttrValue(attrs: js.Dynamic): AttrValue =
    AttrValue(attrs)
  //@inline implicit def cvtJsObjectToAttrValue(attrs: js.Object): AttrValue = AttrValue(attrs)
}

/**
 * Import this to use the list-style attribute syntax. Create an attribtute
 * list using `Attrs` or after importing the contents of this object, if they
 * are styles, `Style(<attribute list>)` and convert it to a `js.Object` using
 * `.toJs()`. You can then use the js.Object when creating elements by merging
 * that value into trait structures that represent only allowed attributes for a
 * specific tag.
 */
object attrlist extends AttributeListSyntax {
  lazy val noAttributes = Attrs.zero

  /** For now, style attributes are just attributes so fake it out. */
  lazy val Style = Attrs

  /** Create a list of attribtes. */
  lazy val Attributes = Attrs

  /**
   * Default tags and attributes you can use. You can import all of the actual
   * values using `import ^._, <._`.  This style is popular with other
   * scalajs react facades but should really not be used in this library vs the
   * more (but not perfectly) strongly typed versions in `vdom.tags`.
   */
  object prefix_<^ {
    object ^ extends HtmlAttrs
    object < extends HtmlTags
  }
}

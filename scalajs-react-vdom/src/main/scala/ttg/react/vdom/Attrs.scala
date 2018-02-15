// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

import scalajs.js

/** The rhs of an Attribute name value pair. value could be a js undefined value. */
case class AttrValue(value: js.Any)

/**
  * This definition won't stop you from assigning the wrong type of value
  * to an attribute so be careful.
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

case class Attr(
    val name: AttrName,
    val value: AttrValue
)

class Attrs(private val attrs: Seq[Attr]) {
  def toJs: js.Object =
    js.Dictionary(
        attrs
          .filter(a => !js.isUndefined(a.value.value))
          .map(a => a.name.value -> a.value.value): _*
      )
      .asInstanceOf[js.Object]
}

object Attrs {

  import scala.language.implicitConversions

  def apply(attrs: Attr*): Attrs = new Attrs(attrs)
  def apply(attr0: Attrs, attrs: Attrs*): Attrs = concat(attr0 +: attrs)

  // Attrs is a monoid
  def zero: Attrs = Attrs()

  def append(a: Attrs, b: Attrs): Attrs =
    Attrs(a.attrs ++: b.attrs: _*)

  def concat(as: Seq[Attrs]): Attrs =
    Attrs(as.flatMap(_.attrs): _*)

  implicit def attrToAttrs(attr: Attr): Attrs = Attrs(attr)
}

trait VDOMLowerOrderPriorityImplicits {
  @inline implicit def cvtJsAnyToAttrValue(v: js.Any): AttrValue = AttrValue(v)
}

trait VDOMSyntax extends VDOMLowerOrderPriorityImplicits {

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
  @inline implicit def cvtFunctionToAttrValue(v: js.Function): AttrValue =
    AttrValue(v)
  @inline implicit def cvtAttrsToAttrValue(attrs: Attrs): AttrValue =
    AttrValue(attrs.toJs)
  @inline implicit def cvtDynamicToAttrValue(attrs: js.Dynamic): AttrValue =
    AttrValue(attrs)
  @inline def tag(name: String): Tag = new Tag(name)
}

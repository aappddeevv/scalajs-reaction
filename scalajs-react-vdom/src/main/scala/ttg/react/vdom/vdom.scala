// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// This entire package was copied from: https://github.com/eldis/scalajs-react and that Copyright applies.

package ttg.react

import scalajs.js

package object vdom extends vdom.Events with VDOMSyntax {

  import scala.language.implicitConversions

  val noAttributes = Attrs.zero

  /** For now, style attributes are just attributes so fake it out. */
  val Style = Attrs

  /**
    * Default prefixes you can use. You can import all of the
    * actual values using `import ^._, <._`.
    */
  object prefix_<^ {
    object ^ extends HtmlAttrs
    object < extends HtmlTags
  }

  /**
    * Helper to create classnames from pairse. You can flatten a map to get pairs
    * via toSeq.
    */
  def classNames(c: (String, js.UndefOr[js.Any])*): AttrValue =
    AttrValue(c.filter(p => js.DynamicImplicits.truthValue(p._2.asInstanceOf[js.Dynamic])).map(_._2).mkString(" "))
}

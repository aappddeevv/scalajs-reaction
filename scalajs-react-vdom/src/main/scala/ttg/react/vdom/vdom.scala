// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// This entire package was copied from: https://github.com/eldis/scalajs-react and that Copyright applies.

package ttg.react

import scalajs.js
import org.scalajs.dom
import scala.annotation.unchecked.{uncheckedVariance => uv}

/**
  * Simple vdom. This vdom implemntation uses attribute lists instead of
  * non-native JS traits so you may or may not create a valid list of
  * attributes.
  */
package object vdom extends vdom.Events with VDOMSyntax {

  import scala.language.implicitConversions

  val noAttributes = Attrs.zero

  /** For now, style attributes are just attributes so fake it out. */
  val Style = Attrs

  /**
    * Default tags and attributes you can use. You can import all of the actual
    * values using `import ^._, <._`.
    */
  object prefix_<^ {
    object ^ extends HtmlAttrs
    object < extends HtmlTags
  }

}

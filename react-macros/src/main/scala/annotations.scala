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
//package annotations

import scala.annotation.compileTimeOnly
import scala.language.experimental.macros
import scala.reflect.macros.whitebox._

// should use non-tree call in order to get params easier

/** Projection of a Styles object to a ClassNames trait. This is specific to fabric styling
  * but essentially ignores the type anyway so it could be used elsewhere.
  * No check is performed to make sure you are annotating a Styles object. A styles object
  * has only var members with types `js.UndefOr[IStyle]` and initial value `js.undefined`.
  * The macro does not search superclasses (yet).
  */
@compileTimeOnly("Enable macros -Ymacro-annotations (2.13+) to expand macro annotation")
//class deriveClassNames(name: String = "ClassNames", parent: String="fabric.styling.IClassNamesTag") extends scala.annotation.StaticAnnotation {
class deriveClassNames() extends scala.annotation.StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro ProjectMacros.impl
}

/** Build/enhance companion object with various convenience methods.
  *
  * Convenience methods include:
  *
  *     1. `apply` method for properties in the immediate js.Object trait. This will overwrite any existing apply method.
  *     1. Extensions for changing individual properties: `aTrait.withYourPropertyName("blah")`.
  *     1. Extension `combineWith(that)` to shallow combine two objects with `that` taking precedence.
  *     1. Extension `.duplicate` that shallow clones the object using `js.Object.assign`.
  *
  * These methods adds/creates new companion object with methods and implicit classes.
  *
  * WARNING: Does not handle trait parameters yet.
  */
@compileTimeOnly("Enable macros -Ymacro-annotations (2.13+) to expand macro annotation")
class jsenrich() extends scala.annotation.StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro JSEnrichMacros.impl
}

/** "Exports" all defs and val/vars from a source object to another trait by creating static forwarders
  * in the annotated trait. This allows you to proxy methods imported from a js module into any
  * trait easily.
  * Only vals (or readers for vars) and defs are exported from the source.
  *
  * NOT FULLY TESTED YET!
  */
@compileTimeOnly("Enable macros -Ymacro-annotations (2.13+) to expand macro annotation")
class deriveForwardingTrait(source: scala.Any) extends scala.annotation.StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro ExportMacros.impl
}

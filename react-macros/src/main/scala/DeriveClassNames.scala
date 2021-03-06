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

import scala.reflect.macros.whitebox.Context
import scala.language.experimental.macros

private[react] class ProjectMacros(val c: Context) extends ImplTransformers {
  import c.universe._

  private class Transformer(name: String, parent: String) extends ImplTransformer {
    override def transformTrait(cdef: ClassDef, mdef: ModuleDef): List[ImplDef] =
      cdef match {
        case q"$mods trait $tpname[..$tparams] extends { ..$earlydefns } with ..$parents { $self => ..$stats }" =>
          if (debug) System.err.println(s"Parents: ${showRaw(parents)}")

          val newstats = stats.collect {
            case t @ ValDef(_, name, _, _) =>
              q"val ${name}: String = _root_.scala.scalajs.js.native"
          }

          val mods0 = Modifiers(
            NoFlags
          )
          val parents0 = List(
            tq"_root_.fabric.styling.IClassNamesTag"
          )
          val newName = TypeName(name)
          val res =
            q"""@_root_.scala.scalajs.js.native trait $newName extends ..$parents0 {
              ..$newstats
          }"""

          if (debug)
            System.err.println(res)

          List(cdef, res)

        case _ =>
          c.abort(c.enclosingPosition, "@deriveClassNames must annotate a trait")
      }
  }

  def impl(annottees: Tree*): Tree = {
    //System.err.println(s"prefix ${showRaw(c.prefix)}")
    val params: List[Tree] = c.prefix.tree match {
      case q"new deriveClassNames()"           => Nil
      case q"new deriveClassNames(..$params0)" => params0
    }

    if (debug) System.err.println(s"annottees raw tree ${showRaw(annottees, true, true)}")
    if (debug) System.err.println(s"Params: ${showRaw(params)}")

    annottees.transformAnnottees(new Transformer("ClassNames", "IClassNamesTag"))
  }

}

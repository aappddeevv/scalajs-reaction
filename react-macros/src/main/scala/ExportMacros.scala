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

private[react] class ExportMacros(val c: Context) extends ImplTransformers {
  import c.universe._

  /** Exporting should pull in both immediate and superclass adeclarations. */
  private class Transformer(source: ModuleSymbol) extends ImplTransformer {
    override def transformTrait(cdef: ClassDef, mdef: ModuleDef): List[ImplDef] =
      cdef match {
        case q"$mods trait $tpname[..$tparams] extends { ..$earlydefns } with ..$parents { $self => ..$stats }" =>
          val mname = source.name.toTermName
          val mname_ident = Ident(mname)
          if (debug)
            System.err.println(
              s"mname: ${showRaw(mname)}, ${mname.getClass}, ${showRaw(mname_ident)}, ${mname_ident.getClass}"
            )

          // get defs if source was a ModuleDef, this is easy compared to working with symbols
          // val defs = source match {
          //   case q"$mods object $tname extends { ..$earlydefns } with ..$parents { $self => ..$body }" =>
          //     body.collect {
          //       case DefDef(mods, name, tparams, vparmss, tpt, _) =>
          //         q"$mods def $name[..$tparams](...$vparmss): $tpt = $tname.$name(...$vparmss)"
          //     }
          //   case _ => abort("No object found!")
          // }

          // Scan all members but filter out top-level and private members
          val to_process = source.moduleClass.asType.toType.members
            .filter(standardFilter)
            .filter(s => !s.isPrivate && !s.isPrivateThis)

          if (debug)
            to_process.foreach(s => System.err.println(s"Processing ${s}, ${isValues(s)}"))

          val ignored = to_process.collect {
            case sm: MethodSymbol if sm.isSetter => sm
          }
          if (debug)
            ignored.foreach(i =>
              System.err.println(s"Forwarding from a module only forwards vals and defs. Ignoring ${i.name}."))

          // I think can filter better by using aMethodSymbol.accessed.
          val vals_only = to_process.collect {
            case sm: MethodSymbol if !sm.isSetter && sm.isGetter =>
              // simple val
              if (debug) System.err.println(s"Exporting as val: ${sm}")
              q""" def ${sm.name} = ${mname}.${sm.name}"""
          }

          val undefined_value = c.parse("scala.scalajs.js.undefined")

          // Bit of a hack since we can't seem to find a parameters default value.
          // Look for js.UndefOr explicitly as that's often common in js signatures.
          import compat._
          val defs_only = to_process.collect {
            case sm: MethodSymbol if !sm.isSetter && !sm.isGetter =>
              if (sm.paramLists.length > 1)
                abort(s"Multiple parameter lists are not supported on ${sm.name}")
              if (debug) System.err.println(s"Exporting as def: ${sm}")
              val tparams_strs = sm.typeParams.map(_.name.decodedName.toString)
              val tp = sm.typeParams.map(TypeDef(_))
              // where can you get default parameter values?
              // if scala.js and parameter is wrapped in js.UndefOr, maybe always assume its js.undefined
              val params = sm.paramLists.map(_.map { p =>
                val has_undefor_default_value =
                  isScalaJS && p.typeSignature.toString.contains("UndefOr")
                if (has_undefor_default_value)
                  ValDef(
                    Modifiers(Flag.DEFAULTPARAM),
                    p.name.toTermName,
                    TypeTree(p.typeSignature),
                    undefined_value
                  )
                else
                  ValDef(
                    Modifiers(Flag.PARAM),
                    p.name.toTermName,
                    TypeTree(p.typeSignature),
                    EmptyTree
                  )
              })
              if (debug)
                System.err.println(s"""fake type params strs: ${tparams_strs.mkString(",")}""")
              // this is safe because only 1 parameter list is allowed
              val param_strs = sm.paramLists.flatMap(_.map(_.name.decodedName.toString))
              val rhs =
                if (tparams_strs.length > 0)
                  c.parse(s"""${mname}.${sm.name}[${tparams_strs.mkString(",")}](${param_strs
                    .mkString(",")})""")
                else
                  c.parse(s"""${mname}.${sm.name}(${param_strs.mkString(",")})""")
              //val rhs = reify(???).tree

              q"""def ${sm.name}[..${tp}](...$params): ${sm.returnType} =  $rhs"""
          }

          if (debug) {
            vals_only.foreach(d => System.err.println(s"migration val: ${showRaw(d)}\n${show(d)}"))
            defs_only.foreach(d => System.err.println(s"migrated def: ${showRaw(d)}\n${show(d)}"))
          }

          val modified_trait = q"""
          $mods trait $tpname[..$tparams] extends { ..$earlydefns } with ..$parents { $self =>             
            ..$stats
            ..$vals_only
           }
          """
          if (debug) System.err.println(s"Generated: ${showRaw(modified_trait)}")

          List(modified_trait, mdef)
        case _ =>
          c.abort(
            c.enclosingPosition,
            "@deriveForwardingTrait must annotate a trait"
          )
      }
  }

  def impl(annottees: Tree*): Tree = {
    if (debug) System.err.println(s"prefix ${showRaw(c.prefix)}")

    val params: List[Tree] = c.prefix.tree match {
      case q"new deriveForwardingTrait()"           => Nil
      case q"new deriveForwardingTrait(..$params0)" => params0
    }

    if (debug) System.err.println(s"params: $params")
    params.headOption match {
      case Some(m) =>
        val sym = c.typecheck(m, c.TYPEmode, withMacrosDisabled = false).symbol
        if (debug) System.err.println(s"input object symbol: $sym")
        if (sym.isModule)
          // val obj = c.parse("""object Foo { def blah() = 10 }""")
          // println(s"obj: $obj, ${obj.getClass}")
          //annottees.transformAnnottees(new Transformer(obj.asInstanceOf[ModuleDef]))
          annottees.transformAnnottees(new Transformer(sym.asModule))
        else abort(s"${sym.name.decodedName.toString} must be a module/object.")
      case _ => abort("You must provide a module/object name.")
    }
  }
}

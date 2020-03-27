package react
// package annotations
import scala.reflect.macros.whitebox.Context
import scala.language.experimental.macros

private[react] class JSEnrichMacros(val c: Context) extends ImplTransformers {
  import c.universe._

  /** Recursively get all parent declarations filtering out a few top-level object types. */
  def getParentDeclsRecursive(s: ClassSymbol): Seq[MethodSymbol] = {
    //println(s"recursing: ${s.fullName}, ${s.baseClasses.map(_.fullName)}")
    if (SDKClasses.contains(s.fullName)) Nil
    val here = s.toType.decls.map(_.asMethod).toSeq
    here ++ s.baseClasses
      .filter(_.fullName != s.fullName)
      .flatMap(su => getParentDeclsRecursive(su.asClass))
  }

  private class Transformer() extends ImplTransformer {
    override def transformTrait(cdef: ClassDef, mdef: ModuleDef): List[ImplDef] =
      cdef match {
        case q"$mods trait $tpname[..$tparams] extends { ..$earlydefns } with ..$parents { $self => ..$stats }" =>
          // Type checking on a type dependent on another type within the same module may be problematic...
          // Do not typecheck this object, which may have dependencies that won't typecheck yet.
          // Expand parent search through the quasiquote and expand those directly with the assumption
          // that these are not in the same module. This assumes tpe.members returns all parent methods and
          // not just that layer's methods.
          // parents = sequence of Tree objects
          val parentProperties = parents
            .filter(_.isType)
            .map(c.typecheck(_, c.TYPEmode, withMacrosDisabled = true))
            .flatMap(tree => getParentDeclsRecursive(tree.symbol.asClass))
            //.filter(m => m.isVal || m.isVar && !m.isGetter)
            .filter(m => m.isVal || m.isVar && m.isSetter)

          if (debug)
            System.err.println(s"${cdef.name.decodedName} parent properties: ${parentProperties}")
          //System.err.println(s"${cdef.name.decodedName} parent properties: ${parentProperties}")

          // expand out all types so that this class's superclasses are available easily
          //val cdefSymbol = c.typecheck(cdef, c.TYPEmode, withMacrosDisabled = true).symbol
          //val clazz      = cdefSymbol.asClass // convert to ClassSymbol
          //println(s"clazz $clazz")
          //val info = clazz.info
          //println(s"info $info")
          // val baseClasses = clazz.baseClasses.tail
          // val processingBaseClasses =
          //   baseClasses.filter(c => !SDKClasses.contains(c.fullName)).reverse
          //println(s"base classes: $baseClasses, $processingBaseClasses")
          // println(
          //   s"type.members: ${ttype.members.filter(m => m.asMethod.isVal || m.asMethod.isVar)}"
          // )

          val tparamsRef = tparams.map(t => tq"${t.name}")

          val setters = stats.collect {
            case ValDef(mods, name, tpt, rhs) =>
              val rawName = name.decodedName.toString
              val fn = rawName.capitalize
              val withDefIdent = TermName(s"with$fn")
              val quotedName = Literal(Constant(rawName)) //q"${rawName}"
              q"""def $withDefIdent(${name}: ${tpt})
                        = _root_.scala.scalajs.js.Object.assign(new js.Object, item, 
                            _root_.scala.scalajs.js.Dynamic.literal($quotedName -> $name.asInstanceOf[js.Any])).asInstanceOf[$tpname]"""
          }

          val parent_setters = parentProperties.map { p =>
            // this seems to be missing, we don't want any of the encodings for val/vars setter/getter
            // source code has unexpandedName but its not present on public API???
            // decodedName will have _= attached to it.
            if (debug) System.err.println(s"==>${isValues(p)}")
            val sym = p.asMethod
            // setters should only take 1 argument, the value
            require(sym.paramLists.length == 1 && sym.paramLists.head.length == 1)
            val argSymbol = sym.paramLists.head.head
            val base = p.name.decodedName.toString.dropRight(2)
            val fn = base.capitalize
            val name = TermName(base)
            val withDefIdent = TermName(s"with$fn")
            val quotedName = Literal(Constant(name.toString))
            q"""def $withDefIdent($name: ${argSymbol.info}) 
                        = _root_.scala.scalajs.js.Object.assign(new js.Object, item,
                            _root_.scala.scalajs.js.Dynamic.literal($quotedName -> $name.asInstanceOf[js.Any])).asInstanceOf[$tpname]"""
          }

          val combine = q"""def combineWith(that: $tpname) = 
            _root_.scala.scalajs.js.Object.assign(new js.Object, item, that).asInstanceOf[$tpname]"""

          val duplicate = q"""def duplicate = 
            _root_.scala.scalajs.js.Object.assign(new js.Object, item).asInstanceOf[$tpname]"""

          val mods = Modifiers(Flag.IMPLICIT)
          val implicitSetters = q"""
            $mods class ${TypeName("Rich" + tpname.decodedName.toString)}(val item: $tpname) extends AnyVal {
           ..$setters
           ..$parent_setters
           $combine
           $duplicate
          }"""

          val q"$mmods object $mname extends { ..$mearlydefns } with ..$mparents { $mself => ..$mstats }" =
            mdef

          val applyMethods = {
            val immutables = stats.collect {
              case ValDef(mods, name, tpt, rhs) if !mods.hasFlag(Flag.MUTABLE) =>
                val vd = ValDef(Modifiers(Flag.PARAM), name, tpt, rhs)
                (
                  q"${name.decodedName.toString}",
                  q"$vd",
                  name
                )
            } /* ++ parent_setters.map(_.isVal).map { p =>
              // how do you turn a symbol into a ValDef and get its default value if it has one???
              val base = p.name.decodedName.toString.dropRight(2)
              val name = TermName(name)
              (
                q"$base",
                q"$name: ${p.returnType} =  ",
                base
              )
            }*/

            val mutables = stats.collect {
              case ValDef(mods, name, tpt, rhs) if mods.hasFlag(Flag.MUTABLE) =>
                val vd = ValDef(Modifiers(Flag.PARAM), name, tpt, rhs)
                (
                  q"${name.decodedName.toString}",
                  q"$vd",
                  name
                )
            }

            q"""def apply[..$tparams](..${immutables.map(_._2) ++ mutables.map(_._2)}): $tpname[..$tparamsRef] = 
                    _root_.scala.scalajs.js.Object.assign(js.Dynamic.literal(
                        ..${(immutables ++ mutables).map(p => q"${p._1} -> ${p._3}.asInstanceOf[js.Any]")}
                )).asInstanceOf[$tpname]"""
          }

          val mdef0 =
            q"$mmods object $mname extends { ..$mearlydefns } with ..$mparents { $mself => ..$mstats; ..$applyMethods; ..$implicitSetters }"

          if (debug)
            System.err.println(mdef0)
          //System.err.println(mdef0)

          List(cdef, mdef0)

        case _ =>
          c.abort(c.enclosingPosition, "@jsenrich must annotate a trait")
      }
  }

  def impl(annottees: Tree*): Tree =
    annottees.transformAnnottees(new Transformer())

}

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.meta._
import scala.reflect.runtime.{universe => ru}

/**
 * This works for simple non-native js traits that do not have type definitions
 * inside the trait.
 * 
 */
private[ttg] object SimpleCreateImpl {
  case class Params(name: String)

  def extractAnnotationParams(annotation: Stat): Params = {
    annotation match {
      case q"new $_ (makeName = ${Lit.String(value)})" => Params(value)
      case _ => Params(name="apply")
    }
  }

  def expand(
    t: Defn.Trait,
    companionOpt: Option[Defn.Object],
    params: Params): Term.Block = {

    // generate an import statement in case there are types in the traits
    //val ivalue = Import(Seq(Importer(Term.Name(t.name.value), Seq(Importee.Wildcard()))))

    companionOpt match {
      case Some(companion) =>
        val applyMethods = createApplyMethods(params.name, t, companionOpt)
        val templateStatements = companion.templ.stats.getOrElse(Nil) ++ applyMethods
        //val newCompanion = companion.copy(templ = companion.templ.copy(stats = Some(Seq(ivalue) ++ templateStatements)))
        val newCompanion = companion.copy(templ = companion.templ.copy(stats = Some(templateStatements)))
        Term.Block(Seq(t, newCompanion))
      case None =>
        val applyMethods = createApplyMethods(params.name, t, companionOpt)
    //val newCompanion = q"object ${Term.Name(t.name.value)} { $ivalue; ..$applyMethods }"
    val newCompanion = q"object ${Term.Name(t.name.value)} { ..$applyMethods }"
        Term.Block(Seq(t, newCompanion))
    }
  }

  def mkNewname(tname: Term.Name) = Pat.Var.Term(Term.Name("_" + tname.value))

  /** Takes a param named "name" and creates _name = name */
  def mkTemp(tname: Term.Name): Stat = {
    val newname = mkNewname(tname)
    q"""val $newname = $tname"""
  }

  /** Takes a name "name" and creates */
  def mkAssign(tname: Term.Name, isVal: Boolean = false): Stat = {
    val newname = mkNewname(tname).name
    if(isVal) {
      val p = Pat.Var.Term(tname)
      q"val $p = $newname"
    }
    else q"$tname = $newname"
  }

  def createApplyMethods(
    makeName: String,
    t: Defn.Trait,
    companionOpt: Option[Defn.Object]
  ): Seq[Defn] = {
    // println("trait")
    // println(ru.showRaw(t))
    // println("template")
    // println(ru.showRaw(t.templ))

    // println("individual statements:")
    // t.templ.stats.map{stats =>
    //   stats.foreach(stat => println(ru.showRaw(stat)))
    // }
    val args =
      // decltpe is Option[Type] for definitions, rhs is Term
      t.templ.stats.map{ _.collect {
        case d@Decl.Val(_, Seq(Pat.Var.Term(tname)), decltpe) =>
          (param"$tname : $decltpe", tname, mkTemp(tname), mkAssign(tname, true))
        case d@Decl.Var(_, Seq(Pat.Var.Term(tname)), decltpe) =>
          (param"$tname: $decltpe", tname, mkTemp(tname), mkAssign(tname))
        case d@Defn.Val(_, Seq(Pat.Var.Term(tname)), Some(decltpe), rhs) =>
          (param"$tname: $decltpe = $rhs", tname, mkTemp(tname), mkAssign(tname, true))
        case d@Defn.Var(_, Seq(Pat.Var.Term(tname)), Some(decltpe), rhs) =>
          (param"$tname: $decltpe = $rhs", tname, mkTemp(tname), mkAssign(tname))
          // case _ =>
          //   // we skip everything else, which may actually cause a problem
          //   abort("Only traits swith val/var declarations or definitions are allowed when using this annotation")
      }}.getOrElse(Nil)

    val params = args.map(_._1)
    val tmps = args.map(_._3)
    val assigns = args.map(_._4)
    val newApply =
      if(t.tparams.size > 0) {
        val returnTypeTypeParams = t.tparams.map(tp => Type.Name(tp.name.value))
        val ctorRef = t.name.ctorRef(Ctor.Ref.Name(t.name.value))
        val newObjectCall = q"new ${ctorRef}[..$returnTypeTypeParams]{ ..$assigns}"
        q"""def ${Term.Name(makeName)}[..${t.tparams}]( ..$params ): ${t.name}[..${returnTypeTypeParams}] = { ..$tmps; $newObjectCall }"""
      }
      else {
        // now sure why the above can fail, looks like [..] has some checks for non-empty list...so special case.
        val ctorRef = t.name.ctorRef(Ctor.Ref.Name(t.name.value))
        val newObjectCall = q"new ${ctorRef} { ..$assigns }"
        q"""def ${Term.Name(makeName)}( ..$params ): ${t.name} = { ..$tmps; $newObjectCall }"""

      }
    //println(ru.showRaw(newApply))
    Seq(newApply)
  }

}

/**
 * Translate var/val decl/defns to an apply in the companion object with a list of args.
 */
@compileTimeOnly("enable macro paradise to expand macro annotations")
class simplecreate(makeName: String = "apply") extends scala.annotation.StaticAnnotation {

  inline def apply(defn: Any): Any = meta {
    //Test.doit
    val params = SimpleCreateImpl.extractAnnotationParams(this)
    defn match {
      case Term.Block(Seq(t: Defn.Trait, companion: Defn.Object))=>
        SimpleCreateImpl.expand(t, Some(companion), params)
      case t: Defn.Trait =>
        SimpleCreateImpl.expand(t, None, params)
      case _ =>
          abort(s"@simplecreate must annotate a non-native JS trait that only contains val/var declarations.")
    }
  }
}

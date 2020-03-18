package react
package macros

import scala.annotation.compileTimeOnly
import scala.collection.immutable.Seq
import scala.language.experimental.macros
import scala.reflect.macros.whitebox._

private[macros] trait ImplTransformers {
  val c: Context
  import c.universe._
  import Flag._

  def fail(msg: String) = c.abort(c.enclosingPosition, msg)
}

private[macros] class WithsMacro(val c: Context) extends ImplTransformers {
  import c.universe._

  private val debug = sys.env
    .get("REACT_MACROS_DEBUG")
    .map(_.toBoolean)
    .getOrElse(java.lang.Boolean.getBoolean("react.macros.debug"))

  def impl(annottees: Tree*): Tree = {
    val params = c.prefix.tree match {
      case q"new withs()"           => Nil
      case q"new withs(..$params0)" => params0
    }
    println(s"parameters $params")
    println(s"ANNOTTEES: ${showRaw(annottees)}")
    ???
  }
}

@compileTimeOnly("enable macros -Ymacro-annotation (2.13+) to expand macro annotation")
class withs() extends scala.annotation.StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro WithsMacro.impl
}


/*
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
*/

// https://docs.scala-lang.org/overviews/macros/annotations.html
// object ReactMacro {
//   def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
//     import c.universe._
//     val inputs = annottees.map(_.tree).toList
//     val (annottee, expandees) = inputs match {
//       case (param: TypeDef) :: (rest @ (_ :: _)) => (param, rest)
//       case _ => (EmptyTree, inputs)
//       case someDef =>
//         c.abort(
//           c.enclosingPosition,
//             """@react must annotate a def
//         """.stripMargin
//           )
//     }
//     val outputs = expandees
//     c.Expr[Any](Block(outputs, Literal(Constant(()))))
//   }
// }

// @compileTimeOnly("enable macros -Ymacro-annotation (2.13+) to expand macro annotation")
// class react() extends scala.annotation.StaticAnnotation {
//   def macroTransform(annottees: Any*): Any = macro ReactMacro.impl
// }

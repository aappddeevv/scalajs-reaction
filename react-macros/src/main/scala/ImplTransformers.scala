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

import scala.reflect.macros._
import scala.language.experimental.macros

private[react] trait ImplTransformers {
  val c: blackbox.Context
  import c.universe._
  import Flag._

  final lazy val isScalaJS =
    c.compilerSettings.exists(o => o.startsWith("-Xplugin:") && o.contains("scalajs-compiler"))

  /** Be in macro debug mode. Checks REACT_MACROS_DEBUG env variable and react.macros.debug sys property. */
  val debug = sys.env
    .get("REACT_MACROS_DEBUG")
    .map(_.toBoolean)
    .getOrElse(java.lang.Boolean.getBoolean("react.macros.debug"))

  def error(msg: String) = c.error(c.enclosingPosition, msg)
  def warning(msg: String) = c.warning(c.enclosingPosition, msg)
  def info(msg: String) = c.info(c.enclosingPosition, msg, false)
  def abort(msg: String) = c.abort(c.enclosingPosition, msg)
  def ensure(condition: Boolean, msg: => String): Unit =
    if (!condition) {
      abort(msg)
    }
  def trace(s: => String) = if (debug) info(s)

  /** Use strings to reduce need to have actual classes on classpath. */
  val SDKClasses =
    Set("java.lang.Object", "scala.Any", "scala.scalajs.js.Any", "scala.scalajs.js.Object")

  /** Use in `.filter` to remove symbols that represent items belonging to SDK classes. */
  def ownerNotInSDK(s: Symbol) = !SDKClasses.contains(s.owner.fullName)

  /** A symbole whose name term is not a constructor. */
  def isNotConstructor(s: Symbol) = s.name.toTermName != termNames.CONSTRUCTOR

  /** Standard filter to ignore items returned in `symbol.asType.toType.members` */
  def standardFilter(s: Symbol) = isNotConstructor(s) && ownerNotInSDK(s)

  /** Given a symbol, create a string with is* methods evaluated. */
  def isValues(s: Symbol) =
    s"""|name = ${s.name}, fullname = ${s.fullName}, decodedName = ${s.name.decodedName}, encodedName = ${s.name.encodedName}
        |owner = ${s.owner}, ${if (s.owner != NoSymbol) s.owner.fullName else "<no owner>"}
        |info = ${s.info}, typeSymbol = ${s.info.typeSymbol}, termSymbol = ${s.info.termSymbol}, resultType = ${s.info.resultType}
        |      typeArgs = ${s.info.typeArgs}, typeParams = ${s.info.typeParams}, class = ${s.info.getClass}
        |      paramLists = ${s.info.paramLists}
        |alternatives = ${s.alternatives}
        |isAbstract = ${s.isAbstract}
        |isAbstractOverride = ${s.isAbstractOverride}
        |isClass = ${s.isClass}
        |""".stripMargin +
      (if (s.isClass) {
         val sc = s.asClass
         s"""|  isCaseClass = ${sc.isCaseClass}
             |  isDerivedValueClass = ${sc.isDerivedValueClass}
             |  isNumeric = ${sc.isNumeric}
             |  isPrimitive = ${sc.isPrimitive}
             |  isSleaed = ${sc.isSealed}
             |  isTrait = ${sc.isTrait}""".stripMargin
       } else "") +
      s"""|isConstructor = ${s.isConstructor}
          |isFinal = ${s.isFinal}
          |isImplementationArtifact = ${s.isImplementationArtifact}
          |isImplicit = ${s.isImplicit}
          |isJava = ${s.isJava}
          |isJavaAnnotation = ${s.isJavaAnnotation}
          |isJavaEnum = ${s.isJavaEnum}
          |isMacro = ${s.isMacro}
          |isMethod = ${s.isMethod}
          |""".stripMargin +
      (if (s.isMethod) {
         val sm = s.asMethod
         s"""|  returnType = ${sm.returnType}, class = ${sm.returnType.getClass}
             |  isConstructor = ${sm.isConstructor}
             |  isPrimaryConstructor = ${sm.isPrimaryConstructor}
             |  isVarargs = ${sm.isVarargs}
             |  paramLists = ${sm.paramLists}
             |  typeParams = ${sm.typeParams}""".stripMargin
       } else "") +
      s"""|isModule = ${s.isModule}
          |isModuleClass = ${s.isModuleClass}
          |isPackage = ${s.isPackage}
          |isPackageClass = ${s.isPackageClass}
          |isParameter = ${s.isParameter}
          |isPrivate = ${s.isPrivate}
          |isPrivateThis = ${s.isPrivateThis}
          |isProtected = ${s.isProtected}
          |isProtectedThis = ${s.isProtectedThis}
          |isSpecialized = ${s.isSpecialized}
          |isStatic = ${s.isStatic}
          |isSnythetic = ${s.isSynthetic}
          |isTerm = ${s.isTerm}
          |""".stripMargin +
      (if (s.isTerm) {
         val st = s.asTerm
         s"""|  isAccessor = ${st.isAccessor}
             |  isByNameParam = ${st.isByNameParam}
             |  isCaseAccessor = ${st.isCaseAccessor}
             |  isGetter = ${st.isGetter}
             |  isLazy = ${st.isLazy}
             |  isOverloaded = ${st.isOverloaded}
             |  isParamAccessor = ${st.isParamAccessor}
             |  isParamWithDefault = ${st.isParamWithDefault}
             |  isSetter = ${st.isSetter}
             |  isStable = ${st.isStable}
             |  isVal = ${st.isVal}
             |  isVar = ${st.isVar}
             |  getter = ${st.getter}, ${if (st.getter != NoSymbol) st.getter.fullName else ""}
             |  setter = ${st.setter}, ${if (st.setter != NoSymbol) st.setter.fullName else ""}
             |  accessed = ${if (st.isAccessor) st.accessed.fullName else ""}""".stripMargin
       } else "") +
      s"""|isType = ${s.isType}
          |""".stripMargin +
      (if (s.isType) {
         val st = s.asType
         s"""|  isAliasType = ${st.isAliasType}
             |  isContravariant = ${st.isContravariant}
             |  isCovariant = ${st.isCovariant}
             |  isExistential = ${st.isExistential}""".stripMargin
       } else "")

  implicit class XtensionAnnotteeTransformer(annottees: Seq[Tree]) {
    def transformAnnottees(transformer: ImplTransformer): Tree =
      transformer.transform(annottees: _*)
  }

  class ImplTransformer {
    def transformClass(cdef: ClassDef, mdef: ModuleDef): List[ImplDef] = ???
    def transformTrait(cdef: ClassDef, mdef: ModuleDef): List[ImplDef] = ???
    def transformModule(mdef: ModuleDef): ModuleDef = ???

    def transform(annottees: Tree*): Tree = {
      def isImplemented(body: => Any): Boolean =
        try {
          body; true
        } catch {
          case _: NotImplementedError => false; case _: Throwable => true
        }
      val allowClasses = isImplemented(transformClass(null, null))
      val allowTraits = isImplemented(transformTrait(null, null))
      val allowModules = isImplemented(transformModule(null))
      if (!allowClasses && !allowTraits && !allowModules)
        sys.error("invalid ImplTransformer")

      def failUnexpectedAnnottees() = {
        var allowed = List[String]()
        if (allowClasses) allowed :+= "classes"
        if (allowTraits) allowed :+= "traits"
        if (allowModules) allowed :+= "modules"
        val s_allowed = {
          if (allowed.length > 1)
            allowed.dropRight(1).mkString(", ") + " and " + allowed.last
          else allowed.mkString
        }
        val q"new $s_name(...$_).macroTransform(..$_)" = c.macroApplication
        c.abort(annottees.head.pos, s"only $s_allowed can be $s_name")
      }

      val expanded = annottees match {
        case (cdef @ ClassDef(mods, _, _, _)) :: (mdef: ModuleDef) :: rest =>
          if (!(mods hasFlag TRAIT)) {
            if (!allowClasses) failUnexpectedAnnottees()
            transformClass(cdef, mdef) ++ rest
          } else {
            if (!allowTraits) failUnexpectedAnnottees()
            transformTrait(cdef, mdef) ++ rest
          }
        case (cdef @ ClassDef(mods, name, _, _)) :: rest =>
          val syntheticMdef = q"object ${name.toTermName}"
          if (!(mods hasFlag TRAIT)) {
            if (!allowClasses) failUnexpectedAnnottees()
            transformClass(cdef, syntheticMdef) ++ rest
          } else {
            if (!allowTraits) failUnexpectedAnnottees()
            transformTrait(cdef, syntheticMdef) ++ rest
          }
        case (mdef @ ModuleDef(_, _, _)) :: rest =>
          if (!allowModules) failUnexpectedAnnottees()
          transformModule(mdef) +: rest
        case annottee :: rest =>
          failUnexpectedAnnottees()
      }
      q"{ ..$expanded; () }"
    }
  }
}

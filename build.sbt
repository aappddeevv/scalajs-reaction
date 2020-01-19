import scala.sys.process._

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val bintraySettings = Seq(
  bintrayReleaseOnPublish := false,
  bintrayPackageLabels := Seq("scala.js", "react", "fabric", "react-native", "office", "material-ui", "bootstrap"),
  bintrayVcsUrl := Some("git:git@github.com:aappddeevv/scalajs-reaction"),
  publishMavenStyle := true
)

lazy val resolverSettings = Seq(
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.jcenterRepo
    //ivyLocal
  )
)

lazy val ivyLocal = Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

val commonScalacOptions = Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-language:_",
  "-unchecked",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-unused:imports,locals",
  "-Xlint:infer-any",
  "-Yrangepos"
)

lazy val jsSettings = Seq(
  scalacOptions ++= (
    if (scalaJSVersion.startsWith("0.6.")) Seq("-P:scalajs:sjsDefinedByDefault")
    else Nil
  ),
  //scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) },
  scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
  scalaModuleInfo ~= (_.map(_.withOverrideScalaVersion(true))),
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.8"
  )
)

def buildinfo_settings(pkg: String) =
  Seq(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion, isSnapshot),
    buildInfoObject := "BuildInfo",
    buildInfoPackage := pkg
  )

lazy val compilerSettings = Seq(
  scalaVersion := "2.13.1",
  scalacOptions ++= commonScalacOptions,
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full),
  addCompilerPlugin(scalafixSemanticdb),
  autoAPIMappings := true,
  autoCompilerPlugins := true // kind-projector
)

val catsVersion = "2.1.0"

lazy val fpsettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %%% "cats-core" % catsVersion
  )
)

def std_settings(p: String, d: String) =
  Seq(
    name := p,
    description := d,
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.2.0-M2" % Test
    )
  ) ++ resolverSettings ++ compilerSettings ++ bintraySettings ++ jsSettings

inThisBuild(
  List(
    organization := "ttg",
    organizationName := "The Trapelo Group",
    startYear := Some(2018),
    licenses ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
    homepage := Some(url("https://github.io/aappddeevv/scalajs-reaction/")),
    developers := List(
      Developer("aappddeevv", "Devon Miller", "aappddeevv@gmail.com", url("https://aappddeevv.github.io"))
    ),
    scmInfo := Some(
      ScmInfo(url("https://github.com/scalajs-reaction"), "scm:git:git@github.com:aappddeevv/scalajs-reaction.git")
    ),
    scalafixDependencies += "com.nequissimus" %% "sort-imports" % "0.3.2"
  )
)

lazy val root = project
  .in(file("."))
  .settings(skip in publish := true)
  .aggregate(
    helmet,
    react,
    fabric,
    `fabric-experiments`,
    native,
    vdom,
    `react-redux`,
    `react-dom`,
    `prop-types`,
    bootstrap,
    mui,
    //router,
    `react-big-calendar`,
    `react-native-nativebase`,
    `react-native-elements`,
    `react-navigation`,
    `react-native-sideswipe`,
    jss,
    apollo,
    forms,
    `react-router-dom`,
    pathtoregexp,
    dataValidationJS,
    msal,
    mssql,
    express
  )

lazy val `react` = project
  .settings(std_settings("react", "reactjs package"))
  .settings(buildinfo_settings("react"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val native = project
  .settings(std_settings("native", "reactjs native package"))
  .settings(buildinfo_settings("native"))
  .dependsOn(react, vdom)
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val msal = project
  .in(file("components/msal"))
  .settings(std_settings("msal", "Microsoft Authentication Library msal"))
  .settings(buildinfo_settings("msal"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val mssql = project
  .in(file("components/mssql"))
  .settings(std_settings("mssql", "Microsoft Sql Server based on tedious"))
  .settings(buildinfo_settings("mssql"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val express = project
  .in(file("components/express"))
  .settings(std_settings("express", "express node.js http server"))
  .settings(buildinfo_settings("express"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val `react-big-calendar` = project
  .in(file("components/react-big-calendar"))
  .settings(std_settings("react-big-calendar", "react-big-calender bindings"))
  .settings(buildinfo_settings("react_big_calendar"))
  .dependsOn(react, vdom)
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val pathtoregexp = project
  .in(file("components/pathtoregexp"))
  .settings(std_settings("pathtoregex", "pathtoregexp"))
  .settings(buildinfo_settings("pathtoregex"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val jss = project
  .in(file("components/jss"))
  .settings(std_settings("jss", "cssinjs jss"))
  .settings(buildinfo_settings("jss"))
  .dependsOn(react, vdom)
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val `react-router-dom` = project
  .in(file("components/react-router-dom"))
  .settings(std_settings("react-router-dom", "scalajs reaction react-router-dom bindings (hook)"))
  .settings(buildinfo_settings("react_router.dom"))
  .dependsOn(react, vdom)
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val `react-navigation` = project
  .in(file("components/react-navigation"))
  .settings(std_settings("react-navigation", "react-navigation facade"))
  .settings(buildinfo_settings("react_navigation"))
  .dependsOn(react, native)
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val `react-native-sideswipe` = project
  .in(file("components/react-native-sideswipe"))
  .settings(std_settings("react-native-sideswipe", "react-native-sideswipe"))
  .settings(buildinfo_settings("react_native_sideswipe"))
  .dependsOn(react, native)
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val `react-native-nativebase` = project
  .in(file("components/react-native-nativebase"))
  .settings(std_settings("react-native-nativebase", "nativebase library"))
  .settings(buildinfo_settings("react_native_nativebase"))
  .dependsOn(react, native)
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val `react-native-elements` = project
  .in(file("components/react-native-elements"))
  .settings(std_settings("react-native-elements", "react-native-elements library"))
  .settings(buildinfo_settings("react_native_elements"))
  .dependsOn(react, native)
  .enablePlugins(ScalaJSPlugin)

// jvm and js based project
// lazy val dataValidation =
//   crossProject(JSPlatform, JVMPlatform)
//     .crossType(CrossType.Pure)
//     .in(file("data-validation"))
//     .settings(name := "data-validation")
//     .jvmSettings(jvmlibsettings)
//     .jsSettings(libsettings)
//     .settings(publishSettings)
//     .settings(fpsettings)
//     .settings(description :=
//       "General purpose data validation library based on cats and applicatives.")

lazy val dataValidationJS = project
  .in(file("data-validation"))
  .settings(fpsettings)
  .settings(std_settings("data-validation", "General purpose data validation library based on cats and applicatives."))
  .settings(buildinfo_settings("data.validation"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

//lazy val dataValidationJS = dataValidation.js
//lazy val dataValidationJVM = dataValidation.jvm

// lazy val `react-macros` = project
//   .settings(libsettings)
//   .settings(publishSettings)
//   .enablePlugins(ScalaJSPlugin)
//   .settings(macroSettings)
//   .settings(
//     description := "Helpful macros.",
//     libraryDependencies ++= Seq(
//       "org.scala-lang" % "scala-reflect" % scalaVersion.value,
//       "org.scalameta" %% "scalameta" % "1.8.0" // old version required
//     ))

lazy val `prop-types` = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react)
  .settings(std_settings("prop-types", "prop-types package."))
  .settings(buildinfo_settings("prop_types"))

lazy val `react-dom` = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react)
  .settings(std_settings("react-dom", "react-dom package."))
  .settings(buildinfo_settings("react_dom"))

lazy val vdom = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react)
  .settings(std_settings("vdom", "vdom helpers"))
  .settings(buildinfo_settings("vdom"))

lazy val `react-redux` = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react)
  .settings(std_settings("react-redux", "redux via react-redux."))
  .settings(buildinfo_settings("react_redux"))

lazy val helmet = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("helmet", "react-helmet"))
  .settings(buildinfo_settings("helmet"))

lazy val apollo = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("apollo", "Combination of apollo-boost, graphql, react-apollo"))
  .settings(buildinfo_settings("apollo"))

lazy val fabric = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("fabric", "microsoft office-ui-fabric facade."))
  .settings(buildinfo_settings("fabric"))

lazy val `fabric-experiments` = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom, fabric)
  .settings(std_settings("fabric-experiments", "microsoft @uifbaric experiments."))
  .settings(buildinfo_settings("fabric.experiments"))

lazy val bootstrap = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("bootstrap", "bootstrap facade."))
  .settings(buildinfo_settings("bootstrap"))

lazy val mui = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom, jss)
  .settings(std_settings("mui", "material ui facade."))
  .settings(buildinfo_settings("mui"))

lazy val router = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("router", "scalajs-reaction browser js oriented router"))
  .settings(buildinfo_settings("router"))

lazy val forms = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("forms", "scalajs-reaction forms library."))
  .settings(buildinfo_settings("forms"))

lazy val examples: Project = project
  .settings(fpsettings)
  .settings(
    scalaVersion := "2.13.1",
    // Watch non-scala assets.
    watchSources += baseDirectory.value / "examples/src/main/assets"
    ,libraryDependencies ++= Seq(
      "ru.pavkin" %%% "scala-js-momentjs" % "0.10.0" //"0.10.0-SNAPSHOT"
    )
  )
  .dependsOn(
    helmet,
    fabric,
    `fabric-experiments`,
    `react-redux`,
    `react-dom`,
    `prop-types`,
    //router,
    forms,
    bootstrap,
    mui,
    `react-big-calendar`,
    `react-router-dom`
//     ,dataValidationJS
  )
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .settings(buildinfo_settings("ttg.examples"))
  .settings(artifactPath.in(Compile, fastOptJS) := crossTarget.in(Compile, fastOptJS).value / "Scala.js")
  .settings(artifactPath.in(Compile, fullOptJS) := crossTarget.in(Compile, fullOptJS).value / "Scala.js")

lazy val docs = project
  .in(file("scalajs-reaction-docs"))
  .settings(
    moduleName := "scalajs-reaction-docs"
    //name := "scalajs-reaction-docs"
    ,
    skip.in(publish) := true
    //,mdocVariables := Map("VERSION" -> version.value)
    //scalacOptions -= -"Yno-imports",
    //scalacOptions -= "-Ydata-warnings",
    ,
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject -- inProjects(examples),
    target in (ScalaUnidoc, unidoc) := (baseDirectory in LocalRootProject).value / "website" / "scalajs-reaction" / "static" / "api",
    cleanFiles += (target in (ScalaUnidoc, unidoc)).value
  )
  .dependsOn(root) // but root does not depend on docs
  .enablePlugins(ScalaJSPlugin, ScalaUnidocPlugin)

addCommandAlias("prepare", "headerCreate; fix; fmt")
addCommandAlias("fmt", "all scalafmtSbt scalafmt")
addCommandAlias("fix", "all compile:scalafix")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck")

val npmBuild = taskKey[Unit]("fullOptJS then webpack")
npmBuild := {
  (fullOptJS in (examples, Compile)).value
  "npm run examples" !
}

val npmBuildFast = taskKey[Unit]("fastOptJS then webpack")
npmBuildFast := {
  (fastOptJS in (examples, Compile)).value
  "npm run examples:dev" !
}

val npmRunDemo = taskKey[Unit]("fastOptJS then run webpack server")
npmRunDemo := {
  (fastOptJS in (examples, Compile)).value
  "npm run examples:dev:start" !
}

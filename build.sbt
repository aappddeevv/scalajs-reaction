import scala.sys.process._

Global / onChangedBuildSource := ReloadOnSourceChanges
//Global / semanticdbVersion := "4.4.0"
//sonatypeLogLevel := "trace"

//bintrayPackageLabels := Seq("scala.js", "react", "fabric", "react-native", "office", "material-ui", "bootstrap"),
// https://github.com/xerial/sbt-sonatype
lazy val sonatypeSettings = Seq(
  publishTo := sonatypePublishToBundle.value,
  sonatypeCredentialHost := "s01.oss.sonatype.org",
  sonatypeProfileName := "org.ttgoss"
)

lazy val resolverSettings = Seq(
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    //Resolver.jcenterRepo
  )
)

val commonScalacOptions = Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-language:_",
  "-language:implicitConversions",
  "-unchecked",
  //"-Ywarn-numeric-widen",
  //"-Ywarn-value-discard",
  //"-Ywarn-unused:imports,locals",
  //,"-Ywarn-dead-code"
  "source",
  "future",
  "-Ysafe-init",
  "-Yexplicit-nulls",
  //"-language:unsafeNulls",
  //"-language:strictEquality"
)

lazy val jsSettings = Seq(
  //scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) },
  scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
  scalaModuleInfo ~= (_.map(_.withOverrideScalaVersion(true))),
  libraryDependencies ++= Seq(
   ("org.scala-js" %%% "scalajs-dom" % "2.0.0"),
  ),
  // testing
libraryDependencies += "com.lihaoyi" %%% "utest" % "0.7.10" % "test",
testFrameworks += new TestFramework("utest.runner.Framework")
)

def buildinfo_settings(pkg: String) =
  Seq(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion, isSnapshot),
    buildInfoObject := "BuildInfo",
    buildInfoPackage := pkg
  )

lazy val compilerSettings = Seq(
  scalacOptions in (Compile, doc) ++= Seq("-groups"),
  scalacOptions ++= commonScalacOptions,
  //addCompilerPlugin(scalafixSemanticdb),
  autoAPIMappings := true,
  autoCompilerPlugins := true
)

val catsVersion = "2.6.1"

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
      //"org.scalatest" %%% "scalatest" % "3.2.0-M2" % Test
    ),
  ) ++ resolverSettings ++ compilerSettings ++ jsSettings ++ sonatypeSettings

inThisBuild(
  List(
    scalaVersion := "3.1.0",
    publishMavenStyle := true,
    organization := "org.ttgoss.js",
    organizationName := "The Trapelo Group (TTG) Open Source Software (TTGOSS)",
    startYear := Some(2018),
    licenses ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
    homepage := Some(url("https://github.com/aappddeevv/scalajs-reaction")),
    developers := List(
      Developer("aappddeevv", "Devon Miller", "aappddeevv@gmail.com", url("https://aappddeevv.github.io"))
    ),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/aappddeevv/scalajs-reaction"),
        "scm:git:git@github.com:aappddeevv/scalajs-reaction.git")
    ),
    scalafixDependencies += "com.nequissimus" %% "sort-imports" % "0.3.2"
    //,scalafmtOnCompile := true,
    // should come from sbt-dynver
    //version := "0.1.0-M7"
    ,
    dynverSonatypeSnapshots := true,
    dynverSeparator := "-"
    //semanticdbVersion := "4.4.0",
    //addCompilerPlugin("org.scalameta" % "semanticdb-scalac" % "4.4.25" cross CrossVersion.full)
  )
)

lazy val root = project
  .in(file("."))
  .settings(skip in publish := true)
  .settings(sonatypeSettings)
  .aggregate(
    //apollo,
    apollo3,
    `apollo-server`,
    bootstrap,
    dataloader,
    //dataValidationJS,
    express,
    fabric,
    `fabric-experiments`,
    formik,
    handlebars,
    helmet,
    jss,
    jshelpers,
    loglevel,
    lodash,
    luxon,
    mui,
    msal,
    `azure-msal-browser`,
    mssql,
    `microsoft-graph-client`,
    //examples,
    //docs,
    native,
    `node-fetch`,
    pathtoregexp,
    plotlyjs,
    `prop-types`,
    //`react-macros`,
    `react-content-loader`,
    react,
    `react-redux`,
    `react-dom`,
    //`react-router-dom5`,
    `react-router-dom6`,
    `react-big-calendar`,
    `react-native-nativebase`,
    `react-native-elements`,
    `react-navigation`,
    `react-native-sideswipe`,
    `react-fast-compare`,
    `react-plotlyjs`,
    `react-device-detect`,
    `react-responsive`,
    recoil,
    `react-teleporter`,
    `use-deep-compare-effect`,
    `use-query-params`,
    `use-error-boundary`,
    vdom,
    whydidyourender
  )

lazy val `react` = project
  .settings(std_settings("react", "reactjs package"))
  .settings(buildinfo_settings("react"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(jshelpers)

lazy val native = project
  .settings(std_settings("native", "reactjs native package"))
  .settings(buildinfo_settings("native"))
  .dependsOn(react, vdom)
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val msal = project
  .in(file("components/msal"))
  .settings(std_settings("azure.msal", "Microsoft Authentication Library msal"))
  .settings(buildinfo_settings("msal"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val `azure-msal-browser` = project
  .in(file("components/azure-msal-browser"))
  .settings(std_settings("azure-msal-browser", "@azure/msal-browser (aka msal v2)"))
  .settings(buildinfo_settings("azure_msal_browser"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val lodash = project
  .in(file("components/lodash"))
  .settings(std_settings("lodash", "lodash"))
  .settings(buildinfo_settings("lodash"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val `react-fast-compare` = project
  .in(file("components/react-fast-compare"))
  .settings(std_settings("react-fast-compare", "fast compare customized for react"))
  .settings(buildinfo_settings("react_fast_compare"))
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

// lazy val `react-router-dom5` = project
//   .in(file("components/react-router-dom5"))
//   .settings(std_settings("react-router-dom5", "react-router-dom5"))
//   .settings(buildinfo_settings("react_router5.dom"))
//   .dependsOn(react, vdom, `react-macros`)
//   .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val `react-router-dom6` = project
  .in(file("components/react-router-dom6"))
  .settings(std_settings("react-router-dom6", "react-router-dom v6"))
  .settings(buildinfo_settings("react_router6.dom"))
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

lazy val loglevel = project
  .in(file("components/loglevel"))
  .settings(std_settings("loglevel", "loglevel library"))
  .settings(buildinfo_settings("loglevel"))
  .enablePlugins(ScalaJSPlugin)

lazy val whydidyourender = project
  .in(file("components/whydidyourender"))
  .settings(std_settings("whydidyourender", "Why Did You Render library"))
  .settings(buildinfo_settings("whydidyourender"))
  .dependsOn(react)
  .enablePlugins(ScalaJSPlugin)

lazy val luxon = project
  .in(file("components/luxon"))
  .settings(std_settings("luxon", "Luxon date time."))
  .settings(buildinfo_settings("luxon"))
  .dependsOn(react)
  .enablePlugins(ScalaJSPlugin)

lazy val `react-content-loader` = project
  .in(file("components/react-content-loader"))
  .settings(std_settings("react-content-loader", "Placeholders"))
  .settings(buildinfo_settings("react_content_loader"))
  .dependsOn(react, vdom)
  .enablePlugins(ScalaJSPlugin)

lazy val dataloader = project
  .in(file("components/dataloader"))
  .settings(std_settings("dataloader", "FB dataloader"))
  .settings(buildinfo_settings("dataloader"))
  .dependsOn(react)
  .enablePlugins(ScalaJSPlugin)

lazy val `use-query-params` = project
  .in(file("components/use-query-params"))
  .settings(std_settings("use-query-params", "use-query-params"))
  .settings(buildinfo_settings("use_query_params"))
  .dependsOn(react)
  .enablePlugins(ScalaJSPlugin)

lazy val `use-error-boundary` = project
  .in(file("components/use-error-boundary"))
  .settings(std_settings("use-error-boundary", "use-error-boundary"))
  .settings(buildinfo_settings("use_error_boundary"))
  .dependsOn(react)
  .enablePlugins(ScalaJSPlugin)

lazy val `react-teleporter` = project
  .in(file("components/react-teleporter"))
  .settings(std_settings("react-teleporter", "react-teleporter"))
  .settings(buildinfo_settings("react_teleporter"))
  .dependsOn(react)
  .enablePlugins(ScalaJSPlugin)

lazy val `apollo-server` = project
  .in(file("components/apollo-server"))
  .settings(std_settings("apollo-server", "apollo-server"))
  .settings(buildinfo_settings("apollo_server"))
  .dependsOn(react, apollo3)
  .enablePlugins(ScalaJSPlugin)

lazy val `react-responsive` = project
  .in(file("components/react-responsive"))
  .settings(std_settings("react-responsive", "react-responsive"))
  .settings(buildinfo_settings("react_responsive"))
  .dependsOn(react)
  .enablePlugins(ScalaJSPlugin)

lazy val `react-device-detect` = project
  .in(file("components/react-device-detect"))
  .settings(std_settings("react-device-detect", "react-device-detect"))
  .settings(buildinfo_settings("react_device_detect"))
  .dependsOn(react)
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
/*
lazy val `react-macros` = project
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalacOptions ++= Seq("-Ymacro-annotations", "-language:experimental.macros"),
    description := "Small but helpful macros.",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    )
  )
 */

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
  .in(file("components/helmet"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("helmet", "react-helmet"))
  .settings(buildinfo_settings("helmet"))

// lazy val apollo = project
//   .in(file("components/apollo"))
//   .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
//   .dependsOn(react, vdom)
//   .settings(std_settings("apollo", "Combination of apollo-boost, graphql, react-apollo"))
//   .settings(buildinfo_settings("apollo"))

lazy val apollo3 = project
  .in(file("components/apollo3"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("apollo3", "Combination of apollo-boost, graphql, react-apollo"))
  .settings(buildinfo_settings("apollo3"))

lazy val fabric = project
  .in(file("components/fabric"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom) //, /*`react-macros`*/)
  .settings(std_settings("fabric", "microsoft office-ui-fabric facade."))
  .settings(buildinfo_settings("fabric"))

lazy val `microsoft-graph-client` = project
  .in(file("components/microsoft-graph-client"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .settings(std_settings("microsoft-graph-client", "@microsoft/microsoft-graph-client."))
  .settings(buildinfo_settings("microsoft.graph_client"))

lazy val `fabric-experiments` = project
  .in(file("components/fabric-experiments"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom, fabric)
  .settings(std_settings("fabric-experiments", "microsoft @uifbaric experiments."))
  .settings(buildinfo_settings("fabric.experiments"))

lazy val bootstrap = project
  .in(file("components/bootstrap"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("bootstrap", "bootstrap facade."))
  .settings(buildinfo_settings("bootstrap"))

lazy val mui = project
  .in(file("components/mui"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom, jss)
  .settings(std_settings("mui", "material ui facade."))
  .settings(buildinfo_settings("mui"))

lazy val jshelpers = project
  .in(file("jshelpers"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .settings(std_settings("jshelpers", "js data casting and conversion helpers"))
  .settings(buildinfo_settings("jshelpers"))

lazy val router = project
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("router", "scalajs-reaction browser js oriented router"))
  .settings(buildinfo_settings("router"))

/*
lazy val forms = project
  .in(file("components/forms"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("forms", "scalajs-reaction forms library."))
  .settings(buildinfo_settings("forms"))
 */

lazy val plotlyjs = project
  .in(file("components/plotlyjs"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("plotlyjs", "plotly.js"))
  .settings(buildinfo_settings("plotlyjs"))

lazy val handlebars = project
  .in(file("components/handlebars"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react)
  .settings(std_settings("handlebars", "handlebars"))
  .settings(buildinfo_settings("handlebars"))

lazy val `react-plotlyjs` = project
  .in(file("components/react-plotlyjs"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(plotlyjs, react, vdom)
  .settings(std_settings("react-plotlyjs", "react-plotly.js"))
  .settings(buildinfo_settings("react_plotlyjs"))

lazy val `node-fetch` = project
  .in(file("components/node-fetch"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .settings(std_settings("node-fetch", "node-fetch"))
  .settings(buildinfo_settings("node_fetch"))

lazy val `recoil` = project
  .in(file("components/recoil"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react)
  .settings(std_settings("recoil", "recoil state management"))
  .settings(buildinfo_settings("recoil"))

lazy val `formik` = project
  .in(file("components/formik"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react, vdom)
  .settings(std_settings("formik", "formik forms management"))
  .settings(buildinfo_settings("formik"))

lazy val `use-deep-compare-effect` = project
  .in(file("components/use-deep-compare-effect"))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .dependsOn(react)
  .settings(std_settings("use-deep-compare-effect", "useEffect but with deep compare"))
  .settings(buildinfo_settings("use_deep_compare_effect"))

lazy val examples = project
  .settings(fpsettings)
  .settings(std_settings("examples", "Example web application"))
  .settings(
    skip in publish := true,
    // Watch non-scala assets.
    watchSources += baseDirectory.value / "examples/src/main/assets",
    libraryDependencies ++= Seq(
      //"ru.pavkin" % "scala-js-momentjs-sjs1.0-RC1_2.13" % "0.10.1" //"0.10.0-SNAPSHOT"
    )
  )
  .dependsOn(
    helmet,
    `fabric-experiments`,
    `react-redux`,
    `react-dom`,
    formik,
    bootstrap,
    mui,
    `react-big-calendar`,
    `react-router-dom6`,
    recoil
  )
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .settings(buildinfo_settings("ttg.examples"))
  .settings(artifactPath.in(Compile, fastOptJS) := crossTarget.in(Compile, fastOptJS).value / "Scala.js")
  .settings(artifactPath.in(Compile, fullOptJS) := crossTarget.in(Compile, fullOptJS).value / "Scala.js")

lazy val docs = project
  .in(file("scalajs-reaction-docs"))
  .settings(std_settings("scalajs-reaction-docs", "docs fake project"))
  .settings(
    skip.in(publish) := true
    //,mdocVariables := Map("VERSION" -> version.value)
    //scalacOptions -= -"Yno-imports",
    //scalacOptions -= "-Ydata-warnings",
    ,
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject -- inProjects(examples),// -- inProjects(apollo),
    target in (ScalaUnidoc, unidoc) := (baseDirectory in LocalRootProject).value / "website" / "scalajs-reaction" / "static" / "api",
    cleanFiles += (target in (ScalaUnidoc, unidoc)).value
  )
  .enablePlugins(ScalaJSPlugin, ScalaUnidocPlugin)
  // keep this list in sync with root, or filter the dependencies directly from root...
  .dependsOn(
    jshelpers,
    helmet,
    `fabric-experiments`,
    native,
    `react-redux`,
    `react-dom`,
    `prop-types`,
    bootstrap,
    handlebars,
    lodash,
    `microsoft-graph-client`,
    mui,
    //`react-macros`,
    //router,
    `react-big-calendar`,
    `react-native-nativebase`,
    `react-native-elements`,
    `react-navigation`,
    `react-native-sideswipe`,
    jss,
    //apollo,
    //apollo3,
    //`apollo-server`,
    formik,
    //forms,
    //`react-router-dom5`,
    `react-router-dom6`,
    pathtoregexp,
    //dataValidationJS,
    msal,
    `azure-msal-browser`,
    mssql,
    `node-fetch`,
    express,
    loglevel,
    whydidyourender,
    luxon,
    dataloader,
    `react-content-loader`,
    jshelpers,
    recoil,
    `use-query-params`,
    `use-error-boundary`,
    `react-teleporter`,
    `react-fast-compare`,
    plotlyjs,
    `react-plotlyjs`,
    `react-device-detect`,
    `react-responsive`
  )

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

val dump = taskKey[Unit]("dump classpath that I need")
dump := {
  val cp = (examples / Compile / dependencyClasspath).value
  println(s"""${cp.map(_.data).mkString(" ")}""")
}

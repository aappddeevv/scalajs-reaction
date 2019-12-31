// Build file for ReasonRect like scala.js reactjs bindings.
// Now that's a mouthful...
import scala.sys.process._
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

// reload build.sbt on changes
Global / onChangedBuildSource := ReloadOnSourceChanges

// Run publish and release separately
// Don't forget bintray & unpublish
// You can use `packagedArtifacts` to build artifacts in each project's target.
// To release: run `publish` then `bintrayRelease`.
//bintrayReleaseOnPublish := false
//bintrayPackageLabels := Seq("scala.js", "react", "fabric", "react-native",
//  "office", "material-ui", "bootstrap")
//bintrayVcsUrl := Some("git:git@github.com:aappddeevv/scalajs-react")
//bintrayRepository in ThisBuild := "maven2"

lazy val bintraySettings = Seq(
  bintrayReleaseOnPublish := false,
  bintrayPackageLabels := Seq("scala.js", "react", "fabric", "react-native", "office", "material-ui", "bootstrap"),
  bintrayVcsUrl := Some("git:git@github.com:aappddeevv/scalajs-reaction"),
  publishMavenStyle := true
)

lazy val licenseSettings = Seq(
  headerMappings := headerMappings.value +
    (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment),
  headerLicense := Some(
    HeaderLicense.Custom(
      """|Copyright (c) 2018 The Trapelo Group LLC
         |This software is licensed under the MIT License (MIT).
         |For more information see LICENSE or https://opensource.org/licenses/MIT
         |""".stripMargin
    )
  )
)

// lazy val macroSettings = Seq (
//   resolvers += Resolver.sonatypeRepo("releases"),
//   resolvers += Resolver.bintrayRepo("scalameta", "maven"),
//   addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M11" cross CrossVersion.full),
//   scalacOptions += "-Xplugin-require:macroparadise",
// )

val ivyLocal = Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

lazy val buildSettings = Seq(
  organization := "ttg",
  licenses ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
  //scalaVersion := "2.12.8",
  scalaVersion := "2.13.0",
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.jcenterRepo
    //ivyLocal
  ),
  autoCompilerPlugins := true
) ++ licenseSettings

lazy val noPublishSettings = Seq(
  skip in publish := true, // this did not seem to work
  publish := {},
  publishLocal := {},
  publishArtifact := false
)

lazy val exampleSettings = Seq(
  libraryDependencies ++= Seq(
    // any special ones??
  )
)

lazy val publishSettings = Seq(
  homepage := Some(url("https://github.com/aappddeevv/scalajs-react"))
) ++ bintraySettings

val commonScalacOptions = Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-language:_",
  "-unchecked",
  //  "-Yno-adapted-args",
  "-Ywarn-numeric-widen"
//    "-Xfuture",
//  "-Ypartial-unification",
)

lazy val jssettings = Seq(
  scalacOptions ++= (
    if (scalaJSVersion.startsWith("0.6.")) Seq("-P:scalajs:sjsDefinedByDefault")
    else Nil
  ),
  //scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) },
  scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
  scalaModuleInfo ~= (_.map(_.withOverrideScalaVersion(true))),
  libraryDependencies ++= Seq(
    //"org.scala-js" %%% "scalajs-dom" % "0.9.7"
	"org.scala-js" %%% "scalajs-dom" % "0.9.8"
  )
)

lazy val commonSettings = Seq(
  scalacOptions ++= commonScalacOptions,
  libraryDependencies ++= Seq(
    //"org.scalatest" %%% "scalatest" % "3.1.0-RC3" % "test"
  ),
  dependencyOverrides ++= Seq(
    // none
  ),
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
  autoAPIMappings := true
)

// supports scala 2.13 and scalajs 0.6
val catsVersion = "2.0.0-M4"

lazy val fpsettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %%% "cats-core" % catsVersion
  )
)

lazy val exampleFPSettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %%% "cats-core" % catsVersion, 
    "org.typelevel" %%% "cats-effect" % catsVersion,
    //"tech.sparse" %%% "trail" % "0.1.2", // 0.1.2 uses newest cats
  )
)

lazy val libsettings = buildSettings ++ commonSettings ++ jssettings
lazy val jvmlibsettings = buildSettings ++ commonSettings

lazy val root = project
  .in(file("."))
  .settings(jvmlibsettings)
  .settings(noPublishSettings)
  .settings(name := "scalajs-react")
  .aggregate(
    examples,
    docs,
    `react`,
    `fabric`,
    `fabric-experiments`,
    `native`,
    `vdom`,
    `scalajs-reaction-router`,
    `react-redux`,
    `react-dom`,
    `prop-types`,
    `bootstrap`,
    `mui`,
    `scalajs-reaction-router`,
    `react-big-calendar`,
    `scalajs-reaction-native-nativebase`,
    `scalajs-reaction-native-react-native-elements`,
    `scalajs-reaction-native-react-navigation`,
    `scalajs-reaction-native-react-native-sideswipe`,
    jss,
    `scalajs-reaction-form`,
    `react-router-dom`,
    pathtoregexp,
    dataValidationJS,
    msal
    ,mssql
    ,express
    //,dataValidationJVM
  )
  .enablePlugins(AutomateHeaderPlugin)
  .disablePlugins(BintrayPlugin)

lazy val `react` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(description := "reactjs package.")
  .settings(buildInfoPackage := "ttg.react")

lazy val native = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(description := "reactjs native package.")
  .dependsOn(
    react, vdom
  )

lazy val msal = project
  .in(file("components/msal"))
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(description := "Microsoft Authentication Library msal")

lazy val mssql = project
  .in(file("components/mssql"))
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(description := "Microsoft Sql Server based on tedious")

lazy val express = project
  .in(file("components/express"))
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(description := "express node.js http server")

val `react-big-calendar` = project
  .in(file("components/react-big-calendar"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(
    Seq(
      description := "scalajs reaction react-big-calendar"
    )
  )
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react, vdom)

lazy val pathtoregexp = project
  .in(file("components/pathtoregexp"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(
    Seq(
      description := "pathtoregexp"
    )
  )
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)

lazy val jss = project
  .in(file("components/jss"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(
    Seq(
      description := "cssinjs jss"
    )
  )
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react, vdom)

lazy val `react-router-dom` = project
  .in(file("components/react-router-dom"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(
    Seq(
      description := "scalajs reaction react-router-dom bindings (hook)"
    )
  )
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react,vdom)

lazy val `scalajs-reaction-native-react-navigation` = project
  .in(file("components/scalajs-reaction-native-react-navigation"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(
    Seq(
      description := "react-navigation facade"
    )
  )
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    react, native
  )

lazy val `scalajs-reaction-native-react-native-sideswipe` = project
  .in(file("components/scalajs-reaction-native-react-native-sideswipe"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(
    Seq(
      description := "react-native-sideswipe"
    )
  )
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    react,native
  )

lazy val `scalajs-reaction-native-nativebase` = project
  .in(file("components/scalajs-reaction-native-nativebase"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(
    Seq(
      description := "nativebase library"
    )
  )
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react, native)

lazy val `scalajs-reaction-native-react-native-elements` = project
  .in(file("components/scalajs-reaction-native-react-native-elements"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(
    Seq(
      description := "react-native-elements library"
    )
  )
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react, native)

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
  .settings(name := "data-validation")
  .settings(libsettings)
  .settings(publishSettings)
  .settings(fpsettings)
  .settings(
    description :=
      "General purpose data validation library based on cats and applicatives."
  )
// needed if not using cross-
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)

//lazy val dataValidationJS = dataValidation.js
//lazy val dataValidationJVM = dataValidation.jvm

// lazy val `scalajs-react-macros` = project
//   .settings(libsettings)
//   .settings(publishSettings)
//   .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
//   .settings(macroSettings)
//   .settings(
//     description := "Helpful macros.",
//     libraryDependencies ++= Seq(
//       "org.scala-lang" % "scala-reflect" % scalaVersion.value,
//       "org.scalameta" %% "scalameta" % "1.8.0" // old version required
//     ))

lazy val `prop-types` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react)
  .settings(description := "prop-types package.")

lazy val `react-dom` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react)
  .settings(description := "react-dom package.")

lazy val `vdom` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react)
  .settings(description := "vdom helpers.")

lazy val `react-redux` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react)
  .settings(
    description := "redux via react-redux."
  )

lazy val `fabric` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react,vdom)
  .settings(description := "microsoft office-ui-fabric facade.")

lazy val `fabric-experiments` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react,vdom,fabric
  )
  .settings(description := "microsoft @uifbaric experiments.")

lazy val `bootstrap` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react ,vdom)
  .settings(description := "bootstrap facade.")

lazy val `mui` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    react, vdom, jss
  )
  .settings(description := "material ui facade.")

lazy val `scalajs-reaction-router` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react, vdom)
  .settings(description := "scaljs-reaction browser js oriented router")

lazy val `scalajs-reaction-form` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(react, vdom)
  .settings(description := "scalajs-reaect form library.")

// Watch non-scala assets as well, we add this to root project even
// though its only relevant to examples project.
watchSources += baseDirectory.value / "examples/src/main/assets"

lazy val examples: Project = project
  .settings(libsettings)
  .settings(noPublishSettings)
  .settings(
    // https://github.com/vpavkin/scala-js-momentjs/pull/41
    libraryDependencies ++= Seq(
      "ru.pavkin" %%% "scala-js-momentjs" % "0.10.0" //"0.10.0-SNAPSHOT"
    )
  )
  .settings(fpsettings)
  .settings(exampleFPSettings)
  .dependsOn(
    `fabric`,
    `fabric-experiments`,
    `react-redux`,
    `react-dom`,
    `prop-types`,
    `scalajs-reaction-router`,
    `scalajs-reaction-form`,
    `bootstrap`,
    `mui`,
    `react-big-calendar`,
    `react-router-dom`
//     ,dataValidationJS
  )
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .disablePlugins(BintrayPlugin)
  .settings(exampleSettings)
  .settings(artifactPath.in(Compile, fastOptJS) := crossTarget.in(Compile, fastOptJS).value / "Scala.js")
  .settings(artifactPath.in(Compile, fullOptJS) := crossTarget.in(Compile, fullOptJS).value / "Scala.js")

lazy val docs = project
  .settings(exampleSettings ++ libsettings)
  .settings(noPublishSettings)
  .enablePlugins(ScalaUnidocPlugin)
  .enablePlugins(ScalaJSPlugin)
  .settings(
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject --
      inProjects(examples)
    //-- inProjects(dataValidationJVM) // exclude one of them to avoid dupes
  )

val copyAPI = taskKey[Unit]("Copy API files to website build dir")
copyAPI := {
  IO.copyDirectory(
    // a bit hard-coded
    file("./docs/target/scala-2.13/unidoc"),
    file("./website/scalajs-reaction/static/api"),
    overwrite = true
  )
}

addCommandAlias("fmt", ";scalafmt")

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

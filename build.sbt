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
  bintrayPackageLabels := Seq("scala.js", "react", "fabric", "react-native",
    "office", "material-ui", "bootstrap"),
  bintrayVcsUrl := Some("git:git@github.com:aappddeevv/scalajs-reaction"),
  publishMavenStyle := true
)

lazy val licenseSettings = Seq(
  headerMappings := headerMappings.value +
    (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment),
    headerLicense  := Some(HeaderLicense.Custom(
      """|Copyright (c) 2018 The Trapelo Group LLC
         |This software is licensed under the MIT License (MIT).
         |For more information see LICENSE or https://opensource.org/licenses/MIT
         |""".stripMargin
    )))

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
    Resolver.jcenterRepo,
    //ivyLocal
  ),
  autoCompilerPlugins := true,
) ++ licenseSettings

lazy val noPublishSettings = Seq(
  skip in publish := true, // this did not seem to work
  publish := {},
  publishLocal :={},
  publishArtifact := false 
)

lazy val exampleSettings = Seq(
  libraryDependencies ++= Seq(
    // any special ones??
  ),
)

lazy val publishSettings = Seq(
  homepage := Some(url("https://github.com/aappddeevv/scalajs-react"))
) ++ bintraySettings

val commonScalacOptions = Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:_",
  "-unchecked",
  //  "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
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
    "org.scala-js" %%% "scalajs-dom" % "0.9.7",
  )
)

lazy val commonSettings = Seq(
  scalacOptions ++= commonScalacOptions,
  libraryDependencies ++= Seq(
    "org.scalatest"          %%% "scalatest"    % "latest.release" % "test",
  ),
  dependencyOverrides ++= Seq(
    // none
  ),
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
  autoAPIMappings := true,
)

// supports scala 2.13 and scalajs 0.6
val catsVersion = "2.0.0-M4"

lazy val fpsettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel"          %%% "cats-core"    % catsVersion,
  ))

lazy val exampleFPSettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel"         %%% "cats-effect"    % catsVersion,
    //"tech.sparse" %%% "trail" % "0.1.2", // 0.1.2 uses newest cats
))

lazy val libsettings = buildSettings ++ commonSettings ++ jssettings
lazy val jvmlibsettings = buildSettings ++ commonSettings

lazy val root = project.in(file("."))
  .settings(jvmlibsettings)
  .settings(noPublishSettings)
  .settings(name := "scalajs-react")
  .aggregate(
    examples,
    docs,    
    `scalajs-reaction-core`,
    `scalajs-reaction-fabric`,
    `scalajs-reaction-fabric-experiments`,
    `scalajs-reaction-native`,
    `scalajs-reaction-vdom`,
    `scalajs-reaction-router`,
    `scalajs-reaction-react-redux`,
    `scalajs-reaction-react-dom`,
    `scalajs-reaction-prop-types`,
    `scalajs-reaction-bootstrap`,
    `scalajs-reaction-mui`,
    `scalajs-reaction-router`,
    `scalajs-reaction-react-big-calendar`,
    `scalajs-reaction-native-nativebase`,
    `scalajs-reaction-native-react-native-elements`,
    `scalajs-reaction-native-react-navigation`,
    `scalajs-reaction-native-react-native-sideswipe`,
    `scalajs-reaction-jss`,
    `scalajs-reaction-form`,
    dataValidationJS
      //,dataValidationJVM
  )
    .enablePlugins(AutomateHeaderPlugin)
    .disablePlugins(BintrayPlugin)

lazy val `scalajs-reaction-core` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(description := "reactjs package.")
  .settings(buildInfoPackage := "ttg.react")

lazy val `scalajs-reaction-native` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(description := "reactjs native package.")
  .dependsOn(
    `scalajs-reaction-core`,
    `scalajs-reaction-vdom`
  )

lazy val `scalajs-reaction-react-big-calendar` = project
  .in(file("components/scalajs-reaction-react-big-calendar"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(Seq(
    description := "react-big-calendar",
  ))
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    `scalajs-reaction-core`,
    `scalajs-reaction-vdom`)

lazy val `scalajs-reaction-jss` = project
  .in(file("components/scalajs-reaction-jss"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(Seq(
    description := "cssinjs jss",
  ))
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    `scalajs-reaction-core`,
    `scalajs-reaction-vdom`)

lazy val `scalajs-reaction-native-react-navigation` = project
  .in(file("components/scalajs-reaction-native-react-navigation"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(Seq(
    description := "react-navigation facade",
  ))
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    `scalajs-reaction-core`,
    `scalajs-reaction-native`    
  )

lazy val `scalajs-reaction-native-react-native-sideswipe` = project
  .in(file("components/scalajs-reaction-native-react-native-sideswipe"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(Seq(
    description := "react-native-sideswipe",
  ))
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    `scalajs-reaction-core`,
    `scalajs-reaction-native`    
  )

lazy val `scalajs-reaction-native-nativebase` = project
  .in(file("components/scalajs-reaction-native-nativebase"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(Seq(
    description := "nativebase library",
  ))
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    `scalajs-reaction-core`,
    `scalajs-reaction-native`)

lazy val `scalajs-reaction-native-react-native-elements` = project
  .in(file("components/scalajs-reaction-native-react-native-elements"))
  .settings(libsettings)
  .settings(publishSettings)
  .settings(Seq(
    description := "react-native-elements library",
  ))
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    `scalajs-reaction-core`,
    `scalajs-reaction-native`)


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

lazy val dataValidationJS = project.in(file("data-validation"))
  .settings(name := "data-validation")
    .settings(libsettings)
    .settings(publishSettings)
    .settings(fpsettings)
    .settings(description :=
      "General purpose data validation library based on cats and applicatives.")
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

lazy val `scalajs-reaction-prop-types` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-reaction-core`)
  .settings(description := "prop-types package.")

lazy val `scalajs-reaction-react-dom` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-reaction-core`)
  .settings(description := "react-dom package.")

lazy val `scalajs-reaction-vdom` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-reaction-core`)
  .settings(description := "vdom helpers.")

lazy val `scalajs-reaction-react-redux` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-reaction-core`)
  .settings(
    description := "redux via react-redux.",
  )

lazy val `scalajs-reaction-fabric` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-reaction-core`, `scalajs-reaction-vdom`)
  .settings(description := "microsoft office-ui-fabric facade.")

lazy val `scalajs-reaction-fabric-experiments` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    `scalajs-reaction-core`,
    `scalajs-reaction-vdom`,
    `scalajs-reaction-fabric`,
  )
  .settings(description := "microsoft @uifbaric experiments.")

lazy val `scalajs-reaction-bootstrap` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-reaction-core`, `scalajs-reaction-vdom`)
  .settings(description := "bootstrap facade.")

lazy val `scalajs-reaction-mui` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(
    `scalajs-reaction-core`,
    `scalajs-reaction-vdom`,
    `scalajs-reaction-jss`
  )
  .settings(description := "material ui facade.")

lazy val `scalajs-reaction-router` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-reaction-core`, `scalajs-reaction-vdom`)
  .settings(description := "scaljs-reaction browser js oriented router")

lazy val `scalajs-reaction-form` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-reaction-core`, `scalajs-reaction-vdom`)
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
      "ru.pavkin" %%% "scala-js-momentjs" % "0.10.0-SNAPSHOT"
    ))
  .settings(fpsettings)
  .settings(exampleFPSettings)
  .dependsOn(
    `scalajs-reaction-fabric`,
    `scalajs-reaction-fabric-experiments`,
    `scalajs-reaction-react-redux`,
    `scalajs-reaction-react-dom`,
    `scalajs-reaction-prop-types`,
    `scalajs-reaction-router`,
    `scalajs-reaction-form`,
    `scalajs-reaction-bootstrap`,
    `scalajs-reaction-mui`,
    `scalajs-reaction-react-big-calendar`
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
    overwrite=true)
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

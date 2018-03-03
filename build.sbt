// Build file for ReasonRect like scala.js reactjs bindings.
// Now that's a mouthful...
import scala.sys.process._

lazy val licenseSettings = Seq(
  headerMappings := headerMappings.value +
    (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment),
    headerLicense  := Some(HeaderLicense.Custom(
      """|Copyright (c) 2018 The Trapelo Group LLC
         |This software is licensed under the MIT License (MIT).
         |For more information see LICENSE or https://opensource.org/licenses/MIT
         |""".stripMargin
    )))


lazy val macroSettings = Seq (
  resolvers += Resolver.sonatypeRepo("releases"),
  resolvers += Resolver.bintrayRepo("scalameta", "maven"),
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M11" cross CrossVersion.full),
  scalacOptions += "-Xplugin-require:macroparadise",
)

lazy val buildSettings = Seq(
  organization := "ttg",
  licenses ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
  scalaVersion := "2.12.4",
  resolvers += Resolver.sonatypeRepo("releases"),
  resolvers += Resolver.jcenterRepo,
  scalafmtVersion in ThisBuild := "1.4.0",
  autoCompilerPlugins := true
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
  )
)

lazy val publishSettings = Seq(
  homepage := Some(url("https://github.com/aappddeevv/scalajs-react"))
)

val commonScalacOptions = Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:_",
  "-unchecked",
    "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
    "-Xfuture",
  "-Ypartial-unification",
  )

//scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }

lazy val commonSettings = Seq(
  scalacOptions ++= commonScalacOptions ++
        (if (scalaJSVersion.startsWith("0.6."))
      Seq("-P:scalajs:sjsDefinedByDefault")
        else Nil),
  scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "latest.version"),
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4"),
)

lazy val libsettings = buildSettings ++ commonSettings

lazy val root = project.in(file("."))
  .settings(libsettings)
  .settings(noPublishSettings)
  .settings(name := "scalajs-react")
  .aggregate(`scalajs-react-core`, examples, `scalajs-react-fabric`,
    `scalajs-react-vdom`, `scalajs-react-vdom`, docs, `scalajs-react-redux`,
    `scalajs-react-react-dom`, `scalajs-react-prop-types`)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .disablePlugins(BintrayPlugin)

lazy val `scalajs-react-core` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .settings(description := "reactjs package.")

lazy val `scalajs-react-macros` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .settings(macroSettings)
  .settings(
    description := "Helpful macros.",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scalameta" %% "scalameta" % "1.8.0" // old version required
    ))

lazy val `scalajs-react-prop-types` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-react-core`)
  .settings(description := "prop-types package.")

lazy val `scalajs-react-react-dom` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-react-core`)
  .settings(description := "react-dom package.")

lazy val `scalajs-react-vdom` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-react-core`)
  .settings(description := "vdom helpers.")

lazy val `scalajs-react-redux` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-react-core`)
  .settings(
    description := "redux via react-redux.",
  )

lazy val `scalajs-react-fabric` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-react-core`, `scalajs-react-vdom`)
  .settings(description := "microsoft office-ui-fabric facade.")

// Watch non-scala assets as well, we add this to root project even
// though its only relevant to examples project.
watchSources += baseDirectory.value / "examples/src/main/assets"

lazy val examples = project
  .settings(libsettings)
  .settings(noPublishSettings)
  .dependsOn(`scalajs-react-fabric`, `scalajs-react-redux`, `scalajs-react-react-dom`,
    `scalajs-react-prop-types`)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .disablePlugins(BintrayPlugin)
  //.settings(macroSettings)
  .settings(exampleSettings)

// val CoreConfig = config("scalajs-react-core")
// val VDOMConfig = config("scalajs-react-vdom")
// val FabricConfig = config("scalajs-react-fabric")
// val ReduxConfig = config("scalajs-react-redux")

// macro project was removed
lazy val docs = project
  .settings(buildSettings)
  .settings(noPublishSettings)
  .enablePlugins(MicrositesPlugin)
  .enablePlugins(ScalaUnidocPlugin)
  .disablePlugins(BintrayPlugin)
  //.enablePlugins(SiteScaladocPlugin)
  .dependsOn(`scalajs-react-core`, `scalajs-react-vdom`,
    `scalajs-react-fabric`, `scalajs-react-redux`)
  .settings(
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject -- inProjects(examples)
  )
  .settings(
    micrositeName := "scalajs-react",
    micrositeDescription := "A react integration library for scala.js in the spirit of ReasonReact",
    micrositeBaseUrl := "/scalajs-react",
    micrositeGitterChannel := false,
    micrositeDocumentationUrl := "/scalajs-react/docs",
    micrositeAuthor := "aappddeevv",
    micrositeGithubRepo := "scalajs-react",
    micrositeGithubOwner := sys.env.get("GITHUB_USER").getOrElse("unknown"),
    micrositeGithubToken := sys.env.get("GITHUB_TOKEN"),
    micrositePushSiteWith := GitHub4s
  )
  .settings(exampleSettings)
  .settings(
    siteSubdirName in ScalaUnidoc := "api",
    addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), siteSubdirName in ScalaUnidoc)
    // SiteScaladocPlugin.scaladocSettings(CoreConfig,
    //   mappings in (Compile, packageDoc) in `scalajs-react-core`, "api/scalajs-react-core"),
    // SiteScaladocPlugin.scaladocSettings(VDOMConfig,
    //   mappings in (Compile, packageDoc) in `scalajs-react-vdom`, "api/scalajs-react-vdom"),
    // SiteScaladocPlugin.scaladocSettings(FabricConfig,
    //   mappings in (Compile, packageDoc) in `scalajs-react-fabric`, "api/scalajs-react-fabric"),
    // SiteScaladocPlugin.scaladocSettings(ReduxConfig,
    //   mappings in (Compile, packageDoc) in `scalajs-react-fabric`, "api/scalajs-react-redux"),
      // you need this if you use SiteScaladocPlugin so that the push uses gh-pages vs GitHub4s
      //micrositePushSiteWith := GHPagesPlugin
  )
  //.settings(macroSettings)

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
  "npm run examples:dev-start" !
}

// must run publish and release separately
// don't forget bintray & unpublish
// use packagedArtifacts to build artifacts in each project's target
// to release: publish then bintrayRelease (to release them)
bintrayReleaseOnPublish in ThisBuild := false
bintrayPackageLabels := Seq("scala.js", "react", "office")
bintrayVcsUrl := Some("git:git@github.com:aappddeevv/scalajs-react")
bintrayRepository := "maven"

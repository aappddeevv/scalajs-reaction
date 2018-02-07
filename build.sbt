// Build file for ReasonRect like scala.js reactjs bindings.
// Now that's a mouthful...
import scala.sys.process._

// may not need these next 2
resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.jcenterRepo
autoCompilerPlugins := true

lazy val licenseSettings = Seq(
  headerMappings := headerMappings.value +
    (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment),
    headerLicense  := Some(HeaderLicense.Custom(
      """|Copyright (c) 2018 The Trapelo Group LLC
         |This software is licensed under the MIT License (MIT).
         |For more information see LICENSE or https://opensource.org/licenses/MIT
         |""".stripMargin
    )))

lazy val buildSettings = Seq(
  organization := "ttg",
  licenses ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
  scalaVersion := "2.12.4",
) ++ licenseSettings

lazy val noPublishSettings = Seq(
  skip in publish := true, // this did not seem to work
  publish := {},
  publishLocal :={}
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
    "-Ypartial-unification"
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
  .aggregate(`scalajs-react-core`, examples, `scalajs-react-fabric`, `scalajs-react-vdom`, docs)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)

lazy val `scalajs-react-core` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .settings(
    description := "Core scala.js react facade.",
  )

lazy val `scalajs-react-vdom` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-react-core`)
  .settings(
    description := "vdom helpers.",
  )

lazy val `scalajs-react-redux` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-react-core`)
  .settings(
    description := "Connect to external redux store to drive updates.",
  )

lazy val `scalajs-react-fabric` = project
  .settings(libsettings)
  .settings(publishSettings)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)
  .dependsOn(`scalajs-react-core`, `scalajs-react-vdom`)
  .settings(
    description := "scala.js react facade support for microsoft office-ui-fabric",

  )

lazy val examples = project
  .settings(libsettings)
  .settings(noPublishSettings)
  .dependsOn(`scalajs-react-fabric`)
  .enablePlugins(ScalaJSPlugin, AutomateHeaderPlugin)

// val CoreConfig = config("scalajs-react-core")
// val VDOMConfig = config("scalajs-react-vdom")
// val FabricConfig = config("scalajs-react-fabric")
// val ReduxConfig = config("scalajs-react-redux")

lazy val docs = project
  .settings(buildSettings)
  .settings(noPublishSettings)
  .enablePlugins(MicrositesPlugin)
  .enablePlugins(ScalaUnidocPlugin)
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
  )

val npmBuild = taskKey[Unit]("fullOptJS then webpack")
npmBuild := {
  (fullOptJS in (examples, Compile)).value
  "npm run afterScalaJSFull" !
}

val npmBuildFast = taskKey[Unit]("fastOptJS then webpack")
npmBuildFast := {
  (fastOptJS in (examples, Compile)).value
  "npm run afterScalaJSFast" !
}

val npmTest = taskKey[Unit]("scala testing fastOptJS then webpack")
npmTest := {
  (fastOptJS in (examples, Compile)).value
  "npm run scala" !
}
val npmElectron = taskKey[Unit]("scala then electron build")
npmElectron := {
  (fastOptJS in (examples, Compile)).value
  "npm run electron" !
}

// must run publish and release separately
// don't forget bintray & unpublish
// checkout packagedArtifacts to build artifacts in each project's target
// bintray then bintrayRelease (to release them)
bintrayReleaseOnPublish in ThisBuild := false
bintrayPackageLabels := Seq("scala.js", "react", "office")

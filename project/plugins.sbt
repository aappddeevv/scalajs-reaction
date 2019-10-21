// 1.0.0-M7 or 0.6.28 by default
val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.29")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.0.1")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.9.0")
addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.4")
addSbtPlugin("com.47deg"  % "sbt-microsites" % "0.9.2")
addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.4.0")
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "5.2.0")
// not using this yet, still using per-project docs
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.4.2")
addSbtPlugin("org.tpolecat" % "tut-plugin" % "0.6.12")
addSbtPlugin("com.thoughtworks.sbt-scala-js-map" % "sbt-scala-js-map" % "latest.release")


addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "0.6.1")
addSbtPlugin("org.scala-js"       % "sbt-scalajs"                   % "0.6.23")

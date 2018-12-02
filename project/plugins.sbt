// 1.0.0-M3 or 0.6.25 by default
val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.25")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.5.1")
//addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")
addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.3")
addSbtPlugin("com.47deg"  % "sbt-microsites" % "0.7.24")
addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.3.2")
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "5.0.0")
// not using this yet, still using per-project docs
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.4.1")
addSbtPlugin("org.tpolecat" % "tut-plugin" % "0.6.7")


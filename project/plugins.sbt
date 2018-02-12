// 1.0.0-M3 or 0.6.22 by default
val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.22")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)
addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.14")
//addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")
addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.3")
addSbtPlugin("com.47deg"  % "sbt-microsites" % "0.7.15")
addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.3.1")
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "4.0.0")
// not using this yet, still using per-project docs
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.4.1")

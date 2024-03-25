val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("1.16.0")

addSbtPlugin("com.github.sbt" % "sbt-dynver" % "5.0.1")
addSbtPlugin("org.scala-js"                      % "sbt-scalajs"      % scalaJSVersion)
addSbtPlugin("org.scalameta"                     % "sbt-scalafmt"     % "2.5.2")
addSbtPlugin("com.eed3si9n"                      % "sbt-buildinfo"    % "0.12.0")
//addSbtPlugin("org.foundweekends"                 % "sbt-bintray"      % "0.5.6")
addSbtPlugin("de.heikoseeberger"                 % "sbt-header"       % "5.10.0")
addSbtPlugin("com.github.sbt"                      % "sbt-unidoc"       % "0.5.0")
//addSbtPlugin("org.scalameta"                     % "sbt-mdoc"         % "2.1.1")
//addSbtPlugin("com.thoughtworks.sbt-scala-js-map" % "sbt-scala-js-map" % "latest.release")
//addSbtPlugin("ch.epfl.scala"                     % "sbt-scalafix"     % "0.10.1")
//addSbtPlugin("ch.epfl.scala"                     % "sbt-bloop"        % "1.4.8")
//addSbtPlugin("com.thoughtworks.sbt-scala-js-map" % "sbt-api-mappings" % "4.0.0+21-b1f441da")

//addSbtPlugin("com.geirsson"                      % "sbt-ci-release"                % "1.5.7")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.10.0")
addSbtPlugin("com.github.sbt" % "sbt-pgp" % "2.2.1")



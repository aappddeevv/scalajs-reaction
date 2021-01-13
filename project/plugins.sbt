val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("1.4.0")

addSbtPlugin("com.dwijnand" % "sbt-dynver" % "4.1.1")
addSbtPlugin("org.scala-js"                      % "sbt-scalajs"      % scalaJSVersion)
addSbtPlugin("org.scalameta"                     % "sbt-scalafmt"     % "2.4.2")
addSbtPlugin("com.eed3si9n"                      % "sbt-buildinfo"    % "0.9.0")
addSbtPlugin("org.foundweekends"                 % "sbt-bintray"      % "0.5.6")
addSbtPlugin("de.heikoseeberger"                 % "sbt-header"       % "5.4.0")
addSbtPlugin("com.eed3si9n"                      % "sbt-unidoc"       % "0.4.3")
//addSbtPlugin("org.scalameta"                     % "sbt-mdoc"         % "2.1.1")
//addSbtPlugin("com.thoughtworks.sbt-scala-js-map" % "sbt-scala-js-map" % "latest.release")
addSbtPlugin("ch.epfl.scala"                     % "sbt-scalafix"     % "0.9.15")
addSbtPlugin("ch.epfl.scala"                     % "sbt-bloop"        % "1.4.0")
//addSbtPlugin("com.thoughtworks.sbt-scala-js-map" % "sbt-api-mappings" % "4.0.0+21-b1f441da")


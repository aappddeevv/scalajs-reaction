//import sbt._
//import sbt.Keys._

// Plugin to run webpack dev build when files contained in assets changes
// can also just use watchSources.
// see: https://github.com/tlazaro/texture-packer-plugin/blob/71923eab9e4f626a0bdd1dbe8138733a42f474af/src/main/scala/com/starkengine/TexturePackerPlugin.scala#L28

// object RunWebpackPlugin extends Plugin {

//   val assets = SettingKey[File]("assets")

//   val processNonScala = TaskKey[Unit]("process-non-scala", "Runs a npm/webpack command if non-scala sources change.")

//   val processNonScalaTask = processNonScala <<= (assets) map {
//     (resources) =>
//     println(s"resources $resources")
//   }

// }

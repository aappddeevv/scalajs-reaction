package ttg

import sbt._
import Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin

// the plugin that normally does this has not been updated...hence the hack
// Should be the tag.
// raw.githubusercontent.com/username/repo-name/branch-name/path
// https://raw.githubusercontent.com/aappddeevv/scalajs-reaction/master/react/src/main/scala/react.scala
object URIRemap extends AutoPlugin {
  override final def requires = ScalaJSPlugin
  override def trigger = allRequirements

  override final def projectSettings = Seq(scalacOptions += {
    val a = baseDirectory.value.toURI
    val b = s"""https://raw.githubusercontent.com/aappddeevv/scalajs-reaction/${sys.process.Process("git rev-parse HEAD").lineStream_!.head}/${moduleName.value}/"""
    //println(s"Source map mapping $a -> $b")
    s"""-P:scalajs:mapSourceURI:$a->$b""" 
  })
}

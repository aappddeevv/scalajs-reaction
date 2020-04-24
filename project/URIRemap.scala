package ttg

import sbt._
import Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin

// the plugin that normally does this has not been updated...hence the hack
// Should be the tag not git hash!.
// raw.githubusercontent.com/username/repo-name/branch-name/path
object URIRemap extends AutoPlugin {
  override final def requires = ScalaJSPlugin
  override def trigger = allRequirements

  override final def projectSettings =
    Seq(scalacOptions ++= {
      if (isSnapshot.value) Seq.empty
      else {
        val a = baseDirectory.value.toURI
        val process_result = sys.process
          .Process("git rev-parse HEAD")
          .lineStream_!
          .head
        val g = "https://raw.githubusercontent.com/aappddeevv/scalajs-reaction"
        val b = s"""$g/$process_result/${moduleName.value}/"""
        //println(s"Source map mapping $a -> $b")
        // Use this once I'm on version tags.
        //Seq(s"""-P:scalajs:mapSourceURI:$a->$g/v${version.value}/""")
        // Use this for git heads :-)
        Seq(s"""-P:scalajs:mapSourceURI:$a->$b""")
      }
    })
}

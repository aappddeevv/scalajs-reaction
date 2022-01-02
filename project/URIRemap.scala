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
      // if snapshot, file path is good enough since its probably a local build
      if (isSnapshot.value) Seq.empty
      else {
        // strip off the file path and add a http location
        val a = baseDirectory.value.toURI
        val segment = 
            if(baseDirectory.value.toString.contains("components")) "/components"
            else "/"
        val process_result = sys.process
          .Process("git rev-parse HEAD")
          //.Process("git tag --list | head")
          .lineStream_!
          .head
        val g = "https://raw.githubusercontent.com/aappddeevv/scalajs-reaction"
        val b = s"""$g/$process_result$segment/${moduleName.value}/"""
        //println(s"Source map mapping $a -> $b")
        // Use this once I'm on version tags. Version tags have leading v.
        //Seq(s"""-P:scalajs:mapSourceURI:$a->$g/${version.value}/""")
        // Use this for git heads :-)
        Seq(s"""-scalajs-mapSourceURI:$a->$b""")
      }
    })
}

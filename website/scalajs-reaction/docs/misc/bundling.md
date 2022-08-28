---
id: bundling
title: Bundling
---

## General

Bundling is the process of taking your scala.js program, ensuring that all
dependencies are identified then creating an output file suitable for use on the
web or on a mobile device.

A special bundler that understands http and file URLs is available at
[scalajs-friendly-source-map-loader](https://github.com/aappddeevv/scalajs-friendly-source-map-loader)
for bundling scalajs-reaction programs. It will work with any scala.js source
file and is not specific to scalajs-reaction. Please see that page for
directions.


## Obtaining sbt Output Information for npm

You may use a bundler for your scalajs output in order to bundle the application for
your webapp or other target.

Generally, the `npm build` command needs to know here the scala artifact is located
in order to pull it in. To wire in a well known location you have a few choices:

* Hard code the sbt output directory into your bundler config file
* Change the output file location in sbt (I built a plugin for this but it is unmaintained now)
* Program sbt to output the location.

To have sbt output the location you can use some code devleopde by sjrd: https://github.com/sjrd/scalajs-sbt-vite-laminar-chartjs-example/blob/main/build.sbt


```scala
// top of build.sbt
val publicDev = taskKey[String]("output directory for `npm run dev`")
val publicProd = taskKey[String]("output directory for `npm run build`")

// in the project of your choice
publicDev := linkerOutputDirectory((Compile / fastLinkJS).value).getAbsolutePath(),
publicProd := linkerOutputDirectory((Compile / fullLinkJS).value).getAbsolutePath(),

// bottom of build.sbt
def linkerOutputDirectory(v: Attributed[org.scalajs.linker.interface.Report]): File = {
  v.get(scalaJSLinkerOutputDirectory.key).getOrElse {
    throw new MessageOnlyException(
        "Linking report was not attributed with output directory. " +
        "Please report this as a Scala.js bug.")
  }
}
```

With this you can issue an external process command in your javascript bundler
tool and pickup the location. You will never be out of date again!

Now at the cli:

```sh
// since sbt must start, try to run and cache the result
// at the project level use `print <project>/publicDev`
sbt --error --batch print publicDev
sbt --error --batch print publicProd
```
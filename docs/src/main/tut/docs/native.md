---
layout: docs
title: React Native
---
# React Native

You can easily create react native applications using this library. react native
uses a "runner" application (unlike flutter) to host the javascript execution
engine. You will need the runner skeleton to build a react native
application. The best way to do that is to follow the instructions for
installing
[`react-native-cli`](https://facebook.github.io/react-native/docs/getting-started)
and then overlaying the scalajs library on top of the generated project.

## Setup

Add your build.sbt, project folder and src/main/scala content to the top-level
of the generated react native project. Include the libraries:

* scalajs-react-core
* scalajs-react-native

in your library dependencies. Create your build.sbt build file as you would for
any scala project.

Then, you can alter the index.js in the top level directory to either contain
the "exported to js" component *or* you can call `AppRegistry.registerComponent`
inside of scalajs that is top level exported. In either case, the index.js
should either use your exported JS "app" component or call a function that calls
registerComponent.

That's all you need for setup.

## Calling AppRegistry.registerComponent in scala.js

If your index.js file imports a function that performs the registration:

```scala
@JSExportTopLevel("Main")
object Main {
  @JSExport("main")
  def maian(): Unit = {
    AppRegistry.registerComponent("HelloWorld", () => App.JS)
  }
}

```

Regardless of whether you call registerComponent in scala or JS, you need to
create the application component. That's easy. Don't forget to "wrap" it for JS use. In the code below, it is exported so you can call registerComponent in index.js bt if you call registerComponent in scala as show above, you do need to export it.

```scala
@JSExportTopLevel("App")
object App {
  val Name = "App"
  val c = statelessComponent(Name)
  import c.ops._
  
  def apply() = render { self =>
    View()(
        Text()("This is some text.")
    )
  }
  
  @JSExport("JS")
  val JS = c.wrapScalaForJs[js.Object](_ => App())
}

```

Usually, the top level application is a reducer component but the example above
shows a stateless component to keep it simple.

If you have your export setup, your index.js should include the scala.js output like:

```javascript
// adjust for your output
import { Main } from "./target/scala-2.12/app-fastopt.js"
// or
import { App } from "./target/scala-2.12/app-fastopt.js"

// choose:
Main.main()
// or
AppRegistry.registetrComponent(appName, () => App.JS)
```

## Build

Run sbt as you normally would and during dev turn on `~fastOptJS`. That allows
you to recompile as needed. react native uses its own JS packager, called metro,
that restructures your JS similar to webpack. metro is not as featurefull as
webpack though. When you run `react-native run-android` it first runs gradle to
build the java part of the project and it starts up a JS server similar to the
way that webpack-dev-server works. It is suppose to detect changes in js files
and do a hot reload. You may need to turn on hot reloading using Ctrl+M
(linux/windows hosted emulators).

<<insert comment here about detecting the scala.js output changes>>

## Helpers

While you should install android studio because you will probably need to write some interop, you can start the emulator without starting android studio:

```sh
# list emulator devices
$ emulator -list-devs

# start an emulator
$ emulator -avd Pixel_2_XL_API_28 -no-boot-anim
````

You will want to add:

```sh
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/tools/bin
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

to a shell script you can import into your current terminal.

## Result

Here's a simple screen using Text and Button.

![Android screenshot](./android-screen.png){:width="300px"}

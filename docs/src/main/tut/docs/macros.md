---
layout: docs
title: Macros
---
# macros

Macros have been included that transform your non-native JS traits into list
oriented constructors. So instead of this:

```scala
trait MyProps extends js.Object {
  val p1: String
  var p2: js.UndefOr[Int] = js.undefined
  var p3: js.UndefOr[Int] = js.undefined
}
...
val props = new MyProps { 
   val p1 = "value"
   p2 = 10
}

```

a companion object is generated that allows you to write:

```scala
val props = MyProps(p1="value", p2=10)
```

if you use the annotation:

```
@simplecreate
trait MyProps extends js.Object { ... }
```

Non-native JS traits are like creating javascript literal objects but with
typechecking similar to typescript's "interface".

We don't recommend you use this though or at least only for small traits. It's a
bit goofy. Also, if you have vals/vars in your trait that are dependent on type
aliases, the macro is not written well enough to handle this case.

## Using macros

You will need to add the following dependencies in the project that you wish to
have the annotation expand:

```scala
project.settings(
  libraryDependencies ++= "ttg" %%% "scalajs-react-macros" % scalaJsReactVersion,
  resolvers += Resolver.bintrayRepo("scalameta", "maven"),
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M11" cross CrossVersion.full),
)
```

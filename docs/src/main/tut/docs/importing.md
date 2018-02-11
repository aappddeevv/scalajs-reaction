---
layout: docs
title: Importing from Js
---
Importing a component javascript means you need to create a "Component" in scala. There is a simple function call to do that for you although you still need to perform the scala.js `@JSImport`.

```scala
// import a react component exported as part of a namespace export
// perhaps mark the import private
@js.native
@JSImport("some-lib", JSImport.Namespace)
object SomeLibNS {
  val YourComponent1: ReactJSComponent = js.native
  val YourComponent2: ReactJSComponent = js.native
}

// create a proxy component for it
object SomeLib {
  import ttg.react.elements._
  def YourComponent1(props: Attr*)(children: ReactNode*) = wrapJsForScala(SomeLibNS.YourComponent1, new Attrs(props).toJs, children:_*)
}
```
A component, declared as a react class, should be typed as a `ReactJSComponent`.

How you hook up attributes and children values to your component is up to you. The definition in SomeLib is what you want it to be. In typescript, it is common to define an interface that declares the allowed properties to be passed in. The equivalent in scala.js are JS traits. You will want to make them non-native JS traits so you can instantiate them.
```scala
trait YourComponent1Props extends js.Object {
  val className: js.UndefOr[String] = js.undefined
}
```
Then you can define:
```scala
object SomeLib {
  import ttg.react.elements._
  def YourComponent1(props: js.UndefOr[YourComponent1Props] = js.undefined)(children: ReactNode*) = wrapJsForScala(SomeLibNS, new Attrs(props).toJs, children:_*)
}
```
You can use more fancy approaches to consolidating your imported component's properties into a js.Object, it's up to you.

## Managing Javascript Props
If you need to include additional HTML specific props, you can use the vdom package. Let's say you want to be able to pass in any HTML element properties to your component.
```scala
import ttg.react.vdom._

trait YourComponent1 extends HTMLAttributes[dom.html.Element] {
  // className already declared in HTMLAttributes.
}
```

## Typescript
If you have typescript definitions you may want to try to convert them to a native JS trait. There are converters you can try:
* [definitely typed](https://github.com/DefinitelyTyped/DefinitelyTyped)
* [scala-js-ts-importer](https://github.com/sjrd/scala-js-ts-importer)

You may find that for complicated libraries the conversion is not accurate and you still need hand editing or that the conversion fails.


[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-1.0.0-M3.svg)](https://www.scala-js.org)

A react library for scala written in the spirit of ReasonReact, a react library for an OCaml dialect known as reason (sponsored by facebook). ReasonReact documentation is located [here](https://reasonml.github.io/reason-react/docs) and provides a good description of how this library works since the API is very similar. While more advanced scala functionality could have been used, the scala.js implemetation was kept intentionally similar to ReasonReact so that the documentation would apply.

The facade library is small and provides an opionated API for creating components. JSX is not provided in the scala.js implementation.

Of course, you can code a react interface without using any of these facades since scala.js supports non-native JS class definitions. scala.js's JS class support is extensive and well thought out. One of the reasons I liked the ReasonReact model is that it rethought the abstractions and API. Instead of trying to copy the reactjs API it provides something smaller and simpler and emphasizes benefits in just a few dimensions of the reactive user interface problem--almost the very definition of "disruptive."

## Usage
Include the library in your build:
```scala
resolvers += Resolver.bintrayRepo("aappddeevv", "maven")
val scalaJsReactVersion = "latest.version"
//  or
//val scalaJsReactVersion = "0.1.0-M1"

// grab the the latest version or use a specific version
libraryDependencies ++= Seq(
    "ttg" %%% "scalajs-react-core" % scalaJsReactVersion,
    "ttg" %%% "scalajs-react-vdom" % scalaJsReactVersion,
    // optional but includes a Microsoft UI component set
    "ttg" %%% "scalajs-react-fabric" % scalaJsReactVersion)
```
Do not forget to include the react libraries in your execution environment. For react 16+, the libraries have been split out into multiple libraries, listed below. For 15.x, only the core, monolithic library `react` is needed.
* react 
* react-dom
* create-react-class

You can define a class like so:
```scala
import ttg.react._
import ttg.react.elements._
import ttg.react.reactdom._
import ttg.react.fabric._
import ttg.react.Converters._
import vdom.prefix_<^._

// You can use individual props or a trait or a scala object.
// It's often convenient to use a subtype of js.Object
// non-native JS trait
trait ToDoProps extends js.Object {
  val todo: ToDo // has property "name"
  val remove: Unit => Unit
}

object ToDoC {
  val ToDo = statelessComponent("ToDo")
  // def make(todo: ToDo, remove: Unit => Unit) =
  // or bundle them together into a trait
  def make(props: ToDoProps) =
    ToDo.
      withRender(self => {
        <.div(^.style := Style("display" := "flex"))(
          Label()("Item:"),
          Label()(props.todo.name),
          defaultButton(
            F.text := "Remove",
            ^.onClick ==> ((_: ReactEvent) => props.remove(())))().toEl)})
}
```
You can export the class for use in reactjs as follows:
```scala
@JSExportTopLevel("ToDo")
val exportedToDo = ToDo.wrapScalaForJs((jsProps: ToDoProps) => make(jsProps))
```
Most times the interop has to deal with optional parameters and the conversion from jsPropsp to `make` parameters is much more messy.

If you need to import a javascript class, you can use the standard scala.js machinery to import it:
```scala
@js.native
@JSImport("some-package", JSImport.Namespace)
object SomePackageNS extends js.Object {
  val label: ReactClass = js.native
}
object SomePackage {
  import ttg.react.elements._
  def Label(props: Attr*)(children: ReactNode*) = wrapJsForScala(SomePackageNS, new Atrs(props).toJs, children:_*)
}
  // or...
  def Label(props: js.UndefOr[LabelProps])(children: ReactNode*) = wrapJsForScala(SomePackageNS.Label, props.getOrElse(noProps), children:_*)

  trait LabelProps extends HTMLAttrbutes[dom.html.Label] { // optionally extend HTMLAttributes[]
   // ...
  }
```
You need to call `wrapJsForScala` at some point. You are free to use the model above for attributes and children or you can use a dedicated non-native JS trait or use whatever suits your application for scala-side API. You could also define the component to take `Attr*` props, a non-native JS trait or even `js.Dynamic`.

Note that since functions are used, there are many advanced scala and scala.js patterns you can use to alter the way that components are created and the API to create elements.For example, you could also use type classes to change the attributes to a js.Object since all you need is some type of "writer."

There is no JSX support at this time.

Styling can be performed several ways for example using [ScalaCSS](https://github.com/japgolly/scalacss). You can also just import your style sheets and have them picked up by webpack:
```scala
@js.native
@JSImport("Examples/todo/todo.css", JSImport.Namespace)
object componentStyles extends js.Object

object styles {
  // or cast to js.Dictionary or anything else you want to use
  val component = componentStyles.asInstanceOf[js.Dynamic]
}

// use: styles.component.root
```
In the example above, you would need to ensure webpack has an alias to the Examples directory and that you have setup your CSS processors correctly. post-css is quite popular when using webpack.

# Documentation

Client:
* [client](http://aappddeevv.github.io/scalajs-react)

API documentation:
* [core](api/scalajs-react-core/index.html)
* [vdom](api/scalajs-react-vdom/index.html)
* [fabric](api/scalajs-react-fabric/index.html)
* [redux integration](api/scalajs-react-redux/index.html)

# Motivation
I was looking for a react facade that rethought reactive interactions. Many of the existing facades are straight adaptions of the standard react library and make it quite easy to program react in a scala environment. 

The ReasonReact facade, using a modified OCaml syntax, keeps it quite simple. This library is actually quite small and simple and relies on functions. Scala.js easily supports creating classes that map into javascript classes and hence into react component classes. However, with parallel fiber coming to react and other enhancements that are planned, a more functional library should withstand the change in the core react infrastructure to something that is more sustainable in the long run. The current react approach makes it easy to introduce errors both at compile time and runtime. Even with the use of typescript, it's still quite a clunky API. Of course it is clunky for alot of "reasons" which is why Reason and ReasonReact was born. Facebook took an innovative approach to structuring Reason. Instead of trying to make a js superscript, which is what typescript does well, it is trying to go orthogonal like many other javascript transpiled languages have done e.g. elm, dart and of course scala.js.

## Suitability
Scala.js requires a bundling model that does not allow it to be broken apart and deployed on a pure "file/module" basis. Because of this, scalajs-react is best suited for UIs where it is not required to code split the scala portion of the library unless some type of fancy splitter can split apart optimized scala.js emitted code. Scala.js makes use of optimizations, such as inlining, and integrated type infrastructure that makes it difficult to split.

The core scala.js infrastructure costs you about 2.5k and increases as you use features such as immutability, the collections library or, of course, add data structures to your code.

## Prior Work
There are a few [scala.js](https://www.scala-js.org/) react facades/implementations available:
* [https://github.com/eldis/scalajs-react]: Very clean class oriented react implementation. Class oriented but has a builder as well and includes purely functional (stateless) component support as well. No macros. Allows ES class-like syntax. Has wrappers for japgolly. This lib was created to get around "wrapping" and other artifacts that the author was not fond of in japgolly.
* [https://github.com/eldis/scalajs-redux]: Redux facade by the same as above.
* [https://github.com/shogowada/scalajs-reactjs]: More functionally oriented facade. Contains redux facades and more.
* [https://github.com/japgolly/scalajs-react]: The can't shoot yourself in the foot implementation. well supported and thought out but a bit more complex API wise.
* [https://slinky.shadaj.me/]: Newcomer. Uses macros smartly. Allows you to use scala.js components in js as well. Uses some macros to help with javascript interop.
* [https://github.com/xored/scala-js-react]: Class oriented facade. Uses macros to transform xml literals so you can write xml in your code like jsx.
* [https://github.com/scalajs-react-interface/sri#sri]: React-native and web. New maintainers.

The facades differ in their facade approach but many recreate all of the API parts found in reactjs. japgolly works hard to put a more safe, functional API in place from the outset. Some of these facades have alot of complex facade components to help deal with the type and side-effects flexibility found in react. 

In theory, all of these can "export" a scala.js component to be used in javascript environments. However, various libraries that go deep into scala-land means that they are harder to use from javascript land often because they wrap the javascript props and state objects in a way that makes them less usable from the javascript world. Most, if not all, libraries allow you to use javascript defined components fairly directly via some type of adoption or import process.

This library [https://github.com/ThoughtWorksInc/Binding.scala] isn't react but it is reactive. It uses a new binding framework for scala. Works on jvm and js. The binding framework is at a individual element level like mobx. You can use xml syntax as well. The binding approach is called "precise" binding.

I have not mentioned many other libraries out there that help with react-like development including [diode](https://github.com/suzaku-io/diode).

## License

MIT license. See the LICENSE file.

Copyright 2018 The Trapelo Group LLC.
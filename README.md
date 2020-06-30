<p align="center"><img width="300" src="./logo.svg"/></p>
<p align="center"><i>Use react hooks and scala.js to catch the best user experience.</i></p>
<p align="center">
  <a href="https://www.scala-js.org">
   <img src="https://www.scala-js.org/assets/badges/scalajs-1.0.0.svg"/>
  </a>
</p>

![Bintray](https://img.shields.io/bintray/v/aappddeevv/maven/react)
![Maven Central with version prefix filter](https://img.shields.io/maven-central/v/org.scala-lang/scala-compiler/2.13.1?label=scala)

You need to use a react version 16.8+ to make sure that hooks are included. Use the latest react-native.

Get started with the [docs](http://aappddeevv.github.io/scalajs-reaction)

# What is scalajs-reaction?

A react library for scala written in the spirit of ReasonReact, a react library
for an OCaml dialect known as reason sponsored by facebook. [ReasonReact
documentation](https://reasonml.github.io/reason-react) provides a good description of how this library works since this facade was designed to mimic that facade. While more advanced scala functionality could have been used, the
scala.js implementation is kept intentionally similar to ReasonReact so that its
documentation applies to this project. However, you skip the ReasonReact documentation because this library uses simple scala/scala.js friendly constructs to create your user interface. Read on...

This library is small and focuses on simple js function components and hooks. Hooks are described on the
[react](https://reactjs.org/docs/hooks-reference.html) page.

scalajs-reaction emphasizes:

- Easy to learn/use. Your component is just a standard scala.js js function.

- Integration into an existing
  project by making it easy to import/export components. You just export the function.

- Easy to fit into
  existing application including those using global state-managed solutions such
  as redux.

At the same time, it allows you to build your entire interface in scalajs-reaction. As long as your front-end solution can manage the model of
  scala.js's output (one large module for all scala.js code, not file-by-file/module-by-module), you should consider scalajs-reaction for your solution. By providing a thin veneer over standard scala functions and hooks, it eschews abstractions and avoids getting in your way.

* [Demo (WIP)](http://aappddeevv.github.io/scalajs-reaction/demo/index.html).
* [Live Coding](https://www.youtube.com/watch?v=7on-oT2Naco): Uses the old API but still helpful.

The library supports fragments, the new context provider and hooks. The facade's
API roughly mimics ReasonReact's approach based on hooks. This facade also
supports react-native. The react-native use-case for scala.js is actually more
compelling than for web applications due to scala.js bundling issues.
There is also API support for experimental APIs.

A g8 template is available. Use `sbt new aappddeevv/scalajs-reaction-app.g8` to
create a new project.

# Creating Components

It's easy to create a component and render it:

```scala
val HelloWorld: ReactFC0 = () => div("hello world")
// ...
react_dom.createAndRenderWithId(HelloWorld, "container")
```

ReactFC0 does not do much other than ensure that the scala function on the right
becomes a js function--which is all that is needed to use react. You
could have just declared it directly: 

```scala
val HelloWorld: js.Function0[ReactNode] = props => div("hello world")
```

If you need to pass in an argument, remember that react function components requires only a single js object parameter, so do the following:

```scala
object MyComponent {
    trait Props extends js.Object {
        val name: String
    }
    // I use this declaration style alot.
    val render: ReactFC[Props] = props =>
      div("hello " + props.name)
    }
    render.displayName = "MyComponent"
    def apply(props: Props) = createElement(render, props)
}
```

ReactFC says that the function component HelloWorld takes a single parameter, of
type Props. You do not need to use `ReactFC`, you could just use standard
scala and standard react:

```scala
object MyComponent { 
    trait Props ...

    val render: js.Function1[Props, ReactNode] = props => div(s"hello ${props.name}")

    def apply(props: Props) = createElement(render, props)
}
```

or with types and extension methods:

```scala
object MyComponent {
  trait Props ...

  // A standard scala function is a val
  val render: Props => ReactNode = props => div(s"hello ${props.name}")

  // A standard def scala function
  def render(props: Props): ReactNode = div(s"hello ${props.name}")

  // ReactElementTuple causes scala=>js function conversion using standard scala methods
  def apply(props: Props): ReactElementTuple = (render, props)

  // r convert using the `.elementWith` extension method which reads nicely
  // This is what I use alot.
  def apply(props: Props) = render.elementWith(props)
}
````

That's how simple this facade is. You use the easy parts of scala.

To create a higher-order component or a "container/pure renderer" design, use function composition:

```scala
def withPageLogging[P <: js.Object](component: ReactFC[P]): ReactFC[P] = props => {
  useEffectMounting(() => println("LOG PAGE!"))
  createElement(component, props) // or component.elementWith(props)
}
```

A few, very limited macros provide additional support creating and using `Props` more like case classes if that's important to you. Once scala3 is out, the macros will become industrial strength.

React needs to control when the props are applied to the rendering function, not you. A react "element" is really just a data structure that is a description of the element's rendering logic and props. The actual rendered element is created under react's control, not yours. There are other component specification patterns you can use as well or you can easily create your own like the above. See the docs.

If you want to ensure your component only renders when the props change, use
`memo()`. `memo` uses shallow compare
checking to see if the props have changed. An extension method is available
for memo to apply it as syntax. You need to take into account js's notion of equality when using
function components and hooks or provider your comparator, something useful when you have
scala objects in your props.

Add state and other effects to your component using react hooks just like you normally would.

The facade implementation in scala.js is only about 50 lines of code and is
easily maintained. Various conversion and extension methods are provided to make it easier to use.

You have choices on how to create your components and they are all
straightforward. Depending on the component library you use, having choices
helps you find the easiest way to access the components in your application.
This library does not force many conventions on your code.

This facade uses standard scala.js concepts. Higher level components, 
e.g. composition of smaller components, can
use more idiomatic scala constructs. This library does
not fight scala or scala.js to make it more like js. It uses
the tools provided by scala and scala.js to make it easier to write react applications and control react rendering optimizations.

# Usage

Include the library in your build:

```scala
resolvers += Resolver.bintrayRepo("aappddeevv", "maven")
val scalaJsReactVersion = "latest.version" // see the badge above 

// grab the the latest version or use a specific version
libraryDependencies ++= Seq(
    "ttg" %%% "react" % scalaJsReactVersion,
    "ttg" %%% "vdom" % scalaJsReactVersion,

    // optionals
    // if you need react-dom
    "ttg" %%% "react-dom" % scalaJsReactVersion

    // Microsoft fabric UI components, "MS office", css-in-"scala"
    // css-in-scala can be used independently of fabric
    "ttg" %%% "fabric" % scalaJsReactVersion,

    // Material UI components (bootstrap is also available but limited)
    "ttg" %%% "mui" % scalaJsReactVersion,

    // if you integrate with redux
    "ttg" %%% "redux" % scalaJsReactVersion,
    
    // if you need prop-types--you only need these for interop scenarios
    "ttg" %%% "prop-types" % scalaJsReactVersion,
 
    // if you need react-native
    "ttg" %%% "native" % scalaJsReactversion)
//
// Add import scala.language.implicitConversions to each file or
// add scalacOptions += Seq("-language:_") to your settings 
// to enable implicit conversions and all other language features
//
```

Do not forget to include the react libraries in your execution environment. For
react 16+, the libraries have been split out into multiple libraries. For the
reactjs based modules, the javascript dependencies are:

* core: react
* react-dom: react-dom

```sh
npm i --save react
npm i --save react-dom
```

React 16.8+, the one with hooks, is required. Some experimental APIs are supported
so you may want to use the latest experimental release.

There are many modules available as most of the focus has been on hand-crafted bindings in order
to improve ergonomics. We'll work with ScalablyType to improve the availability of bindings, however,
they are quite simple to write using scalajs-reaction. All of these libraries use the group name `ttg`:

* apollo
* bootstrap
* dataloader
* data-validation (applicative data validation)
* express
* fabric (office-ui-fabric-react): Custom and ergonomic facade.
* fabric-experiments
* forms: Advanced, all-scala.js forms package.
  * In transition so its not currently available.
* formik
* handlebars
* helmet
* jshelpers: Helpers for working with js data. Includes full js.Promise extension methods to avoid Future.
* jss
* lodash
* loglevel
* luxon (datetime library, evolved from moment)
* msal
* mssql
* mui
* native
* pathtoregexp
* plotlyjs
* prop-types
* react
* react-content-loader
* react-device-detect
* react-dom
* react-big-calendar
* react-fast-compare
* react-native-nativebase
* react-native-elements
* react-navigation
* react-native-sideswipe
* react-plotlyjs
* react-redux
* react-responsive
* react-helmet
* react-flexbox-grid
* react-router-dom (5 and 6)
* recoil (facebook state management)
* use-query-params
* use-deep-compare-effect
* vdom
* whydidyourender (use include/exclude regexs, see the readme in that directory)

Some of the external libs have just enough scala.js to write an app with but
they are not fully fleshed out. In most cases, there are enhancements
to make using the library easier, however, scala3 will introduce some
refinements to the scala language that will make many things easier
and I have chose not to try and be too clever until scala3 arrives.
Most of the packages use a simple namespace hierarchy starting with
the package name versus "ttg" or "scalajs-reaction" or anything
complicated. You are likely only to use one set of react libraries
per application so you should not encounter any package namespace
collisions.

In many cases, the full package label has been dramatically shortened
to make it easier to import the content you need. The package names
closely mirror the javascript libraries themselves.

You will most likely also need DOM bindings. Here's a link to the api
for org.scala-js scalajs-dom bindings: [![javadoc](https://javadoc.io/badge2/org.scala-js/scalajs-dom_sjs1_2.13/javadoc.svg)](https://javadoc.io/doc/org.scala-js/scalajs-dom_sjs1_2.13).

# Documentation

Client:
* [user](http://aappddeevv.github.io/scalajs-reaction)

Integrated API documentation:
* [all basic modules](https://aappddeevv.github.io/scalajs-reaction/api/ttg/react)

Sometimes the documentation generation process does not work so
if you need docs it is probably best to generate them
locally. 

Generate integrated documents using unidoc then open the toplevel page: 

```sh
get clone https://github.com/aappdddeevv/scalajs-reaction
sbt "docs/unidoc"
# linux has xdg-open to open a file from the command line
xdg-open website/scalajs-reaction/static/api/index.html
```

# Demo

You can start the demo locally if you run `sbt npmRunDemo`. A browser page
should open automatically. If not, point your browser to
[https://localhost:8080](https://localhost:8080). You can also just load the
demo files (after its built) into your browser but note that the icons will not
render correctly since they require a fetch.

Argh!!! I'm having trouble with gh-pages so the demo probably does not
work at htis time. Run it locally after you clone the repo.

# Motivation

I was looking for a react facade that rethought reactive interactions. Many of
the existing facades are straight adaptions of the standard react library and
make it quite easy to program react in a scala environment.

The ReasonReact facade, using a modified OCaml syntax, keeps it quite
simple. This library is actually quite small and simple and relies on
functions. Scala.js easily supports creating classes that map into javascript
classes and hence into react component classes. However, with parallel fiber
coming to react and other enhancements that are planned, a more functional
library should withstand the change in the core react infrastructure to
something that is more sustainable in the long run. 

# Suitability

Scala.js requires a bundling model that does not allow it to be broken apart and
deployed on a pure "file/module" basis. Because of this, scalajs-react is best
suited for UIs where it is not required to code split the scala portion of the
library unless some type of fancy splitter can split apart optimized scala.js
emitted code. Scala.js makes use of optimizations, such as inlining, and
integrated type infrastructure that makes it difficult to split.

The core scala.js infrastructure costs you about 2.5k and increases as you use
features such as immutability, the collections library or, of course, add data
structures to your code.

# Related

There are a few [scala.js](https://www.scala-js.org/) react
facades/implementations available:

* https://github.com/eldis/scalajs-react: Very clean class oriented react
  implementation. Class oriented but has a builder as well and includes purely
  functional (stateless) component support as well. No macros. Allows ES
  class-like syntax. Has wrappers for japgolly. This lib was created to get
  around "wrapping" and other artifacts that the author was not fond of in
  japgolly.
* https://github.com/eldis/scalajs-redux: Redux facade by the same as above.
* https://github.com/shogowada/scalajs-reactjs: More functionally oriented
  facade. Contains redux facades and more.
* https://github.com/japgolly/scalajs-react: The can't shoot yourself in the
  foot implementation. well supported and thought out but a bit more complex API
  wise.
* https://slinky.shadaj.me: Newcomer. Uses macros smartly. Allows you to use
  scala.js components in js as well. Uses some macros to help with javascript
  interop.
* https://github.com/xored/scala-js-react: Class oriented facade. Uses macros to
  transform xml literals so you can write xml in your code like jsx.
* https://github.com/scalajs-react-interface/sri#sri: React-native and web. New
  maintainers.
* https://github.com/Ahnfelt/react4s: React wrapper. This does not just mimic
  the standard react interface. Includes an interesting CSS builder,
  css-in-scala.

The facades differ in their facade approach but many recreate all of the API
parts found in reactjs. japgolly works hard to put a more safe, functional API
in place from the outset. Some of these facades have alot of complex facade
components to help deal with the type and side-effects flexibility found in
react.

In theory, all of these can "export" a scala.js component to be used in
javascript environments. However, various libraries that go deep into scala-land
means that they are harder to use from javascript land often because they wrap
the javascript props and state objects in a way that makes them less usable from
the javascript world. Most, if not all, libraries allow you to use javascript
defined components fairly directly via some type of adoption or import process.

Libraries you may be interested in:

* [binding.scala](https://github.com/ThoughtWorksInc/Binding.scala): reactive UI
framework. It uses a new binding framework for scala. Works on jvm and js. The
binding framework is at a individual element level like mobx. You can use xml
syntax as well. The binding approach is called "precise" binding.
* [diode](https://github.com/suzaku-io/diode): redux replacement.
* [laminar](https://github.com/raquo/laminar): a reactive web framework.
* [levsha](https://github.com/fomkin/levsha): Fast pure scala.js virtual dom.
* [pine](https://github.com/sparsetech/pine): XML/HTML builder.
* [scalacss](https://github.com/japgolly/scalacss): A solid css-in-scala solution.
* [scala-dom-types](https://github.com/raquo/scala-dom-types): for dom attributes.
* [scala-tags](https://github.com/lihaoyi/scalatags): XML/HTML builder.
* [outwatch](https://github.com/OutWatch/outwatch/): reactive UI framework.
* [udash](https://udash.io/) is another reactive framework that is not
react based, but reactive.
* [udash-css](https://udash.io): A css-in-scala framework.

# License

MIT license. [![GitHub license](https://img.shields.io/badge/license-MIT-lightgrey.svg?maxAge=2592000)](https://raw.githubusercontent.com/aappddeevv/scalajs-reaction/master/LICENSE)

Copyright 2018 The Trapelo Group LLC.

The logo was created partially from content provided by [Scott De
Jonge](https://www.flaticon.com/authors/scott-de-jonge) under CC3.0.

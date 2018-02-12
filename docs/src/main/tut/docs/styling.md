---
layout: docs
title: Styling
---
Styling is a topic with many wide-ranging solutions. I suggest you check out the [blog](http://appddeevvmeanderings.blogspot.com/2017/08/web-app-styling-interlude-how-to.html) on how to think about the styling approach that will work for you.

Generally, doing styling in the language, e.g. js or scala, allows you to customize the styling while still retaining most levels of performance. Heavy and complex styling will always require a complex process.

## Webpack CSS Processing (external CSS)
Assuming you are using webpack or CSS processing, CSS import produces a js.Object whose keys are style names (sometimes mangled) and whose values are styles. css-loader combined with style-loader then translates that into a stylesheet that is dynamically inserted into the DOM.

To use that method, ensure webpack has an alias to the location of your CSS files. I tend to put my CSS files right next to the component and hence my webpack alias "Styles", for example, points to my source directory. Then you can use:
```scala
@js.native
@JSImport("Styles/somecomponentdir/component.css", JSImport.Namespace)
object cstylesns extends js.Object

// put all imported styles into the same object
object styles {
  val cstyles = cstylesns.asInstanceOf[js.Dynamic] // to make it easy, but not type-safe
}

// then import them all or ala carte
import styles._

// use them
// cstyles.root or cystyles.input
```

## CSS in JS/Scala
This is becoming a much more preferred approach when the processing power of your client allows to do this. While pre-processed and smartly bundled CSS can give you load flexbility and performance benefits, the flexibility and sometimes negligible impact on performance that current solutions provide make this more popular.

There are some pure scala solutions available such as [ScalaCSS](https://github.com/japgolly/scalacss) as well as [udash](https://udash.io/) both of have CSS in Scala solutions, you have many choices include glamor (javascript) and something very similar such as fabric's styling (merge-styles).

You can use this style of style processing but if you do, you want to consider defining your styles in JS and importing them as JS data. Then use a facade over a CSS in JS library to add them once you have manipulated them as needed. You do not gain type-safety with this approach but if you use typescript bindings in typescript code, its probably good enough.

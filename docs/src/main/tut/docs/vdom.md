---
layout: docs
title: Virtual DOM
---
I simple "virtual DOM" has been provide. For facade libraries, this usually means some way to create HTML elements with attributes and children. It does *not* mean a virtual dom implementation since react already provides that.

`scalajs-react-vdom` is a separate vdom that you can use if you want to. You can another project's vdom as well as long as the final "element" is typed as a `ReactNode`.

To you use the vdom, do
```scala
import ttg.react.vdom._
```
This import provides you the ability to create attributes as a list, as compared to non-native JS traits and create elements using their name.

```scala
// create a div, ReactDomElement < ReactNode
val aDiv: ReactDomElement = <.div(...attributes...)(..children...)
```
The 2 parameter list (which allows you to easily curry as well) helps break up the lists between attributes and children which improves type inference. Children need to be `ReactNode` or a subclass of this such as `ReactElement`. An `import ttg.react.implicits._` will bring in an implicit conversation from `Component` to `ReactElement` for you. Otherwise on each child you must call `elements.createElement` or use a syntax import and do `yourComponent.toEl`. The implicits make it easy.

Note that the vdom html elements create ReactNodes directly. 

The funny symbol `<.` is meant to look like an element since scalajs-react does not have a JSX macro/processor. Attributes start with `^.` as in `^.className`. If you import both `import ^._` and `import ^._` you can do away with those symbols completely at the risk of having a name clash. If define your own symbols such as `E` or `A`, you can use those. Just repeat the definition of those symbols as they are defined in ttg.react.redux package object and use yours. 

The attributes in a list are automatically converted to a js.Object behind the scenes. Attributes that result in a js.undefined value are removed from the javascript object automatically.

You should note that the list style use of attributes does not allow for type safety in terms of the attributes themselves. You could add an attribute that is not recognized by react. We'll add the non-native JS trait version of these vdom elements shortly.

---
id: facades
title: Facades
---

A few facades are provided, or at least, a WIP version is provided.

# fabric

[Fabric](https://github.com/OfficeDev/office-ui-fabric-react) is a Microsoft
react UI library with many components and is freely available to make office
looking interfaces. It also has many infrastructure functions to handle themes
and styling. A simple facade has been included in `scalajs-react-fabric` that is
a WIP since the API is quite large and complex. Fabric is written in
typescript. `scalajs-react`s demo application was written mostly with fabric.

The lib is `scalajs-react-fabric`.

Fabric requires that your top level element (or at least high up in the
component tree) be `Fabric`:

```scala
Fabric()(
    // your root element
)
```

# Material UI

[Material UI](https://material-ui.com) is google inspired react library. These
binding are provisional until v4 comes out. Styling is based on
[CSSinJS](https://github.com/cssinjs).

The lib is `scalajs-react-mui`.

The default jss styling configuration usage as integrated into the underlying
react toolkit does not contain "extend" or "composes" which I think are
important. To add these properly to the list of jss plugins you can do:

```scala
import ttg.react.mui._
val myjss = JSS.create(jssPreset())

// call this until 4.x comes out
react.mui.install()

// top level provider, you could also add a ThemeProvider
StyleProvider(new StyleProvider.Props {
})(
    // your root element
)
    
```

at the top level to ensure that the proper jss configuration. You can also just
provide the provider at the level in the tree you want to use it. See the
material and cssinjs documentation for more details.

You need to npm include:

* @material-ui/core
* @material-ui/styles
* jss-plugin-rule-value-function
* jss-plugin-global
* jss-plugin-nested
* jss-plugin-compose
* jss-plugin-camel-case
* jss-plugin-default-unit
* jss-plugin-expand
* jss-plugin-vendor-prefixer
* jss-plugin-props-sort
* jss-plugin-cache

Yeah, that's alot. Its believed that you can use the fabric "merge-styles"
solution at the same time with little packaging waste. I have not tried that yet
myself.

# Bootstrap

[bootstrap]() is a bootstrap inspired binding. However, the bootstrap react
project is still maturing and we will wait for it to mature a bit more until we
complete the bindings for it. Styling is still maturing.

The lib is `scalajs-react-bootstrap`.

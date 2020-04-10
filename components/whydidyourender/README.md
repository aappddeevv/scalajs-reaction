Facade for https://github.com/welldone-software/why-did-you-render.

Internet notes:

* https://github.com/welldone-software/why-did-you-render/issues/61

## Usage

Similar to the guide, define your components as:

```scala
val render: ReactFC[Props] = props => {...}
render.asInstanceOf[js.Dynamic].__proto__.whyDidYouRender = true

// or to assign a non-mangled name
render.asInstanceOf[js.Dynamic]__proto__ = ComponentOptions(NAME)

// or more shorter
render.whyOptions(ComponentOptions(NAME))
```

And include the following in your program when it first starts:

```scala
import react._
if(!scala.scalajs.LinkingInfo.productionMode) { 
  ReactPublic,
  new whydidyourender.Options { 
    titleColor = "green"
    diffNameColor = "darkturquoise"
    include = js.Array(js.RegExp("^Shimmered.*"))
  } 
}
```

Which would track all components named `*Shimmered`.

Scala.js munges all the function names so regexps may
or may not work like you expect. You can toplevel 
export your functions and see the actual names that
are generated.

---
id: styling
title: Styling
---

Styling is a topic with many wide-ranging solutions. I suggest you check out the
[blog](http://appddeevvmeanderings.blogspot.com/2017/08/web-app-styling-interlude-how-to.html)
on how to think about the styling approach that will work for you.

Generally, doing styling in the language, e.g. js or scala, allows you to
customize the styling while still retaining most levels of performance. Heavy
and complex styling will always require a complex process. Check out
[ScalaCSS](https://github.com/japgolly/scalacss) or udash's version about scala
specific CSS-in-Scala solutions.

Recommendation: Use a js-based library like uifabric's
[merge-styles](https://github.com/OfficeDev/office-ui-fabric-react/tree/master/packages/merge-styles)
or [glamor (javascript)](https://github.com/threepointone/glamor).

## Inline Styles

If you are looking for something that mildly helps you detect errors, such as bad styling attribute names, you can use vdom's style for inline styles as described in that section. In addition, it also provides inline styles via

```scala
import ttg.react.vdom.style._
val style1 = new StyleAttr { display: "flex" }
val style2 = new StyleAttr { flexDirection: "column" }

// style2 takes precedence
val flexVertical = merge(style1, style2)
```

## Webpack CSS Processing (external CSS) and js.Dynamic

Assuming you are using webpack or CSS processing, CSS import produces a
js.Object whose keys are style names (sometimes mangled) and whose values are
styles. css-loader combined with style-loader then translates that into a
stylesheet that is dynamically inserted into the DOM.

To use that method, ensure webpack has an alias to the location of your CSS
files. I tend to put my CSS files right next to the component and hence my
webpack alias "Styles", for example, points to my source directory. Then you can
use:

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

You could use something other than js.Dynamic but that involves potentially alot
of typing.

## CSS in JS/Scala

This is becoming a much more preferred approach when the processing power of
your client allows to do this. While pre-processed and smartly bundled CSS can
give you load flexbility and performance benefits, the flexibility and sometimes
negligible impact on performance that current solutions provide make this more
popular.

There are some pure scala solutions available such as
[ScalaCSS](https://github.com/japgolly/scalacss) as well as
[udash](https://udash.io/) both of have CSS in Scala solutions, you have many
choices include [glamor (javascript)](https://github.com/threepointone/glamor)
and something very similar such as fabric's styling
([merge-styles](https://github.com/OfficeDev/office-ui-fabric-react/tree/master/packages/merge-styles)). fabric's
merge-styles claims to be slightly smaller on the payload than glamor.

You can use this style of style processing but if you do, you want to consider
defining your styles in JS and importing them as JS data. Then use a facade over
a CSS in JS library to add them once you have manipulated them as needed. You do
not gain type-safety with this approach but if you use typescript bindings in
typescript code, its probably good enough.

## @uifabric merge-styles

@uifabric/merge-styles is an independent package from microsoft. You can
do css-in-js styles as follows:

```scala
// other imports for react and fabric ...

import merge_styles._

object MyComponent {

  trait Props extends js.Object {
    var rootClassName: js.UndefOr[String] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    val children: String => ReactNode
  }

  val Name = "MyComponent"
  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props] { props =>
    React.useDebugValue(Name)
    val cn = getClassNames(new StyleProps {
      className = props.rootClassName
    }, props.styles)

    divWithClassname(
      cn.root,
      props.content(cn.content)
    )
  }

  @js.native
  trait ClassNames extends IClassNamesTag {
    val root: String = js.native
    val content: String = js.native
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var content: js.UndefOr[IStyle] = js.undefined
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
  }

  val getStyles = stylingFunction[StyleProps, Styles] { props =>
    new Styles {
      root = stylearray(
        "app-MyComponent-root",
        new IRawStyle {
          display = "flex"
          flexWrap = "nowrap"
          alignItems = "stretch"
          height = "calc(100% - 48px)"
        },
        props.className
      )
      content = stylearray("app-MyComponent-content", new IRawStyle {
        flex = "1 1 auto"
        overflowY = "auto"
      })
    }
  }

  val getClassNames: GetClassNamesFn[StyleProps, Styles, ClassNames] =
    (props, styles) => mergeStyleSets(concatStyleSetsWithProps[StyleProps, ClassNames](props, getStyles, styles))
}
```

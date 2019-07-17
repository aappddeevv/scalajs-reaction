---
layout: docs
title: Importing from JS
---

# Importing from JS

Importing a component javascript means you need to create a "Component" in
scala. There is a simple function call to do that for you. You still need to
perform the scala.js `@JSImport`.

```scala
// import a react component exported as part of a namespace export
// perhaps mark the import private
@js.native
@JSImport("some-lib", JSImport.Namespace)
object SomeLibNS {
  val YourComponent1: ReactJSComponent = js.native
  val YourComponent2: ReactJSComponent = js.native
}

object SomeLib {
  def YourComponent1(props: Props*)(children: ReactNode*) = 
     React.createElement(SomeLibNS.YourComponent1, props)(children:_*)
}
```

How you hook up attributes and children values to your component is up to
you. The definition in SomeLib is what you want it to be. In typescript, it is
common to define an interface that declares the allowed properties to be passed
in. The equivalent in scala.js are JS traits. You will want to make them
non-native JS traits so you can instantiate them.

## Managing Javascript Props

If you need to include additional HTML specific props, you can use the vdom
package. Let's say you want to be able to pass in any HTML element properties to
your component.

```scala
import ttg.react.vdom._

trait YourComponent1 extends HTMLAttributes[dom.html.Element] {
  // className already declared in HTMLAttributes.
}
```

## Import Typescript SFC

A stateless functional component is just a function. However, react can take a
function definition in its `React.createElement` API. Hence, you can import a
SFC and just "label" it a `ReactJSComponent`. Here's an example:

```typescript
import * as React from "react"
import { Address } from "./datamodel"
import cx = require("classnames")

export interface Props {
    className?: string | null
    address?: Address | null
}

/** Summarize address. */
export const AddressSummary: React.SFC<Address> = (props?: Props) => {
    props = props || {}
    const name = (props && props.address && props.address.name) || "<unnamed address>"
    return (
        <div className={cx("addressSummary", props.className)}>
            Typescript sourced: Address Summary: {name}
        </div>
    )
}
```

`Address` is defined in typescript language and represents an interface you need
to type in:

```typescript
// We have to define this on the scala.js and typescript side.
export interface Address {
    customeraddressid: string | null
    name?: string | null
    city?: string | null
    stateorprovince?: string | null
    postalcode?: string | null
}
```

The key thing to notice is that the SFC takes a js object with two optional
parameters. Let's import it into scala.js

```scala
import scala.scalajs.js
import js.annotation.JSImport
import ttg.react._
import ttg.react.elements._
import js.Dynamic.literal
import js.JSConverters._

@js.native
@JSImport("JSExamples/AddressSummary", JSImport.Namespace)
object AddressSummaryNS extends js.Object {
  val AddressSummary: ReactJSComponent = js.native
}

// model null explicitly or could use syntax extensions
// such as `.toNonNullOption`.
trait Props extends js.Object {
   var className: js.UndefOr[String | Null]
   var address: js.UndefOr[Address | Null]
}

trait Address extends js.Object {
   // ...
}

object AddressSummary {
  def apply(props: Props) = React.createElement0(AddressSummaryNS.AddressSummary, props)
}
```

The `0` indicates that `React.createElement` takes no children and is provided
as API so you avoid appending a useless `()`.

## Typescript

If you have typescript definitions you may want to try to convert them to a
native JS trait. There are converters you can try:

* [definitely typed](https://github.com/DefinitelyTyped/DefinitelyTyped)
* [scala-js-ts-importer](https://github.com/sjrd/scala-js-ts-importer)

You may find that for complicated libraries the conversion is not accurate and
you still need hand editing or that the conversion fails.


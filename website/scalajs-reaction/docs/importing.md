---
id: importing
title: Importing from JS to Scala
---

Importing a component javascript means you need to create a "Component" in
scala.js through `@JSImport`.

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
  def YourComponent1(props: Props)(children: ReactNode*) = 
     createElement(SomeLibNS.YourComponent1, props)(children:_*)
}
```

You can think of this as a template, here's a more ergonomic way 
of doing the above:

```scala
object YourComponent1 {
  @js.native
  @JSImport("some-lib", "YourComponent1")
  object JS extends ReactJsComponent

  trait Props extends js.Object {
    var param1: js.UndefOr[String] = js.undefined
  }
  
  def apply(props: Props)(children: ReactNode*) = 
    createElement(JS, props)(children:_*)
}
```

Since this is an imported component, the children do not need to
be in the Props trait directly.

How you hook up attributes and children values to your component is up to
you. In typescript, it is
common to define an interface that declares the allowed properties to be passed
in. The equivalent in scala.js are JS traits. You will want to make them
non-native JS traits so you can instantiate them.

## Managing Javascript Props

If you need to include additional HTML specific props, you can use the vdom
package. Let's say you want to be able to pass in any HTML element properties to
your component.

```scala
import react.vdom._

trait Props extends HTMLAttributes[dom.html.Element] {
  // className already declared in HTMLAttributes.
}
```

## Import Typescript SFC

A functional component is just a function. However, react can take a
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
    city?: string
    stateorprovince?: string | null
    postalcode?: string | null
}
```

The key thing to notice is that the SFC takes a js object with two optional
parameters. Let's import it into scala.js

```scala
import scala.scalajs.js
import js.annotation.JSImport
import react._
import js.Dynamic.literal
import js.JSConverters._

// use non-native JS trait so you can create an Address in scala.js
trait Address extends js.Object {
  val customeraddressid: js.UndefOr[String|Null]
  var name: js.UndefOr[String|Null] = js.undefined
  var city: js.UndefOr[String] = js.undefined
  ...
}


object AddressSummary {

  @js.native
  @JSImport("JSExamples", "AddressSummary")
  object JS extends ReactJsComponent

  trait Props extends js.Object {
   var className: js.UndefOr[String | Null]
   var address: js.UndefOr[Address | Null]
  }

  def apply(props: Props) = 
    createElement0(AddressSummaryNS.AddressSummary, props)
}
```

The `0` indicates that `createElement` takes no children and is provided
as API so you avoid appending a useless `()`. There are no children
to worry about so the children parameter list is dropped.

## Typescript

If you have typescript definitions you may want to try to convert them to a
native JS trait. There are converters you can try:

* [definitely typed](https://github.com/DefinitelyTyped/DefinitelyTyped)
* [scala-js-ts-importer](https://github.com/sjrd/scala-js-ts-importer)

You may find that for complicated libraries the conversion is not accurate and
you still need hand editing or that the conversion fails.


---
layout: docs
title: Data Interop
---
# Data Interop
Since there is an emphasis on interop with javascript reactjs components, you need to also interop with javascript data. Scala.js offers `js.UndefOr` to interop with fields in data structures that do not exist. Of course, in javascript a value can be undefined or null and sometimes they mean the same thing and sometimes not. Also, scala itself offers the ability to assign `null` (subclass of `Null`) to most values e.g. a String can be a value ("george"), empty ("") or null.

The key message below is that even with a typescript description to guide you, you need to be defensive since null and undefined are often used interchangeably in javascript since its so easy to do `if(a) ...` and if a is null or undefined, it will evaluate to "false".

## JS -> Scala
Given that we also use native and non-native JS traits to describe the shape of data, we may wonder how to handle data from web apis, for example in the structure:

```typescript
export interface Foo { 
    field1?: string
    field2?: string | null
}
```

field1 can be undefined or a string and field2 can be a string value, undefined or null. Depending on the nature of the data source, you may encounter alot of field1 or field2 on some combination of that. For example, field1 could represent a value that is non-nullable in the source database but may not have been requested in a fetch and may not be present. field2 could represent a nullable field in the source database that was not requested or was requested and was null.

The question is how to best model these in scala so that we do not have to write an object conversion for each data structure. There was talk at one time to create a structure like UndefOr for nulls, `NullOr` however when you use a union type with null (whose type is Null), strange things can happen in scala's type system, such as collapsing a `T|Null` to just T since a T can be null to begin with.

If you know the data is either there or not there, then UndefOr is fine:

```scala
trait Foo extends js.Object {
  var field: js.UndefOr[String] // var so mutable, use val for immutable
}
```

However, if field can be null, undefined or a value what should we use? Consider:

```scala
trait Foo extends js.Object {
  var field1: js.UndefOr[String]
}
```

Here, the value may not be present but it could be null, which is *different* then not being present. We could test:

```scala
aFoo.field == null
// or
aFoo.orNull == null
```

which returns true or false depending on whether the value is null. `UndefOr` has its own pimp which returns null if UndefOr is undefined, but not if its null:

```scala
val x: jsUndefOr[String] = ...
val test: String = x.orNull // test could be a string or null
```

What we generally need when obtaining data from javascript is something that recognizes the idioms that if a value is undefined or null, then its "false" or None, not just if its' undefined. UndefOr's `isEmpty` function just checks if its undefined, so its not quite right for our needs. What we need is to get to a scala Option (or something like that) when the value is null *or* undefined.

scalajs-react provides a pimp:
```scala
final case class JsUndefOrOps[A](a: UndefOr[A]) {
  def isNull  = a == null
  def isEmpty = isNull || !a.isDefined
}
```

which can be implicitly used by importing the syntax so you can test:

```scala
val aBoolean = aFoo.field1.isNull // is it just null?
val aBoolean2 = afoo.field1.isEmpty // is it null or undefined
```

So we can test for null fairly easily given the `UndefOr` type definition. But we want to manipulate these things with some more functional syntax. For example, we would like an Option-based variant that is None if `isEmpty==true` and the value otherwise. Using `UndefOr.toOption` actually may wrap a "null" value and produce a `Some(null)` instead of None. So clearly, just using `.toOption` is not a good answer. So at the expense of a little boolean check, perhaps we could do something more convenient. Let's state what we want, an Option that reflects both UndefOr and possible underlying null value:

```scala
final case class JsUndefOrOps[A](a: UndefOr[A]) {
  @inline def toNonNullOption = if(a.isEmpty) None else a.toOption
}
```

So now we can safely convert a UndefOr to an Option and have it be "null" if it is null or undefined and a Some with a non-null value otherwise. The cost you pay is a small conversion charge. So in general, it's ok to model your data us UndefOr:

```scala
 val x: Option[String] = aFoo.field1.toNonNullOption
```

Also, if you need to set data to null and the trait defines it as an UndefOr, just set it directly to null, just before you dispatch it javascript code:
```scala
  aFoo.field1 = null
```

You should note that just declaring the attribute as a type:

```
trait Foo extends js.Object {
  var field1: String
}
```

does not communicate that it can be null explicitly as in typescript. Perhaps, a `var field1: String|Null`. There's nothing wrong with that except scala already defines that String to be potentially null so why go through the hassle!

If you see a trait with just a plain String and you want an Option just wrap it just like in, `Option(field1)` since Option translates null to None in plain scala to begin with.

## Scala -> JS
The story is a bit easier on he Scala -> JS connection. You generally need this when stuffing data into a data structured destined for the js side of the world.

* Have (a: Option[T]): 
   * `a.orUndefined` yields an UndefOr which is the type that will probably be in most of your non-native JS traits
   * Use `a.getOrElse(null)` to obtain the value or null for some non-native JS traits
* Have (a: A): 
   * Wrap it ala `js.defined(a)` to get an `UndefOr[A]` to satisfy your trait's type (if you used the approach above).
   * If the value could be null, you can wrap it in Option(a) then convert it as described above to obtain an UndefOr. However if your JS target is `A|null` then there is nothing you need to do as scala and javascript both match the desired semantics.

## pimps
Check out [syntax.scala](https://github.com/aappddeevv/scalajs-react/blob/master/scalajs-react-core/src/main/scala/syntax.scala) for many pimps that you can apply to UndefOr values to help with data manipulation on the scala side. There's alot there and its easy to forget them unless they are in a cheatsheet.

## Don't Forget About a Structural Check
There is a little known converter in scala.js. If you have two traits that are not related but have overlapping fields, you may need to cast one to the other what is essentially a structural cast, check out [funky structural cast-kindof](https://www.scala-js.org/api/scalajs-library/latest/#scala.scalajs.js.package@use[A](x:A):scala.scalajs.js.Using[A]).

## TL;DR
Let's assume that typescript is not properly annotated and some undefineds and nulls get mixed up. 

If your typescript says:
```typescript
field1?: string
```
use this type of declaration in your trait:
```scala
var/val field1: js.UndefOr[String] = js.undefined
```
and use `field1 = null` if you want to set it to null or extract using `field1.toNonNullOption` to extract an option more safely than just `toOption` since the value could secretly be a null.

If typescript says:
```typescript
field1?: string|null
```
Do the same thing as above because the first example was already doubly safe.

If typescript says:
```typescript
field1: string|null
```
use 
```scala
var/val field1: String
```
and set it to null if you need to: `field1 = null`. If you want to have an option, use `Option(field1)`. You must use `Option` and not `Some` which would wrap a null value to `Some(null)`. To be extra safe use `toSafeOption` or the pimp from the react implicits `js.Any -> Option` via `toNonNullOption`. You may need to provide a type parameter explicitly if type inference is insufficient.

`toSafeOption` is defined in the react package object as follows which should automatically weed out the weird `Some(null)` and "its really undefined" problems:
```scala
  def toSafeOption[T <: js.Any](t: js.Any): Option[T] = {
    if (js.isUndefined(t)) None
    else Option(t.asInstanceOf[T])
  }

```
Let's hope the typescript interface descriptions are correct!

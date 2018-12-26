---
layout: docs
title: Data Validation
---
# Data Validation

A simple applicative-style data validation library has been provided. You can
use it for both JVM and JS environments os it useful for both the backend and
the fronted. It is agnostic to the data structures used to capture data
validation information so it does not impose its on "error" class or the use of
`Either` or cats `Validated`. Of course, such flexibility comes at a price. The
price to use it is that you must decide which data structure to use the provide
some typeclasses with the required behavior. Some pre-exisiting typeclasses are
available for a few common scala and cats data structures.

The following out of the box algebras are supported:

* cats ValidatedNec (Validated with a NonEmptyChain error channel)
* scala Option (no error message accumulation)
* scala Eithter (with a NonEmptyChain left)

Algebras with NonEmptyChain collect error messages when you validated in
parallel. Collecting error messages in parallel is sometimes desired, sometimes
not, depending on your application context. A single application can also have
different needs.

The overall algebra is called `ValidationAlgebra` and is composed of various
sub-algebras that include logic operations, string algebras and conversion
algebras.


## Usage

To use the data validation library, included it in your build:

```scala
libraryDependencies ++= Seq(
    "ttg" %%% "data-validation" % "latest.version"
)
```

Then setup the validation algebra you want to use:

```scala


````

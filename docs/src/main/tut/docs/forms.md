---
layout: docs
title: Forms
---
# Forms

A simple forms package has been created. It allows you to create forms and
managed the user interaction lifecycle. It is based on several different forms
package in ReasonReact and javascript.

The form library bakes in asynchronous data management by default through the
use of a `F` parameter which is constrained, using cats, to provide Async.

Very few assumptions are made around how forms are actually composed as there
are many different ways to code up a form.

Concerns that are abstracted out include:

* How the data object a form represents is represented.
* Validation of data.
* Form state.
* Form submission.

## Usage

To use the data validation library, included it in your build:

```scala
libraryDependencies ++= Seq(
    "ttg" %%% "scalajs-reaction-forms" % "latest.version"
)
```

## Creating Form Inputs

The library is un-opionated in that it does not force you to use any specific
form control components. There as a wide variety of form input control needs
that are too numerous to codify easy for all situations.

## Prior Art

This form library abstracts out form handling to ensure that it can be adapted
to most form situations similar to libraries like the following:

* [re-formality](https://github.com/alexfedoseev/re-formality)
* [reform](https://github.com/Astrocoders/reform)
* [formik](https://jaredpalmer.com/formik)
* [formsy-react](https://github.com/christianalfoni/formsy-react)
    * [fromsy-react-components](https://github.com/twisty/formsy-react-components)


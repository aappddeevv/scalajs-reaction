# helloworld

Simple example that shows some text as a label. The text is generated from components:

* Native in scala.js using `apply` and a parameter list.
* Native in scala.js using `make` and a non-native JS trait that is created from
  individual parameters.
* Imported component (from typescript) that takes this HelloWorld component as a
  child, hence, this component is exported to JS.


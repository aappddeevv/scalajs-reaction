Macros are hard work and we are really waiting for scala3 to implement
some really good macros.

For scala 2:

- response to how to find default values for superclasses that are not in the AST scope (defined elsewhere)
  - https://stackoverflow.com/questions/60806283/scala-macro-how-to-convert-a-methodsymbol-to-defdef-with-parameter-default-value
  - https://stackoverflow.com/questions/13767582/creating-a-method-definition-tree-from-a-method-symbol-and-a-body/13768595#13768595
- scala compiler that handles JSExport annotations, well made code
  - https://github.com/scala-js/scala-js/blob/a176fefe6a5570928480c1c8159972b6cba804d5/compiler/src/main/scala/org/scalajs/core/compiler/GenJSExports.scala#L47
- http://caryrobbins.com/dev/scala-macros-intro/
  - Good intro
- https://github.com/alexarchambault/data-class
- https://github.com/shadaj/slinky/blob/master/core/src/main/scala/slinky/core/annotations/react.scala
- https://scalac.io/def-hello-macro-world/
  - Good intro
- https://stackoverflow.com/questions/33279472/use-scala-macros-to-generate-methods
  - Short and sweet, not quite the right way to handle class/def annotation though.
- https://www.programcreek.com/scala/scala.reflect.macros.whitebox.Context
  - white macros
  - black: https://www.programcreek.com/scala/scala.reflect.macros.blackbox.Context
- https://www.47deg.com/blog/scala-macros-annotate-your-case-classes/
  - good intro
- Large set of macro helpers including processing arguments:
  - https://github.com/AVSystem/scala-commons/blob/master/commons-macros/src/main/scala/com/avsystem/commons/macros/MacroCommons.scala
  - They show how to convert a `Type` to a `Def` but its super hard and cannot be replicated
  - Why is this so hard, its a critical need. See `import compat._` below.
- How to cheat to process arguments:
  - https://stackoverflow.com/questions/32631372/getting-parameters-from-scala-macro-annotation/42961043#42961043
  - https://stackoverflow.com/questions/37891855/macro-annotation-with-default-arguments
- Getting superclass information
  - https://stackoverflow.com/questions/34023763/annotation-macro-that-rewrites-and-impls-a-trait-generics-not-processed-correct
- https://stackoverflow.com/questions/32619822/scala-macros-type-or-symbol-lifted
- https://meta.plasm.us/posts/2013/08/30/horrible-code/
  - shows how to process a MethodSymbol to create a def
- posts explaining how hard it is to go from a MethodSymbol to a DefDef
  - https://stackoverflow.com/questions/13767582/creating-a-method-definition-tree-from-a-method-symbol-and-a-body
    - Uses MethodSymbol => DefDefs but uses some older API that needs `import compat._`
  - https://github.com/scalamacros/kepler/blob/0acb8a30c379f268e8a3e1340504530493a1a1dc/src/reflect/scala/reflect/internal/Trees.scala#L975
    - Appears that `import compat._` helps with converting `List[TypeSymbol]` to `List[TypeDef]` via `TypeDef(_)`
    - Why was it removed?
- nice little macro test to convert case class to a string and back
  - https://stackoverflow.com/questions/19544756/scala-macros-accessing-members-with-quasiquotes
- scala compiler test involving macros
  - https://github.com/scala/scala/blob/d4a9eaa0704ef0bcc09ac60993254cedece08e31/test/files/run/macro-whitebox-fundep-materialization/Macros_1.scala
- simple macro example:
  - https://gist.github.com/travisbrown/4234441
- Can you get default values from a Symbol (we can get it from a tree, but don't have a tree always):
  - https://github.com/scaldi/scaldi/blob/master/src/main/scala/scaldi/util/ReflectionHelper.scala
  - The bottom answer is that it looks really hard to do that.

* How to call a method using MethodSymbol, corresponds to the java approach but for scala's Type system versus Class
  - https://issue.life/questions/38472058

Code generator style, but generates from graphql-like syntax:

- https://github.com/sbt/contraband

Useful API links:

- https://www.scala-lang.org/api/current/scala-reflect/scala/reflect/macros/whitebox/Context.html
- https://www.scala-lang.org/api/current/scala-reflect/scala/reflect/api/Trees$Modifiers.html
- https://docs.scala-lang.org/overviews/quasiquotes/syntax-summary.html

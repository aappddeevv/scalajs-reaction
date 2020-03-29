There are alot of sub-packages so based on the docs, run:

```
npm i apollo-boost graphql react-apollo -S
```

The namespaces are crazy complicated in the apollo packages but those three seem to 
bundle what we need for react into it.

In general, the types in apollo cannot be precisely typed without undue burden
on the programmer. You should consider them typed, but not fully type-safe.
In order to make them fully type-safe and slighly ergonomic, we would need
to instantiate a large number of types at once across multiple modules.

